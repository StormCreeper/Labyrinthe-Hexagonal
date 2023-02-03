package ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ui.MazeWindow;

public class WindowPanel extends JPanel {

	private static final long serialVersionUID = -4761522094862460317L;
	
	private MazePanel mazePanel;
	private ToolsPanel toolsPanel;
	@SuppressWarnings("unused")
	private ConsolePanel consolePanel;

	public WindowPanel(MazeWindow window) {
		setLayout(new BorderLayout());
		
		add(mazePanel = new MazePanel(window), BorderLayout.CENTER);
		add(toolsPanel = new ToolsPanel(window), BorderLayout.EAST);
		add(consolePanel = new ConsolePanel(window), BorderLayout.SOUTH);
	}
	
	public void tick() {
		mazePanel.tick();
	}
	
	public ToolsPanel getToolsPanel() {
		return toolsPanel;
	}
	public MazePanel getMazePanel() {
		return mazePanel;
	}

}
