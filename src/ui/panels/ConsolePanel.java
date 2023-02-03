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

public class ConsolePanel extends JPanel {
	private static final long serialVersionUID = -8890252228755132252L;
	
	JTextPane txtPane;
	JScrollPane scrollPane;
	StyledDocument sdoc;
	
	private Style dft;
	private Style error;
	private Style info;
	
	public static final String DEFAULT = "default";
	public static final String ERROR = "error";
	public static final String INFO = "info";
	
	public static ConsolePanel instance;
	
	int cursor = 0;

	public ConsolePanel(MazeWindow window) {
		super();
		
		instance = this;
		
		setPreferredSize(new Dimension(800, 100));
		setLayout(new BorderLayout());
		setVisible(false);
		
		txtPane = new JTextPane();
		scrollPane = new JScrollPane(txtPane);
		
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
	
	public void WriteMessage(String line, String style) {
		try {
			sdoc.insertString(cursor, line + "\n", txtPane.getStyle(style));
			cursor += line.length() + 1;
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
