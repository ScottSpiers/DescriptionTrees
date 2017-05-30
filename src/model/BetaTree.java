package model;

import java.util.ArrayList;
import java.util.List;

import model.scala.Tree;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Conccrete class for BetaTrees
 */
public class BetaTree extends DescriptionTree {

	/**
	 * Default Constructor creating an empty tree
	 */
	public BetaTree() {
		super();
	}
	
	/**
	 * Constructor building the description tree from an existing tree
	 * @param t The tree to build the description tree from
	 */
	public BetaTree(Tree t) {
		super(t);
	}
	
	/**
	 * Constructs a description tree with the valuation parameters set
	 * @param a The first parameter
	 * @param b The second parameter
	 */
	public BetaTree(int a, int b) {
		super(a, b);
	}
	
	/**
	 * Constructs a description tree with the valuation parameters set 
	 * @param t The tree to build the description tree from
	 * @param a The first parameter
	 * @param b The second parameter
	 */
	public BetaTree(Tree t, int a , int b) {
		super(t, a, b);
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.DescriptionTree#evaluateTree(int)
	 * 
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
		
		//if we have no children simply return the tree
		//its a leaf so has already been valued
		if(getNumChildren() < 1) {
			newTrees.add(this);
			return newTrees;
		}
		
		//if we are setting the root node
		if(n == 0) {
			nodeMax = beta;
			//calculate maximum value
			for(Tree t : nodes.get(0).getAllChildren()) {
				nodeMax += t.getValue();
			}
			this.setNodeValue(nodeMax, 0); //set this as the nodes value as stated by the definition
			if(!newTrees.contains(this)) {
				newTrees.add(this);				
			}
		}
		else { //otherwise
			for(int i = n; i >= 0; i--) {			
				nodeMax = beta;
				nodes = descriptionTree.getNodes(); //get the updated nodes
				for(Tree t : nodes.get(i).getAllChildren()) {
					nodeMax += t.getValue(); //calculate the max value
				}
				
				//if we are at the root node
				if(i == 0) {
					this.setNodeValue(nodeMax, 0); //simply set its value
					if(!newTrees.contains(this)) {
						newTrees.add(this);				
					}
				}
				else if(nodeMin == nodeMax) { //if we only have one possibility
					this.setNodeValue(nodeMin, i); //set the nodes value to this value
				}
				else {
					DescriptionTree cln_t = (DescriptionTree) this.clone(); //clone the tree
					for(int j = nodeMin; j <= nodeMax; j++) { //for all possible values
						cln_t.setNodeValue(j, i); //set the clone's node at i to the current value
						newTrees.addAll(cln_t.evaluateTree(i-1)); //evaluate this new tree
						cln_t = (DescriptionTree) this.clone(); //re-clone the tree
					}
					i = 0; //we are now at the root as all nodes have been covered by the recursion
				}
			}			
		}
		
		return newTrees;
	}

	/*
	 * (non-Javadoc)
	 * @see model.DescriptionTree#setAllLeafValues()
	 */
	@Override
	protected void setAllLeafValues() {
		descriptionTree = descriptionTree.setAllLeafValues(alpha);
		
	}
}
