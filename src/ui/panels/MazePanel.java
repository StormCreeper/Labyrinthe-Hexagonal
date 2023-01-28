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
import maze.Maze;
import maze.MazeBox;
import ui.MazeWindow;

public class MazePanel extends JPanel implements MouseMotionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038870474473344877L;

	private MazeWindow window;
	
	private MazeBox selected = null;
	private MazeBox lastSelected = null;
	private char tool = MazeBox.wallChara;
	
	private double hexagonRatio = (float) Math.cos(Math.PI *(1/3.-1/2.));
	private int padding = 10;
	
	public MazePanel(MazeWindow window) {
		setPreferredSize(new Dimension(600, 600));
		
		addMouseMotionListener(this);
		addMouseListener(this);
		
		this.window = window;
	}
	
	//Les coordonnées du coin haut-gauche calculés du labyrinthe pour l'affichage
	private double resultX = 0;
	private double resultY = 0;

	//La taille du labyrinthe calculée pour l'affichage 
	private double resultW = 0;
	private double resultH = 0;
	
	private double radius = 0;
	
	/**
	 * Calcule le rayon maximal de chaque hexagone tel que le labyrinthe total ne dépasse pas de la zone de dessin, en prenant en compte le padding.
	 * Cette fonction calcule aussi la largeur, hauteur, ainsi que les coordonnées du coin haut-gauche du labyrinthe quand il est affiché avec ce rayon.
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
		
		if(tmpH > drawH) {
			// Le rayon tel que le labyrinthe ait une hauteur d'exactement drawH.
			radius = drawH / (1.5 * mazeH + 0.5);
		}
		
		// A partir de ce rayon optimal, on peut calculer le rectangle qui englobe le labyrinthe.
		resultW = radius * hexagonRatio * (2 * mazeW + 1);
		resultH = radius * (1.5 * mazeH + 0.5);
		
		resultX = padding + (drawW - resultW) / 2.;
		resultY = padding + (drawH - resultH) / 2.;
	}
	
	/**
	 * Crée un hexagone de type java.awt.Polygon, de rayon radius, associé aux coordonnéees i et j.
	 * 
	 * @param i abcisse de l'hexagone.
	 * @param j ordonnée de l'hexagone.
	 * @return un Polygon représentant l'hexagone.
	 */
	private Polygon getHexa(int i, int j) {
		double x = resultX + radius * (2 * i + 1 + j % 2) * hexagonRatio;
		double y = resultY + radius * (1.5 * j + 1);
		
		int[] xpoints = new int[6];
		int[] ypoints = new int[6];
		
		for(int k=0; k<6; k++) {
			xpoints[k] = (int) Math.round(x + radius * Math.cos(2 * k * Math.PI / 6. + Math.PI / 2.));
			ypoints[k] = (int) Math.round(y + radius * Math.sin(2 * k * Math.PI / 6. + Math.PI / 2.));
		}
		
		return new Polygon(xpoints, ypoints, 6);
	}
	
	private Color getCellColor(int i, int j) {
		Maze maze = window.getLaby().getMaze();
		char c = maze.getBox(i, j);
		if(c == MazeBox.emptyChara) return new Color(i/(float)maze.getWidth(), j/(float)maze.getHeight(), 1.0f);
		if(c == MazeBox.arrivalChara) return Color.GREEN;
		if(c == MazeBox.departureChara) return Color.RED;
		return Color.BLACK;
	}
	
	/**
	 * Fonction utilitaire permettant d'interpoler linéairement entre deux couleurs.
	 * @param c1 première couleur.
	 * @param c2 deuxième couleur.
	 * @param t paramètre controlant la proportion de c2 dans la couleur résultante.
	 * @return
	 */
	private Color mixColor(Color c1, Color c2, float t) {
		return new Color(
			(int)(c1.getRed() * (1-t) + c2.getRed() * t),
			(int)(c1.getGreen() * (1-t) + c2.getGreen() * t),
			(int)(c1.getBlue() * (1-t) + c2.getBlue() * t)
		);
	}
	
	private void highlight(MazeBox box, Color color, Graphics g) {
		if(box != null) {
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
	
	@Override
	public final void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		
		Maze maze = window.getLaby().getMaze();
		
		int w = maze.getWidth();
		int h = maze.getHeight();
		
		recalculateMazeBounds();

		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				g.setColor(getCellColor(i, j));
				g.fillPolygon(getHexa(i, j));
			}
		}
		List<Vertex> path = window.getLaby().path;
		if(path != null) {
			for(int i = 0; i<path.size() && i < cellIndex; i++) {
				//for(Vertex v : window.getLaby().path) {
				MazeBox m = (MazeBox)path.get(i);
				
				if(m.getChara() != MazeBox.emptyChara) continue;
				
				g.setColor(Color.YELLOW);
				g.fillPolygon(getHexa(m.getI(), m.getJ()));
			}
		}
		
		highlight(selected, Color.white, g);
		highlight(lastSelected, Color.red, g);
		/*
		g.setColor(Color.black);
		g.drawRect((int)Math.round(resultX), (int)Math.round(resultY), (int)Math.round(resultW), (int)Math.round(resultH));

		g.setColor(Color.red);
		g.drawRect(padding, padding, getWidth() - padding * 2, getHeight() - padding * 2);
		*/
	}

	private boolean changeCell() {
		if(selected == null) return false;
		
		int i = selected.getI();
		int j = selected.getJ();
		
		return changeCell(i, j, tool);
	}
	
	private boolean changeCell(int i, int j, char tool) {
		if(tool == MazeBox.invalidChara) return false;

		window.getLaby().getMaze().setCell(i, j, tool);
		window.getLaby().path = null;
		
		return true;
	}
	
	private void selectCell(int mouseX, int mouseY) {
		Maze maze = window.getLaby().getMaze();
		selected = null;
		
		for(int i = 0; i<maze.getWidth(); i++) {
			for(int j=0; j<maze.getHeight(); j++) {
				if(getHexa(i, j).contains(new Point(mouseX, mouseY))) selected = maze.boxes[i][j];
			}
		}
	}
	
	private int cellIndex = 0;
	
	public void tick() {
		cellIndex++;
		if(window.getLaby().path == null) cellIndex = 0;
		repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		selectCell(e.getX(), e.getY());
		if(tool != MazeBox.arrivalChara && tool != MazeBox.departureChara
				&& selected != null
				&& selected.getChara() != MazeBox.arrivalChara
				&& selected.getChara() != MazeBox.departureChara)
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
		if(selected == null) return;
		
		if(tool == MazeBox.arrivalChara || tool == MazeBox.departureChara) {
			if(selected.getChara() != MazeBox.arrivalChara && selected.getChara() != MazeBox.departureChara && changeCell()) {
				changeCell(lastSelected.getI(), lastSelected.getJ(), MazeBox.emptyChara);
				tool = MazeBox.emptyChara;
				lastSelected = null;
			}
		} else {
		
			switch(selected.getChara()) {
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
		// TODO Auto-generated method stub
		
	}

}
