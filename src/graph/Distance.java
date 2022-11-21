package graph;


/**
 * Interface définissant la fonction de distance entre deux sommets
 * @author Telo PHILIPPE
 *
 */
public interface Distance {

	/**
	 * Permet de récupérer la distance entre deux sommets.
	 * @param v1 le sommet 1.
	 * @param v2 le sommet 2.
	 * @return la distance entre les deux.
	 */
	int getDistance(Vertex v1, Vertex v2);
}
