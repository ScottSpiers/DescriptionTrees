package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DescriptionTreeModel;
import tools.OEISAutoSearcher;
import view.OEISAutoSearchView;

public class AutoSearchListener implements ActionListener {
	
	private DescriptionTreeModel model;
	private OEISAutoSearchView view;
	
	public AutoSearchListener(DescriptionTreeModel model, OEISAutoSearchView view) {
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		new OEISAutoSearcher(model, view);
	}

}
