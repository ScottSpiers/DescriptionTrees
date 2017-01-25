package model;

import java.util.ArrayList;
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
		List<DescriptionTree> newTrees = new ArrayList<DescriptionTree>();
		setAllLeafValues();
		int nodeMin = alpha;
		int nodeMax = beta;
		List<Tree> nodes = descriptionTree.getNodes();
		
		for(int i = 0; i <= nodes.get(n).getNumChildren()-2; i++) {
			nodeMin += nodes.get(n).getChild(i).getValue();
			nodeMax += nodes.get(n).getChild(i).getValue();
		}
		nodeMax += nodes.get(n).getChild(nodes.get(n).getNumChildren()-1).getValue();
		
		if(n <= 0) {
			for(int j = nodeMin; j <= nodeMax; j++) {
				DescriptionTree cln_t = (DescriptionTree) this.clone();
				cln_t.setNodeValue(j, 0);
				newTrees.add(cln_t);
			}
		}
		else {
			for(int i = n; i > 0; i--) {			
				/*for(Tree t : nodes.get(i).getAllChildren()) {
					nodeMax += t.getValue();
				}*/
				
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
		descriptionTree = descriptionTree.setAllLeafValues(beta);
		
	}
}
