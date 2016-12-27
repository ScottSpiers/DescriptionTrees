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
		/*if(n == 1) {
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
		this.notifyObservers(trees);*/
		return trees;
		
	}
	
	private List<DescriptionTree> genTrees(int n) {
		List<DescriptionTree> treeList = new ArrayList<DescriptionTree>();
		if(n == 1) {
			DescriptionTree root = new AlphaTree();
			treeList.add(root);
			return treeList;
		}
		else {
			List<DescriptionTree> prevTrees = genTrees(n-1);
			for(DescriptionTree t : prevTrees) {
				DescriptionTree cln_t = (DescriptionTree) t.clone();
				//cln_t.addRoot();
				treeList.add(cln_t);
				
				DescriptionTree cln2_t = (DescriptionTree) t.clone();
				//cln2_t.getRoot().addLeaf();
				treeList.add(cln2_t);
				
				//need to make sure we have clean copy of t every time we do this
				DescriptionTree cln3_t = (DescriptionTree) t.clone();
				//for(DescriptionTree l : cln3_t.getLeaves()) {
				//	l.addLeaf();
				//  treeList.add(cln3_t);
				//}
				
				//again make sure we have clean copy of t every time we do this
				DescriptionTree cln4_t = (DescriptionTree)	t.clone();
				//for(DescriptionTree n : cln4_t.getNodes()) {
				//	n.addLeaf();
				//treeList.add(cln4_t);
				//}				
			}
			return treeList;
				
		}
		//add root to genTrees(n-1)
		//add leaf to root of all genTrees(n-1)
		//forall leaves in genTrees(n-1): addLeaf()
		//forall nodes in genTrees(n-1); addLeaf()
	}
}
