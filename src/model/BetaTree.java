package model;

import java.util.ArrayList;
import java.util.List;

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
	public List<DescriptionTree> evaluateTree(int n) {
		List<DescriptionTree> newTrees = new ArrayList<DescriptionTree>();
		setAllLeafValues();
		int nodeMin = alpha;
		int nodeMax = beta;
		List<Tree> nodes = descriptionTree.getNodes();
		
		if(n <= 0) {
			int sum = beta;
			for(Tree child : nodes.get(0).getAllChildren()) {
				sum += child.getValue();
			}
			this.setNodeValue(sum, 0);
			if(!newTrees.contains(this)) {
				newTrees.add(this);				
			}
		}
		else {
			for(int i = n; i > 0; i--) {			
				for(Tree t : nodes.get(i).getAllChildren()) {
					nodeMax += t.getValue();
				}
				
				if(nodeMin == nodeMax) {
					this.setNodeValue(nodeMin, i);
				}
				else {
					DescriptionTree cln_t = (DescriptionTree) this.clone();
					for(int j = nodeMin; j <= nodeMax; j++) {
						cln_t.setNodeValue(j, i);
						newTrees.addAll(cln_t.evaluateTree(i-1));
						cln_t = (DescriptionTree) this.clone();
					}				
				}
			}			
		}
		
		return newTrees;
	}

	@Override
	protected void setAllLeafValues() {
		descriptionTree = descriptionTree.setAllLeafValues(alpha);
		
	}
}
