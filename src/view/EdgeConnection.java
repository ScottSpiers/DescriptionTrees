package view;

import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Figure;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * A ConnectionFigure to represent an edge of the tree
 */
public class EdgeConnection extends LineConnection {

	private static final long serialVersionUID = -9129831921216033098L;
	
	/**
	 * Set the 'direction' of the connection to the end
	 */
	public EdgeConnection() {
		super();
		setStartDecoration(null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.figure.connection.LineConnection#canConnect(CH.ifa.draw.framework.Figure, CH.ifa.draw.framework.Figure)
	 */
	@Override
	public boolean canConnect(Figure start, Figure end) {
		NodeFigure source = (NodeFigure) start;
        NodeFigure target = (NodeFigure) end;
        
        //if we have a cycle
        if (source.hasCycle(target)) {
        	return false; //we can't connect
        }
        else { //otherwise
        	//return whether or not a target already has a parent or not
        	// if it does we can't connect
        	return ((NodeFigure) end).getParent() == null;        	
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.figure.connection.LineConnection#handleConnect(CH.ifa.draw.framework.Figure, CH.ifa.draw.framework.Figure)
	 */
	@Override
	public void handleConnect(Figure start, Figure end) {
		NodeFigure source = (NodeFigure) start;
        NodeFigure target = (NodeFigure) end;        
        
        //simply add the child to source and set the parent of target
        source.addChild(target);
        target.setParent(source);
	}
	
	/*
	 * (non-Javadoc)
	 * @see CH.ifa.draw.figure.connection.LineConnection#handleDisconnect(CH.ifa.draw.framework.Figure, CH.ifa.draw.framework.Figure)
	 */
	@Override
	public void handleDisconnect(Figure start, Figure end) {
		NodeFigure source = (NodeFigure) start;
        NodeFigure target = (NodeFigure) end;
        //safety check
        if (source != null) {
        	//remove target as a child of source
            source.removeChild(target);
        }
        //safety check
        if(target != null) {
        	//reset targets parent
        	target.resetParent();
        }
	}
}
