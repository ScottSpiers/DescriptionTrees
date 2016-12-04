package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.LeafNumRestrictor;

public class DescriptionTreeView implements Observer {

	private JFrame frame;
	
	
	public void display() {
		frame = new JFrame("Description Trees");
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.getContentPane().add(background);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2)); 
		
		JMenuBar menu = new DescriptionTreeMenuBar();
		frame.setJMenuBar(menu);
		
		Box westBox = new Box(BoxLayout.Y_AXIS);
		Box eastBox = new Box(BoxLayout.X_AXIS);
		
		JLabel lbl_treeChoiceInstr = new JLabel("Please choose a description tree:");
		lbl_treeChoiceInstr.setAlignmentX(Component.LEFT_ALIGNMENT);
		ButtonGroup grp_trees = new ButtonGroup();
		JRadioButton rdo_alpha = new JRadioButton("Alpha(a, b)");
		rdo_alpha.setAlignmentX(Component.LEFT_ALIGNMENT);
		JRadioButton rdo_beta = new JRadioButton("Beta(a, b)");
		rdo_beta.setAlignmentX(Component.LEFT_ALIGNMENT);
		grp_trees.add(rdo_alpha);
		grp_trees.add(rdo_beta);
		
		JLabel lbl_params = new JLabel("Provide the paramaters");
		lbl_params.setAlignmentX(Component.LEFT_ALIGNMENT);
		Box box_a = new Box(BoxLayout.X_AXIS);
		Box box_b = new Box(BoxLayout.Y_AXIS);
		JLabel lbl_a = new JLabel("a = ");
		lbl_a.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel lbl_b = new JLabel("b = ");
		lbl_b.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSpinner spnr_a = new JSpinner();
		spnr_a.setModel(new SpinnerNumberModel(0, 0, null, 1));
		spnr_a.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSpinner spnr_b = new JSpinner();
		spnr_b.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_b.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		box_a.add(lbl_a);
		box_a.add(spnr_a);
		box_b.add(lbl_b);
		box_b.add(spnr_b);
		
		
		JSpinner spnr_nodeMin = new JSpinner();
		spnr_nodeMin.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_nodeMin.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSpinner spnr_nodeMax = new JSpinner();
		spnr_nodeMax.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_nodeMax.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lbl_numNodes = new JLabel("Provide the Number of Nodes:");
		lbl_numNodes.setAlignmentX(Component.LEFT_ALIGNMENT);
		Box box_node = new Box(BoxLayout.X_AXIS);
		JLabel lbl_node = new JLabel("n = ");
		lbl_node.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel lbl_toMax = new JLabel(" to ");
		lbl_toMax.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_node.add(lbl_node);
		box_node.add(spnr_nodeMin);
		box_node.add(lbl_toMax);
		box_node.add(spnr_nodeMax);
		
		JLabel lbl_totalTrees = new JLabel("Total Number of Trees:");
		lbl_totalTrees.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel lbl_numTrees = new JLabel();
		lbl_numTrees.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lbl_numTreeSeqDesc = new JLabel("Number of Trees sequence:");
		lbl_numTreeSeqDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel lbl_numTreeSeq = new JLabel();
		lbl_numTreeSeq.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JPanel pnl_restrictions = new JPanel();
		JScrollPane scrl_restrictions = new JScrollPane(pnl_restrictions);
		pnl_restrictions.add(new RestrictionComponent(frame, new LeafNumRestrictor("Number of Leaves:", "Restricts the number of leaves")));
		//scrl_restrictions.setMaximumSize(new Dimension(background.getWidth() /2, background.getHeight()));
		
		
		westBox.add(lbl_treeChoiceInstr);
		westBox.add(rdo_alpha);
		westBox.add(rdo_beta);
		westBox.add(lbl_params);
		westBox.add(box_a);
		westBox.add(box_b);
		westBox.add(lbl_numNodes);
		westBox.add(box_node);
		westBox.add(lbl_totalTrees);
		westBox.add(lbl_numTrees);
		westBox.add(lbl_numTreeSeqDesc);		
		westBox.add(lbl_numTreeSeq);
		westBox.setMinimumSize(new Dimension((int) (frame.getWidth() * 0.5), frame.getHeight()));
		
		eastBox.add(scrl_restrictions);
		//eastBox.add(new RestrictionComponent(frame, new LeafNumRestrictor("Number of Leaves:", "Restricts the number of leaves")));
		eastBox.setMinimumSize(new Dimension((int) (frame.getWidth() * 0.5), frame.getHeight()));
		
		background.add(BorderLayout.WEST, westBox);
		background.add(BorderLayout.EAST, eastBox);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		
	}
}
