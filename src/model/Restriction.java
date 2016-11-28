package model;

import java.util.List;

import model.scala.Tree;

public interface Restriction {

	public List<Tree> applyRestriction(List<Tree> trees);
}
