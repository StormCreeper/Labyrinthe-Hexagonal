package ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import graph.Vertex;
import maze.Maze;
import maze.MazeBox;
import ui.Window;

public class MazePanel extends JPanel implements MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038870474473344877L;

	private Window window;
	
	private MazeBox selected = null;
	
	public MazePanel(Window window) {
		setPreferredSize(new Dimension(600, 600));
		setBackground(Color.green);
		
		addMouseMotionListener(this);
		
		this.window = window;
	}
	
	@Override
	public final void paintComponent(Graphics g) {
		Maze maze = window.getLaby().getMaze();
		
		int w = maze.getWidth();
		int h = maze.getHeight();

		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				char c = maze.getBox(i, j);
				if(c == MazeBox.emptyChara) {
					g.setColor(new Color(i/(float)w, j/(float)h, 1.0f));
					if(window.getLaby().path != null) {
						for(Vertex v : window.getLaby().path) {
							MazeBox m = (MazeBox)v;
							if(m.getI() == i && m.getJ() == j) {
								g.setColor(Color.GREEN);
							}
						}
					}
				}
				else if(c == MazeBox.arrivalChara) g.setColor(Color.RED);
				else if(c == MazeBox.departureChara) g.setColor(Color.GREEN);
				else g.setColor(Color.BLACK);
				//g.setColor(new Color(i/(float)w, j/(float)h, 1.0f));
				
				float x = 50 + i * 500.f / (float)w;
				float y = 50 + j * 500.f / (float)h;
				if(j % 2 == 1) x += 250 / w;
				if(selected != null && selected.getI() == i && selected.getJ() == j)
					g.fillRect((int)x - 5, (int)y - 5, 500/w + 10, 500/h + 10);
				else
					g.fillRect((int)x, (int)y, 500/w, 500/h);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int w = window.getLaby().getMaze().getWidth();
		int h = window.getLaby().getMaze().getHeight();
		int i = (int)((e.getX() - 50) * w / 500.f);
		int j = (int)((e.getY() - 50) * h / 500.f);
		
		System.out.println(i + ", " + j);
		
		if(i<0 || i >= w || j < 0 || j >= h) return;
		selected = window.getLaby().getMaze().boxes[i][j];
		
		repaint();
	}

}
