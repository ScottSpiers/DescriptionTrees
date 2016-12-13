package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.scala.Node;

public class DescriptionTreeModel extends Observable {
	private List<DescriptionTree> trees;
	
	public DescriptionTreeModel() {
		trees = new ArrayList<DescriptionTree>();
	}
	
	public void genTrees(DescriptionTree t, int n) {
		DescriptionTree root = t;
		DescriptionTree tree1 = root;
		while(n > 0) {
			tree1.addLeaf();
		}
		trees.add(tree1);
		
		DescriptionTree midTrees;
		while(n - 1 > 0) {
			midTrees = root;
			midTrees.addLeaf();
		}
		for(Node node : midTree) {
			
		}
	}
}
