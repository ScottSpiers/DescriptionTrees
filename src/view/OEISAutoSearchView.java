package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import listeners.AutoSearchCheckBoxSelectedListener;
import listeners.AutoSearchRestrictorSpinnerListener;
import listeners.RunAutoSearchListener;
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
 * The view for the auto search tool
 * The tool allows various parameters and returns a table 
 * with the values and oeis search link
 */
public class OEISAutoSearchView extends JFrame {

	private static final long serialVersionUID = 7422466254922478739L;
	
	private DescriptionTreeModel model;
	private List<Restrictor> restrictors;
	private Map<Restrictor, Boolean> selectedRestrictors;
	private JRadioButton rdo_alpha;
	private JRadioButton rdo_beta;
	private JSpinner spnr_nodeMin;
	private JSpinner spnr_nodeMax;
	private JSpinner spnr_aMin;
	private JSpinner spnr_aMax;
	private JSpinner spnr_bMin;
	private JSpinner spnr_bMax;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 */
	public OEISAutoSearchView(DescriptionTreeModel model) {
		super("OEIS Auto Search");
		this.model = model;
		initRestrictors();
		display();
	}
	
	/**
	 * Initialises the restrictors list and map
	 * of restirctors to whether or not they are selected
	 */
	private void initRestrictors() {
		restrictors = new ArrayList<Restrictor>();
		selectedRestrictors = new HashMap<Restrictor, Boolean>();
		
		Restrictor leafNum = new LeafNumRestrictor("Number of Leaves: ", "Restricts the number of leaves ", 1, 1);
		restrictors.add(leafNum);
		selectedRestrictors.put(leafNum, false);
		
		Restrictor nodeNum = new InternalNodeNumRestrictor("Number of Nodes: ", "Restricts the number of internal nodes (Excludes root) ");
		restrictors.add(nodeNum);
		selectedRestrictors.put(nodeNum, false);
		
		Restrictor rootVal = new RootValueRestrictor("Root Value", "Restricts the value of the Root node only ");
		restrictors.add(rootVal);
		selectedRestrictors.put(rootVal, false);
		
		Restrictor nodeVal = new InternalNodeValueRestrictor("Internal Node Value", "Restricts the value of internal nodes (Excludes root) ");
		restrictors.add(nodeVal);
		selectedRestrictors.put(nodeVal, false);
		
		Restrictor rootChildren = new RootChildrenNumRestrictor("Number of Children of Root", "Restricts the number of children the root node can have ");
		restrictors.add(rootChildren);
		selectedRestrictors.put(rootChildren, false);
		
		Restrictor nodeChildren = new InternalNodeChildrenNumRestrictor("Number of Children of Internal Nodes", "Restricts the number of children internal nodes can have (Excludes root) ");
		restrictors.add(nodeChildren);
		selectedRestrictors.put(nodeChildren, false);
		
		Restrictor pathLength = new PathLengthRestrictor("Path Length", "Restricts the path length (depth of the tree ");
		restrictors.add(pathLength);
		selectedRestrictors.put(pathLength, false);
		
		Restrictor jumpNum = new JumpNumRestrictor("Number of Jumps", "Restricts the number of times a node has a value greater than sum of its children");
		restrictors.add(jumpNum);
		selectedRestrictors.put(jumpNum, false);
	}
	
	/**
	 * builds and displays the view
	 */
	private void display() {
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(background);
		
		Box bx_nodes = new Box(BoxLayout.X_AXIS);
		
		//create node spinners
		spnr_nodeMin = new JSpinner();
		spnr_nodeMin.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_nodeMin.setMaximumSize(new Dimension(50, 20));
		
		spnr_nodeMax = new JSpinner();
		spnr_nodeMax.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_nodeMax.setMaximumSize(new Dimension(50,20));
		JLabel lbl_node = new JLabel("n = ");
		JLabel lbl_toMax = new JLabel(" to ");
		bx_nodes.add(lbl_node);
		bx_nodes.add(spnr_nodeMin);
		bx_nodes.add(lbl_toMax);
		bx_nodes.add(spnr_nodeMax);
		
		Box bx_treeType = new Box(BoxLayout.X_AXIS);
		
		//create tree type radio buttons
		JLabel lbl_treeChoiceInstr = new JLabel("Tree Type: ");
		ButtonGroup grp_trees = new ButtonGroup();
		rdo_alpha = new JRadioButton("Alpha(a, b) ");
		rdo_beta = new JRadioButton("Beta(a, b) ");
		grp_trees.add(rdo_alpha);
		grp_trees.add(rdo_beta);
		
		bx_treeType.add(lbl_treeChoiceInstr);
		bx_treeType.add(rdo_alpha);
		bx_treeType.add(rdo_beta);
		
		
		Box bx_values = new Box(BoxLayout.Y_AXIS);
		Box bx_a = new Box(BoxLayout.X_AXIS);
		Box bx_b = new Box(BoxLayout.X_AXIS);
		
		//create value spinners
		JLabel lbl_a = new JLabel(" a = ");
		JLabel lbl_b = new JLabel(" b = ");
		JLabel lbl_to = new JLabel(" to ");
		spnr_aMin = new JSpinner();
		spnr_aMin.setModel(new SpinnerNumberModel(0, 0, null, 1));
		spnr_aMax = new JSpinner();
		spnr_aMax.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_aMin.setMaximumSize(new Dimension(50, 20));
		spnr_aMax.setMaximumSize(new Dimension(50, 20));
		
		spnr_bMin = new JSpinner();
		spnr_bMin.setModel(new SpinnerNumberModel(0, 0, null, 1));
		spnr_bMax = new JSpinner();
		spnr_bMax.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_bMin.setMaximumSize(new Dimension(50, 20));
		spnr_bMax.setMaximumSize(new Dimension(50, 20));
		bx_a.add(lbl_a);
		bx_a.add(spnr_aMin);
		bx_a.add(lbl_to);
		bx_a.add(spnr_aMax);
		lbl_to = new JLabel(" to ");
		bx_b.add(lbl_b);
		bx_b.add(spnr_bMin);
		bx_b.add(lbl_to);
		bx_b.add(spnr_bMax);
		bx_values.add(bx_a);
		bx_values.add(bx_b);
		
		Box bx_restrictors = new Box(BoxLayout.Y_AXIS);		
		
		//add restrictor boxes
		for(Restrictor r : restrictors) {
			bx_restrictors.add(restrictorBox(r));
		}		
		
		JButton btn_runAuto = new JButton("Run");
		btn_runAuto.addActionListener(new RunAutoSearchListener(model, this));
		
		Box bx_main = new Box(BoxLayout.Y_AXIS);
		bx_main.add(bx_nodes);
		bx_main.add(bx_treeType);
		bx_main.add(bx_values);
		bx_main.add(bx_restrictors);
		
		background.add(BorderLayout.CENTER, bx_main);
		background.add(BorderLayout.SOUTH, btn_runAuto);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) screenSize.getWidth() / 3 + 256, (int) screenSize.getHeight() / 3 +  129);
		this.setMinimumSize(new Dimension(500, 400));
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Builds a box containing the components needed to provide restrictor params
	 * @param r The Restrictor this box models
	 * @return The created Box
	 */
	private Box restrictorBox(Restrictor r) {
		Box bx_r = new Box(BoxLayout.X_AXIS);
		bx_r.setToolTipText(r.getDesc());
		JLabel lbl_name = new JLabel(r.getName());
		JCheckBox chkbx_r = new JCheckBox();
		chkbx_r.addActionListener(new AutoSearchCheckBoxSelectedListener(this));
		JLabel lbl_min = new JLabel("Min: " + r.getMin() + " Max: ");
		JSpinner spnr_max = new JSpinner();
		spnr_max.addChangeListener(new AutoSearchRestrictorSpinnerListener(this));
		spnr_max.setEnabled(false);
		spnr_max.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_max.setMaximumSize(new Dimension(50, 20));
		
		bx_r.add(lbl_name);
		bx_r.add(chkbx_r);
		bx_r.add(lbl_min);
		bx_r.add(spnr_max);
		return bx_r;
	}
	
	/**
	 * Gets the user selected restrictors
	 * @return The restrictors selected by the user
	 */
	public List<Restrictor> getSelectedRestrictors() {
		List<Restrictor> selected = new ArrayList<Restrictor>();
		for(Restrictor r : restrictors) {
			if(selectedRestrictors.get(r)) {
				selected.add(r);
			}
		}
		return selected;
	}	
	
	/**
	 * Toggles whether or not the restriction is selected in the map
	 * @param index The index of the restrictor to toggle
	 */
	public void toggleRestrictionSelected(int index) {
		for(int i = 0; i < restrictors.size(); i++) {
			if(i == index) {
				selectedRestrictors.put(restrictors.get(i), !selectedRestrictors.get(restrictors.get(i)));
			}
		}
	}
	
	/**
	 * Gets the minimum number of nodes
	 * @return The minimum number of nodes
	 */
	public int getNodeMin() {
		return (int) spnr_nodeMin.getValue();
	}
	
	/**
	 * Gets the maximum number of nodes
	 * @return The maximum number of nodes
	 */
	public int getNodeMax() {
		return (int) spnr_nodeMax.getValue();
	}
	
	/**
	 * Gets the minimum value for the first parameter
	 * @return the minimum value for the first parameter
	 */
	public int getAMin() {
		return (int) spnr_aMin.getValue();
	}
	
	/**
	 * Gets the maximum value for the first parameter
	 * @return the maximum value for the first parameter
	 */
	public int getAMax() {
		return (int) spnr_aMax.getValue();
	}
	
	/**
	 * Gets the minimum value for the second parameter
	 * @return the minimum value for the second parameter
	 */
	public int getBMin() {
		return (int) spnr_bMin.getValue();
	}
	
	/**
	 * Gets the maximum value for the second parameter
	 * @return the maximum value for the second parameter
	 */
	public int getBMax() {
		return (int) spnr_bMax.getValue();
	}
	
	/**
	 * 
	 * @return whether or not the selected tree type is Alpha
	 */
	public boolean isAlphaChecked() {
		return rdo_alpha.isSelected();
	}
	
	/**
	 * 
	 * @return whether or not the selected tree type is Beta
	 */
	public boolean isBetaChecked() {
		return rdo_beta.isSelected();
	}
	
	/**
	 * 
	 * @param index Index of the restrictor to get
	 * @return the restrictor at index in the restrictor list
	 */
	public Restrictor getRestrictor(int index) {
		return restrictors.get(index);
	}
}
