package restrictors;

import java.util.List;

import model.DescriptionTree;
import model.scala.Tree;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Restricts trees based on the number of jumps
 * A jump is any time an internal node is greater than the sum of its children
 */
public class JumpNumRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = -7127594767349336223L;

	/**
	 * Constructor to provide the Restrictor's name
	 * @param name The name of the Restrictor
	 */
	public JumpNumRestrictor(String name) {
		super(name);
	}
	
	/**
	 * Constructor to provide the Restricto's name and description
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 */
	public JumpNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	/**
	 * Constructor to set all properties of the Restrictor
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 * @param min The initial minimum value to apply
	 * @param max The initial maximum value to apply
	 */
	public JumpNumRestrictor(String name, String desc, int min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		int jumps = 0;
		List<Tree> nodes = t.getNodes(); //get nodes
		
		int index = 0;
		for(Tree n : nodes) { //for every node
			if (index == 0) {
				index++;
				continue; //ignore the root
			}
			int sum = 0;
			for(Tree child : n.getAllChildren()) { //for every child of current node
				sum += child.getValue(); //add its value to the sum
			}
			if(n.getValue() > sum) { //if the nodes value is greater than sum
				jumps++; //we have a jump
			}
			index++;
		}
		
		//return true if the number of jumps is between min and max (inclusive)
		if(jumps >= min && jumps <= max) {
			return true;
		}
		
		return false;
	}
	

}
