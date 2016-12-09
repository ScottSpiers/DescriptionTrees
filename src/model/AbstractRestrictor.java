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
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public List<Tree> applyRestriction(List<Tree> trees) {
		List<Tree> newTreeList = new ArrayList<Tree>();
		
		for(Tree t : trees) {
			if (restrict(t)) {
				newTreeList.add(t);
			}
		}
		return newTreeList;
	}
	
}
