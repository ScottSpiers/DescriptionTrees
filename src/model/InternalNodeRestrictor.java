package model;

import model.scala.Tree;

public class InternalNodeRestrictor extends AbstractRestrictor {

	public InternalNodeRestrictor(String name) {
		super(name);
	}
	
	public InternalNodeRestrictor(String name, String desc) {
		super(name, desc);
	}
	
	public InternalNodeRestrictor(String name, String desc, int  min, int max) {
		super(name, desc, min, max);
	}

	@Override
	public boolean restrict(Tree t) {
		// TODO Auto-generated method stub
		return false;
	}

}
