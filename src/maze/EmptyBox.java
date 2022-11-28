package maze;


/**
 * Classe représentant une case vide du labyrinthe
 * 
 * @author Telo PHILIPPE
 *
 */
public class EmptyBox extends MazeBox {

	public EmptyBox(int i, int j, Maze maze) {
		super(i, j, maze);
		this.chara = ' ';
	}

}
