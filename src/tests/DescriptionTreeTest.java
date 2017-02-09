package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.AlphaTree;
import model.BetaTree;
import model.DescriptionTree;

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
	
	/*@Test
	public void testEqualNode() {
		t.getChild(0).addLeaf();
		assertTrue(t.equals(node));
	}*/
	
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
	public void testGetWidth() {
		DescriptionTree node2 = (DescriptionTree) node.clone();
		node2.addLeaf();
		node2.addLeaf();
		node2.addLeafToLeaf(0);
		node2.addLeafToNode(1);
		node2.addLeafToNode(1);
		node2.addLeafToNode(1);
		node2.addLeafToNode(1);
		node2.addLeafToLeaf(5);
		node2.addLeafToNode(2);
		System.out.println(node2);
		node2.printString();
		assertTrue(node2.getWidth(1) == 2 && node2.getWidth(2) == 7);
	}
	
	@Test
	public void testGetDepth() {
		DescriptionTree node3 = (DescriptionTree) node.clone();
		node3.addLeaf();
		node3.addLeafToLeaf(0);
		node3.addLeafToLeaf(1);
		node3.addLeafToLeaf(0);
		assertTrue(node3.getDepth() == 3);
	}
	
	@Test
	public void testMaxWidth() {
		DescriptionTree node4 = (DescriptionTree) node.clone();
		node4.addLeaf();
		node4.addLeaf();
		node4.addLeafToLeaf(0);
		node4.addLeafToNode(1);
		node4.addLeafToNode(1);
		node4.addLeafToNode(1);
		node4.addLeafToNode(1);
		node4.addLeafToLeaf(5);
		node4.addLeafToNode(2);
		System.out.println(node4);
		assertTrue(node4.getMaxWidth() == 7);
	}
	
	@Test
	public void testPrint() {
		DescriptionTree node5 = new BetaTree();
		node5.addLeaf(); //root
		node5.addLeaf(); //node 1
		node5.addLeaf(); //leaf 6
		node5.addLeaf(); // node 3
		node5.addLeaf(); // leaf 11
		node5.addLeaf(); //node 4
		
		node5.addLeafToLeaf(0);
		node5.addLeafToNode(1);
		node5.addLeafToNode(1);
		node5.addLeafToNode(1); //node 2
		
		node5.addLeafToLeaf(3);
		node5.addLeafToNode(2);
		node5.addLeafToNode(2);
		
		node5.addLeafToLeaf(7);
		node5.addLeafToNode(3);
		node5.addLeafToNode(3);
		node5.addLeafToNode(3);
		
		node5.addLeafToLeaf(12);
		node5.addLeafToNode(4);
		node5.addLeafToNode(4);
		System.out.println("Testing print");
		System.out.println(node5.printTree());
		System.out.println(node5);
		
		
	}
	
	/*@Test
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
	}*/
	
	@Test
	public void testEval() {
		System.out.println("Eval 1:\n");
		DescriptionTree bTree = new BetaTree(1, 0);
		bTree.addLeaf();
		bTree.addLeaf();
		bTree.addLeaf();
		bTree.addLeafToLeaf(1);
		bTree.addLeafToLeaf(1);
		bTree.addLeafToLeaf(0);
		System.out.println(bTree);
		bTree.setNodeValue(2, 0);
		bTree.setNodeValue(3, 1);
		bTree.setNodeValue(4, 2);
		bTree.setNodeValue(5, 3);
		System.out.println(bTree);
		assertTrue(bTree.equals(bTree));
	}
	
	@Test
	public void testEval2() {
		System.out.println("Eval 2\n");
		DescriptionTree bTree = new BetaTree(1, 0);
		bTree.addLeaf();
		bTree.addLeaf();
		bTree.addLeaf();
		bTree.addLeafToLeaf(0);
		bTree.addLeafToLeaf(1);
		bTree.addLeafToNode(1);
		bTree.addLeafToLeaf(1);
		System.out.println(bTree);
		bTree.setNodeValue(2, 2);
		System.out.println(bTree);
		assertTrue(bTree.equals(bTree));
	}

}
