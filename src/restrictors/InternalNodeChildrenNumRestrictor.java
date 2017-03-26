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
 * Restricts trees based on the number of children for each internal node
 */
public class InternalNodeChildrenNumRestrictor  extends AbstractRestrictor {

	private static final long serialVersionUID = 3569823286706768487L;

	/**
	 * Constructor to provide the Restrictor's name
	 * @param name The name of the Restrictor
	 */
	public InternalNodeChildrenNumRestrictor(String name) {
		super(name);
	}
	
	/**
	 * Constructor to provide the Restricto's name and description
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 */
	public InternalNodeChildrenNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	/**
	 * Constructor to set all properties of the Restrictor
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 * @param min The initial minimum value to apply
	 * @param max The initial maximum value to apply
	 */
	public InternalNodeChildrenNumRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	/*
	 * (non-Javadoc)
	 * @see restrictors.AbstractRestrictor#restrict(model.DescriptionTree)
	 */
	@Override
	public boolean restrict(DescriptionTree t) {
		List<Tree> nodes = t.getNodes(); //get nodes
		
		//restrict based on each nodes number of children
		for(Tree n : nodes) {
			int numChildren = n.getNumChildren();
			if(numChildren < min || numChildren > max) {
				return false;
			}
		}
		
		return true;
	}	
}
