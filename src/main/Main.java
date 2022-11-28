package main;

import maze.Maze;

public class Main {

	public static void main(String[] args) {
		Maze maze = new Maze(10, 10);
		maze.initFromTextFile("data/labyrinthe.maze");
	}

}
