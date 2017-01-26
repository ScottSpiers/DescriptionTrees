package model;

import java.util.List;

import model.scala.Empty;
import model.scala.Tree;

public abstract class DescriptionTree implements Cloneable {
	
	protected Tree descriptionTree;
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
	
	public int getValue() {
		return descriptionTree.getValue();
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
	
	public List<Tree> getAllChildren() {
		return descriptionTree.getAllChildren();
	}
	
	public int getNumVertices() {
		return descriptionTree.getNumVertices(descriptionTree);
	}
	
	public int getNumChildren() {
		return descriptionTree.getNumChildren();
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
	
	public void addLeafToNode(int i) {
		descriptionTree = descriptionTree.addLeafToNode(i);
	}
	
	public void addLeafAsFirstChild() {
		descriptionTree = descriptionTree.addLeafAsFirstChild();
	}
	
	public void addChildToChildAt(int i) {
		descriptionTree = descriptionTree.addLeafAsChildAt(i);
	}
	
	public void addNode(DescriptionTree t) {
		descriptionTree = descriptionTree.addNode(t.descriptionTree);
	}	
	
	public abstract List<DescriptionTree> evaluateTree(int n);
	protected abstract void setAllLeafValues();
	
	public void setNodeValue(int n, int i) {
		descriptionTree = descriptionTree.setNodeValue(n, i);
	}
	
	public int getNumLeaves() {
		return descriptionTree.getNumLeaves(descriptionTree);
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
	
	private String toStringHelper(Tree t, int level) {
		String str_out = "";
		String indent = "  ";
		for(int i = 0; i < level; i++) {
			indent += "  ";
		}
		for(Tree child : t.getAllChildren()) {
			str_out += indent;
			str_out += child.getValue() + "\n";
			str_out += toStringHelper(child, level + 1);
		}
		return str_out;
	}
	
	@Override
	public String toString() {
		String str_out = "";
		String indent = "  ";
		str_out += getValue() + "\n";
		for(Tree t : getAllChildren()) {
			str_out += indent + t.getValue() + "\n";
			str_out += toStringHelper(t, 1);
		}
		str_out += "\n";
		return str_out;
	}
	
}
