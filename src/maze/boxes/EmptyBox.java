package maze.boxes;

import java.awt.Color;

import maze.Maze;
import maze.MazeBox;

/**
 * Classe représentant une case vide du labyrinthe
 * 
 * @author Telo PHILIPPE
 *
 */
public final class EmptyBox extends MazeBox {

	public EmptyBox(int i, int j, Maze maze) {
		super(i, j, maze);
	}

	@Override
	public char getChara() {
		return MazeBox.emptyChara;
	}
	
	@Override
	public Color getColor() {
		return new Color(i/(float)maze.getWidth(), j/(float)maze.getHeight(), 1.0f);
	}

}
