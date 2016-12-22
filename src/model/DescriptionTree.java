package model;

import java.util.List;

import model.scala.Empty;
import model.scala.Tree;

public abstract class DescriptionTree implements Cloneable {
	
	private Tree descriptionTree;
	
	public DescriptionTree() {
		descriptionTree = new Empty();
	}
	
	public DescriptionTree(Tree t) {
		descriptionTree = t;
	}
	
	public List<Tree> getNodes() {
		//System.out.println(descriptionTree.getNodes());
		return descriptionTree.getNodes();
	}
	
	public Tree getChild(int i) {
		return descriptionTree.getChild(0);
	}
	
	public void addLeaf() {
		descriptionTree = descriptionTree.addLeaf();
	}
	
	public void addNode(DescriptionTree t) {
		descriptionTree = descriptionTree.addNode(t.descriptionTree);
	}
	
	public void addSubtrees(DescriptionTree t) {
		descriptionTree = descriptionTree.addSubtrees(t.descriptionTree);
	}
	
	@Override
	public Object clone() {		
		try {
			return super.clone();		
		}
		catch (CloneNotSupportedException ex) {
			return null;
		}
	}
	
	@Override
	public boolean equals(Object t) {
		if(t instanceof DescriptionTree) {
			return (descriptionTree.getNodes().equals(((DescriptionTree) t).getNodes()));
		}
		return false;
	}
	
}
