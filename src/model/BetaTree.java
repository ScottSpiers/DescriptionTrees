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
		
		if(getNumChildren() < 1) {
			newTrees.add(this);
			return newTrees;
		}
		
		if(n <= 0) {
			nodeMax = beta;
			for(Tree t : nodes.get(0).getAllChildren()) {
				nodeMax += t.getValue();
			}
			this.setNodeValue(nodeMax, 0);
			if(!newTrees.contains(this)) {
				newTrees.add(this);				
			}
		}
		else {
			for(int i = n; i >= 0; i--) {			
				nodeMax = beta;
				nodes = descriptionTree.getNodes();
				for(Tree t : nodes.get(i).getAllChildren()) {
					nodeMax += t.getValue();
				}
				
				if(i == 0) {
					this.setNodeValue(nodeMax, 0);
					if(!newTrees.contains(this)) {
						newTrees.add(this);				
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
					i = 0;
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
