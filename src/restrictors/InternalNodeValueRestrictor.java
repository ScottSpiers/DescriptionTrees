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
 * Restricts trees based on the value of internal nodes
 *
 */
public class InternalNodeValueRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = 4663910349099675318L;

	/**
	 * Constructor to provide the Restrictor's name
	 * @param name The name of the Restrictor
	 */
	public InternalNodeValueRestrictor(String name) {
		super(name);
	}
	
	/**
	 * Constructor to provide the Restricto's name and description
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 */
	public InternalNodeValueRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	/**
	 * Constructor to set all properties of the Restrictor
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 * @param min The initial minimum value to apply
	 * @param max The initial maximum value to apply
	 */
	public InternalNodeValueRestrictor(String name, String desc, int min, int max) {
		super(name, desc, min, max);
	}

	/*
	 * (non-Javadoc)
	 * @see restrictors.AbstractRestrictor#restrict(model.DescriptionTree)
	 */
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
