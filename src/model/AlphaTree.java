package model;

import java.util.List;

import model.scala.Tree;

public class AlphaTree extends DescriptionTree {

	public AlphaTree() {
		super();
	}
	
	public AlphaTree(Tree t) {
		super(t);
	}
	
	public AlphaTree(int a, int b) {
		super(a, b);
	}
	
	/**
	 * Evaluate leaves as b
	 * Evaluate Internal nodes as:
	 * 		>= a + sum(node1.val -> nodek-1.val) &&
	 * 		<= b + sum(node1.val -> nodek.val)
	 * (including root)
	 */
	@Override
	public List<DescriptionTree> evaluateTree(int n) {
		return null;
	}

	@Override
	protected void setAllLeafValues() {
		descriptionTree = descriptionTree.setAllLeafValues(beta);
		
	}
}
