package graph;

import java.util.ArrayList;

/**
 * Interface définissant le graphe, sous forme de liste de sucesseurs.
 * @author Telo PHILIPPE
 *
 */
public interface Graph {
	
	/**
	 * Permet de récupérer l'ensemble de tous les sommets du graphe.
	 * @return une ArrayList contenant les sommets du graphe.
	 */
	
	public ArrayList<Vertex> getVertices();
	/**
	 * Permet de récupérer l'ensemble des successeurs d'un certain sommet du graphe.
	 * @param vertex le sommet considéré
	 * @return une ArrayList contenant les successeurs du sommet dans le graphe.
	 */
	public ArrayList<Vertex> getSuccessors(Vertex vertex);

}
