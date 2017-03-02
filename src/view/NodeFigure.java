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
import CH.ifa.draw.samples.pert.PertFigure;

public class NodeFigure extends GroupFigure {

	private static final long serialVersionUID = -7088318881204016542L;
	private static int count = 0;
	private int nodeNum = 0;
	private NodeFigure parent;
	private Vector<NodeFigure> children;
	
	public NodeFigure() {
		init();		
	}
	
	public void addChild(NodeFigure child) {
		if(!children.contains(child)) {
			children.add(child);			
		}
	}
	
	public void removeChild(NodeFigure child) {
		if(children.contains(child)) {
			children.remove(child);
		}
	}
	
	public void setParent(NodeFigure parent) {
		this.parent = parent;
	}
	
	public void resetParent() {
		parent = null;
	}
	
	public NodeFigure getParent() {
		if(parent == null) {
			return null;
		}
		return (NodeFigure) parent.clone();
	}
	
	public Enumeration<NodeFigure> getChildren() {
		return children.elements();
	}
	
	private void init() {
		children = new Vector<NodeFigure>();
		parent = null;
		
		Figure ellipse = new EllipseFigure(new Point(0, 0), new Point(50,50));		
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
	
	public boolean hasCycle(Figure start) {
		if (start == this) {
			return true;			
		}		
		
        Enumeration<NodeFigure> i = children.elements();
        if(parent != null) {
        	if(parent.hasCycle(start)) {
        		return true;
        	}        	
        }
        return false;
	}
	
	@Override
	public void basicDisplayBox(Point origin, Point corner) {
		Rectangle r = displayBox();
		moveBy((int) origin.getX() - r.x, (int) origin.getY() - r.y);
	}

	@Override
	public Vector<Handle> handles() {
		Vector<Handle> handles = new Vector<Handle>();
		handles.add(new NullHandle(this, RelativeLocator.northEast()));
		handles.add(new NullHandle(this, RelativeLocator.northWest()));
		handles.add(new NullHandle(this, RelativeLocator.southEast()));
		handles.add(new NullHandle(this, RelativeLocator.southWest()));
		return handles;
	}
	
	@Override
	public boolean canConnect() {
		return true;
	}
}
