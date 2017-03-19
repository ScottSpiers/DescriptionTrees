package restrictors;

import java.util.List;

import model.AbstractRestrictor;
import model.DescriptionTree;
import model.scala.Tree;

public class JumpNumRestrictor extends AbstractRestrictor {

	public JumpNumRestrictor(String name) {
		super(name);
	}
	
	public JumpNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public JumpNumRestrictor(String name, String desc, int min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		int jumps = 0;
		List<Tree> nodes = t.getNodes();
		
		for(Tree n : nodes) {
			int sum = 0;
			for(Tree child : n.getAllChildren()) {
				sum += child.getValue();
			}
			if(n.getValue() > sum) {
				jumps++;
			}
		}
		
		if(jumps >= min && jumps <= max) {
			return true;
		}
		
		return false;
	}
	

}
