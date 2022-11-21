package graph;

import graph.*;

/**
 * Classe implémentant l'algotithme de Dijkstra en utilisant les différentes interfaces définies dans les autres fichiers.
 * @author Telo PHILIPPE
 * 
 */

public class Dijkstra {
	/**
	 * Implémentation de l'algorihtme de Dijkstra
	 * 
	 * @param graph
	 * @param startVertex
	 * @param endVertex
	 * @param processedVertexes
	 * @param minDistance
	 * @param distance
	 * @param shortestPaths
	 * 
	 * @return l'objet shortestPaths mis à jour pour contenir le chemin le plus court dans le graphe.
	 */
	public ShortestPaths dijkstra(Graph graph, Vertex startVertex, Vertex endVertex, ProcessedVertexes processedVertexes, MinDistance minDistance, Distance distance, ShortestPaths shortestPaths) {
		processedVertexes.addVertex(startVertex);
		Vertex pivotVertex = startVertex;
		minDistance.setMinDistance(startVertex, 0);
		
		for(Vertex vertex : graph.getVertices())
			if(vertex.getLabel() != startVertex.getLabel())
				minDistance.setMinDistance(vertex, Integer.MAX_VALUE);
		
		while(!processedVertexes.containsVertex(endVertex)) {
			for(Vertex succVertex : graph.getSuccessors(pivotVertex)) {
				if(!processedVertexes.containsVertex(succVertex)) {
					if(minDistance.getMinDistance(pivotVertex) + distance.getDistance(pivotVertex, succVertex) < minDistance.getMinDistance(succVertex)) {
						minDistance.setMinDistance(succVertex, minDistance.getMinDistance(pivotVertex) + distance.getDistance(pivotVertex, succVertex));
						shortestPaths.setPrevious(succVertex, pivotVertex);
					}
				}
			}
			Vertex minVertex = null;
			for(Vertex v : graph.getVertices()) {
				if(!processedVertexes.containsVertex(v)) {
					if(minVertex == null || minDistance.getMinDistance(v) < minDistance.getMinDistance(minVertex)) {
						minVertex = v;
					}
				}
			}
			pivotVertex = minVertex;
			processedVertexes.addVertex(pivotVertex);
		}
		return shortestPaths;
	}

}
