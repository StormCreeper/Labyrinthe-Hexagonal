package ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import maze.generator.Generator;
import ui.MazeWindow;
import ui.buttons.MazeButton;


/**
 * Panel qui contient les boutons d'édition. Il utilise un gridlayout, et fait le lien entre les boutons et les fonctions du labyrinthe.
 * 
 * @author telop
 *
 */
public final class ToolsPanel extends JPanel {
	private static final long serialVersionUID = 4432571170375056548L;

	private MazeButton emptyButton;
	private MazeButton loadButton;
	private MazeButton saveButton;
	private MazeButton solveButton;
	
	public ToolsPanel(MazeWindow window) {
		
		setLayout(new GridLayout(4, 1));
		
		setPreferredSize(new Dimension(200, 600));
		setBackground(Color.red);
		
		add(emptyButton = new MazeButton(window, "Empty"));
		add(loadButton = new MazeButton(window, "Load"));
		add(saveButton = new MazeButton(window, "Save"));
		add(solveButton = new MazeButton(window, "Solve"));
		
		// Ajout des actions des boutons.
		
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
					ConsolePanel.Write("No path found !", ConsolePanel.ERROR);
				}
				window.repaint();
			}
		});
	}
	
	
	
	

}
