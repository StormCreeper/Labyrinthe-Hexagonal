package maze.boxes;

import java.awt.Color;

import maze.Maze;
import maze.MazeBox;

/**
 * Classe représentant une case d'arrivée du labyrinthe
 * 
 * @author Telo PHILIPPE
 *
 */
public final class ArrivalBox extends MazeBox {

	public ArrivalBox(int i, int j, Maze maze) {
		super(i, j, maze);
	}

	@Override
	public char getChara() {
		return MazeBox.arrivalChara;
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}
	
	
}
