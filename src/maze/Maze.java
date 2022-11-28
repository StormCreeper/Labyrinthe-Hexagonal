package maze;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import exceptions.MazeReadingException;
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
	
	public final void initFromTextFile(String filename) throws MazeReadingException {
		try {
			List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
			
			if(lines.size() != height) {
				throw new MazeReadingException(filename, -1, "Wrong number of rows, expected " + height + ", got " + lines.size() + ".");
			}
			
			for(int j=0; j<height; j++) {
				if(lines.get(j).length() != width) {
					throw new MazeReadingException(filename, j, "Wrong number of column, expected " + width + ", got " + lines.get(j).length() + ".");
				}
				for(int i = 0; i<width; i++) {
					switch (lines.get(j).charAt(i)) {
					case MazeBox.wallChara:
						boxes[i][j] = new WallBox(i, j, this);
						break;
					case MazeBox.emptyChara:
						boxes[i][j] = new EmptyBox(i, j, this);
						break;
					case MazeBox.departureChara:
						boxes[i][j] = new DepartureBox(i, j, this);
						break;
					case MazeBox.arrivalChara:
						boxes[i][j] = new ArrivalBox(i, j, this);
						break;
					default:
						throw new MazeReadingException(filename, j, "Unrecognized character : " + lines.get(j).charAt(i));
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param filename le nom du fichier dans lequel enregistrer le labyrinthe.
	 */
	public final void saveToTextFile(String filename) {
		// Ecrit le contenu du fichier dans une chaîne de caractère 
		String content = toString();
		
		// Puis écrit tout d'un seul coup dans le fichier
		try {
			Files.write(Paths.get(filename), content.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Convertit le labyrinthe en une chaîne de charactère (dans le même format que pour l'enregistrement dans un fichier.
	 */
	@Override
	public String toString() {
		String content = "";
		for(int j=0; j<height; j++) {
			for(int i = 0; i<width; i++) {
				content += boxes[i][j].getChara();
			}
			content += '\n';
		}
		
		return content;
	}

}
