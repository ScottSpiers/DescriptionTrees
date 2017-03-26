package tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import model.BetaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;

public class DescriptionTreeModelTest {

	
	@Test
	public void testGenTrees() {
		DescriptionTreeModel m = new DescriptionTreeModel();
		List<DescriptionTree> trees = m.genTrees(new BetaTree(), 4);
		assertTrue(trees.size() == 5);
	}
	
	@Test
	public void testGenTrees2() {
		DescriptionTreeModel m = new DescriptionTreeModel();
		List<DescriptionTree> trees = m.genTrees(new BetaTree(), 9);
		assertTrue(trees.size() == 1430);
	}
	
	@Test
	public void testGenTrees3() {
		DescriptionTreeModel m = new DescriptionTreeModel();
		List<DescriptionTree> trees = m.genTrees(new BetaTree(), 0);
		assertTrue(trees.size() == 0);
	}
	
	@Test
	public void testGenTrees4() {
		DescriptionTreeModel m = new DescriptionTreeModel();
		List<DescriptionTree> trees = m.genTrees(new BetaTree(), 1);
		assertTrue(trees.size() == 1);
	}
	
	@Test
	public void testGenTrees5() {
		DescriptionTreeModel m = new DescriptionTreeModel();
		List<DescriptionTree> trees = m.genTrees(new BetaTree(), 10);
		assertTrue(trees.size() == 4862);
	}
	
	//This one takes a while
	@Test
	public void testGenTrees6() {
		DescriptionTreeModel m = new DescriptionTreeModel();
		List<DescriptionTree> trees = m.genTrees(new BetaTree(), 11);
		assertTrue(trees.size() == 16796);
	}
}
