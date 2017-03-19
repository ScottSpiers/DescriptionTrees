package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DescriptionTreeModel;
import tools.OEISAutoSearcher;
import view.OEISAutoSearchView;

/**
 * 
 *  @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Listener for running the auto search
 */
public class RunAutoSearchListener implements ActionListener {
	
	private DescriptionTreeModel model;
	private OEISAutoSearchView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The auto search view being used
	 */
	public RunAutoSearchListener(DescriptionTreeModel model, OEISAutoSearchView view) {
		this.model = model;
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new OEISAutoSearcher(model, view);
	}

}
