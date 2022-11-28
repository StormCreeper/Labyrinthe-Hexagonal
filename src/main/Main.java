package main;

import exceptions.MazeReadingException;
import maze.Maze;

public class Main {

	public static void main(String[] args) {
		Maze maze = new Maze(10, 10);
		try {
			
			maze.initFromTextFile("data/labyrinthe.maze");
			System.out.println(maze);
			maze.saveToTextFile("data/labyrinthe2.maze");
			
		} catch (MazeReadingException e) {
			e.printStackTrace();
		}
	}

}
