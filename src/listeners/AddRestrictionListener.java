package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DescriptionTreeModel;
import view.RestrictionPane;

public class AddRestrictionListener implements ActionListener {

	private DescriptionTreeModel model;
	
	public AddRestrictionListener(DescriptionTreeModel model) {
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		RestrictionPane restrictionSelect = new RestrictionPane(model);
		
	}

}
