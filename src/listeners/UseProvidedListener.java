package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.DescriptionTreeView;

public class UseProvidedListener implements ActionListener {

	DescriptionTreeView view;
	
	public UseProvidedListener(DescriptionTreeView view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		view.toggleNodesEnabled();		
	} 
	
}
