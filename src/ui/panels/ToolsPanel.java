package ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import ui.Window;

public class ToolsPanel extends JPanel {
	private static final long serialVersionUID = 4432571170375056548L;


	private JButton emptyButton;
	private JButton loadButton;
	private JButton saveButton;
	private JButton solveButton;
	
	public ToolsPanel(Window window) {
		setLayout(new GridLayout(4, 1));
		
		setPreferredSize(new Dimension(200, 600));
		setBackground(Color.red);
		
		add(emptyButton = new JButton("Empty Maze"));
		add(loadButton = new JButton("Load Maze"));
		add(saveButton = new JButton("Save Maze"));
		add(solveButton = new JButton("Solve Maze"));
		
		emptyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SpinnerNumberModel widthModel = new  SpinnerNumberModel(10, 2, 50, 1);
				JSpinner widthSpinner = new JSpinner(widthModel);
				SpinnerNumberModel heightModel = new  SpinnerNumberModel(10, 2, 50, 1);
				JSpinner heightSpinner = new JSpinner(heightModel);
				
				final JComponent[] inputs = new JComponent[] {
					new JLabel("Width"),
					widthSpinner,
					new JLabel("Height"),
					heightSpinner
				};
				
				int result = JOptionPane.showConfirmDialog(window, inputs, "Custom dialog", JOptionPane.PLAIN_MESSAGE);
				
				if(result == JOptionPane.OK_OPTION) {
					int width = (int) widthModel.getValue();
					int height = (int) heightModel.getValue();
					
					window.getLaby().setSize(width, height);
					
					window.getLaby().reset();
					window.repaint();
				} else {
					System.out.println("Refused blank");
				}
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File("data"));
				int returnVal = fc.showOpenDialog(window);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					window.getLaby().load(file.getPath());
					window.repaint();
				} else {
					System.out.println("File dialog declined");
				}
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File("data"));
				int returnVal = fc.showSaveDialog(window);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					window.getLaby().save(file.getPath());
					window.repaint();
				} else {
					System.out.println("File dialog declined");
				}
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
