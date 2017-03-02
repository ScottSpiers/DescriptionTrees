package restrictors;

import model.AbstractRestrictor;
import model.DescriptionTree;

public class RootValueRestrictor extends AbstractRestrictor {

	public RootValueRestrictor(String name) {
		super(name);
	}
	
	public RootValueRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public RootValueRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		if(t.getValue() >= min && t.getValue() <= max) {
			return true;
		}
		return false;
	}

}
