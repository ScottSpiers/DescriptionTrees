package restrictors;

import model.DescriptionTree;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Restricts trees based on the number of children of the root
 *
 */
public class RootChildrenNumRestrictor extends AbstractRestrictor {

	private static final long serialVersionUID = 7370083957299180247L;

	/**
	 * Constructor to provide the Restrictor's name
	 * @param name The name of the Restrictor
	 */
	public RootChildrenNumRestrictor(String name) {
		super(name);
	}
	
	/**
	 * Constructor to provide the Restricto's name and description
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 */
	public RootChildrenNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	/**
	 * Constructor to set all properties of the Restrictor
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 * @param min The initial minimum value to apply
	 * @param max The initial maximum value to apply
	 */
	public RootChildrenNumRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	/*
	 * (non-Javadoc)
	 * @see restrictors.AbstractRestrictor#restrict(model.DescriptionTree)
	 */
	@Override
	public boolean restrict(DescriptionTree t) {
		int numChildren = t.getNumChildren(); //get the number of children
		
		//return true if the number of children is between min and max (inclusive)
		if(numChildren >= min && numChildren <= max) {
			return true;
		}
		
		return false;
	}

}
