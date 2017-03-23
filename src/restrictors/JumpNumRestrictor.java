package restrictors;

import java.util.List;

import model.DescriptionTree;
import model.scala.Tree;

public class JumpNumRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = -7127594767349336223L;

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
		
		int index = 0;
		for(Tree n : nodes) {
			if (index == 0) {
				index++;
				continue; //ignore the root
			}
			int sum = 0;
			for(Tree child : n.getAllChildren()) {
				sum += child.getValue();
			}
			if(n.getValue() > sum) {
				jumps++;
			}
			index++;
		}
		
		if(jumps >= min && jumps <= max) {
			return true;
		}
		
		return false;
	}
	

}
