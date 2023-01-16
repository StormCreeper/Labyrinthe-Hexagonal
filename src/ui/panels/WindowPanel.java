package ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ui.Window;

public class WindowPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761522094862460317L;
	
	private MazePanel mp;

	public WindowPanel(Window window) {
		setLayout(new BorderLayout());
		
		add(mp = new MazePanel(window), BorderLayout.CENTER);
		add(new ToolsPanel(window), BorderLayout.EAST);
	}
	
	public void tick() {
		mp.tick();
	}

}
