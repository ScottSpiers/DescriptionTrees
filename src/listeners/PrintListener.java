package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.AlphaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;
import model.Restrictor;
import tools.FileManager;
import view.DescriptionTreeView;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Listener for printing results to text
 *
 */
public class PrintListener implements ActionListener {

	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public PrintListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<DescriptionTree> trees = model.getTrees(); //get the list of trees
		String type;
		
		//if the list is empty
		if(trees.isEmpty()) {
			//inform the user
			view.displayError("Save Error", "There are no trees to print. Please calculate the number of trees for your input parameters first");
			return;
		}
		else { //otherwise
			//if the trees are alpha trees
			if(trees.get(0) instanceof AlphaTree) {
				type = "Alpha"; //set the type the alpha
			}
			else { //otherwise
				//set it to alpha
				type = "Beta";
			}
		}
		
		String strOut = "";
				
		strOut += "Tree type: " + type + "\n"; //print the tree type
		//print num nodes
		strOut += "Nodes: Minimum: " + view.getNodeMin() + " Maximum: " + view.getNodeMax() + "\n";	//print the node nums
		//print a and b
		strOut += "First Parameter (a): " + view.getParamA() + " Second Parameter (b): " + view.getParamB() + "\n"; //print the value params
		//print restrictions
		for(Restrictor r : model.getRestrictors()) {
			strOut += r.toString() + "\n";
		}
		
		strOut += "Total Number of Trees: " + trees.size();
		strOut += "\n"; //add a newline
		
		//print he trees
		for(DescriptionTree dt : trees) {
			strOut += dt.printTree() + "\n\n";
		}
		
		boolean saved = FileManager.saveAsText(view.getFrame(), strOut);
		if(saved) {
			view.displayMessage("File Saved", "File Saved");
		}
				
	}	
}
