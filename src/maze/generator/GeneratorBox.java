package maze.generator;

import java.awt.Color;

import maze.Maze;
import maze.MazeBox;

public class GeneratorBox extends MazeBox {
	
	public int depth = 0;
	public int tmpDepth = 0;
	boolean isTemp = false;
	
	public GeneratorBox(int i, int j, Maze maze) {
		super(i, j, maze);
	}

	@Override
	public char getChara() {
		return Integer.toString(depth).charAt(0);
	}

	@Override
	public Color getColor() {
		return Color.pink;
	}

}
