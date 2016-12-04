package model;

import java.util.List;

import model.scala.Tree;

public class LeafNumRestrictor extends AbstractRestriction {

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
	public List<Tree> applyRestriction(List<Tree> trees) {
		// TODO Auto-generated method stub
		return null;
	}


}
