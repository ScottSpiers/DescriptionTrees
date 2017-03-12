package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DescriptionTreeModel;
import tools.OEISAutoSearcher;

public class AutoSearchListener implements ActionListener {
	
	private DescriptionTreeModel model;
	
	public AutoSearchListener(DescriptionTreeModel model) {
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		new OEISAutoSearcher(model);
	}

}
