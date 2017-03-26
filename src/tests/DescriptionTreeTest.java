package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.AlphaTree;
import model.BetaTree;
import model.DescriptionTree;
import model.scala.Leaf;
import model.scala.Tree;

public class DescriptionTreeTest {

	private static DescriptionTree t;
	private static DescriptionTree empty;
	private static DescriptionTree leaf;
	private static DescriptionTree node;
	
	@Before
	public void setup() {
		t  = new AlphaTree(); //chosen tree type doesn't matter for equality
		empty = new AlphaTree();
		leaf = new AlphaTree();
		leaf.addLeaf();
		node = new AlphaTree();
		node.addLeaf();
	}	
	
	@Test
	public void testSetNodeValue() {
		node.addLeaf();
		node.setNodeValue(5, 0);
		assertTrue(node.getValue() == 5);
	}
	
	@Test
	public void testGetLeaves() {
		node.addLeaf();
		node.addLeaf();
		node.addLeaf();
		node.addLeafToLeaf(0);
		List<Tree> leaves = new ArrayList<Tree>();
		leaves.add(new Leaf(0));
		leaves.add(new Leaf(0));
		leaves.add(new Leaf(0));
		assertTrue(leaves.equals(node.getLeaves()));
	}
	
	@Test
	public void testGetNodes() {
		Tree root = new Leaf(0);
		root = root.addLeafToLeaf(0);
		root = root.addLeafToLeaf(0);
		root = root.addLeafToLeaf(0);
		root = root.addLeafToLeaf(0);
		List<Tree> nodes = new ArrayList<Tree>();
		nodes.add(root);
		Tree node1 = new Leaf(0);
		node1 = node1.addLeafToLeaf(0);
		node1 = node1.addLeafToLeaf(0);
		node1 = node1.addLeafToLeaf(0);
		nodes.add(node1);
		Tree node2 = new Leaf(0);
		node2 = node2.addLeafToLeaf(0);
		node2 = node2.addLeafToLeaf(0);
		nodes.add(node2);
		Tree node3 = new Leaf(0);
		node3 = node3.addLeafToLeaf(0);
		nodes.add(node3);
		assertTrue(nodes.equals(root.getNodes()));
	}
	
	@Test
	public void testGetChild() {
		node.addLeaf();
		node.addLeaf();
		node.addLeaf();
		node.addLeafToLeaf(0);
		node.addLeafToLeaf(1);
		node.addLeafToLeaf(2);
		Tree node1 = new Leaf(0);
		node1 = node1.addLeaf();
		assertTrue(node.getChild(1).equals(node1));
	}
	
	@Test
	public void testGetAllChildren() {
		node.addLeaf();
		node.addLeaf();
		node.addLeaf();
		node.addLeafToLeaf(0);
		node.addLeafToLeaf(1);
		node.addLeafToLeaf(2);
		Tree node1 = new Leaf(0);
		node1 = node1.addLeaf();
		Tree node2 = node1;
		Tree node3 = node1;
		List<Tree> children = new ArrayList<Tree>();
		children.add(node1);
		children.add(node2);
		children.add(node3);
		assertTrue(children.equals(node.getAllChildren()));		
	}
	
	@Test
	public void testGetNumVertices() {
		node.addLeaf();
		node.addLeaf();
		node.addLeaf();
		node.addLeafToLeaf(0);
		node.addLeafToLeaf(1);
		node.addLeafToLeaf(2);
		assertTrue(node.getNumVertices() == 7);
	}
	
	@Test
	public void getNumChildren() {
		node.addLeaf();
		node.addLeaf();
		node.addLeaf();
		node.addLeafToLeaf(0);
		node.addLeafToLeaf(1);
		node.addLeafToLeaf(2);
		assertTrue(node.getNumChildren() == 3);
	}
	
	@Test
	public void testAddRoot() {
		node.addLeaf();
		node.addLeaf();
		node.addLeaf();
		node.addRoot();
		DescriptionTree t = new BetaTree(new Leaf(0));
		t.addLeaf();
		t.addLeafToLeaf(0);
		t.addLeafToNode(1);
		t.addLeafToNode(1);
		assertTrue(node.equals(t));
	}
	
	@Test
	public void testAddLeaf() {
		node.addLeaf();
		node.addLeafToLeaf(0);
		node.addLeaf();
		assertTrue(node.getNumChildren() == 2);
	}
	
	@Test
	public void testAddLeafAsFirstChild() {
		node.addLeaf();
		node.addLeaf();
		node.addLeafToLeaf(0);
		node.addLeafToLeaf(1);
		node.addLeafAsFirstChild();
		DescriptionTree t = new BetaTree(new Leaf(0));
		t.addLeaf();
		t.addLeaf();
		t.addLeaf();
		t.addLeafToLeaf(1);
		t.addLeafToLeaf(2);
		assertTrue(node.equals(t));
	}
	
	@Test
	public void testAddLeafAsChildAt() {
		node.addLeaf();
		node.addLeaf();
		node.addLeafToLeaf(0);
		node.addLeafToLeaf(1);
		node.addLeafAsChildAt(1);
		DescriptionTree t = new BetaTree(new Leaf(0));
		t.addLeaf();
		t.addLeaf();
		t.addLeaf();
		t.addLeafToLeaf(0);
		t.addLeafToLeaf(2);
		assertTrue(node.equals(t));		
	}
	
	@Test
	public void testAddNode() {
		
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
		assertTrue(node2.getWidth(1) == 2 && node2.getWidth(2) == 7);
	}
	
	@Test
	public void testGetDepth() {
		DescriptionTree node3 = (DescriptionTree) node.clone();
		node3.addLeaf();
		node3.addLeafToLeaf(0);
		node3.addLeafToLeaf(1);
		node3.addLeafToLeaf(0);
		System.out.println(node3.printTree());
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
		System.out.println(node4.printTree());
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
		
		node5.setNodeValue(15, 0);
		node5.setNodeValue(11, 1);
		node5.setNodeValue(23, 2);
		node5.setNodeValue(50, 3);
		node5.setNodeValue(19, 4);
		System.out.println("Testing print");
		System.out.println(node5.printTree());
		System.out.println(node5);		
	}
	
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
		
	}

}
