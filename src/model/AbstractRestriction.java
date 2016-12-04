package model;

public abstract class AbstractRestriction implements Restriction {

	protected String name;
	protected String desc;
	protected int min;
	protected int max;
	
	public AbstractRestriction(String name) {
		this.name = name;
		desc = name;
		min = 0;
		max = 1;
	}
	
	public AbstractRestriction(String name, String desc) {
		this.name = name;
		this.desc = desc;
		min = 0;
		max = 1;
	}
	
	public AbstractRestriction(String name, String desc, int min, int max) {
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
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
}
