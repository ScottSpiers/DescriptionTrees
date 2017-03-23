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
	
	public AlphaTree(Tree t, int a, int b) {
		super(t, a , b);
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
		
		//if we have no children simply return the tree
		//its a leaf so has already been valued
		if(getNumChildren() < 1) {
			newTrees.add(this);
			return newTrees;
		}
		
		//if we are setting the root node
		if(n <= 0) {
			//calculate minimum value
			for(int j = 0; j < nodes.get(0).getNumChildren()-1; j++) {
				nodeMin += nodes.get(0).getChild(j).getValue();
				
			}
			//calculate maximum value
			for(Tree t : nodes.get(0).getAllChildren()) {
				nodeMax += t.getValue();
			}
			//foreach of these values
			for(int j = nodeMin; j <= nodeMax; j++) {
				DescriptionTree cln_t = (DescriptionTree) this.clone(); //clone the tree
				cln_t.setNodeValue(j, 0); //value the root
				newTrees.add(cln_t); //add the clone
			}
		}
		else {
			for(int i = n; i >= 0; i--) {			
				nodeMin = alpha; //initialise min
				nodeMax = beta; //initialise max
				nodes = descriptionTree.getNodes(); //get the nodeList
				//calculate min
				for(int j = 0; j < nodes.get(i).getNumChildren()-1; j++) {
					nodeMin += nodes.get(i).getChild(j).getValue();
					
				}
				//calculate max
				for(int j = 0; j < nodes.get(i).getNumChildren(); j++) {
					nodeMax += nodes.get(i).getChild(j).getValue();
				}
				
				//if we are at the root node
				if(i == 0) {
					DescriptionTree cln_t = (DescriptionTree) this.clone(); // clone the tree
					for(int j = nodeMin; j <= nodeMax; j++) { //for every possible value
						cln_t.setNodeValue(j, 0); //modify the clone
						newTrees.add(cln_t); //store the modified tree
						cln_t = (DescriptionTree) this.clone(); //reset the clone
					}
				}
				else if(nodeMin == nodeMax) { //if we only have one possible value
					this.setNodeValue(nodeMin, i); //set the node
				}
				else { //otherwise
					DescriptionTree cln_t = (DescriptionTree) this.clone(); //clone the tree
					for(int j = nodeMin; j <= nodeMax; j++) { //for every possible value
						cln_t.setNodeValue(j, i); //set the node value
						newTrees.addAll(cln_t.evaluateTree(i-1)); //store the result of the recursion creating the remaining trees
						cln_t = (DescriptionTree) this.clone(); // reset the tree
					}
					i = 0; //we are now at the root as all nodes have been covered by the recursion
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
