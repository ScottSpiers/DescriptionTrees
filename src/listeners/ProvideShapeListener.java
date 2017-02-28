package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CH.ifa.draw.application.DrawApplication;
import model.AlphaTree;
import model.BetaTree;
import model.DescriptionTreeModel;
import view.DescriptionTreeView;
import view.TreeCreatorView;

public class ProvideShapeListener implements ActionListener {
	
	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	public ProvideShapeListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DrawApplication shapeProvider = new TreeCreatorView(model, view);
		shapeProvider.open();
	}	
}
