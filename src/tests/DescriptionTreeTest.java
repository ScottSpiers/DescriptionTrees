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
		t.addLeaf();
		t.addLeaf();
		t.addLeafToLeaf(0);
		t.addLeafToLeaf(1);
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
		node.addLeaf();
		Tree nodeToAdd = new Leaf(0);
		nodeToAdd = nodeToAdd.addLeaf();
		nodeToAdd = nodeToAdd.addLeaf();
		node.addNode(new AlphaTree(nodeToAdd));
		DescriptionTree testTree = new AlphaTree();
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeaf();
		testTree.addLeafToLeaf(1);
		testTree.addLeafToNode(1);
		assertTrue(node.equals(testTree));
	}
	
	@Test
	public void testSetAllLeafValuesBeta() {
		DescriptionTree Bnode = new BetaTree(1, 0);
		Bnode.addLeaf();
		Bnode.addLeaf();
		Bnode.addLeafToLeaf(0);
		Bnode.addLeafToNode(1);
		Bnode.addLeafToNode(1);
		Bnode.evaluateTree(Bnode.getNodes().size()-1);
		List<Tree> leaves = Bnode.getLeaves();
		boolean isValued = true;
		for(Tree l : leaves) {
			if(l.getValue() != 1) {
				isValued = false;
				break;
			}
		}
		assertTrue(isValued);
	}
	
	@Test
	public void testSetAllLeafValuesAlpha() {
		DescriptionTree Anode = new AlphaTree(0, 1);
		Anode.addLeaf();
		Anode.addLeaf();
		Anode.addLeafToLeaf(0);
		Anode.addLeafToNode(1);
		Anode.addLeafToNode(1);
		Anode.evaluateTree(Anode.getNodes().size()-1);
		List<Tree> leaves = Anode.getLeaves();
		boolean isValued = true;
		for(Tree l : leaves) {
			if(l.getValue() != 1) {
				isValued = false;
				break;
			}
		}
		assertTrue(isValued);
	}
	
	@Test
	public void testEvaluateAlpha() {
		DescriptionTree aNode = new AlphaTree(0,1);
		aNode.addLeaf();
		aNode.addLeaf();
		aNode.addLeaf();
		List<DescriptionTree> valdTrees = aNode.evaluateTree(aNode.getNodes().size()-1);
		assertTrue(valdTrees.size() == 3);
	}
	
	@Test
	public void testEvaluateBeta() {
		DescriptionTree bNode = new BetaTree(0,1);
		bNode.addLeaf();
		bNode.addLeaf();
		bNode.addLeaf();
		List<DescriptionTree> valdTrees = bNode.evaluateTree(bNode.getNodes().size()-1);
		assertTrue(valdTrees.size() == 1);
	}
	
	@Test
	public void testGetNumLeaves() {
		node.addLeaf();
		node.addLeaf();
		node.addLeaf();
		node.addLeafToLeaf(0);
		node.addLeafToLeaf(0);
		assertTrue(node.getNumLeaves() == 3);
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
	public void testNumNodes() {
		DescriptionTree node6 = new BetaTree();
		node6.addLeaf(); //root
		node6.addLeaf(); //node 1
		node6.addLeaf(); //leaf 6
		node6.addLeaf(); // node 3
		node6.addLeaf(); // leaf 11
		node6.addLeaf(); //node 4
		
		node6.addLeafToLeaf(0);
		node6.addLeafToNode(1);
		node6.addLeafToNode(1);
		node6.addLeafToNode(1); //node 2
		
		node6.addLeafToLeaf(3);
		node6.addLeafToNode(2);
		node6.addLeafToNode(2);
		
		node6.addLeafToLeaf(7);
		node6.addLeafToNode(3);
		node6.addLeafToNode(3);
		node6.addLeafToNode(3);
		
		node6.addLeafToLeaf(12);
		node6.addLeafToNode(4);
		node6.addLeafToNode(4);
		
		assertTrue((node6.getNumVertices() - node6.getNumLeaves()) == node6.getNodes().size());
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
	public void testEqualsNull() {
		assertFalse(t.equals(null));
	}
	
	@Test
	public void testEqualsEmpty() {
		DescriptionTree testEmpty = new AlphaTree();
		assertTrue(empty.equals(testEmpty));
	}
	
	@Test
	public void testEqualsLeaf() {
		DescriptionTree testLeaf = new AlphaTree();
		testLeaf.addLeaf();
		assertTrue(leaf.equals(testLeaf));
	}
	
	@Test
	public void testEqualsNode() {
		node.addLeaf();
		DescriptionTree testNode = new AlphaTree();
		testNode.addLeaf();
		testNode.addLeaf();
		assertTrue(node.equals(testNode));
	}
	
	@Test
	public void testNotEqualLeaf() {
		DescriptionTree testLeaf = new AlphaTree(new Leaf(1), 0, 1);
		assertFalse(leaf.equals(testLeaf));
	}
	
	@Test
	public void testNotEqualNodeRoot() {
		node.addLeaf();
		DescriptionTree testNode = new AlphaTree(new Leaf(1));
		testNode.addLeaf();
		assertFalse(node.equals(testNode));
	}
	
	@Test
	public void testNotEqualNodeChild() {
		node.addLeaf();
		DescriptionTree testNode = new AlphaTree(new Leaf(0), 0, 1);
		testNode.addLeaf();
		testNode.evaluateTree(0);
		assertFalse(node.equals(testNode));		
	}
	
	@Test
	public void testEqualsReflexive() {
		assertTrue(leaf.equals(leaf));
	}
	
	@Test
	public void testEqualsSymmetric() {
		DescriptionTree testLeaf = new AlphaTree();
		testLeaf.addLeaf();
		assertTrue(leaf.equals(testLeaf) && testLeaf.equals(leaf));
	}
	
	@Test
	public void testEqualsTransitive() {
		DescriptionTree testLeaf = new AlphaTree();
		DescriptionTree leaf2 = new AlphaTree(new Leaf(0));
		testLeaf.addLeaf();
		assertTrue(leaf.equals(testLeaf) && testLeaf.equals(leaf2) && leaf2.equals(leaf));
	}
}
