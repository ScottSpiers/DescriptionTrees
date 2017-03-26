package restrictors;

import model.DescriptionTree;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Restricts trees based on path length
 *
 */
public class PathLengthRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = 2921226218724363858L;

	/**
	 * Constructor to provide the Restrictor's name
	 * @param name The name of the Restrictor
	 */
	public PathLengthRestrictor(String name) {
		super(name);
	}
	
	/**
	 * Constructor to provide the Restricto's name and description
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 */
	public PathLengthRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	/**
	 * Constructor to set all properties of the Restrictor
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 * @param min The initial minimum value to apply
	 * @param max The initial maximum value to apply
	 */
	public PathLengthRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	/*
	 * (non-Javadoc)
	 * @see restrictors.AbstractRestrictor#restrict(model.DescriptionTree)
	 */
	@Override
	public boolean restrict(DescriptionTree t) {
		int depth = t.getDepth(); //get the tree depth
		
		//return true if the depth is between min and max (inclusive)
		if(depth >= min && depth <= max) {
			return true;
		}
		
		return false;
	}
}
