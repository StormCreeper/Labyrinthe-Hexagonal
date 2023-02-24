package ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;
import graph.Vertex;
import main.LabyrintheHexagonal;
import maze.Maze;
import maze.MazeBox;
import ui.MazeWindow;

/**
 * Panel qui contient le dessin du labyrinthe, et qui gère les évènements
 * d'entrée utilisateur : sélection, modification des cellules etc.
 * 
 * @author telop
 */
public final class MazePanel extends JPanel implements MouseMotionListener, MouseListener {

	// Pour enlever des warnings
	private static final long serialVersionUID = 3038870474473344877L;

	private MazeWindow window;

	// Variables d'états pour la sélection et modification
	private MazeBox selected = null;
	private MazeBox lastSelected = null;
	private char tool = MazeBox.wallChara;

	// Constantes pour le dessin
	private final double hexagonRatio = (float) Math.cos(Math.PI * (1 / 3. - 1 / 2.));
	// Espace vide entre le bord du labyrinthe et le bord de la fenêtre
	private final int padding = 10;

	// Les coordonnées du coin haut-gauche calculés du labyrinthe pour l'affichage
	private double boundsX = 0;
	private double boundsY = 0;

	// La taille du labyrinthe calculée pour l'affichage
	private double boundsW = 0;
	private double boundsH = 0;

	private int selX = 0;
	private int selY = 0;

	// Rayon d'un hexagone
	private double radius = 0;

	public MazePanel(MazeWindow window) {
		setPreferredSize(new Dimension(600, 600));

		addMouseMotionListener(this);
		addMouseListener(this);

		this.window = window;
	}

	/**
	 * Calcule le rayon maximal des hexagones tel que le dessin du labyrinthe ne
	 * dépasse pas de la zone de dessin, en prenant en compte le padding. Cette
	 * fonction calcule aussi la largeur, hauteur, ainsi que les coordonnées du coin
	 * haut-gauche du labyrinthe quand il est affiché avec ce rayon.
	 */
	private void recalculateMazeBounds() {
		Maze maze = window.getLaby().getMaze();

		int mazeW = maze.getWidth();
		int mazeH = maze.getHeight();

		// Taille de la zone de dessin
		int drawW = (getWidth() - 2 * padding);
		int drawH = (getHeight() - 2 * padding);

		// Le rayon tel que le labyrinthe ait une largeur d'exactement drawW.
		radius = drawW / ((mazeW + 0.5) * 2. * hexagonRatio);

		// La hauteur qu'aurait le labyrinthe avec ce rayon.
		double tmpH = radius * (1.5 * mazeH + 0.5);

		if (tmpH > drawH) {
			// Le rayon tel que le labyrinthe ait une hauteur d'exactement drawH.
			radius = drawH / (1.5 * mazeH + 0.5);
		}

		// A partir de ce rayon optimal, on peut calculer le rectangle qui englobe le
		// labyrinthe.
		boundsW = radius * hexagonRatio * (2 * mazeW + 1);
		boundsH = radius * (1.5 * mazeH + 0.5);

		boundsX = padding + (drawW - boundsW) / 2.;
		boundsY = padding + (drawH - boundsH) / 2.;
	}

	/**
	 * Crée un hexagone de type java.awt.Polygon, de rayon radius, associé aux
	 * coordonnéees i et j.
	 * 
	 * @param i abcisse de l'hexagone dans le tableau.
	 * @param j ordonnée de l'hexagone dans le tableau.
	 * @return un Polygon représentant l'hexagone.
	 */
	private Polygon getHexa(int i, int j) {
		double x = boundsX + radius * (2 * i + 1 + j % 2) * hexagonRatio;
		double y = boundsY + radius * (1.5 * j + 1);

		int[] xpoints = new int[6];
		int[] ypoints = new int[6];

		for (int k = 0; k < 6; k++) {
			xpoints[k] = (int) Math.round(x + radius * Math.cos(2 * k * Math.PI / 6. + Math.PI / 2.));
			ypoints[k] = (int) Math.round(y + radius * Math.sin(2 * k * Math.PI / 6. + Math.PI / 2.));
		}

		return new Polygon(xpoints, ypoints, 6);
	}

	/**
	 * Récupère de manière sécurisée la couleur d'une case (rose signifie une
	 * erreur, même si ce n'est pas sensé arriver.
	 * 
	 * @param i abcisse de la case dans le tableau.
	 * @param j ordonnée de la case dans le tableau.
	 * @return la couleur de la case.
	 */
	private Color getCellColor(int i, int j) {
		MazeBox box = window.getLaby().getMaze().getBox(i, j);
		if (box == null)
			return Color.PINK; // Du rose correspond à une erreur.
		Color color = box.getColor();
		if (color == null)
			return Color.PINK;

		return color;
	}

	/**
	 * Fonction utilitaire permettant d'interpoler linéairement entre deux couleurs.
	 * 
	 * @param c1 première couleur.
	 * @param c2 deuxième couleur.
	 * @param t  paramètre controlant la proportion de c2 dans la couleur
	 *           résultante.
	 * @return
	 */
	private Color mixColor(Color c1, Color c2, float t) {
		return new Color((int) (c1.getRed() * (1 - t) + c2.getRed() * t),
				(int) (c1.getGreen() * (1 - t) + c2.getGreen() * t), (int) (c1.getBlue() * (1 - t) + c2.getBlue() * t));
	}

	/**
	 * Dessine une case surlignée, en mélangeant sa couleur avec une couleur de
	 * référence.
	 * 
	 * @param box   La case à dessiner.
	 * @param color La couleur de surlignage.
	 * @param g     L'object Graphics sur lequel dessiner.
	 */
	private void highlight(MazeBox box, Color color, Graphics g) {
		if (box != null) {
			int i = box.getI();
			int j = box.getJ();

			Color baseColor = getCellColor(i, j);
			Color finalColor = mixColor(baseColor, color, .4f);

			g.setColor(finalColor);

			Polygon hexa = getHexa(i, j);
			g.fillPolygon(hexa);
			g.setColor(Color.black);
			g.drawPolygon(hexa);
		}
	}

	/**
	 * Dessin du labyrinthe hexagonal.
	 * 
	 * @param g
	 */
	private void drawMaze(Graphics g) {
		// On recalcule à chaque fois pour prendre en compte un éventuel
		// redimensionnement de la fenêtre de dessin
		recalculateMazeBounds();

		Maze maze = window.getLaby().getMaze();

		for (int i = 0; i < maze.getWidth(); i++) {
			for (int j = 0; j < maze.getHeight(); j++) {
				g.setColor(getCellColor(i, j));
				g.fillPolygon(getHexa(i, j));
				g.setColor(Color.black);
				g.drawPolygon(getHexa(i, j));
			}
		}
	}

	/**
	 * Dessin du chemin résolu s'il existe.
	 * 
	 * @param g
	 */
	private void drawPath(Graphics g) {
		// Affichage du chemin trouvé par l'algorithme s'il existe
		List<Vertex> path = window.getLaby().path;
		if (path != null) {
			for (int i = 0; i < path.size() && i < cellIndex; i++) {
				MazeBox m = (MazeBox) path.get(i);

				if (m.getChara() != MazeBox.emptyChara)
					continue;

				g.setColor(Color.YELLOW);
				g.fillPolygon(getHexa(m.getI(), m.getJ()));
			}
		}
	}

	/**
	 * Dessin du curseur de selection.
	 * 
	 * @param g
	 */
	private void drawCursor(Graphics g) {
		// Affichage du curseur
		highlight(selected, Color.white, g);
		highlight(lastSelected, Color.red, g);
	}

	/**
	 * Dessin des éléments de debug (boite englobante du labyrinthe) et curseur de
	 * sélection.
	 * 
	 * @param g
	 */
	private void drawDebug(Graphics g) {
		g.setColor(Color.black);
		g.drawRect((int) Math.round(boundsX), (int) Math.round(boundsY), (int) Math.round(boundsW),
				(int) Math.round(boundsH));

		g.setColor(Color.red);
		g.drawRect(padding, padding, getWidth() - padding * 2, getHeight() - padding * 2);

		// Highlight des cases utilisées pour le test de sélection.
		Maze maze = window.getLaby().getMaze();

		for (int i = Math.max(selX - 1, 0); i <= Math.min(selX + 1, maze.getWidth() - 1); i++) {
			for (int j = Math.max(selY - 1, 0); j <= Math.min(selY + 1, maze.getHeight() - 1); j++) {
				highlight(maze.getBox(i, j), Color.GREEN, g);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawMaze(g);
		drawPath(g);
		drawCursor(g);

		// Affichage des éléments de débug

		if (LabyrintheHexagonal.Debug)
			drawDebug(g);
	}

	/**
	 * Modifie la case sous le curseur avec l'outil actuellement utilisé.
	 * 
	 * @return si la modification a bien eu lieu
	 */
	private boolean changeCell() {
		if (selected == null)
			return false;

		int i = selected.getI();
		int j = selected.getJ();

		return changeCell(i, j, tool);
	}

	/**
	 * Modifie la case (i, j) avec l'outil tool.
	 * 
	 * @param i
	 * @param j
	 * @param tool
	 * @return si la modification a bien eu lieu
	 */
	private boolean changeCell(int i, int j, char tool) {
		if (tool == MazeBox.invalidChara)
			return false;

		window.getLaby().getMaze().setCell(i, j, tool);
		window.getLaby().path = null;

		return true;
	}

	/**
	 * Cherche parmi toutes les cases une intersection avec la souris, pour
	 * déterminer la case sélectionnée
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	private void selectCell(int mouseX, int mouseY) {
		Maze maze = window.getLaby().getMaze();
		selected = null;

		// Déterminer la case approximative correspondant à la souris
		selX = (int) ((mouseX - boundsX) / boundsW * maze.getWidth());
		selY = (int) ((mouseY - boundsY) / boundsH * maze.getHeight());

		// On fait ensuite un test de collision précis autour de cette position
		// approximative

		for (int i = Math.max(selX - 1, 0); i <= Math.min(selX + 1, maze.getWidth() - 1); i++) {
			for (int j = Math.max(selY - 1, 0); j <= Math.min(selY + 1, maze.getHeight() - 1); j++) {
				if (getHexa(i, j).contains(new Point(mouseX, mouseY)))
					selected = maze.boxes[i][j];
			}
		}

		// On limite ainsi le nombre de tests de collision entre le pointeur et les
		// hexagones
		// (Ceci a permi de supprimer un ralentissement observé lorsque le curseur
		// bougeait sur une grille très grande)

		// Ancienne méthode :
		/*
		 * for (int i = 0; i < maze.getWidth(); i++) { for (int j = 0; j <
		 * maze.getHeight(); j++) { if (getHexa(i, j).contains(new Point(mouseX,
		 * mouseY))) selected = maze.boxes[i][j]; } }
		 */
	}

	private int cellIndex = 0;

	/**
	 * Fonction qui est appelée toutes les 25 millisecondes, permettant d'animer
	 * l'affichage du chemin
	 */
	public void tick() {
		cellIndex++;
		if (window.getLaby().path == null)
			cellIndex = 0;
		repaint();
	}

// ------------------- Implémentation des méthodes d'interfaces -----------------------

	@Override
	public void mouseDragged(MouseEvent e) {
		selectCell(e.getX(), e.getY());
		if (tool != MazeBox.arrivalChara && tool != MazeBox.departureChara && selected != null
				&& selected.getChara() != MazeBox.arrivalChara && selected.getChara() != MazeBox.departureChara)
			changeCell();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		selectCell(e.getX(), e.getY());
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectCell(e.getX(), e.getY());
		if (selected == null)
			return;

		if (tool == MazeBox.arrivalChara || tool == MazeBox.departureChara) {
			if (selected.getChara() != MazeBox.arrivalChara && selected.getChara() != MazeBox.departureChara
					&& changeCell()) {
				changeCell(lastSelected.getI(), lastSelected.getJ(), MazeBox.emptyChara);
				tool = MazeBox.emptyChara;
				lastSelected = null;
			}
		} else {
			switch (selected.getChara()) {
			case MazeBox.arrivalChara:
			case MazeBox.departureChara:
				tool = selected.getChara();
				lastSelected = selected;
				break;
			case MazeBox.emptyChara:
				tool = MazeBox.wallChara;
				changeCell();
				break;
			case MazeBox.wallChara:
				tool = MazeBox.emptyChara;
				changeCell();
				break;
			}

		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
