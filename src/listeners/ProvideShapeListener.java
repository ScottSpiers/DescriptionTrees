package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CH.ifa.draw.application.DrawApplication;
import model.DescriptionTreeModel;
import view.DescriptionTreeView;
import view.TreeCreatorView;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Listener to open the trea creator view
 */
public class ProvideShapeListener implements ActionListener {
	
	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public ProvideShapeListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//open the trea creator view
		DrawApplication shapeProvider = new TreeCreatorView(model, view);
		shapeProvider.open();
	}	
}
