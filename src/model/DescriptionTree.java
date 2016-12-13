package model;

import java.util.List;

import model.scala.Leaf;
import model.scala.Tree;

public abstract class DescriptionTree {
	
	private Tree descriptionTree;
	
	public DescriptionTree() {
		descriptionTree = new Leaf(0);
	}
	
	public List<Tree> getNodes() {
		return null;
	}
	
	
	public void addLeaf() {
		descriptionTree.addLeaf();
	}
	
}
