package model;

import java.util.List;

import model.scala.Tree;

public class LeafNumRestrictor extends AbstractRestrictor {

	public LeafNumRestrictor(String name) {
		super(name);
	}
	
	public LeafNumRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public LeafNumRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(DescriptionTree t) {
		if(t.getNumLeaves() >= min && t.getNumLeaves() <= max) {
			return true;
		}
		return false;
	}


}
