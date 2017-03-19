package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import CH.ifa.draw.figure.EllipseFigure;
import CH.ifa.draw.figure.GroupFigure;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.NullHandle;
import CH.ifa.draw.locator.RelativeLocator;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * A Composite Figure containing an ellipse and a textbox for labels
 * modelled on CH.ifa.drae.samples.pert.PertFigure
 * Represents a node or leaf of the tree
 */
public class NodeFigure extends GroupFigure {

	private static final long serialVersionUID = -7088318881204016542L;
	private static int count = 0; //used for node labels
	private int nodeNum = 0;
	private NodeFigure parent;
	private Vector<NodeFigure> children;
	
	/**
	 * Build the figure
	 */
	public NodeFigure() {
		init();		
	}
	
	/**
	 * Adds a child to this node if it does not already exist
	 * @param child The node or leaf which is this node's child
	 */
	public void addChild(NodeFigure child) {
		if(!children.contains(child)) {
			children.add(child);			
		}
	}
	
	/**
	 * Removes a child from this node if it exists
	 * @param child
	 */
	public void removeChild(NodeFigure child) {
		if(children.contains(child)) {
			children.remove(child);
		}
	}
	
	/**
	 * Sets this node's parent
	 * This is important for checking if the node can connect
	 * @param parent This node's parent
	 */
	public void setParent(NodeFigure parent) {
		this.parent = parent;
	}
	
	/**
	 * Reset this node's parent to null
	 * Used when removing connections to this node
	 */
	public void resetParent() {
		parent = null;
	}
	
	/**
	 * 
	 * @return this node's parent node
	 */
	public NodeFigure getParent() {
		if(parent == null) {
			return null;
		}
		return (NodeFigure) parent.clone();
	}
	
	/**
	 * 
	 * @return the children of this node
	 */
	public Enumeration<NodeFigure> getChildren() {
		return children.elements();
	}
	
	/**
	 * Build the node and initialise values
	 */
	private void init() {
		children = new Vector<NodeFigure>();
		parent = null;
		
		Figure ellipse = new EllipseFigure(new Point(0, 0), new Point(50,50));	//make a circle
		ellipse.setAttribute("FillColor", Color.white);
		
		TextFigure text = new TextFigure();
		text.moveBy(20, 20);
		TextFigure.setCurrentFontSize(18);
		TextFigure.setCurrentFontStyle(Font.BOLD);
		
		nodeNum = count++;
		text.setText(Integer.toString(nodeNum));
		text.setReadOnly(true);
		super.add(ellipse);
		super.add(text);
	}
	
	/**
	 * Checks whether or not this node is part of a cycle
	 * Do this by checking against the figure the connection is from
	 * if we have a parent then check the parent for cycles
	 * eventually, if there is a cycle, parent and start will be the same
	 * @param start The node to begin checking for cycles from
	 * @return true if a cycle is found, false otherwise
	 */
	public boolean hasCycle(Figure start) {
		//our catch case
		//if the following is true we have a cycle
		if (start == this) {
			return true;			
		}		
		
		//if we have a parent
        if(parent != null) {
        	if(parent.hasCycle(start)) { //check if parent has a cycle
        		return true;
        	}        	
        }
        return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.figure.GroupFigure#basicDisplayBox(java.awt.Point, java.awt.Point)
	 */
	@Override
	public void basicDisplayBox(Point origin, Point corner) {
		Rectangle r = displayBox();
		moveBy((int) origin.getX() - r.x, (int) origin.getY() - r.y);
	}

	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.figure.GroupFigure#handles()
	 */
	@Override
	public Vector<Handle> handles() {
		//no need to reshape but nice to have handles to see the figure is selected
		Vector<Handle> handles = new Vector<Handle>();
		handles.add(new NullHandle(this, RelativeLocator.northEast()));
		handles.add(new NullHandle(this, RelativeLocator.northWest()));
		handles.add(new NullHandle(this, RelativeLocator.southEast()));
		handles.add(new NullHandle(this, RelativeLocator.southWest()));
		return handles;
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.figure.GroupFigure#canConnect()
	 */
	@Override
	public boolean canConnect() {
		return true;
	}
}
