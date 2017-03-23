package restrictors;

import model.DescriptionTree;

public class LeafNumRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = -3656758475448134321L;

	public LeafNumRestrictor(String name) {
		super(name);
	}
	
	public LeafNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public LeafNumRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		if(t.getNumLeaves() >= min && t.getNumLeaves() <= max) {
			return true;
		}
		return false;
	}


}
