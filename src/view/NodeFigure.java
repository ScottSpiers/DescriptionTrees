package view;

import java.awt.Point;
import java.util.Vector;

import CH.ifa.draw.figure.EllipseFigure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.NullHandle;
import CH.ifa.draw.locator.RelativeLocator;

public class NodeFigure extends EllipseFigure {

	private static final long serialVersionUID = -7088318881204016542L;
	
	@Override
	public void basicDisplayBox(Point origin, Point corner) {
		int size = 50;
		corner = new Point(origin.x + size, origin.y + size);
		super.basicDisplayBox(origin, corner);
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
}
