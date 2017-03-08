package model;

import java.io.Serializable;
import java.util.List;

public interface Restrictor extends Serializable {

	public String getName();
	public String getDesc();
	public void setMin(int n);
	public void setMax(int n);
	public int getMin();
	public int getMax();
	public boolean restrict(DescriptionTree t);
	public List<DescriptionTree> applyRestriction(List<DescriptionTree> trees);
}
