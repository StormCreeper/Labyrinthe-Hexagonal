package ui.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import ui.Window;

public class MazePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038870474473344877L;

	public MazePanel(Window window) {
		setPreferredSize(new Dimension(600, 600));
		setBackground(Color.green);
	}

}
