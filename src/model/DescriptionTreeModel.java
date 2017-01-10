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
	
	public List<DescriptionTree> genTrees(int n) {
		List<DescriptionTree> treeList = new ArrayList<DescriptionTree>();
		if(n == 1) {
			DescriptionTree root = new AlphaTree();
			root.addLeaf();
			treeList.add(root);
			return treeList;
		}
		else {
			List<DescriptionTree> prevTrees = genTrees(n-1);
			for(DescriptionTree t : prevTrees) {
				DescriptionTree cln_t = (DescriptionTree) t.clone();
				cln_t.addRoot();
				if(!treeList.contains(cln_t)) {
					treeList.add(cln_t);						
				}
				
				DescriptionTree cln2_t = (DescriptionTree) t.clone();
				cln2_t.addLeaf();
				if(!treeList.contains(cln2_t)) {
					treeList.add(cln2_t);						
				}
				
				//need to make sure we have clean copy of t every time we do this
				DescriptionTree cln3_t = (DescriptionTree) t.clone();
				List<Tree> leaves = cln3_t.getLeaves();
				for(int i = 0; i < leaves.size(); i++) {
					cln3_t.addLeafToLeaf(i);
					if(!treeList.contains(cln3_t)) {
						treeList.add(cln3_t);
					}
					cln3_t = (DescriptionTree) t.clone();
				}
				/*for(Tree l : cln3_t.getLeaves()) {
					System.out.println("Pre change: " + cln3_t);
					l = l.addLeaf();
					System.out.println("Post change: " + cln3_t);
					if(!treeList.contains(cln3_t)) {
						treeList.add(cln3_t);
					}
				    cln3_t = (DescriptionTree) t.clone();
				}*/
				
				//again make sure we have clean copy of t every time we do this
				DescriptionTree cln4_t = (DescriptionTree)	t.clone();
				for(Tree node : cln4_t.getNodes()) {
					//System.out.println(node);
					node = node.addLeaf();
					//System.out.println(node);
					if(!treeList.contains(cln4_t)) {
						treeList.add(cln4_t);						
					}
				    cln4_t = (DescriptionTree) t.clone();
				}				
			}
			
			trees.addAll(treeList);
			for(int i = 0; i < trees.size(); i++) {
				if(trees.get(i).getNumVertices() != n) {
					trees.remove(trees.get(i));
					i--;
				}
			}
			
			this.setChanged();
			this.notifyObservers(trees);
			return trees;
				
		}
		//add root to genTrees(n-1)
		//add leaf to root of all genTrees(n-1)
		//forall leaves in genTrees(n-1): addLeaf()
		//forall nodes in genTrees(n-1); addLeaf()
	}
}
