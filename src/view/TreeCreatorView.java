package view;

import java.awt.Component;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.tool.ConnectionTool;
import listeners.SetDrawingListener;
import model.DescriptionTreeModel;

public class TreeCreatorView extends DrawApplication {

	private static final long serialVersionUID = -73769436478731568L;
	
	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	public TreeCreatorView(DescriptionTreeModel model, DescriptionTreeView view) {
		super("Tree Creator");
		this.model = model;
		this.view = view;
	}
	
	@Override
	protected void createTools(JPanel palette) {
		super.createTools(palette);
		Tool nodeTool = new NodeFigureCreationTool(view());
		Tool edgeConnection = new ConnectionTool(view(), new EdgeConnection());
		palette.add(createToolButton(IMAGES + "ELLIPSE", "Node Tool", nodeTool));
		palette.add(createToolButton(IMAGES + "CONN", "Edge Tool", edgeConnection));
	}
	
	@Override
	public void open() {
		super.open();
		Container c = this.getContentPane();
		
		Component drawingView = c.getComponent(0);
		
		Component statusLine = c.getComponent(2);
		c.remove(drawingView);
		c.remove(statusLine);
		
		Box topBox = new Box(BoxLayout.Y_AXIS);
		Box bottomBox = new Box(BoxLayout.Y_AXIS);
		
		//Box infoBox = new Box(BoxLayout.X_AXIS);
		JLabel lbl_info = new JLabel("Nodes are processed in numerical order. Therefore, if you create a root node such that it has children shown to be in order 2, 6, 3; then "
									+ "	the tree is considered as a root node with children 2, 3 and 6 in that order\n The overall structure of the children is similar.");
		//JButton btn_closeInfo = new JButton()
		
		JButton btn_setDrawing = new JButton("Set Drawing");
		btn_setDrawing.addActionListener(new SetDrawingListener(model, view,  this));
		
		topBox.add(lbl_info);
		topBox.add(drawingView);
		bottomBox.add(statusLine);
		bottomBox.add(btn_setDrawing);
		c.add("Center", topBox);
		c.add("South", bottomBox);			
		pack();
	}
	
	/**
	 * This is ill-advised however is 
	 * necessary to not shut down the main application
	 */
	@Override
	public void exit() {
		destroy();
        setVisible(false);      // hide the Frame
        dispose();   
	}
	
}
