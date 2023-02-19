package ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ui.MazeWindow;

/**
 * Panel qui contient toute l'application, et place les éléments correctement.
 * 
 * @author telop
 * 
 */
public class WindowPanel extends JPanel {

	// Pour enlever le warning d'Eclipse
	private static final long serialVersionUID = -4761522094862460317L;
	
	private MazePanel mazePanel;

	public WindowPanel(MazeWindow window) {
		setLayout(new BorderLayout());
		
		add(mazePanel = new MazePanel(window), BorderLayout.CENTER);
		add(new ToolsPanel(window), BorderLayout.EAST);
		add(new ConsolePanel(window), BorderLayout.SOUTH);
	}
	
	/*
	 * Progage le signal de tick
	 */
	public void tick() {
		mazePanel.tick();
		ConsolePanel.Tick();
	}
}
