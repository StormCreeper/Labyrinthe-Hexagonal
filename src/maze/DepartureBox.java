package maze;

/**
 * Classe représentant une case de départ du labyrinthe
 * 
 * @author Telo PHILIPPE
 *
 */
public class DepartureBox extends MazeBox {

	public DepartureBox(int i, int j, Maze maze) {
		super(i, j, maze);
		this.chara = 'D';
	}

}
