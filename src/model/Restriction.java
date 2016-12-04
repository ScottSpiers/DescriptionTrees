package model;

import java.util.List;

import model.scala.Tree;

public interface Restriction {

	public String getName();
	public String getDesc();
	public int getMin();
	public int getMax();
	public List<Tree> applyRestriction(List<Tree> trees);
}
