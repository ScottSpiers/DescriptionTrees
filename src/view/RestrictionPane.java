package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
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
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.DescriptionTreeModel;
import model.Restrictor;
import restrictors.*;

public class RestrictionPane extends JFrame {
	
	private static final long serialVersionUID = -4046493742306257235L;
	
	private DescriptionTreeModel model;
	private Map<String, Restrictor> restrictions;
	private JList<String> lst_restrictions;
	private JPanel pnl_list;
	
	public RestrictionPane(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		restrictions = new HashMap<String, Restrictor>();
		initRestrictorList();
		display(view.frameBounds());
	}
	
	private void display(Rectangle bounds) {
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		pnl_list = new JPanel();
		pnl_list.setLayout(new BorderLayout());
		JScrollPane scrl_restrictions = new JScrollPane(pnl_list);		
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
		
		contentPane.add(BorderLayout.CENTER, scrl_restrictions);
		contentPane.add(BorderLayout.SOUTH, box_btns);
		
		this.setBounds(bounds);
		this.add(scrl_restrictions);
		this.pack();
		this.setVisible(true);
		
	}
	
	private void close() {
		this.setVisible(false);
	}
	
	private void initRestrictorList() {
		restrictions.put("Number of Leaves", new LeafNumRestrictor("Number of Leaves: ", "Restricts the number of leaves", 1, 1));
		restrictions.put("Number of Nodes", new InternalNodeNumRestrictor("Number of Nodes: ", "Restricts the number of internal nodes (Excludes root)"));
		restrictions.put("Root Value", new RootValueRestrictor("Root Value", "Restricts the value of the Root node only"));
		restrictions.put("Internal Node Value", new InternalNodeValueRestrictor("Internal Node Value", "Restricts the value of internal nodes (Excludes root)"));
		restrictions.put("Number of Children (Root)", new RootChildrenNumRestrictor("Number of Children of Root", "Restricts the number of children the root node can have"));
		restrictions.put("Number of Children (Internal Node)", new InternalNodeChildrenNumRestrictor("Number of Children of Internal Nodes", "Restricts the number of children internal nodes can have (Excludes root)"));
		restrictions.put("Path Length", new PathLengthRestrictor("Path Length", "Restricts the path length (depth0 of the tree"));
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
