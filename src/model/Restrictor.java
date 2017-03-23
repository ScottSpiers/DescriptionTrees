package model;

import java.io.Serializable;

public interface Restrictor extends Serializable {

	public String getName();
	public String getDesc();
	public void setMin(int n);
	public void setMax(int n);
	public int getMin();
	public int getMax();
	public boolean restrict(DescriptionTree t);
	public boolean applyRestriction(DescriptionTree trees);
}
