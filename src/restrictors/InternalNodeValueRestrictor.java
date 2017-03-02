package restrictors;

import java.util.List;

import model.AbstractRestrictor;
import model.DescriptionTree;
import model.scala.Tree;

public class InternalNodeValueRestrictor extends AbstractRestrictor {

	public InternalNodeValueRestrictor(String name) {
		super(name);
	}
	
	public InternalNodeValueRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public InternalNodeValueRestrictor(String name, String desc, int min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		List<Tree> nodes = t.getNodes();
		//start at 1 to ignore root
		for(int i = 1; i < nodes.size(); i++) {
			Tree curNode = nodes.get(i);
			if(curNode.getValue() < min || curNode.getValue() > max) {
				return false;
			}
		}
		return true;
	}
}
