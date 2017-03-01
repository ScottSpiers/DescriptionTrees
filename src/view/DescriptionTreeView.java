package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import listeners.CalcNumTreesListener;
import model.DescriptionTreeModel;
import model.Restrictor;

public class DescriptionTreeView implements Observer {

	private DescriptionTreeModel model;
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
	private JPanel pnl_img;
	
	public DescriptionTreeView() {
		model = new DescriptionTreeModel();
		model.addObserver(this);
	}
	
	public void display() {
		frame = new JFrame("Description Trees");
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.getContentPane().add(background);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2)); 
		
		JMenuBar menu = new DescriptionTreeMenuBar(model, this);
		frame.setJMenuBar(menu);
		
		Box westBox = new Box(BoxLayout.Y_AXIS);
		Box eastBox = new Box(BoxLayout.X_AXIS);
		
		Dimension spnrSize = new Dimension(50, 20);
		
		JLabel lbl_treeChoiceInstr = new JLabel("<html>Please choose a description tree:</html>");
		ButtonGroup grp_trees = new ButtonGroup();
		rdo_alpha = new JRadioButton("Alpha(a, b)");
		rdo_beta = new JRadioButton("Beta(a, b)");
		grp_trees.add(rdo_alpha);
		grp_trees.add(rdo_beta);
		
		JLabel lbl_params = new JLabel("<html>Provide the paramaters:</html>");
		Box box_a = new Box(BoxLayout.X_AXIS);
		Box box_b = new Box(BoxLayout.X_AXIS);
		JLabel lbl_a = new JLabel("a = ");
		JLabel lbl_b = new JLabel("b = ");
		
		spnr_a = new JSpinner();
		spnr_a.setModel(new SpinnerNumberModel(0, 0, null, 1));
		spnr_a.setMaximumSize(spnrSize);
		
		spnr_b = new JSpinner();
		spnr_b.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_b.setMaximumSize(spnrSize);
		
		box_a.add(lbl_a);
		box_a.add(spnr_a);
		box_b.add(lbl_b);
		box_b.add(spnr_b);
		
		
		spnr_nodeMin = new JSpinner();
		spnr_nodeMin.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_nodeMin.setMaximumSize(spnrSize);
		
		spnr_nodeMax = new JSpinner();
		spnr_nodeMax.setModel(new SpinnerNumberModel(1, 0, null, 1));
		spnr_nodeMax.setMaximumSize(spnrSize);
		
		JLabel lbl_numNodes = new JLabel("Provide the Number of Nodes:");
		lbl_numNodes.setAlignmentX(Component.LEFT_ALIGNMENT);
		Box box_node = new Box(BoxLayout.X_AXIS);
		JLabel lbl_node = new JLabel("n = ");
		JLabel lbl_toMax = new JLabel(" to ");
		box_node.add(lbl_node);
		box_node.add(spnr_nodeMin);
		box_node.add(lbl_toMax);
		box_node.add(spnr_nodeMax);
		
		Box box_btns = new Box(BoxLayout.X_AXIS);
		JButton btn_print = new JButton("Print");
		JButton btn_run = new JButton("Calculate");
		btn_run.addActionListener(new CalcNumTreesListener(this, model));
		box_btns.add(btn_run);
		box_btns.add(btn_print);
		JLabel lbl_totalTrees = new JLabel("<html>Total Number of Trees:</html>");
		lbl_numTrees = new JLabel();
		
		JLabel lbl_numTreeSeqDesc = new JLabel("<html>Number of Trees sequence:</html>");
		JLabel lbl_numTreeSeq = new JLabel();
		
		JPanel pnl_restrictions = new JPanel();
		JScrollPane scrl_restrictions = new JScrollPane(pnl_restrictions);
		box_scrl= new Box(BoxLayout.Y_AXIS);
		//pnl_restrictions.setLayout(new BoxLayout(pnl_restrictions, BoxLayout.Y_AXIS));
		pnl_restrictions.add(box_scrl);
		/*box_scrl.add(new RestrictionComponent(frame, new LeafNumRestrictor("Number of Leaves: ", "Restricts the number of leaves")));
		box_scrl.add(new RestrictionComponent(frame, new InternalNodeRestrictor("Number of Nodes: ", "Restricts the number of internal nodes (Excluding root)")));*/
		
		Border paramBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);
		
		Box box_treeChoice = new Box(BoxLayout.Y_AXIS);
		box_treeChoice.setBorder(paramBorder);
		box_treeChoice.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_treeChoice.add(lbl_treeChoiceInstr);
		box_treeChoice.add(rdo_alpha);
		box_treeChoice.add(rdo_beta);
		
		Box box_params = new Box(BoxLayout.Y_AXIS);
		box_params.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_params.setBorder(paramBorder);
		box_params.add(lbl_params);
		box_params.add(box_a);
		box_params.add(box_b);
		
		Box box_numNodes = new Box(BoxLayout.Y_AXIS);
		box_numNodes.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_numNodes.setBorder(paramBorder);
		box_numNodes.add(lbl_numNodes);
		box_numNodes.add(box_node);
		
		chkbx_useProvided = new JCheckBox("Use Provided Tree");
		
		Box box_output = new Box(BoxLayout.Y_AXIS);
		box_output.setAlignmentX(Component.LEFT_ALIGNMENT);
		box_output.setBorder(paramBorder);
		box_output.add(chkbx_useProvided);
		box_output.add(box_btns);
		box_output.add(lbl_totalTrees);
		box_output.add(lbl_numTrees);
		box_output.add(lbl_numTreeSeqDesc);		
		box_output.add(lbl_numTreeSeq);
		
		westBox.add(box_treeChoice);
		westBox.add(box_params);
		westBox.add(box_numNodes);
		westBox.add(box_output);
		westBox.setMinimumSize(new Dimension((int) (frame.getWidth() * 0.5), frame.getHeight()));
		
		eastBox.add(scrl_restrictions);
		eastBox.setMinimumSize(new Dimension((int) (frame.getWidth() * 0.5), frame.getHeight()));
		
		pnl_img = new JPanel();
		
		
		background.add(BorderLayout.WEST, westBox);
		background.add(BorderLayout.EAST, eastBox);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void displayError(String title, String msg) {
		JOptionPane.showMessageDialog(frame, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public void setNumTrees(int n) {
		lbl_numTrees.setText(Integer.toString(n));
	}
	
	public boolean isAlphaChecked() {
		return rdo_alpha.isSelected();
	}
	
	public boolean isBetaChecked() {
		return rdo_beta.isSelected();
	}
	
	public int getParamA() {
		return (int) spnr_a.getValue();
	}
	
	public int getParamB() {
		return (int) spnr_b.getValue();
	}
	
	public int getNodeMin() {
		return (int) spnr_nodeMin.getValue();
	}
	
	public int getNodeMax() {
		return (int) spnr_nodeMax.getValue();
	}
	
	public boolean getChecked() {
		return chkbx_useProvided.isSelected();
	}
	
	public void removeRestriction(Restrictor r) {
		model.removeRestrictor(r);
	}
	
	public void setImage(Image i) {
		pnl_img.removeAll();
		Graphics g = pnl_img.getGraphics();
		g.drawImage(i, 0, 0, pnl_img);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Boolean) {
			if((Boolean) arg) {
				lbl_numTrees.setText(Integer.toString(model.getNumTrees()));
				System.out.println(model.getNumTrees());
			}
			else if (!(Boolean) arg){
				box_scrl.removeAll();
				List<Restrictor> restrictors = model.getRestrictors();
				for(Restrictor r : restrictors) {
					box_scrl.add(new RestrictionComponent(frame, r, this));
					System.out.println(r);
				}
				frame.pack();
			}
		}
	}
}
