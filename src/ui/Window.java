package ui;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import main.LabyrintheHexagonal;
import ui.panels.WindowPanel;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1157307886576199547L;
	
	private LabyrintheHexagonal laby;

	public Window(LabyrintheHexagonal laby) throws HeadlessException {
		super("Labyrinthe Hexagonal");
		
		this.laby = laby;
		
		setContentPane(new WindowPanel(this));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}
	
	public LabyrintheHexagonal getLaby() {
		return laby;
	}

}
