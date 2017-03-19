package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import listeners.AddRestrictionListener;
import listeners.CalcNumTreesListener;
import listeners.UseProvidedListener;
import model.AlphaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;
import model.Restrictor;
import model.scala.Tree;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * The main GUI for the system
 * Acts as Observer of the model
 */
public class DescriptionTreeView implements Observer {

	private DescriptionTreeModel model; //The model we observe
	private JFrame frame;
	private JLabel lbl_numTrees;
	private JRadioButton rdo_alpha;
	private JRadioButton rdo_beta;
	private JSpinner spnr_a;
	private JSpinner spnr_b;
	private JSpinner spnr_nodeMin;
	private JSpinner spnr_nodeMax;
	private JCheckBox chkbx_useProvided;
	private Box box_scrl;
	private JLabel lbl_numTreeSeq;
	private JTextPane txt_providedShape;
	
	public DescriptionTreeView() {
		//init the model and add this as an observer of it
		model = new DescriptionTreeModel();
		model.addObserver(this);
	}
	
	/**
	 * Build and display the GUI
	 */
	public void display() {
		frame = new JFrame("Description Trees");
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.getContentPane().add(background);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//create the menu bar
		JMenuBar menu = new DescriptionTreeMenuBar(model, this);
		frame.setJMenuBar(menu);				
		
		//create the main compoenents
		Box westBox = createInputBox();
		Box centreBox = createTextOutputBox();
		Box eastBox = createRestrictionBox(background.getHeight());
		
		//add them to the background
		background.add(BorderLayout.CENTER, centreBox);
		background.add(BorderLayout.WEST, westBox);
		background.add(BorderLayout.EAST, eastBox);
		
		//set size and location of frame
		frame.setPreferredSize(new Dimension(1024, 517));
		frame.setLocation(screenSize.width / 3, screenSize.height / 3);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close the application on close
		frame.pack();
		frame.setVisible(true); //display
	}
	
	/**
	 * Creates the necessary components for input and returns them in a Box
	 * @return the Box containing the necessary components
	 */
	private Box createInputBox() {
		Box inputBox = new Box(BoxLayout.Y_AXIS);
		inputBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//create a dimension to be used for spinners
		Dimension spnrSize = new Dimension(50, 20);
		//create a border for containers
		Border paramBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);
		
		//create necessary Containers containing the necessary components
		Box box_params = createParamBox(spnrSize, paramBorder);
		Box box_treeChoice = createTreeChoiceBox(paramBorder);		
		Box box_numNodes = createNodeBox(spnrSize, paramBorder);		
		Box box_output = createOutputBox(paramBorder);	
		
		//add the containers to this box
		inputBox.add(box_treeChoice);
		inputBox.add(box_params);
		inputBox.add(box_numNodes);
		inputBox.add(box_output);
		inputBox.setMinimumSize(new Dimension((int) (frame.getWidth() * 0.5), frame.getHeight()));
		return inputBox;
	}
	
	/**
	 * Creates the necessary components for text output and returns them in a Box
	 * @return the Box containing the necessary components
	 */
	private Box createTextOutputBox() {
		Box txtOutputBox = new Box(BoxLayout.Y_AXIS);
		JPanel pnl_txtOut = new JPanel();
		JScrollPane scrl_txt = new JScrollPane(pnl_txtOut);
		scrl_txt.setToolTipText("Calculated/ Provided Tree(s)");
		txt_providedShape = new JTextPane();
		
		//style the output
		SimpleAttributeSet att = new SimpleAttributeSet();	
		StyleConstants.setFontSize(att, 30);
		
		txt_providedShape.setParagraphAttributes(att, true);
		txt_providedShape.setSize(new Dimension(pnl_txtOut.getWidth(), pnl_txtOut.getHeight()));
		txt_providedShape.setAutoscrolls(true);
		txt_providedShape.setEditable(false);
		
		pnl_txtOut.add(txt_providedShape);
		txtOutputBox.add(scrl_txt);
		return txtOutputBox;
	}
	
	/**
	 * Creates and returns a Box containing a scrolling JPanel for containing Restrictions
	 * @param height The height of the JPanel
	 * @return the Box containing the ScrollPane and add button
	 */
	private Box createRestrictionBox(int height) {
		Box restrictionBox = new Box(BoxLayout.Y_AXIS);
		restrictionBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JPanel pnl_restrictions = new JPanel();
		JScrollPane scrl_restrictions = new JScrollPane(pnl_restrictions);
		scrl_restrictions.setToolTipText("Restriction List");
		scrl_restrictions.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		box_scrl = new Box(BoxLayout.Y_AXIS);
		pnl_restrictions.add(box_scrl);
		pnl_restrictions.setPreferredSize(new Dimension(400, height));
		
		JButton btn_addRestriction = new JButton("+");
		btn_addRestriction.setHorizontalTextPosition(SwingConstants.CENTER);
		btn_addRestriction.setAlignmentX(Component.LEFT_ALIGNMENT);
		btn_addRestriction.setMaximumSize(new Dimension(400, 20));
		btn_addRestriction.addActionListener(new AddRestrictionListener(model, this));
		
		restrictionBox.setMinimumSize(new Dimension((int) (frame.getWidth() * 0.5), frame.getHeight()));
		restrictionBox.add(scrl_restrictions);
		restrictionBox.add(btn_addRestriction);
		return restrictionBox;
	}
	
	/**
	 * Creates a Box containing all the necessary components for choosing a tree type
	 * @param b The Border for this component
	 * @return the Box containing all of the necessary components
	 */
	private Box createTreeChoiceBox(Border b) {
		Box box_treeChoice = new Box(BoxLayout.Y_AXIS);
		
		JLabel lbl_treeChoiceInstr = new JLabel("<html>Please choose a description tree:</html>");
		ButtonGroup grp_trees = new ButtonGroup();
		rdo_alpha = new JRadioButton("Alpha(a, b)");
		rdo_alpha.setAlignmentX(Component.LEFT_ALIGNMENT);
		rdo_beta = new JRadioButton("Beta(a, b)");
		rdo_beta.setAlignmentX(Component.LEFT_ALIGNMENT);
		grp_trees.add(rdo_alpha);
		grp_trees.add(rdo_beta);
		
		box_treeChoice.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_treeChoice.setBorder(b);
		box_treeChoice.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_treeChoice.add(lbl_treeChoiceInstr);
		box_treeChoice.add(rdo_alpha);
		box_treeChoice.add(rdo_beta);
		return box_treeChoice;
	}
	
	/**
	 * Creates and returns a Box containing all the necessary 
	 * components for providing tree value parameters
	 * @param spnrSize the size for JSpinners
	 * @param b The Border for this component
	 * @return the Box containing all of the necessary components
	 */
	private Box createParamBox(Dimension spnrSize, Border b) {
		Box box_params = new Box(BoxLayout.Y_AXIS);
		JLabel lbl_params = new JLabel("<html>Provide the paramaters:</html>");
		lbl_params.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		Box box_a = new Box(BoxLayout.X_AXIS);
		box_a.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		Box box_b = new Box(BoxLayout.X_AXIS);
		box_b.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lbl_a = new JLabel("a = ");
		lbl_a.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel lbl_b = new JLabel("b = ");
		lbl_b.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		spnr_a = new JSpinner();
		spnr_a.setAlignmentX(Component.LEFT_ALIGNMENT);
		spnr_a.setModel(new SpinnerNumberModel(0, 0, null, 1));
		spnr_a.setMaximumSize(spnrSize);
		
		spnr_b = new JSpinner();
		spnr_b.setAlignmentX(Component.LEFT_ALIGNMENT);
		spnr_b.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_b.setMaximumSize(spnrSize);
		
		box_a.add(lbl_a);
		box_a.add(spnr_a);
		box_b.add(lbl_b);
		box_b.add(spnr_b);
		
		box_params.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_params.setBorder(b);
		box_params.add(lbl_params);
		box_params.add(box_a);
		box_params.add(box_b);
		return box_params;
	}
	
	/**
	 * Creates and returns a Box containing all the necessary 
	 * components for providing the minimum and maximum number of nodes
	 * @param spnrSize spnrSize the size for JSpinners
	 * @param b he Border for this component
	 * @return the Box containing all of the necessary components
	 */
	private Box createNodeBox(Dimension spnrSize, Border b) {
		Box box_numNodes = new Box(BoxLayout.Y_AXIS);
		spnr_nodeMin = new JSpinner();
		spnr_nodeMin.setAlignmentX(Component.LEFT_ALIGNMENT);
		spnr_nodeMin.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_nodeMin.setMaximumSize(spnrSize);
		
		spnr_nodeMax = new JSpinner();
		spnr_nodeMax.setAlignmentX(Component.LEFT_ALIGNMENT);
		spnr_nodeMax.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_nodeMax.setMaximumSize(spnrSize);
		
		JLabel lbl_numNodes = new JLabel("Provide the Number of Nodes:");
		lbl_numNodes.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		Box box_node = new Box(BoxLayout.X_AXIS);
		box_node.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lbl_node = new JLabel("n = ");
		lbl_node.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel lbl_toMax = new JLabel(" to ");
		lbl_toMax.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		box_node.add(lbl_node);
		box_node.add(spnr_nodeMin);
		box_node.add(lbl_toMax);
		box_node.add(spnr_nodeMax);
		
		box_numNodes.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_numNodes.setBorder(b);
		box_numNodes.add(lbl_numNodes);
		box_numNodes.add(box_node);
		return box_numNodes;
	}
	
	/**
	 * Creates and returns a Box containing all the necessary 
	 * components for calculating and outputting results
	 * @param b the Border for this component
	 * @return the Box containing the necessary components
	 */
	private Box createOutputBox(Border b) {
		Box box_output = new Box(BoxLayout.Y_AXIS);
		Box box_btns = new Box(BoxLayout.X_AXIS);
		box_btns.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		chkbx_useProvided = new JCheckBox("Use Provided Tree");
		chkbx_useProvided.setAlignmentX(Component.LEFT_ALIGNMENT);
		chkbx_useProvided.addActionListener(new UseProvidedListener(this));
		
		JButton btn_run = new JButton("Calculate");
		btn_run.setAlignmentX(Component.LEFT_ALIGNMENT);
		btn_run.addActionListener(new CalcNumTreesListener(this, model));
		box_btns.add(btn_run);
		
		JLabel lbl_totalTrees = new JLabel("<html>Total Number of Trees:</html>");
		lbl_totalTrees.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl_numTrees = new JLabel();
		lbl_numTrees.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lbl_numTreeSeqDesc = new JLabel("<html>Number of Trees sequence:</html>");
		lbl_numTreeSeqDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl_numTreeSeq = new JLabel();
		lbl_numTreeSeq.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		box_output.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_output.setBorder(b);
		box_output.add(chkbx_useProvided);
		box_output.add(box_btns);
		box_output.add(lbl_totalTrees);
		box_output.add(lbl_numTrees);
		box_output.add(lbl_numTreeSeqDesc);		
		box_output.add(lbl_numTreeSeq);
		return box_output;
	}
	
	/**
	 * Displays an error message to the users
	 * @param title the title of the message
	 * @param msg //the message
	 */
	public void displayError(String title, String msg) {
		JOptionPane.showMessageDialog(frame, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * sets the output for number of trees
	 * @param n the number of trees
	 */
	public void setNumTrees(int n) {
		lbl_numTrees.setText(Integer.toString(n));
	}
	
	/**
	 * 
	 * @return whether or not the requested tree type is Alpha
	 */
	public boolean isAlphaChecked() {
		return rdo_alpha.isSelected();
	}
	
	/**
	 * 
	 * @return whether or not the requested tree type is Beta
	 */
	public boolean isBetaChecked() {
		return rdo_beta.isSelected();
	}
	
	/**
	 * 
	 * @return the first parameter
	 */
	public int getParamA() {
		return (int) spnr_a.getValue();
	}
	
	/**
	 * 
	 * @return the second parameter
	 */
	public int getParamB() {
		return (int) spnr_b.getValue();
	}
	
	/**
	 * 
	 * @return the minimum node number
	 */
	public int getNodeMin() {
		return (int) spnr_nodeMin.getValue();
	}
	
	/**
	 * 
	 * @return the maximum node number
	 */
	public int getNodeMax() {
		return (int) spnr_nodeMax.getValue();
	}
	
	/**
	 * 
	 * @return whether or not to use the provided tree
	 */
	public boolean getChecked() {
		return chkbx_useProvided.isSelected();
	}
	
	/**
	 * Removes the given restrictor from the model
	 * @param r the Restrictor to remove
	 */
	public void removeRestriction(Restrictor r) {
		model.removeRestrictor(r);
	}
	
	/**
	 * Sets the tree num sequence output
	 * @param seq the sequence to set the output to
	 */
	public void setSequence(String seq) {
		lbl_numTreeSeq.setText(seq);
	}

	/**
	 * 
	 * @return the sequence of tree numbers
	 */
	public String getSequence() {
		return lbl_numTreeSeq.getText();
	}
	
	/**
	 * toggles the node spinners from enable to disabled and vice versa
	 */
	public void toggleNodesEnabled() {
		spnr_nodeMin.setEnabled(!spnr_nodeMin.isEnabled());
		spnr_nodeMax.setEnabled(!spnr_nodeMax.isEnabled());
	}
	
	/**
	 * 
	 * @return the location of the frame
	 */
	public Point frameLocation() {
		return frame.getLocation();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Tree) { //if we're given a Tree
			DescriptionTree t = new AlphaTree((Tree) arg); //create a DescriptionTree (type does not matter)
			txt_providedShape.setText(t.printTree()); //print it to the text output
		}		
		else if(arg instanceof Boolean) { //if we have a boolean
			if((Boolean) arg) { //if the bool is true
				//we were gievn a list of trees
				lbl_numTrees.setText(Integer.toString(model.getNumTrees())); //set the number of trees
				txt_providedShape.setText(""); //reset the text
				String printOut = "";
				for(DescriptionTree t : model.getTrees()) {
					printOut += t.printTree() + "\n"; //print every tree
				}
				txt_providedShape.setText(printOut);
			}
			else if (!(Boolean) arg){ //otherwise
				//we were given a list of restrictors
				box_scrl.removeAll(); //remove all components from the restrictor box
				List<Restrictor> restrictors = model.getRestrictors(); //get the new restrictor list
				for(Restrictor r : restrictors) {
					box_scrl.add(new RestrictionComponent(frame, r, this)); //add all the new components
				}
				frame.pack(); //pack the frame
			}
		}
	}
}
