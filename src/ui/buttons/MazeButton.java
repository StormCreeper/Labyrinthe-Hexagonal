package ui.buttons;

import java.awt.Color;

import javax.swing.JButton;

import ui.MazeWindow;

public class MazeButton extends JButton {
	private static final long serialVersionUID = -8397203269460765L;
	
	public MazeWindow window;
	
	public MazeButton(MazeWindow window, String text) {
		super(text);
		
		this.window = window;
		
		setForeground(new Color(.1f, .1f, .1f));
		setBackground(new Color(.9f, .9f, .9f));
	}

}
