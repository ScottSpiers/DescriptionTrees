package model;

import java.util.List;

import model.scala.Tree;

public interface Restrictor {

	public String getName();
	public String getDesc();
	public void setMin(int n);
	public void setMax(int n);
	public int getMin();
	public int getMax();
	public boolean restrict(DescriptionTree t);
	public List<DescriptionTree> applyRestriction(List<DescriptionTree> trees);
}
