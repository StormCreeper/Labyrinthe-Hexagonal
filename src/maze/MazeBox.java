package maze;

import graph.Vertex;


/**
 * Classe abstraite représentant une case du labyrinthe.
 * Elle implémente l'interface Vertex pour pouvoir être utilisée dans l'agorithme de Dijkstra.
 * @author Telo PHILIPPE
 *
 */
public abstract class MazeBox implements Vertex {
	
	// Référence au labyrinthe qui contient la case
	protected Maze maze;
	
	//Coordonnées de la case.
	protected int i;
	protected int j;
	protected char chara;
	
	public MazeBox(int i, int j, Maze maze) {
		this.i = i;
		this.j = j;
		this.maze = maze;
		this.chara = '@';
	}
	
	public int getI() {
		return i;
	}
	public int getJ() {
		return j;
	}
	public char getChara() {
		return chara;
	}
	
	@Override
	public String getLabel() {

		return "(" + i + ", " + j + ")";
	}

}
