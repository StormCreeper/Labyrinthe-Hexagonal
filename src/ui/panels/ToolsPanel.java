package ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.Window;

public class ToolsPanel extends JPanel {
	
	private JButton emptyButton;
	private JButton loadButton;
	private JButton saveButton;
	private JButton solveButton;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4432571170375056548L;

	public ToolsPanel(Window window) {
		setLayout(new GridBagLayout());
		
		setPreferredSize(new Dimension(200, 600));
		setBackground(Color.red);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		//c.anchor = GridBagConstraints.CENTER;
		
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 2;
		
		c.gridx=0;
		c.gridy=0;
		add(emptyButton = new JButton("Empty Maze"), c);

		c.gridwidth = 1;
		
		c.gridx=0;
		c.gridy=1;
		add(new JTextField(), c);
		c.gridx=1;
		c.gridy=1;
		add(new JTextField(), c);

		c.gridwidth = 2;
		
		c.gridx=0;
		c.gridy=2;
		add(loadButton = new JButton("Load Maze"), c);
		
		c.gridx=0;
		c.gridy=3;
		add(saveButton = new JButton("Save Maze"), c);
		
		//c.gridwidth=3;
		c.gridx=0;
		c.gridy=4;
		add(solveButton = new JButton("Solve Maze"), c);
		
		emptyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.getLaby().reset();
				window.repaint();
				
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.getLaby().load();
				window.repaint();
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		solveButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				window.getLaby().solve();
				window.repaint();
			}
		});
	}

}
