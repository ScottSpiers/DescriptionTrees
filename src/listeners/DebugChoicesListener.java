package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.BetaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;
import model.scala.Tree;
import view.DescriptionTreeView;

public class DebugChoicesListener implements ActionListener {
	
	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	public DebugChoicesListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		List<DescriptionTree> trees = model.genTrees(new BetaTree(0,0), 5);
		int choices = 0;
		
		for(DescriptionTree dt : trees) {
			choices += choices(dt, 0);
		}
		
		view.displayMessage("Number of Possibilities", "There are " + choices + " possible B(1,0)-trees with 8 nodes");
	}
	
	private int choices(DescriptionTree t, int c) {
		int choices = 1;
		
		for(Tree child : t.getAllChildren()) {
			if(child.getNumChildren() > 1) {
				choices++;
				choices *= choices(new BetaTree(child, 0, 0), choices);
			}
		}			
		
		return choices;
	}

}
