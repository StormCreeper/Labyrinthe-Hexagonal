package maze;
import java.io.*;

import java.util.ArrayList;

import graph.Distance;
import graph.Graph;
import graph.Vertex;


/**
 * Classe représentant un labyrinthe, sous forme de tableau 2D de cases MazeBox
 * Il implémente l'interface de graphe et de distance pour pouvoir utiliser l'algorithme de Dijkstra.
 * Il est caractérisé par sa longueur d 
 * @author Telo PHILIPPE
 *
 */
public class Maze implements Graph, Distance{
	
	private MazeBox[][] boxes;
	private int width, height;
	
	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		
		boxes = new MazeBox[width][height];
	}
	
	/**
	 * Fonction permettant d'ajouter de manière sécurisée un sommet du tableau à une ArrayList
	 * 
	 * @param vertices la liste à mettre à jour
	 * @param i la première coordonnée du sommet
	 * @param j la deuxième coordonnée du sommet
	 */
	private void addVertex(ArrayList<Vertex> vertices, int i, int j) {
		if(i<0 || i >= width) return;
		if(j<0 || j >= height) return;
		
		vertices.add(boxes[i][j]);
	}
	
	@Override
	public ArrayList<Vertex> getVertices() {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				addVertex(vertices, i, j);
		
		return vertices;
	}

	@Override
	public ArrayList<Vertex> getSuccessors(Vertex vertex) {
		ArrayList<Vertex> successors = new ArrayList<Vertex>();
		
		MazeBox mazeBox = (MazeBox)vertex;
		int i = mazeBox.getI();
		int j = mazeBox.getJ();
		
		addVertex(successors, i-1, j);
		addVertex(successors, i+1, j);
		addVertex(successors, i, j+1);
		addVertex(successors, i, j-1);
		
		if(j % 2 == 0) {
			addVertex(successors, i-1, j+1);
			addVertex(successors, i-1, j-1);
		} else {
			addVertex(successors, i+1, j+1);
			addVertex(successors, i+1, j-1);
		}
		
		return successors;
	}

	@Override
	public int getDistance(Vertex v1, Vertex v2) {
		return 1;
	}
	
	public final void initFromTextFile(String filename) {
		try {
			FileInputStream is = new FileInputStream(filename);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
