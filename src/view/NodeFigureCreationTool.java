package view;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.CreationTool;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * The creation tool for node figures
 * makes use of the factory method to ensure 
 * static field in NodeFigure updates
 */
public class NodeFigureCreationTool extends CreationTool {
	
	/**
	 * 
	 * @param view The DrawingView
	 */
	public NodeFigureCreationTool(DrawingView view) {
		super(view);
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.tool.CreationTool#createFigure()
	 */
	@Override
	public Figure createFigure() {
		return new NodeFigure();
	}
}
