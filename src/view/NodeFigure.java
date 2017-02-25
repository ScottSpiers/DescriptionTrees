package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import CH.ifa.draw.figure.EllipseFigure;
import CH.ifa.draw.figure.GroupFigure;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.NullHandle;
import CH.ifa.draw.locator.RelativeLocator;

public class NodeFigure extends GroupFigure {

	private static final long serialVersionUID = -7088318881204016542L;
	private static int count = 0;
	private int nodeNum = 0;
	
	public NodeFigure() {
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
