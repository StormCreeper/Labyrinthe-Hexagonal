package maze.generator;

import java.awt.Color;

import maze.Maze;
import maze.MazeBox;

public class GeneratorBox extends MazeBox {
	
	public int depth = 0;
	public int tmpDepth = 0;
	boolean isTemp = false;
	
	GeneratorBox parent;
	
	public GeneratorBox(int i, int j, Maze maze) {
		super(i, j, maze);
	}

	@Override
	public char getChara() {
		return Integer.toString(depth).charAt(0);
	}

	@Override
	public Color getColor() {
		if(isTemp) {
			if(tmpDepth == 3) return Color.green;
			if(tmpDepth == 2) return new Color(0, 150, 0);
			if(tmpDepth == 1) return new Color(0, 50, 0);
		} else {
			if(depth == 3) return Color.white;
			if(depth == 2) return new Color(150, 150, 150);
			if(depth == 1) return new Color(50, 50, 50);
		}
		return Color.black;
	}

}
