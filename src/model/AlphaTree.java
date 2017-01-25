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
		
		
		//nodeMax += nodes.get(n).getChild(nodes.get(n).getNumChildren()-1).getValue();
		
		if(n <= 0) {
			for(int j = 0; j < nodes.get(0).getNumChildren()-1; j++) {
				nodeMin += nodes.get(0).getChild(j).getValue();
				
			}
			for(Tree t : nodes.get(0).getAllChildren()) {
				nodeMax += t.getValue();
			}
			for(int j = nodeMin; j <= nodeMax; j++) {
				DescriptionTree cln_t = (DescriptionTree) this.clone();
				cln_t.setNodeValue(j, 0);
				newTrees.add(cln_t);
			}
		}
		else {
			for(int i = n; i >= 0; i--) {			
				nodeMin = alpha;
				nodeMax = beta;
				nodes = descriptionTree.getNodes();
				for(int j = 0; j < nodes.get(i).getNumChildren()-1; j++) {
					nodeMin += nodes.get(i).getChild(j).getValue();
					
				}
				for(int j = 0; j < nodes.get(i).getNumChildren(); j++) {
					nodeMax += nodes.get(i).getChild(j).getValue();
				}
				
				if(i == 0) {
					DescriptionTree cln_t = (DescriptionTree) this.clone();
					for(int j = nodeMin; j <= nodeMax; j++) {
						cln_t.setNodeValue(j, 0);
						newTrees.add(cln_t);
						cln_t = (DescriptionTree) this.clone();
					}
				}
				else if(nodeMin == nodeMax) {
					this.setNodeValue(nodeMin, i);
				}
				else {
					DescriptionTree cln_t = (DescriptionTree) this.clone();
					for(int j = nodeMin; j <= nodeMax; j++) {
						cln_t.setNodeValue(j, i);
						newTrees.addAll(cln_t.evaluateTree(i-1));
						cln_t = (DescriptionTree) this.clone();
					}
					if(i == 1) {
						i--;
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
