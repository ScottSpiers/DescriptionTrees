package model;

import java.util.List;

import model.scala.Tree;

public interface Restrictor {

	public String getName();
	public String getDesc();
	public int getMin();
	public int getMax();
	public boolean restrict(Tree t);
	public List<Tree> applyRestriction(List<Tree> trees);
}
