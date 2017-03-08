package model;

import java.util.ArrayList;
import java.util.List;

import model.scala.Tree;

public abstract class AbstractRestrictor implements Restrictor {

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
	
	public List<DescriptionTree> applyRestriction(List<DescriptionTree> trees) {
		List<DescriptionTree> newTreeList = new ArrayList<DescriptionTree>();
		newTreeList.addAll(trees);
		for(DescriptionTree t : trees) {
			if (!restrict(t)) {
				newTreeList.remove(t);
			}
		}
		return newTreeList;
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
		return ("Restriction: " + name + " Min: " + min + "Max: " + max);
	}
}
