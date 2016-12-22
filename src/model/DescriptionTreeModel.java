package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.scala.Leaf;


public class DescriptionTreeModel extends Observable {
	private List<DescriptionTree> trees;
	
	public DescriptionTreeModel() {
		trees = new ArrayList<DescriptionTree>();
	}
	
	public void resetTrees() {
		trees.clear();
	}
	
	public List<DescriptionTree> genTrees(DescriptionTree t, int n) {
		if(n == 1) {
			t.addLeaf();
			trees.add(t);
		}
		
		int i = 0;
		for(i = n -1; i > 0; i--) {
			DescriptionTree newTree = (DescriptionTree) t.clone();
			int j = i;
			while(j > 0) {
				newTree.addLeaf();
				j--;
			}
			int k = (n-1) - i;
			if(k > 0) {
				List<DescriptionTree> ts = genRestTrees(k);
				for(int m = 0; m < ts.size(); m++) {
					//for every new gen'd tree
					//for each child of the root
					for(int l = ((n - 1) - k) - 1; l >= 0; l--) {
						DescriptionTree nt = (DescriptionTree) newTree.clone();
						DescriptionTree child = new AlphaTree(nt.getChild(l));
						child.addSubtrees(ts.get(m));
						if(!trees.contains(nt)) {
							trees.add(nt);					
						}
					}
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
		return trees;
		
	}
	
	private List<DescriptionTree> genRestTrees(int n) {
		//List<DescriptionTree> trees = new ArrayList<DescriptionTree>();
		DescriptionTree root = new AlphaTree(new Leaf(0));
		return genTrees(root, n);		
	}
}
