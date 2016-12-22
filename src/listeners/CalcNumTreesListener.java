package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.AlphaTree;
import model.DescriptionTreeModel;
import model.scala.Leaf;
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
		model.resetTrees();
		model.genTrees(new AlphaTree(new Leaf(0)), 4);
		
	}

}
