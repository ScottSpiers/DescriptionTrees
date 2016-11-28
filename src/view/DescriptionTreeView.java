package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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

public class DescriptionTreeView implements Observer {

	private JFrame frame;
	
	
	public void display() {
		frame = new JFrame("Description Trees");
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel();
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JMenuBar menu = new DescriptionTreeMenuBar();
		frame.setJMenuBar(menu);
		
		Box westBox = new Box(BoxLayout.Y_AXIS);
		Box eastBox = new Box(BoxLayout.X_AXIS);
		
		JLabel lbl_treeChoiceInstr = new JLabel("Please choose a description tree:");
		ButtonGroup grp_trees = new ButtonGroup();
		JRadioButton rdo_alpha = new JRadioButton("Alpha(a, b)");
		JRadioButton rdo_beta = new JRadioButton("Beta(a, b)");
		grp_trees.add(rdo_alpha);
		grp_trees.add(rdo_beta);
		
		JLabel lbl_params = new JLabel("Provide the paramaters");
		Box box_a = new Box(BoxLayout.X_AXIS);
		Box box_b = new Box(BoxLayout.Y_AXIS);
		JLabel lbl_a = new JLabel("a =");
		JLabel lbl_b = new JLabel("b =");
		
		JSpinner spnr_params = new JSpinner();
		spnr_params.setModel(new SpinnerNumberModel(0, 0, null, 1));
		
		box_a.add(lbl_a);
		box_a.add(spnr_params);
		box_b.add(lbl_b);
		box_b.add(spnr_params);
		
		JLabel lbl_numNodes = new JLabel("Provide the Number of Nodes:");
		Box box_node = new Box(BoxLayout.X_AXIS);
		JLabel lbl_node = new JLabel("n =");
		JLabel lbl_toMax = new JLabel("to");
		box_node.add(lbl_node);
		box_node.add(spnr_params);
		box_node.add(lbl_toMax);
		box_node.add(spnr_params);
		
		JLabel lbl_totalTrees = new JLabel("Total Number of Trees:");
		JLabel lbl_numTrees = new JLabel();
		
		JLabel lbl_numTreeSeqDesc = new JLabel("Number of Trees sequence:");
		JLabel lbl_numTreeSeq = new JLabel();
		
		JPanel pnl_restrictions = new JPanel();
		JScrollPane scrl_restrictions = new JScrollPane(pnl_restrictions);
		
		
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
		westBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		eastBox.add(scrl_restrictions);
		eastBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		background.add(BorderLayout.WEST, westBox);
		background.add(BorderLayout.EAST, eastBox);
		
		frame.getContentPane().add(background);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		
	}
}
