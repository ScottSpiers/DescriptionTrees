package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.scala.Tree;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * The model for the application. Stores trees and restrictions.
 * Provides tree generation functioanltiy as well as methods to get and add trees/restrictors
 */
public class DescriptionTreeModel extends Observable {
	private List<DescriptionTree> trees;
	private List<Restrictor> restrictors;
	private Tree providedTree;
	
	/**
	 * Constructor to initialise the model
	 */
	public DescriptionTreeModel() {
		trees = new ArrayList<DescriptionTree>();
		restrictors = new ArrayList<Restrictor>();
	}
	
	/**
	 * 
	 * @return the list of trees
	 */
	public List<DescriptionTree> getTrees() {
		return trees;
	}
	
	/**
	 * Adds a restrictor to the list
	 * @param r The Restrictor to add
	 */
	public void addRestrictor(Restrictor r) {
		if(!restrictors.contains(r)) {
			restrictors.add(r);
			this.setChanged();
			this.notifyObservers(false);			
		}
	}
	
	/**
	 * Removes a restrictor from the list
	 * @param r The Restrictor to remove
	 */
	public void removeRestrictor(Restrictor r) {
		restrictors.remove(r);
		this.setChanged();
		this.notifyObservers(false);
	}
	
	/**
	 * Adds all trees in a list to the tree list
	 * @param ts The list of trees to add
	 */
	public void addTrees(List<DescriptionTree> ts) {
		trees.addAll(trees.size(), ts);
		this.setChanged();
		this.notifyObservers(true);
	}
	
	/**
	 * Removes a tree from the list
	 * @param i The index of the tree to remove
	 */
	public void removeTree(int i) {
		trees.remove(i);
		this.setChanged();
		this.notifyObservers(true);
	}
	
	/**
	 * Removes a tree from the list
	 * @param t The tree to remove
	 */
	public void removeTree(DescriptionTree t) {
		trees.remove(t);
		this.setChanged();
		this.notifyObservers(true);
	}
	
	/**
	 * Empty the tree list
	 */
	public void resetTrees() {
		trees.clear();
	}
	
	/**
	 * Removes duplicates from a tree list
	 * @param ts The list of trees to remove duplicates from
	 */
	public void removeDuplicates(List<DescriptionTree> ts) {
		for(int i = 0; i < ts.size(); i++) { //for all trees
			for(int j = 0; j < ts.size(); j++) { //again for all trees
				if(i != j && ts.get(i).equals(ts.get(j))) { //if the index isn't the same and we have the same tree
					ts.remove(j); //remove it
					j--; //decrement the index
				}
			}
		}
	}
	
	/**
	 * 
	 * @return the number of trees
	 */
	public int getNumTrees() {
		return trees.size();
	}
	
	/**
	 * 
	 * @return the restrictors list
	 */
	public List<Restrictor> getRestrictors() {
		return restrictors;		
	}
	
	/**
	 * Provide a tree to be used for calculation
	 * @param t The tree provided
	 */
	public void setProvidedTree(Tree t) {
		providedTree = t;
		this.setChanged();
		this.notifyObservers(t);
	}
	
	/**
	 * 
	 * @return the currently provided tree
	 */
	public Tree getProvidedTree() {
		return providedTree;
	}
	
	/**
	 * Applies restrictions to all trees in the provided list
	 * @param ts The list of trees to apply the restrictions to
	 * @return the new list with all non-matching trees removed
	 */
	public List<DescriptionTree> applyRestrictions(List<DescriptionTree> ts) {
		for(int i = 0; i< ts.size(); i++) {	//for all trees
			for(Restrictor r : restrictors) { //for every restrictor
				if(r.getMin() > r.getMax()) {  //error check
					return null; //error return null
				}
				if(!r.applyRestriction(ts.get(i))) { //if the current tree doesn't match
					ts.remove(i); //remove it
					i--; //decrement the index
					break; //no need to check with other restrictors
				}
			}
		}
		return ts;
	}
	
	/**
	 * Genrate all trees of n nodes
	 * @param tree The starting tree used for the recursion
	 * @param n The number of nodes within the trees to be created
	 * @return The list of all generated trees
	 */
	public List<DescriptionTree> genTrees(DescriptionTree tree, int n) {
		List<DescriptionTree> treeList = new ArrayList<DescriptionTree>();
		if(n == 0) { //if we want 0 node trees
			return treeList; //return
		}
		if(n == 1) { //if we want a single node tree
			DescriptionTree root = tree;
			root.addLeaf();
			treeList.add(root); //add it to the list
			return treeList;
		}
		else {
			List<DescriptionTree> prevTrees = genTrees(tree, n-1); //get the list of trees with the n-1 nodes
			for(DescriptionTree t : prevTrees) { //for each of these trees
				DescriptionTree cln_t = (DescriptionTree) t.clone(); //clone it
				cln_t.addRoot(); //give it a root node to make a new tree
				if(!treeList.contains(cln_t)) {
					treeList.add(cln_t);						
				}
				
				//add root to genTrees(n-1)
				//add leaf to root of all genTrees(n-1)
				//forall leaves in genTrees(n-1): addLeaf()
				//forall nodes in genTrees(n-1); addLeaf()
				
				//need to make sure we have clean copy of t every time we do this
				DescriptionTree cln2_t = (DescriptionTree) t.clone();
				for(int i = 0; i <= cln2_t.getNumChildren(); i++) { //for the number of children
					cln2_t.addLeafAsChildAt(i); //add a leaf at the current position of the root
					if(!treeList.contains(cln2_t)) {
						treeList.add(cln2_t);						
					}
					cln2_t = (DescriptionTree) t.clone(); //re-clone as to only add one leaf
				}
				
				//need to make sure we have clean copy of t every time we do this
				DescriptionTree cln3_t = (DescriptionTree) t.clone();
				List<Tree> leaves = cln3_t.getLeaves(); //get the leaves of the tree
				for(int i = 0; i < leaves.size(); i++) { //for all leaves
					cln3_t.addLeafToLeaf(i); //add a leaf to it
					if(!treeList.contains(cln3_t)) {
						treeList.add(cln3_t);
					}
					cln3_t = (DescriptionTree) t.clone(); //re-clone so only one modification is made at a time
				}
				
				//again make sure we have clean copy of t every time we do this
				DescriptionTree cln4_t = (DescriptionTree)	t.clone();
				List<Tree> nodes = cln4_t.getNodes(); //get all the nodes in the tree
				for(int i = 0; i < nodes.size(); i++) { //for each of these
					cln4_t.addLeafToNode(i); //add a leaf to the node
					if(!treeList.contains(cln4_t)) { 
						treeList.add(cln4_t);
					}
					cln4_t = (DescriptionTree) t.clone();
				}		
			}
			
			trees.addAll(treeList); //add all the new trees to the list
			for(int i = 0; i < trees.size(); i++) { //for all trees
				if(trees.get(i).getNumVertices() != n) { //if the number of vertices doesn't match n
					trees.remove(trees.get(i)); //remove it
					i--;
				}
			}			
			//notify observers
			this.setChanged();
			this.notifyObservers(trees);
			return trees;
				
		}
	}
}
