package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DescriptionTreeModel;
import view.DescriptionTreeView;
import view.RestrictionPane;

public class AddRestrictionListener implements ActionListener {

	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	public AddRestrictionListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new RestrictionPane(model, view);		
	}

}
