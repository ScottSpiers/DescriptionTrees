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
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.Figure;
import model.DescriptionTreeModel;
import model.scala.Empty;
import model.scala.Tree;
import view.DescriptionTreeView;
import view.NodeFigure;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Listener for setting a drawing
 * Builds a Tree by parsing figures and connections through the DrawingView
 */
public class SetDrawingListener implements ActionListener {

	DescriptionTreeModel model;
	DescriptionTreeView view;
	DrawApplication drawingApp;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 * @param d The current drawing application
	 */
	public SetDrawingListener(DescriptionTreeModel model, DescriptionTreeView view, DrawApplication d) {
		this.model = model;
		this.view = view;
		drawingApp = d;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Drawing drawing = drawingApp.drawing(); //get the drawing
		Figure f = null;
		NodeFigure root = null;
		List<NodeFigure> nodeFigs = new ArrayList<NodeFigure>();
		List<ConnectionFigure> connFigs = new ArrayList<ConnectionFigure>();
		Enumeration<Figure> figs = null;
		//create a root 
		Tree tree = new Empty();
		tree = tree.addLeaf();
		
		
		int rootCount = 0;
		boolean isRoot = true;
		
		figs = drawing.figures(); //get the figures
		
		while(figs.hasMoreElements()) { //while there are figures
			f = figs.nextElement(); //get the next figure
			if(f instanceof NodeFigure) { //if it is a node
				nodeFigs.add((NodeFigure) f);	//add it to the list of nodes			
			}
			else if(f instanceof ConnectionFigure) { //if its a connection
				connFigs.add((ConnectionFigure) f); //add it to the list of connections
			}
		}
		
		//for every node
		for(NodeFigure n : nodeFigs) {
			isRoot = true; //assume its a root until otherwise told
			for(ConnectionFigure conn : connFigs) { //for every connections
				if(conn.end().owner().equals(n)) { //if the current node is the connections end
					isRoot = false; //its not a root
					break; //no need to keep checking other connections
				}
			}
			if(isRoot) { //if its a root
				rootCount++; //increment root counter	
				root = n; //set the root to be this node
			}
		}
				
		//if we have more than one root
		if(rootCount > 1) {
			//this can't happen, tell the user
			JOptionPane.showMessageDialog(view.getFrame(), "More than one root node found.", "Tree Creation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else if(rootCount == 0) { //if we have no roots
			// tell the user
			JOptionPane.showMessageDialog(view.getFrame(), "No Root Node Found", "Tree Creation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {
			//intialise a map from nodes to their children
			Map<NodeFigure, List<NodeFigure>> figMap = new HashMap<NodeFigure, List<NodeFigure>>();
			
			//for every node figure
			for(NodeFigure nf : nodeFigs) {
				List<NodeFigure> children = new ArrayList<NodeFigure>(); //init a list
				for(ConnectionFigure conn : connFigs) { //for every connection
					if(conn.start().owner().equals(nf)) { //if this node is the connection start
						if(!conn.end().owner().equals(nf)) { // and not the end
							children.add((NodeFigure) conn.end().owner()); //add the end to the children list
						}
					}
				}
				figMap.put(nf, children); //map the node to the list of children
			}
			
			//create a stack
			Stack<NodeFigure> figStack = new Stack<NodeFigure>();
			figStack.push(root); //add the root
			
			int curNode = 0; //index begins from 0
			int numLeaves = 1; //always have atleast 1 leaf
			int numNodes = 0;
			int childCount = 0;
			while(!figStack.isEmpty()) { //while we have nodes in the stack
				NodeFigure node = figStack.pop(); //get the node
				Enumeration<NodeFigure> children = node.getChildren(); //get the children
				List<NodeFigure> kids = new ArrayList<NodeFigure>(); //init a list
				while(children.hasMoreElements()) { //while the child list has more elements
					NodeFigure child = children.nextElement(); //get the list
					kids.add(child); //add to the list
					if(childCount == 0) { // if current is a leaf
						//add a leaf to it
						tree = tree.addLeafToLeaf(numLeaves - (tree.getNumVertices(0) - curNode)); //the leaf index is the total leaves - total vertices - the current node index
						numNodes++; //we have a new node
						//no new leaves
						childCount++;
					}
					else { //otherwise we are a node
						tree = tree.addLeafToNode(numNodes - 1); //add a leaf to the node. THe node index is the number of nodes - 1
						numLeaves++; //increase the number of leaves
					}
				}
				
				//reverse the list of children
				Collections.reverse(kids);
				for(NodeFigure k : kids) { //for every child
					figStack.push(k); //push to the stack
				}
				childCount = 0;
				curNode++;
			}
		}
		
		model.setProvidedTree(tree); //set the tree in the model
		view.getFocus();
	}

}
