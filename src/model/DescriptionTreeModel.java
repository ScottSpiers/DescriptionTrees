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
	
	public void resetTrees() {
		trees.clear();
	}
	
	public void genTrees(DescriptionTree t, int n) {
		/*if(n == 1) {
			t.addLeaf();
			trees.add(t);
		}*/
		for(int i = n -1; i > 0; i--) {
			DescriptionTree newTree = t;
			int j = i;
			while(j > 0) {
				newTree.addLeaf();
				j--;
			}
			int k = (n-1) - i;
			if(k > 0) {
				for(Tree t1 : newTree.getNodes()) {
					DescriptionTree t2 = new AlphaTree(t1);
					genTrees(t2, k);
				}
			}
			else {
				if(!trees.contains(newTree)) {
					trees.add(newTree);					
				}
			}			
		}
		
		
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
