package main;

import java.util.List;

import exceptions.MazeReadingException;
import graph.Dijkstra;
import graph.ShortestPaths;
import graph.Vertex;
import maze.Maze;
import ui.MazeWindow;
import ui.panels.ConsolePanel;

/**
 * Classe qui contient le modèle du labyrinthe (classe Maze) et sa vue (classe MazeWindow).
 * C'est lui qui gère l'interface entre les boutons et le modèle du labyrinthe, et qui donne la référence au modèle à tous les éléments graphiques.
 * 
 * @author telop
 *
 */
public class LabyrintheHexagonal {
	
	// Mode débogage activé ou non : montre les bordure du labyrinthe, utilisées pour faire en sorte que le labyrinthe s'adapte à la taille de la fenêtre.
	public static boolean Debug = false;
	
	// Modèle du labyrinthe 
	private Maze maze;
	
	// Chemin résolu par Dijkstra
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
			ConsolePanel.Write("Successfully loaded " + filename + ".", ConsolePanel.INFO);
		} catch (MazeReadingException e) {
			//e.printStackTrace();
			ConsolePanel.Write("Error while loading" + filename + " : " + e.getMessage(), ConsolePanel.ERROR);
		}
	}
	public void save(String filename) {
		maze.saveToTextFile(filename);
		ConsolePanel.Write("Successfully saved in " + filename + ".", ConsolePanel.INFO);
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
