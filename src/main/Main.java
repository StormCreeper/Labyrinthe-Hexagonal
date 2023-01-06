package main;

import java.util.List;

import exceptions.MazeReadingException;
import graph.Dijkstra;
import graph.ShortestPaths;
import graph.Vertex;
import maze.Maze;

import ui.*;

public class Main {

	public static void main(String[] args) {
		Maze maze = new Maze(10, 10);
		try {
			maze.initFromTextFile("data/labyrinthe.maze");
			System.out.println(maze);
			ShortestPaths sp = Dijkstra.dijkstra(maze, maze.departures.get(0), maze.arrivals.get(0), maze);
			
			List<Vertex> path = sp.getShortestPath(maze.arrivals.get(0));
			
			maze.drawWithPath(path);
			
		} catch (MazeReadingException e) {
			e.printStackTrace();
		}
		
		new Window(maze);
	}
 
}
