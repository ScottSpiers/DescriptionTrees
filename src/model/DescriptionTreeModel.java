package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.scala.Tree;


public class DescriptionTreeModel extends Observable {
	private List<DescriptionTree> trees;
	
	public DescriptionTreeModel() {
		trees = new ArrayList<DescriptionTree>();
	}
	
	public void genTrees(DescriptionTree t, int n) {
		trees.clear();
		DescriptionTree root = t;
		DescriptionTree tree1 = root;
		
		if(n > 0) { //change to if
			tree1.addLeaf();
		}
		trees.add(tree1);
		
		DescriptionTree midTree = null;
		if(n - 1 > 0) {
			midTree = root;
			midTree.addLeaf();
			
			if(midTree.getNodes() != null) {		
				for(Tree node : midTree.getNodes()) {
					DescriptionTree tree;
					if(t instanceof AlphaTree) {
						tree = new AlphaTree(node);
					}
					else {
						tree = new BetaTree(node);
					}
					genTrees(tree, n-2);
				}
			}
			trees.add(midTree);
		}
		genSimpleTree(t, n);
		
		this.setChanged();
		this.notifyObservers(trees);
		
	}
	
	private void genSimpleTree(DescriptionTree t, int n) {
		DescriptionTree tree = t;
		
		if(n > 0) {
			tree.addLeaf();
			DescriptionTree child;
			if(t instanceof AlphaTree) {
				child = new AlphaTree(tree.getChild(0));
			}
			else {
				child = new BetaTree(tree.getChild(0));
			}
			genSimpleTree(child, n -1);			
		}
		else {
			trees.add(tree);			
		}
	}
}
