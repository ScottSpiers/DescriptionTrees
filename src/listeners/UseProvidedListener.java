package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.DescriptionTreeView;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Listener for the main gui's use provided check box
 * Disables the main gui's min and max node spinner
 */
public class UseProvidedListener implements ActionListener {

	DescriptionTreeView view;
	
	/**
	 * 
	 * @param view The DescriptionTreeView being used
	 */
	public UseProvidedListener(DescriptionTreeView view) {
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		view.toggleNodesEnabled(); //toggle the spinners on or off
	} 
	
}
