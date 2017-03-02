package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.AlphaTree;
import model.BetaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;
import view.DescriptionTreeView;

public class CalcNumTreesListener implements ActionListener {
	
	private DescriptionTreeView view;
	private DescriptionTreeModel model;
	
	public CalcNumTreesListener(DescriptionTreeView view, DescriptionTreeModel model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<DescriptionTree> newTrees = new ArrayList<DescriptionTree>();
		DescriptionTree tree = null;
		model.resetTrees();
		
		if(view.getChecked()) {
			if(model.getProvidedTree() != null) {
				int paramA = view.getParamA();
				int paramB = view.getParamB();
				if(view.isAlphaChecked()) {
					tree = new AlphaTree(model.getProvidedTree(), paramA, paramB);
				}
				else if(view.isBetaChecked()) {
					tree = new BetaTree(model.getProvidedTree(), paramA, paramB);
				}
				else {
					view.displayError("Select a Tree Type", "Please Select either Alpha Tree or Beta Tree");
					return;
				}
			}
			else {
				view.displayError("No Tree Provided", "You must provide a tree shape before calculating");
				return;
			}
			newTrees.addAll(tree.evaluateTree(tree.getNodes().size()-1));
		}
		else {			
			int nodeMin = view.getNodeMin();
			int nodeMax = view.getNodeMax();
			//temp int
			int nodes = 0;
			//temp check
			if(nodeMin == nodeMax) {
				nodes = nodeMin;
			}
			
			int paramA = view.getParamA();
			int paramB = view.getParamB();
			if(view.isAlphaChecked()) {
				tree = new AlphaTree(paramA, paramB);
			}
			else if(view.isBetaChecked()) {
				tree = new BetaTree(paramA, paramB);
			}
			else {
				view.displayError("Select a Tree Type", "Please Select either Alpha Tree or Beta Tree");
				return;
			}
			
			
			int[] nodeSeq = new int[(nodeMax - nodeMin) + 1];
			for(int i = nodeMin; i <= nodeMax; i++) {
				System.out.println(tree);
				
				for(DescriptionTree dt : model.genTrees(tree, i)){
					newTrees.addAll(dt.evaluateTree(dt.getNodes().size()-1));
				}
				
				for(int j = 0; j < newTrees.size(); j++) {
					for(int k = 0; k < newTrees.size(); k++) {
						if(j != k && newTrees.get(j).equals(newTrees.get(k))) {
							newTrees.remove(k);
						}
					}
				}
				
				newTrees = model.applyRestrictions(newTrees);
				
				if((i - nodeMin) > 0) {
					nodeSeq[i - nodeMin] = newTrees.size() - nodeSeq[(i - nodeMin) - 1];
				}
				else {
					nodeSeq[i - nodeMin] = newTrees.size();
				}
				
			}
			
			String seq = "";
			for(int i = 0; i < nodeSeq.length - 1 ; i++) {
				seq += nodeSeq[i] + ",";				
			}
			
			seq += nodeSeq[nodeSeq.length - 1];
			view.setSequence(seq);
		}		
		
		
		model.resetTrees();
		model.addTrees(newTrees);
		//model.restrictTrees();
		
		//test output
		System.out.println("\nThe Trees:");
		for(DescriptionTree t : model.getTrees()) {
			System.out.println("\n" + t);
		}
	}

}
