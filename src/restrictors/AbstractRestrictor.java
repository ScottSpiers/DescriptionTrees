package restrictors;

import model.DescriptionTree;
import model.Restrictor;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Abstract class to provide majority of Restrictor implementation.
 * Utilises Strategy Method for actual restriction (DescriptionTreeModel is the Context)
 */
public abstract class AbstractRestrictor implements Restrictor {
	
	private static final long serialVersionUID = -5908220996575041727L;
	protected String name;
	protected String desc;
	protected int min;
	protected int max;
	
	/**
	 * Constructor to provide the Restrictor's name
	 * @param name The name of the Restrictor
	 */
	public AbstractRestrictor(String name) {
		this.name = name;
		desc = name;
		min = 0;
		max = 1;
	}
	
	/**
	 * Constructor to provide the Restricto's name and description
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 */
	public AbstractRestrictor(String name, String desc) {
		this.name = name;
		this.desc = desc;
		min = 0;
		max = 1;
	}
	
	/**
	 * Constructor to set all properties of a Restrictor
	 * @param name The name of the Restrictor
	 * @param desc The Restrictor's description
	 * @param min The initial minimum value to apply
	 * @param max The initial maximum value to apply
	 */
	public AbstractRestrictor(String name, String desc, int min, int max) {
		this.name = name;
		this.desc = desc;
		this.min = min;
		this.max = max;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see model.Restrictor#getName()
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.Restrictor#getDesc()
	 */
	public String getDesc() {
		return desc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.Restrictor#setMin(int)
	 */
	public void setMin(int n) {
		min = n;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.Restrictor#setMax(int)
	 */
	public void setMax(int n) {
		max = n;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.Restrictor#getMin()
	 */
	public int getMin() {
		return min;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.Restrictor#getMax()
	 */
	public int getMax() {
		return max;
	}
	
	/**
	 * Variable Method: Implementation set by subclasses for Template Method applyRestriction() to call
	 * @param t The tree to test
	 * @return Whether or not the tree meets the conditions set by this Restrictor
	 */
	protected abstract boolean restrict(DescriptionTree t);
	
	/*
	 * (non-Javadoc)
	 * @see model.Restrictor#applyRestriction(model.DescriptionTree)
	 */
	public boolean applyRestriction(DescriptionTree tree) {
		return restrict(tree);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Restrictor) {
			if(name.equals(((Restrictor) o).getName())) {
				if(desc.equals(((Restrictor) o).getDesc())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ("Restriction: " + name + " Min: " + min + " Max: " + max);
	}
}
