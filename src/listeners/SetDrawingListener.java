package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JOptionPane;

import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.figure.CompositeFigure;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.Figure;
import view.NodeFigure;
import model.DescriptionTreeModel;
import model.scala.Empty;
import model.scala.Tree;
import view.DescriptionTreeView;

public class SetDrawingListener implements ActionListener {

	DescriptionTreeModel model;
	DescriptionTreeView view;
	DrawApplication drawingApp;
	
	public SetDrawingListener(DescriptionTreeModel model, DescriptionTreeView view, DrawApplication d) {
		this.model = model;
		this.view = view;
		drawingApp = d;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Drawing drawing = drawing();
		Drawing drawing = drawingApp.drawing(); 
		Figure f = null;
		Figure root = null;
		ConnectionFigure cf = null;
		List<NodeFigure> nodeFigs = new ArrayList<NodeFigure>();
		List<ConnectionFigure> connFigs = new ArrayList<ConnectionFigure>();
		Enumeration<Figure> figs = null;
		Enumeration<Figure> conFigs = null;
		Tree tree = new Empty();
		tree = tree.addLeaf();
		
		
		int rootCount = 0;
		boolean isRoot = true;
		int nodeCount = 0;
		
		figs = drawing.figures();
		
		while(figs.hasMoreElements()) {
			f = figs.nextElement();
			if(f instanceof NodeFigure) {
				nodeFigs.add((NodeFigure) f);				
			}
			else if(f instanceof ConnectionFigure) {
				connFigs.add((ConnectionFigure) f);
			}
		}
		
		
		for(NodeFigure n : nodeFigs) {
			isRoot = true;
			for(ConnectionFigure conn : connFigs) {
				if(conn.end().owner().equals(n)) {
					isRoot = false;
					break;
				}
			}
			if(isRoot) {
				rootCount++;				
				root = n;
			}
		}
		
		
		
		if(rootCount > 1) {
			JOptionPane.showMessageDialog(null, "More than one root node found.");
			return;
		}
		else if(rootCount == 0) {
			JOptionPane.showMessageDialog(null, "No Root Node Found");
			return;
		}
		else {
			Map<Figure, List<Figure>> figMap = new HashMap<Figure, List<Figure>>();
			
			
			for(NodeFigure nf : nodeFigs) {
				List<Figure> children = new ArrayList<Figure>();
				for(ConnectionFigure conn : connFigs) {
					if(conn.start().owner().equals(nf)) {
						if(!conn.end().owner().equals(nf)) {
							children.add(conn.end().owner());
						}
					}
				}
				figMap.put(nf, children);
			}
			
			
			Stack<Figure> figStack = new Stack<Figure>();
			figStack.push(root);
			
			int curNode = 0;
			int numLeaves = 1;
			int numNodes = 0;
			int childCount = 0;
			while(!figStack.isEmpty()) {
				Figure node = figStack.pop();
				for(Figure child : figMap.get(node)) {
					if(childCount == 0) {
						tree = tree.addLeafToLeaf(numLeaves - (tree.getNumVertices() - curNode));
						numNodes++;
						childCount++;
					}
					else {
						tree = tree.addLeafToNode(numNodes - 1);
						numLeaves++;
					}
				}
				List<Figure> kids = figMap.get(node);
				Collections.reverse(kids);
				for(Figure k : kids) {
					figStack.push(k);
				}
				childCount = 0;
				curNode++;
			}
		}
		//temp
		System.out.println(tree);
		System.out.println(tree.getNodes().size());
		model.setProvidedTree(tree);
		
		view.setImage(drawingApp.createImage(drawingApp.getWidth(), drawingApp.getHeight()));
		
		drawingApp.exit();
	}

}
