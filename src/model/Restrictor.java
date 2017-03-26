package model;

import java.io.Serializable;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Interface to provide the specification for a Restrictor
 */
public interface Restrictor extends Serializable {

	/**
	 * @return this Restrictor's name
	 */
	public String getName();
	
	/**
	 * @return this Restrictor's description
	 */
	public String getDesc();
	
	/**
	 * Sets the Restrictio'r current min value
	 * @param n The value to be used
	 */
	public void setMin(int n);
	
	/**
	 * Sets the Restrictior's current max value
	 * @param n The value to be used
	 */
	public void setMax(int n);
	
	/**
	 * 
	 * @return this Restrictor's current min value
	 */
	public int getMin();
	
	/**
	 * 
	 * @return this Restrictor's current max value
	 */
	public int getMax();
	
	
	/**
	 * Applies the restriction to the provided tree
	 * Template Method: This will call a variable method to be implemented by subclasses
	 * @param tree The tree to test
	 * @return Whether or not the tree matches the conditions set by this Restrictor
	 */
	public boolean applyRestriction(DescriptionTree tree);
}
