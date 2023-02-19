package maze;

import java.awt.Color;

import graph.Vertex;


/**
 * Classe abstraite représentant une case du labyrinthe.
 * Elle implémente l'interface Vertex pour pouvoir être utilisée dans l'agorithme de Dijkstra.
 * @author Telo PHILIPPE
 *
 */
public abstract class MazeBox implements Vertex {
	
	public static final char wallChara = 'O';
	public static final char emptyChara = ' ';
	public static final char departureChara = 'D';
	public static final char arrivalChara = 'A';
	public static final char invalidChara = 'X';
	
	// Référence au labyrinthe qui contient la case
	protected Maze maze;
	
	//Coordonnées de la case.
	protected int i;
	protected int j;

	public abstract char getChara();
	public abstract Color getColor();
	
	public static MazeBox createMazeBox(int i, int j, char chara, Maze maze) {
		switch(chara) {
		case wallChara:
			return new WallBox(i, j, maze);
		case emptyChara:
			return new EmptyBox(i, j, maze);
		case departureChara:
			return new DepartureBox(i, j, maze);
		case arrivalChara:
			return new ArrivalBox(i, j, maze);
		default:
			return new MazeBox(i, j, maze) {
				public Color getColor() { return Color.PINK; }
				public char getChara() { return invalidChara; }
			};
		}
	}
	
	public MazeBox(int i, int j, Maze maze) {
		this.i = i;
		this.j = j;
		this.maze = maze;
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
