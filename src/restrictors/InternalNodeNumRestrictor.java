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
 * Restricts trees based on the number of internal nodes
 *
 */
public class InternalNodeNumRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = -5294518702029885866L;

	/**
	 * Constructor to provide the Restrictor's name
	 * @param name The name of the Restrictor
	 */
	public InternalNodeNumRestrictor(String name) {
		super(name);
	}
	
	/**
	 * Constructor to provide the Restricto's name and description
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 */
	public InternalNodeNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	/**
	 * Constructor to set all properties of the Restrictor
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 * @param min The initial minimum value to apply
	 * @param max The initial maximum value to apply
	 */
	public InternalNodeNumRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	/*
	 * (non-Javadoc)
	 * @see restrictors.AbstractRestrictor#restrict(model.DescriptionTree)
	 */
	@Override
	public boolean restrict(DescriptionTree t) {
		List<Tree> nodes = t.getNodes();
		if(nodes.size()-1 >= min && nodes.size()-1 <= max) {
			return true;
		}
		return false;
	}

}
