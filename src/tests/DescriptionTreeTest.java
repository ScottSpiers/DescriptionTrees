package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.AlphaTree;
import model.DescriptionTree;
import model.scala.Tree;

public class DescriptionTreeTest {

	private static DescriptionTree t;
	private static DescriptionTree empty;
	private static DescriptionTree leaf;
	private static DescriptionTree node;
	
	@BeforeClass
	public static void setup() {
		t  = new AlphaTree(); //chosen tree type doesn't matter for equality
		empty = new AlphaTree();
		leaf = new AlphaTree();
		leaf.addLeaf();
		node = new AlphaTree();
		node.addLeaf();
		node.getChild(0).addLeaf();
	}	
	
	@Test
	public void testEqualsNull() {
		assertFalse(t.equals(null));
	}
	
	@Test
	public void testEqualEmpty() {
		assertTrue(t.equals(empty));
	}
	
	@Test
	public void testEqualLeaf() {
		t.addLeaf();
		assertTrue(t.equals(leaf));
	}
	
	@Test
	public void testEqualNode() {
		t.getChild(0).addLeaf();
		assertTrue(t.equals(node));
	}
	
	@Test
	public void testEqualTree() {
		
	}
	
	@Test
	public void testNotEqualLeaf() {
		
	}
	
	@Test
	public void testNotEqualNode() {
		
	}
	
	@Test
	public void testNotEqualTree() {
		DescriptionTree tree1 = new AlphaTree();
		tree1.addLeaf();
		tree1.addLeaf();
		tree1.addLeaf();
		Tree c1 = tree1.getChild(0);
		c1 = c1.addLeaf();
		System.out.println(tree1);
		System.out.println(tree1.getNodes());
		DescriptionTree tree2 = new AlphaTree();
		tree2.addLeaf();
		tree2.addLeaf();
		tree2.addLeaf();
		Tree c2 = tree2.getChild(1);
		c2 = c2.addLeaf();
		//tree2.getChild(1).addLeaf();
		System.out.println(tree2);
		System.out.println(tree2.getNodes());
		assertFalse(tree1.equals(tree2));
	}

}
