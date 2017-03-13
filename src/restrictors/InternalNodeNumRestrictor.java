package restrictors;

import java.util.List;

import model.AbstractRestrictor;
import model.DescriptionTree;
import model.scala.Tree;

public class InternalNodeNumRestrictor extends AbstractRestrictor {

	public InternalNodeNumRestrictor(String name) {
		super(name);
	}
	
	public InternalNodeNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public InternalNodeNumRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		List<Tree> nodes = t.getNodes();
		if(nodes.size()-1 >= min && nodes.size()-1 <= max) {
			return true;
		}
		return false;
	}

}