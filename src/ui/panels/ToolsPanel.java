package ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import ui.MazeWindow;
import ui.buttons.MazeButton;

public class ToolsPanel extends JPanel {
	private static final long serialVersionUID = 4432571170375056548L;

	private MazeButton emptyButton;
	private MazeButton loadButton;
	private MazeButton saveButton;
	private MazeButton solveButton;
	
	public ToolsPanel(MazeWindow window) {
		
		setLayout(new GridLayout(4, 1));
		
		setPreferredSize(new Dimension(200, 600));
		setBackground(Color.red);
		
		add(emptyButton = new MazeButton(window, "Empty Maze"));
		add(loadButton = new MazeButton(window, "Load Maze"));
		add(saveButton = new MazeButton(window, "Save Maze"));
		add(solveButton = new MazeButton(window, "Solve Maze"));
		
		emptyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.showChooseSizeDialog();
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.showLoadDialog();
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.showSaveDialog();
			}
		});
		solveButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				window.getLaby().solve();
				if(window.getLaby().path == null) {
					ConsolePanel.instance.WriteMessage("No path found !", ConsolePanel.ERROR);
				}
				window.repaint();
			}
		});
	}
	
	
	
	

}
