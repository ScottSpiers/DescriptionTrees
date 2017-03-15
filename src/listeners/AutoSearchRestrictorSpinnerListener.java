package listeners;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.OEISAutoSearchView;

public class AutoSearchRestrictorSpinnerListener implements ChangeListener {

	private OEISAutoSearchView view;
	
	public AutoSearchRestrictorSpinnerListener(OEISAutoSearchView view) {
		this.view = view;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner s = (JSpinner) e.getSource();
		Box indiRestrictorBox = (Box) s.getParent();
		Box restrictorsBox = (Box) indiRestrictorBox.getParent();
		
		int index = 0;
		for(Component c : restrictorsBox.getComponents()) {
			if(indiRestrictorBox.equals(c)) {
				view.getRestrictor(index).setMax((int) s.getValue()); 
			}
			else {
				index++;
			}
		}
		
	}

}
