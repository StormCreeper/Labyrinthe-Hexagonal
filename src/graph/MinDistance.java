package graph;

/**
 * Interface permettant de définir la distance minimal d'un sommet au sommet de départ dans le graphe.
 * @author Telo PHILIPPE
 *
 */
public interface MinDistance {
	
	/**
	 * Permet de modifier la distance minimale d'un sommet à l'origine.
	 * @param vertex le sommet à modifier.
	 * @param value la distance à lui attribuer.
	 */
	void setMinDistance(Vertex vertex, int value);
	
	/**
	 * Permet de récuper la distance minimale d'un sommet à l'origine.
	 * @param vertex le sommet dont on veut connaître la distance minimale à l'origine.
	 * @return la distance.
	 */
	int getMinDistance(Vertex vertex);
	
}
