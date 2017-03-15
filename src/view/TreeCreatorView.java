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
		
		JTextField statusLine = (JTextField) c.getComponent(2);
		statusLine.setAlignmentX(Component.LEFT_ALIGNMENT);
		c.remove(drawingView);
		c.remove(statusLine);
		
		Box topBox = new Box(BoxLayout.Y_AXIS);
		Box bottomBox = new Box(BoxLayout.Y_AXIS);
		
		//Box infoBox = new Box(BoxLayout.X_AXIS);
		JLabel lbl_info = new JLabel("Each level is processed in numerical order by the node label shown. Therefore, creating a node such that it has children drawn as 2, 3, 1;");
		JLabel lbl_info2 = new JLabel("gives the node the children 1, 2 and 3 in that order regardless of further structure.");
		
		
		JButton btn_setDrawing = new JButton("Set Drawing");
		btn_setDrawing.setAlignmentX(Component.LEFT_ALIGNMENT);
		btn_setDrawing.setMaximumSize(new Dimension(850, 20));
		btn_setDrawing.addActionListener(new SetDrawingListener(model, view,  this));
		
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
