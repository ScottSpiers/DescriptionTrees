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

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Listener for running a calculation
 */
public class CalcNumTreesListener implements ActionListener {
	
	private DescriptionTreeView view;
	private DescriptionTreeModel model;
	
	/**
	 * 
	 * @param view The DescriptionTreeView being used
	 * @param model The DescriptionTreeModel being used
	 */
	public CalcNumTreesListener(DescriptionTreeView view, DescriptionTreeModel model) {
		this.view = view;
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<DescriptionTree> newTrees = new ArrayList<DescriptionTree>(); //List for the trees
		DescriptionTree tree = null; 
		boolean isAlpha = false;
		model.resetTrees(); //reset the treeList
		
		if(view.getChecked()) { //is using provided tree
			if(model.getProvidedTree() != null) { //if there exists such a tree
				int paramA = view.getParamA(); //get the first param
				int paramB = view.getParamB(); //get the second param
				if(view.isAlphaChecked()) { // is Alpha the tree type
					//if so create an AlphaTree
					tree = new AlphaTree(model.getProvidedTree(), paramA, paramB);
				}
				else if(view.isBetaChecked()) { //is Beta the tree type
					//if so create a BetaTree
					tree = new BetaTree(model.getProvidedTree(), paramA, paramB);
				}
				else { //otherwise
					//display the error
					view.displayError("Select a Tree Type", "Please Select either Alpha Tree or Beta Tree");
					return;
				}
			} //otherwise
			else { //display the error
				view.displayError("No Tree Provided", "You must provide a tree shape before calculating");
				return;
			}
			newTrees.addAll(tree.evaluateTree(tree.getNodes().size()-1)); //generate and valuate the tree
			newTrees = model.applyRestrictions(newTrees); //restrict the trees
			if(newTrees == null) { //if the list is now null
				//we had a restriction with an error
				view.displayError("Restriction Error", "A restriction has a minumum value greater than a maximum. Please resolve this issue");
				return;
			}
			view.setSequence(""); //using a provided tree means a single number of nodes
			model.resetTrees();
			model.addTrees(newTrees);
		}
		else { //otherwise
			int nodeMin = view.getNodeMin(); //get the min number of nodes
			int nodeMax = view.getNodeMax(); //get the max number of nodes
			
			//if user entered a min greater than max
			if(nodeMin > nodeMax) {
				//display the error
				view.displayError("Node Number Error", "Minimum number of nodes is greater than maximum.");
				return;
			}
			
			int paramA = view.getParamA(); //get the first param
			int paramB = view.getParamB(); //get the second param
			if(view.isAlphaChecked()) { //if tree type is Alpha
				isAlpha = true;	
			}
			else if(view.isBetaChecked()) { //if tree type is Beta
				isAlpha = false;				
			}
			else { //otherwise
				//display error: we must have a tree type
				view.displayError("Select a Tree Type", "Please Select either Alpha Tree or Beta Tree");
				return;
			}
			
			//integer array to keep track of a sequence
			int[] nodeSeq = new int[(nodeMax - nodeMin) + 1];
			//loop over the values min to max
			for(int i = nodeMin; i <= nodeMax; i++) {
				// create the tree based on the selected type
				if(isAlpha) {
					tree = new AlphaTree(paramA, paramB);
				}
				else {
					tree = new BetaTree(paramA, paramB);
				}
				
				//if we don't have to valuate
				if(paramA == 0 && paramB == 0) {
					newTrees.addAll(model.genTrees(tree, i)); //skip the process
				}
				else { //otherwise
					for(DescriptionTree dt : model.genTrees(tree, i)){
						//valuate the trees
						newTrees.addAll(dt.evaluateTree(dt.getNodes().size()-1));
					}					
				}
				
				//apply restrictions
				newTrees = model.applyRestrictions(newTrees);
				if(newTrees == null) { //if the tree list is null
					//we had a restriction error, display it
					view.displayError("Restriction Error", "A restriction has a minumum value greater than a maximum. Please resolve this issue");
					return;
				}
				
				model.removeDuplicates(newTrees);
				
				//if our current run is not the first
				if((i - nodeMin) > 0) {
					int oldTrees = 0; //track already generated trees
					//loop over previous runs in the tree sequence
					for(int j = 0; j < i - nodeMin; j++) {
						oldTrees += nodeSeq[j]; //add any trees already generated
					}
					nodeSeq[i - nodeMin] = newTrees.size() - oldTrees; //new trees are the list size - old trees
				}
				else {
					nodeSeq[i - nodeMin] = newTrees.size(); //set the index to the list size
				}
				
			}
			
			String seq = ""; //init the sequence string
			//loop over the sequence array
			for(int i = 0; i < nodeSeq.length - 1 ; i++) {
				seq += nodeSeq[i] + ","; //add the value and a comma to the string	
			}
			
			//add the last value
			seq += nodeSeq[nodeSeq.length - 1];
			view.setSequence(seq); //set the sequence in the view
		}			
		
		//reset the trees prior to addin all the new trees to avoid duplicates
		model.resetTrees();
		model.addTrees(newTrees); //add all the new trees
	}
}
