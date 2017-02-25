package view;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.CreationTool;

public class NodeFigureCreationTool extends CreationTool {
	
	public NodeFigureCreationTool(DrawingView view) {
		super(view);
	}
	
	@Override
	public Figure createFigure() {
		return new NodeFigure();
	}
}
