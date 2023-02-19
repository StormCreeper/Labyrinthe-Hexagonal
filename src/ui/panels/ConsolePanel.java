package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ui.MazeWindow;


// Classe singleton permettant d'avoir une console dqns l'interface du labyrithe.
public class ConsolePanel extends JPanel {
	private static final long serialVersionUID = -8890252228755132252L;

	public static final String DEFAULT = "default";
	public static final String ERROR = "error";
	public static final String INFO = "info";
	
	// On ne veut qu'une seule instance de la console, accessible depuis partout, pour pouvoir écrire depuis n'importe où dans les programme:
	// ConsolePanel.Write(message, style);
	private static ConsolePanel instance;
	
	public static void Write(String line, String style) {
		// On gère aussi le cas où l'on a pas créé de console (Labyrinthe en ligne de commande), dans ce ca, on écrit dans la console java.
		if(instance == null) {
			System.out.println(line);
		} else {
			showBriefly();
			instance.writeMessage(line, style);
		}
	}
	public static void Tick() {
		if(instance != null)
			instance.tick();
	}
	
	public static void ToggleConsole() {
		if(instance != null) {
			instance.visible = !instance.visible;
			instance.setVisible(instance.visible);
			instance.decompte = 0;
		}
	}
	
	JTextPane txtPane;
	JScrollPane scrollPane;
	StyledDocument sdoc;
	
	private Style dft;
	private Style error;
	private Style info;
	
	private boolean visible;
	private int decompte = 0;
	
	int cursor = 0;

	public ConsolePanel(MazeWindow window) {
		super();
		
		instance = this;
		
		setPreferredSize(new Dimension(800, 100));
		setLayout(new BorderLayout());
		setVisible(false);
		
		txtPane = new JTextPane();
		scrollPane = new JScrollPane(txtPane);
		txtPane.setEditable(false);
		
		txtPane.setBackground(new Color(.9f, .9f, .9f));
		
		add(scrollPane, BorderLayout.CENTER);
		
		sdoc = txtPane.getStyledDocument();
		
		dft = txtPane.getStyle(DEFAULT);
		StyleConstants.setFontSize(dft, 18);
		
		error = txtPane.addStyle(ERROR, dft);
		StyleConstants.setForeground(error, new Color(.8f, .0f, .0f));
		
		info = txtPane.addStyle(INFO, dft);
		StyleConstants.setForeground(info, new Color(.0f, .8f, .0f));
	}
	
	private void writeMessage(String line, String style) {
		try {
			sdoc.insertString(cursor, line + "\n", txtPane.getStyle(style));
			cursor += line.length() + 1;
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public static void showBriefly() {
		if(instance != null)
			instance.decompte = 41;
	}
	
	public void tick() {
		if(!visible) {
			if(decompte > 0) decompte--;
			setVisible(decompte > 0);
		} else {
			setVisible(true);
		}
	}

}
