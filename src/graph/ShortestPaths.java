package graph;

import java.util.List;

/**
 * Interface définissant le chemin le plus court d'un sommet à l'origine, sous forme de liste chaînée.
 * @author Telo PHILIPPE
 *
 */
public interface ShortestPaths {
	
	/**
	 * Permet de récupérer le prédécesseur d'un sommet dans la chaîne définissant le chemin.
	 * @param vertex le sommet dont on veut le prédécesseur.
	 * @return le prédecesseur du sommet.
	 */
	public Vertex getPrevious(Vertex vertex);
	
	/**
	 * Permet de modifier le prédécesseur d'un sommet.
	 * @param vertex le sommet à modifier.
	 * @param previous son prédécesseur.
	 */
	public void setPrevious(Vertex vertex, Vertex previous);
	
	public List<Vertex> getShortestPath(Vertex endVertex);
}
