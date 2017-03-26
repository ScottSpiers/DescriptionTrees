package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.BetaTree;
import model.DescriptionTree;
import model.Restrictor;
import model.scala.Leaf;
import restrictors.InternalNodeChildrenNumRestrictor;
import restrictors.InternalNodeNumRestrictor;
import restrictors.InternalNodeValueRestrictor;
import restrictors.JumpNumRestrictor;
import restrictors.LeafNumRestrictor;
import restrictors.PathLengthRestrictor;
import restrictors.RootChildrenNumRestrictor;
import restrictors.RootValueRestrictor;

public class RestrictorTest {
	
	private Restrictor lNum;
	private Restrictor iChildNum;
	private Restrictor iNum;
	private Restrictor iValue;
	private Restrictor jNum;
	private Restrictor pLength;
	private Restrictor rChildNum;
	private Restrictor rValue;
	
	private DescriptionTree testTree;
	
	@Before
	public void setup() {
		lNum = new LeafNumRestrictor("lNum", "restrict leaves", 2, 4);
		iChildNum = new InternalNodeChildrenNumRestrictor("iChildNum", "restrict number of internal node children", 0 , 1);
		iNum = new InternalNodeNumRestrictor("iNum", "restrict num internal nodes", 1, 2);
		iValue = new InternalNodeValueRestrictor("iValue", "restrict internal node value", 2, 3);
		jNum = new JumpNumRestrictor("jNum", "restrict num jumps", 0, 0);
		pLength = new PathLengthRestrictor("pLength", "restrict path length", 2, 2);
		rChildNum = new RootChildrenNumRestrictor("rChildNum", "restrict number of children of the root", 0, 1);
		rValue = new RootValueRestrictor("rValue", "restrict root value", 1, 2);
		
		testTree = new BetaTree(new Leaf(0), 1, 0);
	}
	
	@Test
	public void testLeafNumRestrcitorPass() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeaf();
		assertTrue(lNum.applyRestriction(testTree));
	}
	
	@Test
	public void testLeafNumRestrictorFail() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeaf();
		assertFalse(lNum.applyRestriction(testTree));
	}
	
	@Test
	public void testInternalNodeChildNumRestrcitorPass() {
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);		
		assertTrue(iChildNum.applyRestriction(testTree));
	}
	
	@Test
	public void testInternalNodeChildNumRestrictorFail() {
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		testTree.addLeafToNode(1);
		assertFalse(iChildNum.applyRestriction(testTree));
	}
	
	@Test
	public void testInternalNodeNumRestrcitorPass() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		assertTrue(iNum.applyRestriction(testTree));
	}
	
	@Test
	public void testInternalNodeNumRestrictorFail() {
		testTree.addLeaf();
		testTree.addLeaf();
		assertFalse(iNum.applyRestriction(testTree));
	}
	
	@Test
	public void testInternalNodeValueRestrcitorPass() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		testTree.addLeafToNode(1);	
		testTree.addLeafToNode(1);
		DescriptionTree test = testTree.evaluateTree(testTree.getNodes().size() - 1).get(1);
		assertTrue(iValue.applyRestriction(test));
	}
	
	@Test
	public void testInternalNodeValueRestrictorFail() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		testTree.addLeafToNode(1);	
		DescriptionTree test = testTree.evaluateTree(testTree.getNodes().size() - 1).get(0);
		assertFalse(iValue.applyRestriction(test));
	}
	
	@Test
	public void testJumpNumRestrcitorPass() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		testTree.addLeafToNode(1);	
		testTree.addLeafToNode(1);
		DescriptionTree test = testTree.evaluateTree(testTree.getNodes().size() - 1).get(1);		
		assertTrue(jNum.applyRestriction(test));
	}
	
	@Test
	public void testJumpNumRestrictorFail() {
		DescriptionTree testJump = new BetaTree(new Leaf(0), 0, 1);
		testJump.addLeaf();
		testJump.addLeaf();
		testJump.addLeafToLeaf(0);
		testJump.addLeafToNode(1);	
		testJump.addLeafToNode(1);
		DescriptionTree test = testJump.evaluateTree(testJump.getNodes().size() - 1).get(1);
		assertFalse(jNum.applyRestriction(test));
	}
	
	@Test
	public void testPathLengthRestrcitorPass() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		testTree.addLeafToNode(1);	
		testTree.addLeafToNode(1);
		assertTrue(pLength.applyRestriction(testTree));
	}
	
	@Test
	public void testPathLengthRestrictorFail() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		testTree.addLeafToNode(1);	
		testTree.addLeafToNode(1);
		testTree.addLeafToLeaf(0);
		assertFalse(pLength.applyRestriction(testTree));
	}
	
	@Test
	public void testRootChildNumRestrcitorPass() {
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		assertTrue(rChildNum.applyRestriction(testTree));
	}
	
	@Test
	public void testRootChildNumRestrictorFail() {
		testTree.addLeaf();
		testTree.addLeaf();
		assertFalse(rChildNum.applyRestriction(testTree));
	}
	
	@Test
	public void testRootValueRestrcitorPass() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		testTree.addLeafToNode(1);	
		testTree.addLeafToNode(1);
		DescriptionTree test = testTree.evaluateTree(testTree.getNodes().size() - 1).get(0);
		assertTrue(rValue.applyRestriction(test));
	}
	
	@Test
	public void testRootValueRestrictorFail() {
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(0);
		testTree.addLeafToNode(1);	
		testTree.addLeafToNode(1);
		DescriptionTree test = testTree.evaluateTree(testTree.getNodes().size() - 1).get(1);
		assertFalse(rValue.applyRestriction(test));
	}

}
