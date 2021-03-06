package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.tool.ConnectionTool;
import CH.ifa.draw.application.DrawApplication;
import listeners.SetDrawingListener;
import model.DescriptionTreeModel;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * The implementation of the Provide SHape feature using JHotDraw framework
 */
public class TreeCreatorView extends DrawApplication {

	private static final long serialVersionUID = -73769436478731568L;
	
	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public TreeCreatorView(DescriptionTreeModel model, DescriptionTreeView view) {
		super("Tree Creator");
		this.model = model;
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.application.DrawApplication#createTools(javax.swing.JPanel)
	 */
	@Override
	protected void createTools(JPanel palette) {
		super.createTools(palette);
		Tool nodeTool = new NodeFigureCreationTool(view()); //create the node tool 
		Tool edgeConnection = new ConnectionTool(view(), new EdgeConnection()); //create the edge connection
		//add these to the palette
		palette.add(createToolButton(IMAGES + "ELLIPSE", "Node Tool", nodeTool));
		palette.add(createToolButton(IMAGES + "CONN", "Edge Tool", edgeConnection));
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.application.DrawApplication#open()
	 */
	@Override
	public void open() {
		super.open(); 
		Container c = this.getContentPane(); //get the content pane
		
		Component drawingView = c.getComponent(0); //get the drawing view from that
		
		//From that get the status line
		JTextField statusLine = (JTextField) c.getComponent(2);
		statusLine.setAlignmentX(Component.LEFT_ALIGNMENT); //set its alignment to left
		//remove the two components
		c.remove(drawingView); 
		c.remove(statusLine);
		
		//create new containers
		Box topBox = new Box(BoxLayout.Y_AXIS);
		Box bottomBox = new Box(BoxLayout.Y_AXIS);
		
		//create information labels
		JLabel lbl_info = new JLabel("Each level is processed in numerical order by the node label shown. Therefore, creating a node such that it has children drawn as 2, 3, 1;");
		JLabel lbl_info2 = new JLabel("gives the node the children 1, 2 and 3 in that order regardless of further structure.");
		
		//create a button to set the drawing
		JButton btn_setDrawing = new JButton("Set Drawing");
		btn_setDrawing.setAlignmentX(Component.LEFT_ALIGNMENT);
		btn_setDrawing.setMaximumSize(new Dimension(850, 20));
		btn_setDrawing.addActionListener(new SetDrawingListener(model, view,  this));
		
		//add the new components and the old compoenents back
		//this reorders the components to be displayed correctly
		topBox.add(lbl_info);
		topBox.add(lbl_info2);
		topBox.add(drawingView);
		bottomBox.add(statusLine);
		bottomBox.add(btn_setDrawing);
		c.add("Center", topBox);
		c.add("South", bottomBox);	
		this.setPreferredSize(new Dimension(850, 800));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation( (int) (screenSize.width / 1.675), screenSize.height / 3);
		this.setResizable(false);
		pack();
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.application.DrawApplication#exit()
	 * necessary to not shut down main application
	 */
	@Override
	public void exit() {
		destroy();
        setVisible(false);  // hide the Frame
        dispose();   
	}
	
}
