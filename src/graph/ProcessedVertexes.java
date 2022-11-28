package graph;
/**
 * Interface définissant l'ensemble des sommets déjà étudiés par l'algorithme.
 * @author Telo PHILIPPE
 *
 */
public interface ProcessedVertexes {
	
	/** Ajoute un sommet à l'ensemble.
	 * @param vertex Sommet à ajouter à l'ensemble.
	 **/
	public void addVertex(Vertex vertex);
	
	/** Vérifie si le sommet v est dans l'ensemble.
	 * @param vertex Sommet à vérifier.
	 * @return true si v appartient à l'ensemble, false sinon.
	 **/
	public boolean containsVertex(Vertex vertex);
}
