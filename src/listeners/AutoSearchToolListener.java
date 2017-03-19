package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DescriptionTreeModel;
import view.OEISAutoSearchView;

/**
 * 
 *  @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Listener for opening the auto search tool
 */
public class AutoSearchToolListener implements ActionListener {

	private DescriptionTreeModel model;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 */
	public AutoSearchToolListener(DescriptionTreeModel model) {
		this.model = model;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new OEISAutoSearchView(model);
	}
}
