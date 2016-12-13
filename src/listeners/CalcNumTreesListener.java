package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.AlphaTree;
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
		model.genSimpleTree(new AlphaTree(), 2);
		model.genTrees(new AlphaTree(), 2);
		
	}

}
