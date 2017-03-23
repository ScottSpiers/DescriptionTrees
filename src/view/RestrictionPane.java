package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
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
import restrictors.InternalNodeChildrenNumRestrictor;
import restrictors.InternalNodeNumRestrictor;
import restrictors.InternalNodeValueRestrictor;
import restrictors.JumpNumRestrictor;
import restrictors.LeafNumRestrictor;
import restrictors.PathLengthRestrictor;
import restrictors.RootChildrenNumRestrictor;
import restrictors.RootValueRestrictor;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Displays a list of restrictions that can be added and
 * provides functionality to do so
 */
public class RestrictionPane extends JFrame {
	
	private static final long serialVersionUID = -4046493742306257235L;
	
	private DescriptionTreeModel model;
	private Map<String, Restrictor> restrictions;
	private JList<String> lst_restrictions;
	private JPanel pnl_list;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public RestrictionPane(DescriptionTreeModel model, DescriptionTreeView view) {
		super("Add Restrictions");
		this.model = model;
		restrictions = new HashMap<String, Restrictor>(); //init the map to map restrictor names to Restrictors
		initRestrictorList();
		display(view.frameLocation());
	}
	
	/**
	 * 
	 * @param p The location this window opens
	 */
	private void display(Point p) {
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		pnl_list = new JPanel();
		pnl_list.setLayout(new BorderLayout());
		JScrollPane scrl_restrictions = new JScrollPane(pnl_list);		
		lst_restrictions = new JList<String>();
		
		//get the restriction keys from the map and store them
		String[] restrictors = new String[restrictions.keySet().size()];
		int i = 0;
		for(String key : restrictions.keySet()) {
			restrictors[i] = key;
			i++;
		}
		
		lst_restrictions.setListData(restrictors); //use the array to set the list data
		lst_restrictions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //allow multiple selection
		
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
		
		//move the window
		p.translate(50, 50);
		this.setLocation(p);
		this.add(scrl_restrictions);
		this.setPreferredSize(new Dimension(475, 220));
		this.pack();
		this.setVisible(true);
		
	}
	
	/**
	 * Close the window
	 */
	private void close() {
		this.setVisible(false);
	}
	
	/**
	 * Initialise the restrictor map for use in displaying the Restrictors
	 */
	private void initRestrictorList() {
		restrictions.put("Number of Leaves", new LeafNumRestrictor("Number of Leaves: ", "Restricts the number of leaves", 1, 1));
		restrictions.put("Number of Internal Nodes", new InternalNodeNumRestrictor("Number of Internal Nodes: ", "Restricts the number of internal nodes (Excludes root)"));
		restrictions.put("Root Value", new RootValueRestrictor("Root Value", "Restricts the value of the Root node only"));
		restrictions.put("Internal Node Value", new InternalNodeValueRestrictor("Internal Node Value", "Restricts the value of internal nodes (Excludes root)"));
		restrictions.put("Number of Children (Root)", new RootChildrenNumRestrictor("Number of Children of Root", "Restricts the number of children the root node can have"));
		restrictions.put("Number of Children (Internal Node)", new InternalNodeChildrenNumRestrictor("Number of Children of Internal Nodes", "Restricts the number of children internal nodes can have (Excludes root)"));
		restrictions.put("Path Length", new PathLengthRestrictor("Path Length", "Restricts the path length (depth0 of the tree"));
		restrictions.put("Number of Jumps", new JumpNumRestrictor("Number of Jumps", "Restricts the number of times a node has a value greater than sum of its children", 0, 0));
	}
	
	/**
	 * 
	 * @author Scott Spiers
	 * University of Strathclyde
	 * Final Year Project: Description Trees
	 * Supervisor: Sergey Kitaev
	 *
	 * Listener to add selected restrictors
	 */
	private class AddRestrictorListener implements ActionListener {
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//for every selected restrictor
			for(String s : lst_restrictions.getSelectedValuesList()) {
				model.addRestrictor(restrictions.get(s)); //add it to the model
			}
			close(); //close the window
		}		
	}
	
	/**
	 * 
	 * @author Scott Spiers
	 * University of Strathclyde
	 * Final Year Project: Description Trees
	 * Supervisor: Sergey Kitaev
	 *
	 * Listener to cancel adding restrictors
	 */
	private class CancelAddListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			close(); //just close the window
			
		}
		
	}
}
