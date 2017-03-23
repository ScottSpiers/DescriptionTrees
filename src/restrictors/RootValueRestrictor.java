package restrictors;

import model.DescriptionTree;

public class RootValueRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = 8758905229203795135L;

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
