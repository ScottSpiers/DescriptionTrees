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
			newTrees.addAll(tree.evaluateTree(tree.getNodes().size()-1));
		}
		else {
			model.resetTrees();
			
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
			for(DescriptionTree dt : model.genTrees(tree, nodes)){
				newTrees.addAll(dt.evaluateTree(dt.getNodes().size()-1));
			}
		}
		
		
		for(int i = 0; i < newTrees.size(); i++) {
			for(int j = 0; j < newTrees.size(); j++) {
				if(i != j && newTrees.get(i).equals(newTrees.get(j))) {
					newTrees.remove(j);
				}
			}
		}
		
		model.resetTrees();
		model.addTrees(newTrees);
		model.restrictTrees();
		
		//test output
		System.out.println("\nThe Trees:");
		for(DescriptionTree t : model.getTrees()) {
			System.out.println("\n" + t);
		}
	}

}
