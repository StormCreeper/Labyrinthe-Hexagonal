package main;

import java.util.List;

import exceptions.MazeReadingException;
import graph.Dijkstra;
import graph.ShortestPaths;
import graph.Vertex;
import maze.Maze;
import ui.Window;

public class LabyrintheHexagonal {
	
	private Maze maze;
	public List<Vertex> path;

	public LabyrintheHexagonal() {
		maze = new Maze(10, 10);
		maze.reset();
		
		new Window(this);
	}
	public void reset() {
		maze.reset();
		path = null;
	}
	public void load(String filename) {
		try {
			maze.initFromTextFile(filename);
			path = null;
		} catch (MazeReadingException e) {
			e.printStackTrace();
		}
	}
	public void save(String filename) {
		maze.saveToTextFile(filename);
	}
	
	public void setSize(int width, int height) {
		maze.setWidthHeight(width, height);
	}
	
	public void solve() {
		ShortestPaths sp = Dijkstra.dijkstra(maze, maze.getDeparture(), maze.getArrival(), maze);
		path = sp.getShortestPath(maze.getArrival());
	}
	
	public Maze getMaze() {
		return maze;
	}

}
