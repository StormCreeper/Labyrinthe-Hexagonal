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
	
	public MazeBox[][] boxes;
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
		
		if(boxes[i][j].getChara() == MazeBox.wallChara) return;
		
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
		
		if(mazeBox.getChara() == MazeBox.wallChara) return successors;
		
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
	/**
	 * Charge un labyrinthe depuis un fichier texte.
	 * Format du fichier .maze :
	 * 
	 * WIDTH HEIGHT
	 * #### ... ####
	 * #### ... ####
	 * ...      ....
	 * #### ... ####
	 * #### ... ####
	 * 
	 * où # représente une case du labyrinthe, les caractères correspondants aux types de cases sont décrits dans MazeBox.java
	 * 
	 * @param filename
	 * @throws MazeReadingException
	 */
	public final void initFromTextFile(String filename) throws MazeReadingException {
		try {
			List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
			
			// Lire la première ligne qui contient la taille du labyrinthe
			
			String[] wh = lines.get(0).split(" ");
			width = Integer.parseInt(wh[0]);
			height = Integer.parseInt(wh[1]);
			
			// Lire le contenu du labyrinthe
			
			if(lines.size() != height + 1) {
				throw new MazeReadingException(filename, -1, "Wrong number of rows, expected " + height + ", got " + lines.size() + ".");
			}
			
			for(int j=0; j<height; j++) {
				if(lines.get(j+1).length() != width) {
					throw new MazeReadingException(filename, j, "Wrong number of column at line " + j + ", expected " + width + ", got " + lines.get(j).length() + ".");
				}
				for(int i = 0; i<width; i++) {
					switch (lines.get(j + 1).charAt(i)) {
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
	
	/**
	 * Affiche le labyrinthe en marquant d'un "x" les cases du chemin donné en paramètre/
	 * @param vertices le chemin à afficher
	 */
	public void drawWithPath(List<Vertex> vertices) {
		char[] content = toString().toCharArray();
		
		for(Vertex v : vertices) {
			MazeBox m = (MazeBox)v;
			// On traite la chaîne de caractères comme un tableau à 2 dimensions, de taille (width + 1) * height (le "+1" est pour le caractère de retour à la ligne.
			content[m.getI() + (width + 1) * m.getJ()] = 'x';
		}
		
		System.out.print(String.valueOf(content));
	}
	
	public void reset() {
		for(int i = 0; i<width; i++) {
			for(int j=0; j<height; j++) {
				if(i == 0 && j == 0)  boxes[i][j] = new DepartureBox(i, j, this);
				else if(i == width-1 && j == height-1)  boxes[i][j] = new ArrivalBox(i, j, this);
				else boxes[i][j] = new EmptyBox(i, j, this);
			}
		}
	}
	//TODO Change Mazebox to a single class with a character
	public void setCell(int i, int j, char chara) {
		if(i<0 || i >= width || j<0 || j>= height) return;
		boxes[i][j] = new MazeBox(i, j, this) {
			
			@Override
			public char getChara() {
				return chara;
			}
		};
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public Vertex getArrival() {
		for(int i = 0; i<width; i++) {
			for(int j=0; j<height; j++) {
				if(boxes[i][j].getChara() == MazeBox.arrivalChara) return boxes[i][j];
			}
		}
		
		return null;
		
	}
	
	public Vertex getDeparture() {
		for(int i = 0; i<width; i++) {
			for(int j=0; j<height; j++) {
				if(boxes[i][j].getChara() == MazeBox.departureChara) return boxes[i][j];
			}
		}
		
		return null;
	}
	
	public char getBox(int x, int y) {
		if(x < 0 || x >= width) return ' ';
		if(y < 0 || y >= width) return ' ';
		
		return boxes[x][y].getChara();
	}

}
