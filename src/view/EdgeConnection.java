package view;

import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Figure;

public class EdgeConnection extends LineConnection {

	private static final long serialVersionUID = -9129831921216033098L;
	
	public EdgeConnection() {
		super();
		setStartDecoration(null);
	}
	
	@Override
	public boolean canConnect(Figure start, Figure end) {
		NodeFigure source = (NodeFigure) start;
        NodeFigure target = (NodeFigure) end;
        
        if (source.hasCycle(target)) {
        	return false;
        }
        else {
        	return ((NodeFigure) end).getParent() == null;        	
        }
	}
	
	@Override
	public void handleConnect(Figure start, Figure end) {
		NodeFigure source = (NodeFigure) start;
        NodeFigure target = (NodeFigure) end;        
        
        source.addChild(target);
        target.setParent(source);
	}
	
	@Override
	public void handleDisconnect(Figure start, Figure end) {
		NodeFigure source = (NodeFigure) start;
        NodeFigure target = (NodeFigure) end;
        if (source != null) {
            source.removeChild(target);
        }
        if(target != null) {
        	target.resetParent();
        }
	}
}
