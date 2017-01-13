package model;

import java.util.List;

import model.scala.Empty;
import model.scala.Tree;

public abstract class DescriptionTree implements Cloneable {
	
	private Tree descriptionTree;
	protected int alpha;
	protected int beta;
	
	public DescriptionTree() {
		descriptionTree = new Empty();
	}
	
	public DescriptionTree(Tree t) {
		descriptionTree = t;
	}
	
	public DescriptionTree(int a, int b) {
		descriptionTree = new Empty();
		alpha = a;
		beta = b;
	}
	
	public void setValue(int n, int i) {
		descriptionTree = descriptionTree.setValue(n, i);
	}
	
	public List<Tree> getLeaves() {
		return descriptionTree.getLeaves();
	}
	
	public List<Tree> getNodes() {
		//System.out.println(descriptionTree.getNodes());
		return descriptionTree.getNodes();
	}
	
	public Tree getChild(int i) {
		return descriptionTree.getChild(i);
	}
	
	public int getNumVertices() {
		return descriptionTree.getNumVertices(descriptionTree);
	}
	
	public void addRoot() {
		descriptionTree = descriptionTree.addRoot();
	}
	
	public void addLeaf() {
		descriptionTree = descriptionTree.addLeaf();
	}
	
	public void addLeafToLeaf(int i) {
		descriptionTree = descriptionTree.addLeafToLeaf(i);
	}
	
	public void addNode(DescriptionTree t) {
		descriptionTree = descriptionTree.addNode(t.descriptionTree);
	}
	
	public void addSubtrees(DescriptionTree t) {
		descriptionTree = descriptionTree.addSubtrees(t.descriptionTree);
	}
	
	public abstract void evaluateTree();
	
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
	
	@Override
	public String toString() {
		return descriptionTree.toString();
	}
	
}
