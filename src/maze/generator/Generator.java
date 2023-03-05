package maze.generator;

import java.util.ArrayList;

import graph.Vertex;
import maze.Maze;
import maze.MazeBox;

import java.util.Random;

public class Generator extends Maze {
	
	Random random = new Random();

	public Generator(int width, int height) {
		super(width, height);
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				boxes[i][j] = new GeneratorBox(i, j, this);
			}
		}
	}
	
	public Generator Generate() {
		setBox(0, 0, 3, null);
		GeneratorBox next = randomFromDepth(1, false);
		while(next != null) {
			boolean cont = true;
			while(cont) {
				cont = !randomWalk(next.getI(), next.getJ());
			}
			next = randomFromDepth(1, false);
		}
		return this;
	}
	
	// New path begins with tmpDepth, and all modified boxes have isTmp set to true.
	// TODO : have a predecessor attribute in generatorBoxes, so, it the new path touches a box 2,
	// it can get the box 3 it comes from, and start over from there, or end there if it was a permanent path.
	private boolean randomWalk(int i, int j) {
		resetTmp();
		GeneratorBox current = (GeneratorBox) boxes[i][j];
		for(int k=0; k<1000; k++) {
			ArrayList<Vertex> successors = getSuccessors(current);
			ArrayList<GeneratorBox> candidates = new ArrayList<>();
			for(Vertex v : successors) {
				GeneratorBox succBox = (GeneratorBox) v;
				if(succBox.tmpDepth <= 1 || (succBox.depth == 2 && !succBox.isTemp)) candidates.add(succBox);
			}
			
			setTmpBox(current.getI(), current.getJ(), 3, null);
			if(candidates.size() == 0) break;
			current = candidates.get(random.nextInt(candidates.size()));
			if(current.depth == 2) {
				setTmpBox(current.getI(), current.getJ(), 3, null);
				updateTmp();
				return true;
			}
		}
		return false;
		//updateTmp();
	}
	
	private void setBox(int i, int j, int depth, GeneratorBox parent) {
		GeneratorBox box = (GeneratorBox) boxes[i][j];
		if(box.depth >= depth) return;
		
		box.depth = depth;
		box.parent = parent;
		
		if(depth == 0) return;
		
		ArrayList<Vertex> successors = getSuccessors(box);
		for(Vertex v : successors) {
			GeneratorBox succBox = (GeneratorBox) v;
			setBox(succBox.getI(), succBox.getJ(), depth - 1, box);
		}
	}
	private void setTmpBox(int i, int j, int depth, GeneratorBox parent) {
		GeneratorBox box = (GeneratorBox) boxes[i][j];
		if(box.tmpDepth >= depth) return;
		
		box.parent = parent;
		box.tmpDepth = depth;
		box.isTemp = true;
		
		if(depth == 0) return;
		
		ArrayList<Vertex> successors = getSuccessors(box);
		for(Vertex v : successors) {
			GeneratorBox succBox = (GeneratorBox) v;
			setTmpBox(succBox.getI(), succBox.getJ(), depth - 1, box);
		}
	}
	
	private void updateTmp() {
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				GeneratorBox box = (GeneratorBox)boxes[i][j];
				box.depth = box.tmpDepth;
				box.isTemp = false;
			}
		}
	}
	private void resetTmp() {
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				GeneratorBox box = (GeneratorBox)boxes[i][j];
				box.tmpDepth = box.depth;
				box.isTemp = false;
			}
		}
	}
	
	private ArrayList<GeneratorBox> getAllFromDepth(int depth, boolean tmp) {
		ArrayList<GeneratorBox> result = new ArrayList<>();
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				GeneratorBox box = (GeneratorBox) getBox(i, j);
				if(box.tmpDepth == depth && tmp || box.depth == depth && !tmp) result.add(box);
			}
		}
		
		return result;
	}
	
	private GeneratorBox randomFromDepth(int depth, boolean tmp) {
		ArrayList<GeneratorBox> boxesDepth = getAllFromDepth(depth, tmp);
		if(boxesDepth.size() == 0) return null;
		return boxesDepth.get(random.nextInt(boxesDepth.size()));
	}
	
	public Maze convertToMaze() {
		for(int i = 0; i<width; i++) {
			for(int j = 0; j<height; j++) {
				char chara = MazeBox.wallChara;
				
				if(((GeneratorBox)(boxes[i][j])).depth == 3) chara = MazeBox.emptyChara;
				if(i==0 && j==0) chara = MazeBox.departureChara;
				if(i==width-1 && j==height-1) chara = MazeBox.arrivalChara;
				
				boxes[i][j] = MazeBox.createMazeBox(i, j, chara, this);
			}
		}
		
		return this;
	}
	
}
