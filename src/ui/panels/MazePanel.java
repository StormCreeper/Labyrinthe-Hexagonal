package ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import graph.Vertex;
import maze.Maze;
import maze.MazeBox;
import ui.Window;

public class MazePanel extends JPanel implements MouseMotionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038870474473344877L;

	private Window window;
	
	private MazeBox selected = null;
	private char tool = MazeBox.wallChara;
	
	public MazePanel(Window window) {
		setPreferredSize(new Dimension(600, 600));
		setBackground(Color.green);
		
		addMouseMotionListener(this);
		addMouseListener(this);
		
		this.window = window;
	}
	
	private Polygon getHexa(int i, int j) {
		Maze maze = window.getLaby().getMaze();
		
		int w = maze.getWidth();
		int h = maze.getHeight();
		
		float x = 50 + i * 500.f / (float)w;
		float y = (float) (50 + j * 500.f / (float)w * Math.cos(Math.PI *(1/3.-1/2.)));
		if(j % 2 == 1) x += 250 / w;
		
		//g.fillRect((int)x, (int)y, 500/w, 500/h);
		
		float centerX = x + 250 / w;
		float centerY = y + 250 / h;
		
		double r = 250 / (float) w / Math.cos(Math.PI *(1/3.-1/2.));
		
		int[] xpoints = new int[6];
		int[] ypoints = new int[6];
		
		for(int k=0; k<6; k++) {
			xpoints[k] = (int) (centerX + r * Math.cos(2 * k * Math.PI / 6.0 + Math.PI / 2.0));
			ypoints[k] = (int) (centerY + r * Math.sin(2 * k * Math.PI / 6.0 + Math.PI / 2.0));
		}
		
		return new Polygon(xpoints, ypoints, 6);
	}
	
	private Color getColor(int i, int j) {
		Maze maze = window.getLaby().getMaze();
		char c = maze.getBox(i, j);
		if(c == MazeBox.emptyChara) return new Color(i/(float)maze.getWidth(), j/(float)maze.getHeight(), 1.0f);
		if(c == MazeBox.arrivalChara) return Color.CYAN;
		if(c == MazeBox.departureChara) return Color.YELLOW;
		return Color.BLACK;
	}
	
	private Color mixColor(Color c1, Color c2, float t) {
		return new Color(
			(int)(c1.getRed() * (1-t) + c2.getRed() * t),
			(int)(c1.getGreen() * (1-t) + c2.getGreen() * t),
			(int)(c1.getBlue() * (1-t) + c2.getBlue() * t)
		);
	}
	
	@Override
	public final void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		
		Maze maze = window.getLaby().getMaze();
		
		int w = maze.getWidth();
		int h = maze.getHeight();

		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				g.setColor(getColor(i, j));
				g.fillPolygon(getHexa(i, j));
			
			}
		}
		
		if(window.getLaby().path != null) {
			for(Vertex v : window.getLaby().path) {
				MazeBox m = (MazeBox)v;
				
				if(m.getChara() != MazeBox.emptyChara) continue;
				
				g.setColor(Color.GREEN);
				g.fillPolygon(getHexa(m.getI(), m.getJ()));
			}
		}
		
		if(selected != null) {
			int i = selected.getI();
			int j = selected.getJ();
			
			Color baseColor = getColor(i, j);
			Color finalColor = mixColor(baseColor, Color.white, .4f);
			
			g.setColor(finalColor);
			
			Polygon hexa = getHexa(i, j);
			g.fillPolygon(hexa);
			g.setColor(Color.black);
			g.drawPolygon(hexa);
		}
	}

	private void changeCell() {
		if(tool == MazeBox.invalidChara) return;
		if(selected == null) return;
		
		int i = selected.getI();
		int j = selected.getJ();

		window.getLaby().getMaze().setCell(i, j, tool);
		window.getLaby().path = null;
	}
	
	private void selectCell(int mouseX, int mouseY) {
		Maze maze = window.getLaby().getMaze();
		selected = null;
		
		for(int i = 0; i<maze.getWidth(); i++) {
			for(int j=0; j<maze.getHeight(); j++) {
				if(getHexa(i, j).contains(new Point(mouseX, mouseY))) selected = maze.boxes[i][j];
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		selectCell(e.getX(), e.getY());
		changeCell();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		selectCell(e.getX(), e.getY());
		repaint();
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectCell(e.getX(), e.getY());
		if(selected == null) return;
		
		switch(selected.getChara()) {
		case MazeBox.arrivalChara:
		case MazeBox.departureChara:
			tool = MazeBox.invalidChara;
			break;
		case MazeBox.emptyChara:
			tool = MazeBox.wallChara;
			break;
		case MazeBox.wallChara:
			tool = MazeBox.emptyChara;
			break;
		}
		
		changeCell();
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
