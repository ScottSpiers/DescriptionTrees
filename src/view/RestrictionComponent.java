package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Restrictor;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Defines a layout for a Restrictor to be displayed grpahically
 * and to allow for manipulation of a restrictor
 */
public class RestrictionComponent extends JComponent {
	
	private static final long serialVersionUID = -7694510080532419887L;
	
	private DescriptionTreeView view;
	private Restrictor r; //The restrictor this component models

	/**
	 * 
	 * @param parent The parent component of this component
	 * @param r The restrictor this component models
	 * @param view The DescriptionTreeView being used
	 */
	public RestrictionComponent(Component parent, Restrictor r, DescriptionTreeView view) {
		this.view = view;
		this.r = r;
		this.setLayout(new BorderLayout());
		//create containers
		Box box_layout = new Box(BoxLayout.Y_AXIS);
		Box box_name = new Box(BoxLayout.X_AXIS);
		Box box_details = new Box(BoxLayout.X_AXIS);
		//add components to the containers
		box_name.add(createName(r.getName()));
		box_details.add(new JLabel("Min: "));
		box_details.add(createSpinner(r.getMin()));
		box_details.add(new JLabel("Max: "));
		box_details.add(createSpinner(r.getMax()));
		box_details.add(createRemove());
		box_layout.add(box_name);
		box_layout.add(box_details);
		//add the containers to this
		this.add(box_layout, BorderLayout.NORTH);
		this.setToolTipText(r.getDesc());
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setMinimumSize(new Dimension(parent.getWidth(), (int) (parent.getHeight() * 0.2)));
		this.setVisible(true);
	}
	
	/**
	 * Creates and returns a JLabel for the restrictor's name
	 * @param name The name of restrictor
	 * @return A JLabel with text showing the name of the Restrictor
	 */
	private JLabel createName(String name) {
		JLabel lbl_name = new JLabel(name);
		return lbl_name;
	}
	
	/**
	 * Creates and returns a spinner with a provided min value
	 * @param n The minimum and default value of this spinner
	 * @return The created JSpinner
	 */
	private JSpinner createSpinner(int n) {
		//create the spinner
		JSpinner spnr_limit = new JSpinner(new SpinnerNumberModel(n, 0, null, 1));
		//connect it to the appropriate listener
		spnr_limit.addChangeListener(new SpinnerUpdateListener());
		return spnr_limit;
	}
	
	/**
	 * Creates a remove button to be able to remove the restriction
	 * @return The created JButton
	 */
	private JButton createRemove() {
		JButton btn_remove = new JButton();
		btn_remove.addActionListener(new RemoveRestrictionListener());
		btn_remove.setToolTipText("Remove this restriction");
		btn_remove.setText("Remove");
		return btn_remove;
	}
	
	/**
	 * Checks whether or not he provided spinner is the spinner for the minimum value
	 * @param s The JSpinner being checked
	 * @return True if the spinner is the minimum value spinner, false otherwise
	 */
	private boolean isMinSpinner(JSpinner s) {
		this.getTreeLock(); //recommended
		Component child = this.getComponent(0); //get the main container
		Box box_layout = (Box) child; //cast it
		Box box_details = (Box) box_layout.getComponent(1); //get the details box
		Component[] children = box_details.getComponents(); //get this box's components
		boolean minSpnrFound = false; //we haven't found the min spinner
		for(int i = 0; i < children.length; i++) { //for all children
			if(children[i] instanceof JSpinner) { //if its a JSpinner
				if(((JSpinner) children[i]).equals(s)) { //if it equals s
					if(!minSpnrFound) { //if we haven't yet found it 
						return true; // return true
					}
					else { //otherwise
						return false; //this was the maximum spinner
					}
				}
				else {
					minSpnrFound = true; //we found the min spinner but it wasn't the one provided so continue
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @author Scott Spiers
	 * University of Strathclyde
	 * Final Year Project: Description Trees
	 * Supervisor: Sergey Kitaev
	 *
	 * Listener to remove the restriction
	 */
	private class RemoveRestrictionListener implements ActionListener {
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			view.removeRestriction(r); //simply remove the restriction from the view
			
		}		
	}
	
	/**
	 * 
	 * @author Scott Spiers
	 * University of Strathclyde
	 * Final Year Project: Description Trees
	 * Supervisor: Sergey Kitaev
	 *
	 * Listener to update restrictions on spinner changes
	 */
	private class SpinnerUpdateListener implements ChangeListener {

		/*
		 * (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			JSpinner s = (JSpinner) e.getSource(); //get the spinner that changed
			if(isMinSpinner(s)) { //if its the min spinner
				r.setMin((int) s.getValue()); //set the restrictions min value to the value of the spinner
			}
			else { //otherwise
				r.setMax((int) s.getValue());  //set its max value
			}			
		}
		
	}
	
	
}
