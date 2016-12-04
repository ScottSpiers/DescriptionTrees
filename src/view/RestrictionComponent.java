package view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.Restriction;

public class RestrictionComponent extends JComponent {
	
	private static final long serialVersionUID = -7694510080532419887L;

	public RestrictionComponent(Restriction r) {
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
		this.setToolTipText(r.getDesc());
		this.setVisible(true);
	}
	
	private JLabel createName(String name) {
		JLabel lbl_name = new JLabel(name);
		return lbl_name;
	}
	
	private JSpinner createSpinner(int n) {
		JSpinner spnr_limit = new JSpinner(new SpinnerNumberModel(n, 0, null, 1));
		return spnr_limit;
	}
	
	private JButton createRemove() {
		JButton btn_remove = new JButton();
		//btn_remove.addActionListener(new RemoveRestrictionListener());
		btn_remove.setToolTipText("Remove this restriction");
		btn_remove.setText("Remove");
		return btn_remove;
	}
}
