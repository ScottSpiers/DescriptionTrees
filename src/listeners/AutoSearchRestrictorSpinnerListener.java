package listeners;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.OEISAutoSearchView;

/**
 * 
 *  @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Listener for auto search spinners:
 * Updates the appropriate Restrictor's max value
 */
public class AutoSearchRestrictorSpinnerListener implements ChangeListener {

	private OEISAutoSearchView view;
	
	/**
	 * 
	 * @param view The auto search view being used
	 */
	public AutoSearchRestrictorSpinnerListener(OEISAutoSearchView view) {
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner s = (JSpinner) e.getSource(); //Get the spinner
		Box indiRestrictorBox = (Box) s.getParent(); //Get its parent box
		Box restrictorsBox = (Box) indiRestrictorBox.getParent(); // Get the grandparent box
		
		int index = 0; //track the current index
		//for every component in the grandparent box
		for(Component c : restrictorsBox.getComponents()) {
			if(indiRestrictorBox.equals(c)) { //if the current component is the parent box
				//this is where the spinner is
				view.getRestrictor(index).setMax((int) s.getValue()); //modify the restrictor at index
			}
			else { //otherwise
				index++; //not this index so increment
			}
		}		
	}
}
