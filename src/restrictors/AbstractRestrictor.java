package restrictors;

import model.DescriptionTree;
import model.Restrictor;

public abstract class AbstractRestrictor implements Restrictor {

	
	private static final long serialVersionUID = -5908220996575041727L;
	protected String name;
	protected String desc;
	protected int min;
	protected int max;
	
	public AbstractRestrictor(String name) {
		this.name = name;
		desc = name;
		min = 0;
		max = 1;
	}
	
	public AbstractRestrictor(String name, String desc) {
		this.name = name;
		this.desc = desc;
		min = 0;
		max = 1;
	}
	
	public AbstractRestrictor(String name, String desc, int min, int max) {
		this.name = name;
		this.desc = desc;
		this.min = min;
		this.max = max;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setMin(int n) {
		min = n;
	}
	
	public void setMax(int n) {
		max = n;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public boolean applyRestriction(DescriptionTree tree) {
		return restrict(tree);
	}
	
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
	
	@Override
	public String toString() {
		return ("Restriction: " + name + " Min: " + min + " Max: " + max);
	}
}
