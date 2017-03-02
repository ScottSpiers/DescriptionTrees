package restrictors;

import model.AbstractRestrictor;
import model.DescriptionTree;

public class PathLengthRestrictor extends AbstractRestrictor {

	public PathLengthRestrictor(String name) {
		super(name);
	}
	
	public PathLengthRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public PathLengthRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		int depth = t.getDepth();
		
		if(depth >= min && depth <= max) {
			return true;
		}
		
		return false;
	}
}
