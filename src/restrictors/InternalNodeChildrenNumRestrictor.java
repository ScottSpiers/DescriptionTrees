package restrictors;

import java.util.List;

import model.AbstractRestrictor;
import model.DescriptionTree;
import model.scala.Tree;

public class InternalNodeChildrenNumRestrictor  extends AbstractRestrictor {

	public InternalNodeChildrenNumRestrictor(String name) {
		super(name);
	}
	
	public InternalNodeChildrenNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public InternalNodeChildrenNumRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		List<Tree> nodes = t.getNodes();
		
		for(Tree n : nodes) {
			int numChildren = n.getNumChildren();
			if(numChildren < min || numChildren > max) {
				return false;
			}
		}
		
		return true;
	}	
}