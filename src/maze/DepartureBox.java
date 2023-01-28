package maze;

import java.awt.Color;

/**
 * Classe représentant une case de départ du labyrinthe
 * 
 * @author Telo PHILIPPE
 *
 */
public class DepartureBox extends MazeBox {

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
