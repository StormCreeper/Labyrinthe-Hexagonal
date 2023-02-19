package impls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import graph.ShortestPaths;
import graph.Vertex;

/**
 * Implémentation de ShortestPaths pour le labyrinthe
 * @author telop
 *
 */
public class ShortestPathsImpl implements ShortestPaths {

	private HashMap<Vertex, Vertex> map;
	
	public ShortestPathsImpl() {
		map = new HashMap<Vertex, Vertex>();
	}
	
	@Override
	public void setPrevious(Vertex vertex, Vertex previous) {
		map.put(vertex, previous);

	}

	@Override
	public Vertex getPrevious(Vertex vertex) {
		return map.get(vertex);
	}
	
	private boolean hasPrevious(Vertex vertex) {
		return map.containsKey(vertex);
	}

	@Override
	public List<Vertex> getShortestPath(Vertex endVertex) {
		
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		
		Vertex currentVertex = endVertex;
		
		path.add(currentVertex);
		
		while(hasPrevious(currentVertex)) {
			currentVertex = getPrevious(currentVertex);
			path.add(currentVertex);
		}
		
		Collections.reverse(path);
		
		return path;
	}
}
