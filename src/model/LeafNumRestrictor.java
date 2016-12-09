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
	public boolean restrict(Tree t) {
		if(t.getNumLeaves(t) >= min && t.getNumLeaves(t) <= max) {
			return true;
		}
		return false;
	}


}
