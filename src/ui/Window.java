package ui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import main.LabyrintheHexagonal;
import ui.panels.WindowPanel;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1157307886576199547L;
	
	private LabyrintheHexagonal laby;

	public Window(LabyrintheHexagonal laby) throws HeadlessException {
		super("Labyrinthe Hexagonal");
		
		this.laby = laby;
		
		setContentPane(new WindowPanel(this));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuItem item = new JMenuItem("Quit");
		JMenu menu = new JMenu("File");
		JMenuBar menuBar = new JMenuBar();
		
		JFrame window = this;
		
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showInternalOptionDialog(null, "Maze not saved. Save it ?", "Quit app", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				switch(response) {
				case JOptionPane.CANCEL_OPTION:
					System.out.println("CANCEL");
					break;
				case JOptionPane.OK_OPTION:
					System.out.println("OK");
					dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
					break;
				case JOptionPane.NO_OPTION:
					System.out.println("NO");
					dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
					break;
				}
			}
		});
		
		menu.add(item);
		menuBar.add(menu);
		
		setJMenuBar(menuBar);
		
		pack();
		setVisible(true);
	}
	
	public LabyrintheHexagonal getLaby() {
		return laby;
	}

}
