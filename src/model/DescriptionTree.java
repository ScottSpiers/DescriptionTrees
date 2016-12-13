package model;

import java.util.List;

import model.scala.Leaf;
import model.scala.Tree;

public abstract class DescriptionTree {
	
	private Tree descriptionTree;
	
	public DescriptionTree() {
		descriptionTree = new Leaf(0);
	}
	
	public DescriptionTree(Tree t) {
		descriptionTree = t;
	}
	
	public List<Tree> getNodes() {
		return null;
	}
	
	public Tree getChild(int i) {
		return descriptionTree.getChild(0);
	}
	
	public void addLeaf() {
		descriptionTree.addLeaf();
	}
	
}
