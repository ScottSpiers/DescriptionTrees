package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import model.DescriptionTreeModel;
import model.InternalNodeRestrictor;
import model.LeafNumRestrictor;
import model.Restrictor;

public class RestrictionPane extends JFrame {
	
	private static final long serialVersionUID = -4046493742306257235L;
	
	private DescriptionTreeModel model;
	private Map<String, Restrictor> restrictions;
	private JList<String> lst_restrictions;
	private JPanel pnl_list;
	
	public RestrictionPane(DescriptionTreeModel model) {
		this.model = model;
		restrictions = new HashMap<String, Restrictor>();
		initRestrictorList();
		display();
	}
	
	private void display() {
		pnl_list = new JPanel();
		pnl_list.setLayout(new BorderLayout());
		lst_restrictions = new JList<String>();
		String[] restrictors = new String[restrictions.keySet().size()];
		int i = 0;
		for(String key : restrictions.keySet()) {
			restrictors[i] = key;
			i++;
		}
		lst_restrictions.setListData(restrictors);
		lst_restrictions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JButton btn_add = new JButton("Add");
		btn_add.addActionListener(new AddRestrictorListener());
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new CancelAddListener());
		pnl_list.add(lst_restrictions, BorderLayout.NORTH);
		
		Box box_btns = new Box(BoxLayout.X_AXIS);
		box_btns.add(btn_add);
		box_btns.add(btn_cancel);
		pnl_list.add(box_btns, BorderLayout.SOUTH);
		this.add(pnl_list);
		this.pack();
		this.setVisible(true);
		
	}
	
	private void close() {
		this.setVisible(false);
	}
	
	private void initRestrictorList() {
		restrictions.put("Number of Leaves", new LeafNumRestrictor("Number of Leaves: ", "Restricts the number of leaves"));
		restrictions.put("Number of Nodes", new InternalNodeRestrictor("Number of Nodes: ", "Restricts the number of internal nodes (Excluding root)"));
	}
	
	private class AddRestrictorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for(String s : lst_restrictions.getSelectedValuesList()) {
				model.addRestrictor(restrictions.get(s));
			}
			close();
		}		
	}
	
	private class CancelAddListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			close();
			
		}
		
	}
}
