package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DescriptionTreeModel;
import view.OEISAutoSearchView;

public class AutoSearchToolListener implements ActionListener {

	private DescriptionTreeModel model;
	
	public AutoSearchToolListener(DescriptionTreeModel model) {
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		new OEISAutoSearchView(model);
	}
}
