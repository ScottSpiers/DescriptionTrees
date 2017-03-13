package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
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
import listeners.AutoSearchListener;
import model.DescriptionTreeModel;
import model.Restrictor;
import restrictors.InternalNodeChildrenNumRestrictor;
import restrictors.InternalNodeNumRestrictor;
import restrictors.InternalNodeValueRestrictor;
import restrictors.LeafNumRestrictor;
import restrictors.PathLengthRestrictor;
import restrictors.RootChildrenNumRestrictor;
import restrictors.RootValueRestrictor;

public class OEISAutoSearchView extends JFrame {

	private static final long serialVersionUID = 7422466254922478739L;
	
	private DescriptionTreeModel model;
	private List<Restrictor> restrictors;
	private Map<Restrictor, Boolean> selectedRestrictors;
	private JRadioButton rdo_alpha;
	private JRadioButton rdo_beta;
	private JSpinner spnr_nodeMin;
	private JSpinner spnr_nodeMax;
	private JSpinner spnr_a;
	private JSpinner spnr_b;
	
	public OEISAutoSearchView(DescriptionTreeModel model) {
		super("OEIS Auto Search");
		this.model = model;
		initRestrictors();
		display();
	}
	
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
	}
	
	private void display() {
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(background);
		
		Box bx_params = new Box(BoxLayout.Y_AXIS);
		Box bx_nodes = new Box(BoxLayout.X_AXIS);
		
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
		
		JLabel lbl_treeChoiceInstr = new JLabel("Tree Type: ");
		ButtonGroup grp_trees = new ButtonGroup();
		rdo_alpha = new JRadioButton("Alpha(a, b) ");
		rdo_beta = new JRadioButton("Beta(a, b) ");
		grp_trees.add(rdo_alpha);
		grp_trees.add(rdo_beta);
		
		bx_treeType.add(lbl_treeChoiceInstr);
		bx_treeType.add(rdo_alpha);
		bx_treeType.add(rdo_beta);
		
		
		Box bx_values = new Box(BoxLayout.X_AXIS);
		
		JLabel lbl_a = new JLabel(" a = ");
		JLabel lbl_b = new JLabel(" b = ");
		spnr_a = new JSpinner();
		spnr_a.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_a.setMaximumSize(new Dimension(50, 20));
		
		spnr_b = new JSpinner();
		spnr_b.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_b.setMaximumSize(new Dimension(50, 20));
		bx_values.add(lbl_a);
		bx_values.add(spnr_a);
		bx_values.add(lbl_b);
		bx_values.add(spnr_b);
		
		
		Box bx_restrictors = new Box(BoxLayout.Y_AXIS);		
		
		for(Restrictor r : restrictors) {
			bx_restrictors.add(restrictorBox(r));
		}		
		
		JButton btn_runAuto = new JButton("Run");
		btn_runAuto.addActionListener(new AutoSearchListener(model, this));
		
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
	
	private Box restrictorBox(Restrictor r) {
		Box bx_r = new Box(BoxLayout.X_AXIS);
		bx_r.setToolTipText(r.getDesc());
		JLabel lbl_name = new JLabel(r.getName());
		JCheckBox chkbx_r = new JCheckBox();
		chkbx_r.addActionListener(new AutoSearchCheckBoxSelectedListener(this));
		JLabel lbl_min = new JLabel("Min: " + r.getMin() + " Max: ");
		JSpinner spnr_max = new JSpinner();
		spnr_max.setEnabled(false);
		spnr_max.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_max.setMaximumSize(new Dimension(50, 20));
		
		bx_r.add(lbl_name);
		bx_r.add(chkbx_r);
		bx_r.add(lbl_min);
		bx_r.add(spnr_max);
		return bx_r;
	}
	
	public List<Restrictor> getSelectedRestrictors() {
		List<Restrictor> selected = new ArrayList<Restrictor>();
		for(Restrictor r : restrictors) {
			if(selectedRestrictors.get(r)) {
				selected.add(r);
			}
		}
		return selected;
	}	
	
	public void toggleRestrictionSelected(int index) {
		for(int i = 0; i < restrictors.size(); i++) {
			if(i == index) {
				selectedRestrictors.put(restrictors.get(i), !selectedRestrictors.get(restrictors.get(i)));
			}
		}
		/*toggleRestrictionSpinner(index);*/
	}
	
	public int getNodeMin() {
		return (int) spnr_nodeMin.getValue();
	}
	
	public int getNodeMax() {
		return (int) spnr_nodeMax.getValue();
	}
	
	public boolean isAlphaChecked() {
		return rdo_alpha.isSelected();
	}
	
	public boolean isBetaChecked() {
		return rdo_beta.isSelected();
	}
}
