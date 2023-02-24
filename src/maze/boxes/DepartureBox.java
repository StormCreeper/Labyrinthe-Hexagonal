package maze.boxes;

import java.awt.Color;

import maze.Maze;
import maze.MazeBox;

/**
 * Classe représentant une case de départ du labyrinthe
 * 
 * @author Telo PHILIPPE
 *
 */
public final class DepartureBox extends MazeBox {

	public DepartureBox(int i, int j, Maze maze) {
		super(i, j, maze);
	}

	@Override
	public char getChara() {
		return MazeBox.departureChara;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}

}
