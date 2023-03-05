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
import maze.generator.Generator;
import ui.panels.ConsolePanel;
import ui.panels.WindowPanel;
 

/**
 * Cette classe est la fenêtre principale du jeu. Elle contient un WindowPanel qui prend tout l'écran, et le menu.
 * Elle transmet aussi une référence sur elle-même à tous ses enfants, ce qui leur donne accès au LabyrintheHexagonal.
 * 
 * @author telop
 */
public final class MazeWindow extends JFrame {
	
	// Pour enlever le warning d'Eclipse
	private static final long serialVersionUID = -1157307886576199547L;
	
	private LabyrintheHexagonal laby;
	
	private WindowPanel windowPanel;

	public MazeWindow(LabyrintheHexagonal laby) throws HeadlessException {
		super("Labyrinthe Hexagonal");
		
		this.laby = laby;
		
		setContentPane(windowPanel = new WindowPanel(this));
		
		// Désactive la fermeture automatique de la fenêtre
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		// Pour pouvoir afficher un dialogue à la place.
		addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent event) {
				if(showQuitDialog()) {
					dispose();
					System.exit(0);
				}
		    }
		});
		
		createMenus();
		
		pack();
		setVisible(true);
		
		// Système d'animation par des ticks tous les 25ms.
		
		Timer timer = new Timer(25, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				windowPanel.tick();
			}
		});
		timer.setRepeats(true);
		timer.start();
	}
	
	/**
	 * Initialise tous les menus de la fenêtre.
	 * File
	 * 	-> Quit
	 * Display
	 * 	-> Toggle console
	 * 	-> Toggle debug
	 * Generate
	 *  -> New generated maze
	 */
	public void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem quitItem = new JMenuItem("Quit");
		
		JMenu displayMenu = new JMenu("Display");
		JMenuItem consoleItem = new JMenuItem("Toggle console");
		JMenuItem debugItem = new JMenuItem("Toggle debug");
		
		JMenu generateMenu = new JMenu("Generate");
		JMenuItem generateItem = new JMenuItem("New generated maze");
		
		// Programmation des actions
		
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
				ConsolePanel.ToggleConsole();
			}
		});
		debugItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LabyrintheHexagonal.Debug = !LabyrintheHexagonal.Debug;
				repaint();
			}
		});
		
		generateItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				laby.setMaze(new Generator(laby.getMaze()).Generate().convertToMaze());
				
			}
		});
		
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		
		displayMenu.add(consoleItem);
		displayMenu.add(debugItem);
		menuBar.add(displayMenu);
		
		generateMenu.add(generateItem);
		menuBar.add(generateMenu);
		
		setJMenuBar(menuBar);
	}
	
	/**
	 * Affiche un dialogue de confirmation avant de quitter l'application.
	 * 
	 * @return true si la fenêtre doit se fermer à l'issue de ce dialogue, faux sinon.
	 */
	public boolean showQuitDialog() {
		if(laby.getMaze().hasChanged()) {
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
	
	/**
	 * Affiche un dialogue de séléction de la taille
	 * 
	 * @return si l'utilisateur a choisi une taille ou non
	 */
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
	
	/**
	 * Affiche un dialogue de sélection de fichier pour l'enregistrement et met à jour le labyrinthe si besoin..
	 * 
	 * @return si l'utilisateur a sélectionné un fichier ou non
	 */
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
	
	/**
	 * Affiche un dialogue de sélection de fichier pour l'enregistrement
	 * 
	 * @return si l'utilisateur a sélectionné un fichier ou non
	 */
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
	/**
	 * Getter pour la variable laby.
	 * @return le labyrinthe principal
	 */
	public LabyrintheHexagonal getLaby() {
		return laby;
	}

}
