package listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JCheckBox;

import view.OEISAutoSearchView;

public class AutoSearchCheckBoxSelectedListener implements ActionListener {
	
	private OEISAutoSearchView view;
	
	public AutoSearchCheckBoxSelectedListener(OEISAutoSearchView view) {
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Convert the source to the checkbox we know it is
		JCheckBox chkbx_changed = (JCheckBox) e.getSource();
		Box indiRestrictorBox = (Box) chkbx_changed.getParent(); //get the box it is in
		Box restrictorBox = (Box) indiRestrictorBox.getParent(); //get the grandparent box
		Component spinner = indiRestrictorBox.getComponent(3); //get the spinner for enabling/disabling
		
		//toggle the spinner
		spinner.setEnabled(!spinner.isEnabled());
		
		int index = 0; //counter
		//loop over the components in grandparent box
		for(Component c : restrictorBox.getComponents()) {
			if(indiRestrictorBox.equals(c)) { //if the parent box is the current component
				//this is the restriction we are toggling so call using the counter
				view.toggleRestrictionSelected(index); 
			}
			else {
				index++; //increment counter
			}
		}
		
		
	}
}
