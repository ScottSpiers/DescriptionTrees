package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.scala.Tree;


public class DescriptionTreeModel extends Observable {
	private List<DescriptionTree> trees;
	private List<Restrictor> restrictors;
	private Tree providedTree;
	
	public DescriptionTreeModel() {
		trees = new ArrayList<DescriptionTree>();
		restrictors = new ArrayList<Restrictor>();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<DescriptionTree> getTrees() {
		return trees;
	}
	
	public void addRestrictor(Restrictor r) {
		if(!restrictors.contains(r)) {
			restrictors.add(r);
			this.setChanged();
			this.notifyObservers(false);			
		}
	}
	
	public void removeRestrictor(Restrictor r) {
		restrictors.remove(r);
		this.setChanged();
		this.notifyObservers(false);
	}
	
	public void addTrees(List<DescriptionTree> ts) {
		trees.addAll(trees.size(), ts);
		this.setChanged();
		this.notifyObservers(true);
	}
	
	public void removeTree(int i) {
		trees.remove(i);
		this.setChanged();
		this.notifyObservers(true);
	}
	
	public void removeTree(DescriptionTree t) {
		trees.remove(t);
		this.setChanged();
		this.notifyObservers(true);
	}
	
	public void resetTrees() {
		trees.clear();
	}
	
	public void removeDuplicates(List<DescriptionTree> ts) {
		for(int i = 0; i < ts.size(); i++) {
			for(int j = 0; j < ts.size(); j++) {
				if(i != j && ts.get(i).equals(ts.get(j))) {
					ts.remove(j);
					j--;
				}
			}
		}
	}
	
	public int getNumTrees() {
		return trees.size();
	}
	
	public List<Restrictor> getRestrictors() {
		return restrictors;		
	}
	
	public void setProvidedTree(Tree t) {
		providedTree = t;
		this.setChanged();
		this.notifyObservers(t);
	}
	
	public Tree getProvidedTree() {
		return providedTree;
	}
	
	public void restrictTrees() {
		for(int i = 0; i < trees.size(); i++) {
			for(Restrictor r : restrictors) {
				if(!r.applyRestriction(trees.get(i))) {
					trees.remove(i);
					i--;
				}
			}
			
		}
		
		this.setChanged();
		this.notifyObservers(true);
	}
	
	public List<DescriptionTree> applyRestrictions(List<DescriptionTree> ts) {
		for(int i = 0; i< ts.size(); i++) {			
			for(Restrictor r : restrictors) {
				if(r.getMin() > r.getMax()) {
					return null;
				}
				if(!r.applyRestriction(ts.get(i))) {
					ts.remove(i);
					i--;
				}
			}
		}
		return ts;
	}
	
	public List<DescriptionTree> genTrees(DescriptionTree tree, int n) {
		List<DescriptionTree> treeList = new ArrayList<DescriptionTree>();
		if(n == 0) {
			return treeList;
		}
		if(n == 1) {
			DescriptionTree root = tree;
			root.addLeaf();
			treeList.add(root);
			return treeList;
		}
		else {
			List<DescriptionTree> prevTrees = genTrees(tree, n-1);
			for(DescriptionTree t : prevTrees) {
				DescriptionTree cln_t = (DescriptionTree) t.clone();
				cln_t.addRoot();
				if(!treeList.contains(cln_t)) {
					treeList.add(cln_t);						
				}
				
				DescriptionTree cln2_t = (DescriptionTree) t.clone();
				for(int i = 0; i <= cln2_t.getNumChildren(); i++) {
					cln2_t.addLeafAsChildAt(i);
					if(!treeList.contains(cln2_t)) {
						treeList.add(cln2_t);						
					}
					cln2_t = (DescriptionTree) t.clone();
				}
				
				/*cln2_t = (DescriptionTree) t.clone();
				cln2_t.addLeaf();
				if(!treeList.contains(cln2_t)) {
					treeList.add(cln2_t);						
				}*/
				
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
				
				//again make sure we have clean copy of t every time we do this
				DescriptionTree cln4_t = (DescriptionTree)	t.clone();
				List<Tree> nodes = cln4_t.getNodes();
				for(int i = 0; i < nodes.size(); i++) {
					cln4_t.addLeafToNode(i);
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
