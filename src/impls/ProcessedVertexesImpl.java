package impls;

import java.util.HashSet;

import graph.ProcessedVertexes;
import graph.Vertex;

/**
 * Implémentation de MinProcessedVertices pour le labyrinthe
 * @author telop
 *
 */
public final class ProcessedVertexesImpl implements ProcessedVertexes {
	
	private HashSet<Vertex> set;

	public ProcessedVertexesImpl() {
		set = new HashSet<Vertex>();
	}

	@Override
	public void addVertex(Vertex vertex) {
		set.add(vertex);
	}

	@Override
	public boolean containsVertex(Vertex vertex) {
		return set.contains(vertex);
	}

}
