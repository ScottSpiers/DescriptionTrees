package restrictors;

import model.DescriptionTree;

public class RootChildrenNumRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = 7370083957299180247L;

	public RootChildrenNumRestrictor(String name) {
		super(name);
	}
	
	public RootChildrenNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public RootChildrenNumRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		int numChildren = t.getNumChildren();
		
		if(numChildren >= min && numChildren <= max) {
			return true;
		}
		
		return false;
	}

}
