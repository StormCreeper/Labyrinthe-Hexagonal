package ui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import main.LabyrintheHexagonal;
import ui.panels.ConsolePanel;
import ui.panels.WindowPanel;
 
public class MazeWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1157307886576199547L;
	
	private LabyrintheHexagonal laby;
	
	private WindowPanel wp;

	public MazeWindow(LabyrintheHexagonal laby) throws HeadlessException {
		super("Labyrinthe Hexagonal");
		
		this.laby = laby;
		
		setContentPane(wp = new WindowPanel(this));
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent event) {
				if(showQuitDialog()) {
					dispose();
					System.exit(0);
				}
		    }
		});
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem quitItem = new JMenuItem("Quit");
		JMenu displayMenu = new JMenu("Display");
		JMenuItem consoleItem = new JMenuItem("Toggle console");
		
		
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showQuitDialog()) 
					dispose();
			}
		});
		consoleItem.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsolePanel.instance.setVisible(!ConsolePanel.instance.isVisible());
			}
		});
		
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		
		displayMenu.add(consoleItem);
		menuBar.add(displayMenu);
		
		setJMenuBar(menuBar);
		
		pack();
		setVisible(true);
		
		Timer timer = new Timer(25, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				wp.tick();
			}
		});
		timer.setRepeats(true);
		timer.start();
	}
	
	public boolean showQuitDialog() {
		if(laby.getMaze().isModified()) {
			int response = JOptionPane.showInternalOptionDialog(null, "Maze not saved. Save it ?", "Quit app", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			switch(response) {
			case JOptionPane.CANCEL_OPTION:
				return false;
			case JOptionPane.OK_OPTION:
				return showSaveDialog();
			case JOptionPane.NO_OPTION:
				return true;
			case JOptionPane.CLOSED_OPTION:
				return false;
			}
		}
		return true;
	}
	
	public boolean showChooseSizeDialog() {
		SpinnerNumberModel widthModel = new  SpinnerNumberModel(getLaby().getMaze().getWidth(), 2, 50, 1);
		JSpinner widthSpinner = new JSpinner(widthModel);
		SpinnerNumberModel heightModel = new  SpinnerNumberModel(getLaby().getMaze().getHeight(), 2, 50, 1);
		JSpinner heightSpinner = new JSpinner(heightModel);
		
		final JComponent[] inputs = new JComponent[] {
			new JLabel("Width"),
			widthSpinner,
			new JLabel("Height"),
			heightSpinner
		};
		
		int result = JOptionPane.showConfirmDialog(this, inputs, "Choose maze size", JOptionPane.PLAIN_MESSAGE);
		
		if(result == JOptionPane.OK_OPTION) {
			int width = (int) widthModel.getValue();
			int height = (int) heightModel.getValue();
			
			getLaby().getMaze().setWidthHeight(width, height);
			
			getLaby().reset();
			repaint();
			
			return true;
		}
		return false;
	}
	
	public boolean showLoadDialog() {
		JFileChooser fc = new JFileChooser(new File("data"));
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			getLaby().load(file.getPath());
			
			repaint();
			
			return true;
		}
		return false;
	}
	
	public boolean showSaveDialog() {
		JFileChooser fc = new JFileChooser(new File("data"));
		int returnVal = fc.showSaveDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			getLaby().save(file.getPath());
			
			repaint();
			
			return true;
		}
		return false;
	}
	
	public LabyrintheHexagonal getLaby() {
		return laby;
	}

}
