package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.tool.ConnectionTool;
import model.AlphaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;

public class TreeCreatorView extends DrawApplication {

	private static final long serialVersionUID = -73769436478731568L;
	
	DescriptionTreeModel model;
	
	public TreeCreatorView(DescriptionTreeModel model) {
		super("Tree Creator");
		this.model = model;
	}
	
	@Override
	protected void createTools(JPanel palette) {
		super.createTools(palette);
		Tool nodeTool = new NodeFigureCreationTool(view());
		Tool edgeConnection = new ConnectionTool(view(), new EdgeConnection());
		palette.add(createToolButton(IMAGES + "ELLIPSE", "Node Tool", nodeTool));
		palette.add(createToolButton(IMAGES + "CONN", "Edge Tool", edgeConnection));
	}
	
	/**
	 * This is ill-advised however is 
	 * necessary to not shut down the main application
	 */
	@Override
	public void exit() {
		Drawing drawing = drawing();
		Figure f = null;
		Figure root = null;
		ConnectionFigure cf = null;
		List<Figure> figures = new ArrayList<Figure>();
		DescriptionTree tree = new AlphaTree();
		tree.addLeaf();
		int rootCount = 0;
		boolean isRoot = false;
		int nodeCount = 0;
		
		while(drawing.figures().hasMoreElements()) {
			f = drawing.figures().nextElement();
			while(f.figures().hasMoreElements()) {
				Figure fs = f.figures().nextElement();
				if(fs instanceof ConnectionFigure) {
					cf = (ConnectionFigure) fs;
					if(cf.end().owner().equals(f)) {
						isRoot = false;				
						break;
					}
				}
				else {
					figures.add(f);
				}
			}
			figures.remove(f);
			figures.add(0, f);
			isRoot = true;
			rootCount++;
			root = f;
		}
		
		int curNode = 0;
		
		if(rootCount > 1) {
			JOptionPane.showMessageDialog(this, "More than one root node found.");
			return;
		}
		else {
			while(curNode < figures.size()) {
				int numLeaves = 1;
				while(figures.get(curNode).figures().hasMoreElements()) {
					int childCount = 0;
					f = figures.get(curNode).figures().nextElement();
					if(f instanceof ConnectionFigure) {
						cf = (ConnectionFigure) f;
						if(cf.start().owner().equals(figures.get(curNode)) && !cf.end().owner().equals(figures.get(curNode))) {
							if(childCount == 0) {
								tree.addLeafToLeaf(numLeaves - 1);
								childCount++;
							}
							else {
								tree.addLeafToNode(curNode);
								numLeaves++;
							}
						}
						else {
							JOptionPane.showMessageDialog(this, "Please check the tree structure is correct.");
							return;
						}
					}
				}
				curNode++;
			}
		}
		//temp
		List<DescriptionTree> newTree = new ArrayList<DescriptionTree>();
		newTree.add(tree);
		model.addTrees(newTree);
		
		destroy();
        setVisible(false);      // hide the Frame
        dispose();   
	}
	
}
