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

public class RestrictionComponent extends JComponent {
	
	private static final long serialVersionUID = -7694510080532419887L;
	
	private DescriptionTreeView view;
	private Restrictor r;

	public RestrictionComponent(Component parent, Restrictor r, DescriptionTreeView view) {
		this.view = view;
		this.r = r;
		this.setLayout(new BorderLayout());
		Box box_layout = new Box(BoxLayout.Y_AXIS);
		Box box_name = new Box(BoxLayout.X_AXIS);
		Box box_details = new Box(BoxLayout.X_AXIS);
		box_name.add(createName(r.getName()));
		box_details.add(new JLabel("Min: "));
		box_details.add(createSpinner(r.getMin()));
		box_details.add(new JLabel("Max: "));
		box_details.add(createSpinner(r.getMax()));
		box_details.add(createRemove());
		box_layout.add(box_name);
		box_layout.add(box_details);
		this.add(box_layout, BorderLayout.NORTH);
		this.setToolTipText(r.getDesc());
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setMinimumSize(new Dimension(parent.getWidth(), (int) (parent.getHeight() * 0.2)));
		this.setVisible(true);
	}
	
	private JLabel createName(String name) {
		JLabel lbl_name = new JLabel(name);
		return lbl_name;
	}
	
	private JSpinner createSpinner(int n) {
		JSpinner spnr_limit = new JSpinner(new SpinnerNumberModel(n, 0, null, 1));
		spnr_limit.addChangeListener(new SpinnerUpdateListener());
		return spnr_limit;
	}
	
	private JButton createRemove() {
		JButton btn_remove = new JButton();
		btn_remove.addActionListener(new RemoveRestrictionListener());
		btn_remove.setToolTipText("Remove this restriction");
		btn_remove.setText("Remove");
		return btn_remove;
	}
	
	private boolean isMinSpinner(JSpinner s) {
		this.getTreeLock();
		Component child = this.getComponent(0);
		Box box_layout = (Box) child;
		Box box_details = (Box) box_layout.getComponent(1);
		Component[] children = box_details.getComponents();
		boolean minSpnrFound = false;
		for(int i = 0; i < children.length; i++) {
			if(children[i] instanceof JSpinner) {
				if(((JSpinner) children[i]).equals(s)) {
					if(!minSpnrFound) {
						return true;
					}
					else {
						return false;
					}
				}
				else {
					minSpnrFound = true;
				}
			}
		}
		return false;
	}
	
	private class RemoveRestrictionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.removeRestriction(r);
			
		}		
	}
	
	private class SpinnerUpdateListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSpinner s = (JSpinner) e.getSource();
			if(isMinSpinner(s)) {
				r.setMin((int) s.getValue());
				System.out.println("min set to: " + s.getValue());
			}
			else {
				r.setMax((int) s.getValue()); 
				System.out.println("max set to: " + s.getValue());
			}			
		}
		
	}
	
	
}
