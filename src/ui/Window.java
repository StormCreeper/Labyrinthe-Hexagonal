package ui;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import maze.Maze;
import ui.panels.WindowPanel;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1157307886576199547L;
	
	private Maze maze;

	public Window(Maze maze) throws HeadlessException {
		super("Labyrinthe Hexagonal");
		
		this.maze = maze;
		
		setContentPane(new WindowPanel(this));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}

}
