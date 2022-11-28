package maze;

/**
 * Classe représentant une case pleine du labyrinthe
 * 
 * @author Telo PHILIPPE
 *
 */
public class WallBox extends MazeBox {
	
	public WallBox(int i, int j, Maze maze) {
		super(i, j, maze);
	}

	@Override
	public char getChara() {
		return MazeBox.wallChara;
	}

}
