package view;

import java.awt.Container;

import javax.swing.JButton;
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
		JButton btn_setDrawing = new JButton("Set Drawing");
		btn_setDrawing.addActionListener(new SetDrawingListener(model, view,  this));
		c.add("South", btn_setDrawing);
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
