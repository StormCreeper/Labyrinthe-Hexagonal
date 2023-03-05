package maze.generator;

import java.util.ArrayList;

import graph.Vertex;
import maze.Maze;
import maze.MazeBox;

import java.util.Random;

public class Generator extends Maze {
	
	Random random = new Random();
	MazeBox departure;
	MazeBox arrival;
	
	boolean converted = false;

	public Generator(Maze parent) {
		super(parent.getWidth(), parent.getHeight());
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				boxes[i][j] = new GeneratorBox(i, j, this);
			}
		}

		departure = (MazeBox) parent.getDeparture();
		arrival = (MazeBox) parent.getArrival();
		
		departure = MazeBox.createMazeBox(departure.getI(), departure.getJ(), MazeBox.departureChara, this);
		arrival = MazeBox.createMazeBox(arrival.getI(), arrival.getJ(), MazeBox.arrivalChara, this);
	}
	
	public Generator Generate() {
		setBox(0, 0, 3);
		GeneratorBox next = selectFromDepth(1, false);
		while(next != null) {
			boolean cont = true;
			while(cont) {
				cont = !randomWalk(next.getI(), next.getJ());
			}
			next = selectFromDepth(1, false);
		}
		return this;
	}


	/**
	 * randomWalk() fait un chemin aléatoire partant d'un point, en évitant de se "frotter" à lui même.
	 * @param i première coordonnée du point de départ.
	 * @param j deuxième coordonnée du point de départ.
	 * @return true s'il a réussi à rejoindre une partie déjà construite du labyrinthe.
	 */
	private boolean randomWalk(int i, int j) {
		resetTmp();
		GeneratorBox current = (GeneratorBox) boxes[i][j];
		for(int k=0; k<1000; k++) { // Nombre max abitraire mais normalement jamais atteint
			ArrayList<Vertex> successors = getSuccessors(current);
			ArrayList<GeneratorBox> candidates = new ArrayList<>();
			for(Vertex v : successors) {
				GeneratorBox succBox = (GeneratorBox) v;
				if(succBox.tmpDepth <= 1 || (succBox.depth == 2 && !succBox.isTemp)) candidates.add(succBox);
			}
			
			setTmpBox(current.getI(), current.getJ(), 3);
			if(candidates.size() == 0) break;
			current = candidates.get(random.nextInt(candidates.size()));
			if(current.depth == 2) {
				setTmpBox(current.getI(), current.getJ(), 3);
				updateTmp();
				return true;
			}
		}
		return false;
		//updateTmp();
	}
	
	/**
	 * Met à jour récursivement la profondeur d'une case, qui signifie la distance de cette case à un chemin.
	 * @param i de la case
	 * @param j de la case
	 * @param depth la profondeur voulue
	 */
	private void setBox(int i, int j, int depth) {
		GeneratorBox box = (GeneratorBox) boxes[i][j];
		if(box.depth >= depth) return;
		
		box.depth = depth;
		
		if(depth == 0) return;
		
		ArrayList<Vertex> successors = getSuccessors(box);
		for(Vertex v : successors) {
			GeneratorBox succBox = (GeneratorBox) v;
			setBox(succBox.getI(), succBox.getJ(), depth - 1);
		}
	}
	/**
	 * Met à jour récursivement la profondeur temporaire d'une case, qui signifie la distance de cette case à un chemin.
	 * @param i de la case
	 * @param j de la case
	 * @param depth la profondeur temporaire voulue
	 */
	private void setTmpBox(int i, int j, int depth) {
		GeneratorBox box = (GeneratorBox) boxes[i][j];
		if(box.tmpDepth >= depth) return;
		
		box.tmpDepth = depth;
		box.isTemp = true;
		
		if(depth == 0) return;
		
		ArrayList<Vertex> successors = getSuccessors(box);
		for(Vertex v : successors) {
			GeneratorBox succBox = (GeneratorBox) v;
			setTmpBox(succBox.getI(), succBox.getJ(), depth - 1);
		}
	}
	
	/**
	 * Copie la profondaire temporaire dans la profondeur actuelle
	 */
	private void updateTmp() {
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				GeneratorBox box = (GeneratorBox)boxes[i][j];
				box.depth = box.tmpDepth;
				box.isTemp = false;
			}
		}
	}
	
	/**
	 * Copie la profondaire actuelle dans la profondeur temporaire
	 */
	private void resetTmp() {
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				GeneratorBox box = (GeneratorBox)boxes[i][j];
				box.tmpDepth = box.depth;
				box.isTemp = false;
			}
		}
	}
	
	/**
	 * Renvoie une case qui a la porfondeur voulue, en choisissant la profondeur actuelle ou temporaire.
	 * @param depth la profondeur voulue.
	 * @param tmp si on doit considérer la profondeur temporaire ou actuelle.
	 * @return la case sélectionnée.
	 */
	private GeneratorBox selectFromDepth(int depth, boolean tmp) {
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				GeneratorBox box = (GeneratorBox) getBox(i, j);
				if(box.tmpDepth == depth && tmp || box.depth == depth && !tmp) return box;
			}
		}
		
		return null;
	}
	
	/**
	 * Fonction qui convertit le labyrinthe générateur en labyrinthe fini, et renvoie le résultat.
	 * @return
	 */
	public Maze convertToMaze() {
		for(int i = 0; i<width; i++) {
			for(int j = 0; j<height; j++) {
				char chara = MazeBox.wallChara;
				
				if(((GeneratorBox)(boxes[i][j])).depth == 3) chara = MazeBox.emptyChara;
				if(i==departure.getI() && j==departure.getJ()) chara = MazeBox.departureChara;
				if(i==arrival.getI() && j==arrival.getJ()) chara = MazeBox.arrivalChara;
				
				boxes[i][j] = MazeBox.createMazeBox(i, j, chara, this);
			}
		}
		converted = true;
		return this;
	}
	
	@Override
	public Vertex getArrival() {
		if(converted) return super.getArrival();
		return boxes[arrival.getI()][arrival.getJ()];
	}

	@Override
	public Vertex getDeparture() {
		if(converted) return super.getDeparture();
		return boxes[departure.getI()][departure.getJ()];
	}
	
}
