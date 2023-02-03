package main;

import java.util.List;

import exceptions.MazeReadingException;
import graph.Dijkstra;
import graph.ShortestPaths;
import graph.Vertex;
import maze.Maze;
import ui.MazeWindow;
import ui.panels.ConsolePanel;

public class LabyrintheHexagonal {
	
	private Maze maze;
	public List<Vertex> path;

	public LabyrintheHexagonal(String[] args) {
		maze = new Maze(10, 10);
		maze.reset();
		
		if(args.length > 0) {
			try {
				maze.initFromTextFile(args[0]);
			} catch (MazeReadingException e) {
				e.printStackTrace();
			}
		}
		
		new MazeWindow(this);
	}
	public void reset() {
		maze.reset();
		path = null;
	}
	public void load(String filename) {
		try {
			maze.initFromTextFile(filename);
			path = null;
			ConsolePanel.instance.WriteMessage("Successfully loaded " + filename + ".", ConsolePanel.INFO);
		} catch (MazeReadingException e) {
			//e.printStackTrace();
			ConsolePanel.instance.WriteMessage("Error while loading" + filename + " : " + e.getMessage(), ConsolePanel.ERROR);
		}
	}
	public void save(String filename) {
		maze.saveToTextFile(filename);
		ConsolePanel.instance.WriteMessage("Successfully saved in " + filename + ".", ConsolePanel.INFO);
	}
	
	public void solve() {
		ShortestPaths sp = Dijkstra.dijkstra(maze, maze.getDeparture(), maze.getArrival(), maze);
		path = sp.getShortestPath(maze.getArrival());
		if(!path.get(0).equals(maze.getDeparture())) path = null;
	}
	
	public Maze getMaze() {
		return maze;
	}

}
