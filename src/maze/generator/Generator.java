package maze.generator;

import java.util.ArrayList;

import graph.Vertex;
import maze.Maze;

import java.util.Random;

public class Generator extends Maze{
	
	Random random = new Random();

	public Generator(int width, int height) {
		super(width, height);
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				boxes[i][j] = new GeneratorBox(i, j, this);
			}
		}
	}
	
	public void Generate() {
		setBox(4, 4, 3);
		GeneratorBox box = randomFromDepth(1);
		randomWalk(box.getI(), box.getJ());
		System.out.println(toString());
	}
	
	// New path begins with tmmpDepth, and all modified boxes have isTmp set to true.
	// TODO : have a predecessor attribute in generatorBoxes, so, it the new path touches a box 2,
	// it can get the box 3 it comes from, and start over from there, or end there if it was a permanent path.
	private void randomWalk(int i, int j) {
		setTmpBox(i, j, 3);
	}
	
	private void setBox(int i, int j, int depth) {
		GeneratorBox box = (GeneratorBox) boxes[i][j];
		if(box.depth >= depth) return;
		
		box.depth = depth;
		
		if(depth == 0) return;
		
		ArrayList<Vertex> successors = getSuccessors(box);
		for(Vertex v : successors) {
			GeneratorBox succBox = (GeneratorBox) v;
			setBox(succBox.getI(), succBox.getJ(), depth - 1);
		}
	}
	private void setTmpBox(int i, int j, int depth) {
		GeneratorBox box = (GeneratorBox) boxes[i][j];
		if(box.tmpDepth >= depth) return;
		
		box.tmpDepth = depth;
		
		if(depth == 0) return;
		
		ArrayList<Vertex> successors = getSuccessors(box);
		for(Vertex v : successors) {
			GeneratorBox succBox = (GeneratorBox) v;
			setTmpBox(succBox.getI(), succBox.getJ(), depth - 1);
		}
	}
	
	private ArrayList<GeneratorBox> getAllFromDepth(int depth) {
		ArrayList<GeneratorBox> result = new ArrayList<>();
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				GeneratorBox box = (GeneratorBox) getBox(i, j);
				if(box.depth == depth) result.add(box);
			}
		}
		
		return result;
	}
	
	private GeneratorBox randomFromDepth(int depth) {
		ArrayList<GeneratorBox> boxesDepth = getAllFromDepth(depth);
		return boxesDepth.get(random.nextInt(boxesDepth.size()));
	}
	
}
