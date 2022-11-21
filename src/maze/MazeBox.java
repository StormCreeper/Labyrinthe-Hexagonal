package maze;

import graph.Vertex;


/**
 * Classe abstraite représentant une case du labyrinthe.
 * Elle implémente l'interface Vertex pour pouvoir être utilisée dans l'agorithme de Dijkstra.
 * @author Telo PHILIPPE
 *
 */
public abstract class MazeBox implements Vertex {
	
	//Coordonnées de la case.
	protected int i;
	protected int j;
	
	public MazeBox(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public int getI() {
		return i;
	}
	public int getJ() {
		return j;
	}
	
	@Override
	public String getLabel() {

		return "(" + i + ", " + j + ")";
	}

}
