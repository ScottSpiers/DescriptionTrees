package model;

import model.scala.Tree;

public class BetaTree extends DescriptionTree {

	public BetaTree() {
		super();
	}
	
	public BetaTree(Tree t) {
		super(t);
	}
	
	public BetaTree(int a, int b) {
		super(a, b);
	}
	
	/**
	 * Evaluate leaves as a
	 * Evaluate nodes as:
	 * 		>= a &&
	 * 		<= b + sum(node1.val -> nodek.val)
	 * Evaluate root as sum(node1.val -> nodek.val)
	 */
	@Override
	public void evaluateTree() {
		
	}
}
