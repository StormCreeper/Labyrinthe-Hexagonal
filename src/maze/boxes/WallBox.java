package maze.boxes;

import java.awt.Color;

import maze.Maze;
import maze.MazeBox;

/**
 * Classe représentant une case pleine du labyrinthe
 * 
 * @author Telo PHILIPPE
 *
 */
public final class WallBox extends MazeBox {
	
	public WallBox(int i, int j, Maze maze) {
		super(i, j, maze);
	}

	@Override
	public char getChara() {
		return MazeBox.wallChara;
	}

	@Override
	public Color getColor() {
		return Color.BLACK;
	}
	
	@Override
	public boolean isWall() {
		return true;
	}

}
