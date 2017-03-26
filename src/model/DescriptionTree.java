package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.scala.Empty;
import model.scala.Tree;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Abstract Class providing abstract methods for valuating trees
 * Majority of functionality invokes functions on a Tree object
 */
public abstract class DescriptionTree implements Cloneable {
	
	protected Tree descriptionTree; //The tree representing this description tree
	protected int alpha;
	protected int beta;
	
	/**
	 * Default Constructor creating an empty tree
	 */
	public DescriptionTree() {
		descriptionTree = new Empty();
	}
	
	/**
	 * Constructor building the description tree from an existing tree
	 * @param t The tree to build the description tree from
	 */
	public DescriptionTree(Tree t) {
		descriptionTree = t;
	}
	
	/**
	 * Constructs a description tree with the valuation parameters set
	 * @param a The first parameter
	 * @param b The second parameter
	 */
	public DescriptionTree(int a, int b) {
		descriptionTree = new Empty();
		alpha = a;
		beta = b;
	}
	
	/**
	 * Constructs a description tree with the valuation parameters set 
	 * @param t The tree to build the description tree from
	 * @param a The first parameter
	 * @param b The second parameter
	 */
	public DescriptionTree(Tree t, int a, int b) {
		descriptionTree = t;
		alpha = a;
		beta = b;
	}
	
	/**
	 * 
	 * @return the root's value
	 */
	public int getValue() {
		return descriptionTree.getValue();
	}
	
	/**
	 * 
	 * @return the list of Leaves in the tree
	 */
	public List<Tree> getLeaves() {
		return descriptionTree.getLeaves();
	}
	
	/**
	 * 
	 * @return the list of Nodes within the tree
	 */
	public List<Tree> getNodes() {
		return descriptionTree.getNodes();
	}
	
	/**
	 * Gets the ith child of the root
	 * @param i The position of the child to get
	 * @return The ith child of the root
	 */
	public Tree getChild(int i) {
		return descriptionTree.getChild(i);
	}
	
	/**
	 * 
	 * @return a list of all children of the root
	 */
	public List<Tree> getAllChildren() {
		return descriptionTree.getAllChildren();
	}
	
	/**
	 * 
	 * @return the number of vertices in the tree
	 */
	public int getNumVertices() {
		return descriptionTree.getNumVertices(0);
	}
	
	/**
	 * 
	 * @return the number of children of the root
	 */
	public int getNumChildren() {
		return descriptionTree.getNumChildren();
	}
	
	/**
	 * Makes this tree the child of a new root node
	 */
	public void addRoot() {
		descriptionTree = descriptionTree.addRoot();
	}
	
	/**
	 * Adds a leaf to the root node
	 */
	public void addLeaf() {
		descriptionTree = descriptionTree.addLeaf();
	}
	
	/**
	 * Adds a leaf to a leaf
	 * @param i The index of the leaf to add to
	 */
	public void addLeafToLeaf(int i) {
		descriptionTree = descriptionTree.addLeafToLeaf(i);
	}
	
	/**
	 * Adds a leaf to a node
	 * @param i The index of the node to add to
	 */
	public void addLeafToNode(int i) {
		descriptionTree = descriptionTree.addLeafToNode(i);
	}
	
	/**
	 * Adds a leaf as the first child of the root
	 */
	public void addLeafAsFirstChild() {
		descriptionTree = descriptionTree.addLeafAsFirstChild();
	}
	
	/**
	 * Adds a leaf as the ith child of the root
	 * @param i the position for the leaf
	 */
	public void addLeafAsChildAt(int i) {
		descriptionTree = descriptionTree.addLeafAsChildAt(i);
	}
	
	/**
	 * Adds a node
	 * @param t The Node to add
	 */
	public void addNode(DescriptionTree t) {
		descriptionTree = descriptionTree.addNode(t.descriptionTree);
	}	
	
	/**
	 * Valuates a tree returning all unique trees created in the process
	 * ensuring all possible values are assigned
	 * @param n The node number to first be valued
	 * @return The list of all unique trees created
	 */
	public abstract List<DescriptionTree> evaluateTree(int n);
	
	/**
	 * Sets all leaves to the appropriate value
	 */
	protected abstract void setAllLeafValues();
	
	/**
	 * Sets the specified node's value
	 * @param n The value to give the node
	 * @param i The index of the node being valued
	 */
	public void setNodeValue(int n, int i) {
		descriptionTree = descriptionTree.setNodeValue(n, i);
	}
	
	/**
	 * 
	 * @return the number of leaves
	 */
	public int getNumLeaves() {
		return descriptionTree.getNumLeaves(descriptionTree, 0);
	}
	
	/**
	 * 
	 * @param i The level to get the width for
	 * @return the width of level i
	 */
	public int getWidth(int i) {
		return descriptionTree.getWidth(i);
	}
	
	/**
	 * 
	 * @return the depth of the tree
	 */
	public int getDepth() {
		return descriptionTree.getDepth();
	}
	
	/**
	 * Gets the maximum width of the tree
	 * @return The maximum width of the tree
	 */
	public int getMaxWidth() {
		int max = getWidth(0); //get the width of level 0 (This is 1)
		int width = 0; 
		for(int i = 1; i <= getDepth(); i++) { //for every level
			width = getWidth(i); // get the width of the current level
			if(width > max) { //if this is greater than max
				max = width; //set max to this width
			}
		}
		return max;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {		
		try {
			return super.clone();		
		}
		catch (CloneNotSupportedException ex) {
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object t) {
		if(t instanceof DescriptionTree) {
			if(descriptionTree.getNumChildren() == ((DescriptionTree) t).getNumChildren()) {
				if(descriptionTree.getValue() == ((DescriptionTree) t).getValue()) {
					return (descriptionTree.getAllChildren().equals(((DescriptionTree) t).getAllChildren()));						
				}
				else {
					return false; 
				}
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Prints a textual representation of the tree.
	 * This is done by having node values stored in lists according to level
	 * which are then manipulated to get node values into the correct position
	 * These lists are then converted to strings and connections are drawn
	 * @return the string representation of the tree
	 */
	public String printTree() {
		String strOut = "";
		int fullDepth = getDepth() + 1;
		int[] depths = new int[getNumVertices()];
		int width = getMaxWidth();
		if(width % 2 == 1) {
			width += 1;
		}
		//Lists for node values on each level of the tree
		List<ArrayList<Integer>> valueLists = new ArrayList<ArrayList<Integer>>();
		
		//initialise the lists
		for(int i = 0; i < fullDepth; i++) {
			valueLists.add(new ArrayList<Integer>());
			for(int j = 0; j < width; j++) {
				valueLists.get(i).add(-1);				
			}
		}
		Tree[] verts = new Tree[getNumVertices()]; //array of all tree vertices
		
		int numStrings = (getDepth() * 2) + 1; //the number of strings required
		StringBuilder[] strings = new StringBuilder[numStrings]; //array of string builders used to build the tree
		
		//initialise the strings
		for(int i = 0; i < strings.length; i ++) {
			strings[i] = new StringBuilder();			
			strings[i].setLength(width * 2);
			if(i % 2 == 1) {
				for(int j = 0; j < width; j++) {
					strings[i].append(" ");					
				}
			}
			for(int j = 0; j < width * 2; j++) {
				strings[i].setCharAt(j, ' ');
			}
		}
		
		int depthIndex = 1;
		depths[0] = 0;
		int i = 0;
		int curNode = 0;
		
		//Breadth First Traversal of the tree
		Queue<Tree> q = new LinkedList<Tree>();
		q.add(descriptionTree);
		
		
		while(!q.isEmpty()) {
			Tree t = q.remove();
			
			valueLists.get(depths[curNode]).add(i, t.getValue()); //put the value in the appropriate list
			verts[curNode] = t; //set the vertice
			i++;
			
			//set all child depths
			for(Tree child : t.getAllChildren()) {
				depths[depthIndex] = depths[curNode] + 1;
				depthIndex++;
				
				q.add(child); //add the child to the queue
			}
			
			//if there are vertices left
			if(curNode < getNumVertices() - 1) {				
				if(depths[curNode + 1] == depths[curNode]) { //if we are on the same level
					valueLists.get(depths[curNode]).add(i, -1); //add a space in the lists
					i++;
				}
				else {
					i = 0;
				}
			}
			
			curNode++;			
		}
		
		int nodeCount = ((valueLists.get(valueLists.size() - 1).size() - width + 1) / 2); //get the node count
		int count = 0;
		int vertice = 1;
		int index = 0;
		int depth = valueLists.size() - 2;
		//get the vertice we start with (first node on second last level)
		for(int j = 0; j <= valueLists.size() - 3; j++) {
			int size = (valueLists.get(j).size() - getMaxWidth());
			if(size % 2 == 1) {
				size++;
			}
			vertice += size / 2;
		}
		index = vertice - 1;
		
		//while we have nodes left
		while(nodeCount < getNumVertices()) {
			int elementCount = 1;
			int parentIndex = 0;
			int shift = 0;
			int curIndex = 0; //track the index on the current level
			int numElements = valueLists.get(depth).size();
			//while we still have elements
			while(elementCount <= ((numElements - width + 1) / 2)) { 
				Tree curTree = verts[index]; //get the current vertice
				
				if(elementCount == 1) { //reset parent if needed
					parentIndex = 0;
				}
				
				int[] indices = new int[curTree.getNumChildren()];
				int endIndex;
				count = 0;
				
				//while we haven't found all children
				while(count < curTree.getNumChildren()) {					
					if(valueLists.get(depth + 1).get(curIndex) != -1) {
						indices[count] = curIndex; //store the child's index
						count++;
						curIndex++;
					}
					else {
						curIndex++;
						continue;
					}
				}
				
				endIndex = curIndex;
				//if we have no children, add a space after the current node value and continue
				if(curTree.getNumChildren() == 0) {
					valueLists.get(depth).add(parentIndex + 1, -1);
					parentIndex += 3;
					elementCount++;
					vertice++;
					nodeCount++;
					index = vertice - 1;
					continue;
				}
				//solve spacing issue when nodes have exactly two children
				else if(curTree.getNumChildren() == 2) {
					//get the optimal location for parent
					int locForParent = (indices[0] + endIndex) / 2;
					//if this isn't available
					if(locForParent < parentIndex) {
						//move the children along so the parent is in the 'correct' position
						for(int j = 0; j < parentIndex - locForParent; j++) {
							valueLists.get(depth + 1).add(indices[0], -1);						
						}
						endIndex += parentIndex - locForParent; //update the endIndex
						
						//update the child indexes
						for(int j = 0; j < curTree.getNumChildren(); j++) {
							indices[j] += parentIndex - locForParent;
						}	
						
					}
					else { //otherwise
						//move the parent along to the correct position
						for(int j = 0; j < locForParent - parentIndex; j++) {
							valueLists.get(depth).add(parentIndex, -1);
						}
						parentIndex += (locForParent - parentIndex); //update the parentIndex
						shift += (locForParent - parentIndex);	//ensure the vertice can be calculated properly by accounting for the increase in list width						
					}
					
					valueLists.get(depth).add(parentIndex + 1, -1); //add in another space
					parentIndex++; //update the parentIndex again
				}
				else { //for any other number of children
					int locForParent = (indices[0] + endIndex) / 2;
					if(locForParent < parentIndex) {
						for(int j = 0; j < parentIndex - locForParent; j++) {
							valueLists.get(depth + 1).add(indices[0], -1);						
						}
						endIndex += parentIndex - locForParent;
						
						for(int j = 0; j < curTree.getNumChildren(); j++) {
							indices[j] += parentIndex - locForParent;
						}	
						
					}
					else {
						
						for(int j = 0; j < locForParent - parentIndex; j++) {
							valueLists.get(depth).add(parentIndex, -1);
						}
						parentIndex += (locForParent - parentIndex);
						
						int loopCount = (curTree.getNumChildren() / 2);
						
						for(int j = 0; j < loopCount; j++) {
							valueLists.get(depth).add(parentIndex + 1, -1);
							parentIndex++;
							shift++;
						}
						
						shift += (locForParent - parentIndex);					
						
					}	
				}
				
				parentIndex +=2;
				curIndex = endIndex + 1;
				elementCount++;
				vertice++;
				nodeCount++;
				index = vertice - 1;
			}
			
			depth--;
			if(depth == -1) {
				break;
			}
			vertice--;
			//calculate the first vertice on the previous level
			vertice -= (((valueLists.get(depth).size() - (width + shift) + 1) / 2) - 1) + ((((numElements - width) + 1)/ 2));
			index = vertice - 1;
		}
		
		convertAndConnect(valueLists, verts, strings);
		
		for(StringBuilder s : strings) {			
			strOut += s + "\n";
		}
		
		return strOut;
	}
	
	/**
	 * 
	 * @param valueLists the integer lists to convert
	 * @param verts The array of vertices
	 * @param strings the strings to build
	 */
	private void convertAndConnect(List<ArrayList<Integer>> valueLists, Tree[] verts, StringBuilder[] strings) {
		int depth = 0;
		int valueDepth = 0;
		int childIndex = 0;
		int parentIndex = 0;
		int shift = 0;
		
		//for all lists
		for(ArrayList<Integer> l : valueLists) {
			for(int k = 0; k < l.size(); k++) { //for all integers in the list
				if(l.get(k) == -1) { //if current integer is -1
					//add a space
					if(k + shift < strings[depth].length()) {
						strings[depth].insert(k + shift, " ");						
					}
					else {
						strings[depth].append(" ");
					}
				}
				else { //otherwise
					strings[depth].insert(k, l.get(k)); //add the value to the string
					
					//if the value is > 10
					if(Integer.toString(l.get(k)).length() > 1) {
						k += Integer.toString(l.get(k)).length() - 1;
					}
					
					Tree t = verts[parentIndex]; //get the current parent
					int children = t.getNumChildren();
					int[] indices = new int[children];
					int count = 0;
					
					//while we haven't found all children
					while(count < children) {
						if(valueLists.get(valueDepth + 1).get(childIndex) != -1) { //if we have a node value
							indices[count] = childIndex; //set the child index
							int numDigits = Integer.toString(valueLists.get(valueDepth + 1).get(childIndex)).length(); //get the numberOfDigits for the child value
							if(numDigits > 1) { //if its > 1
								childIndex += numDigits - 1; //add to the child index
								valueLists.get(valueDepth + 1).add(childIndex, -1); //add a space
							}
							count++;
						}
						childIndex++;
					}
					
					int left = k;
					int right = k;
					int gap = 0;
					boolean isRight = false;
					boolean isLeft = false;
					
					//get the leftmost digit's index
					if(Integer.toString(t.getValue()).length() > 1) {
						if(k > 0) {
							while(Character.isDigit(strings[depth].charAt(left - 1))) {
								left--;
								if(left <= 0) {
									break;
								}
							}
						}
						//get the rightmost digit's index
						if(k < valueLists.get(valueDepth).size()) {
							while(Character.isDigit(strings[depth].charAt(right + 1))) {
								right++;
							}							
						}
					}
					
					//forall children
					for(int m : indices) {						
						for(int i = left; i <= right; i++) { //for all digits
							if(left == right) { //if we have a single digit
								isLeft = true; 
								isRight = true;
								gap = m - i;
							}
							else if(i == left) { //if we have the leftmost digit
								isLeft = true;
								isRight = false;
								gap = m - left;
							}
							else if(i == right) { //if we have the rightmost digit
								isLeft = false;
								isRight = true;
								gap = m - right;
							}
							else { //otherwise
								isLeft = false;
								isRight = false;
								gap = m - i;
							}
							
							//connect appropriately
							if(gap == 0) {
								if(i < strings[depth + 1].length()) {
									strings[depth + 1].setCharAt(i, '|');									
								}
								else {
									strings[depth + 1].append("|");
								}
								break;
							}
							else if(gap == 2) {
								if(i + 1< strings[depth + 1].length()) {
									strings[depth + 1].setCharAt(i + 1, '\\');									
								}
								else {
									strings[depth + 1].append('\\');
								}
								break;
							}
							else if(gap == -2) {
								if(i < strings[depth + 1].length()) {
									strings[depth + 1].setCharAt(i - 1, '/');									
								}
								else {
									strings[depth + 1].append('/');
								}
								break;
							}
							else if(gap > 2) {
								int gapCount = 1;
								for(int j = 0; j < gap-2; j++) {
									if(!Character.isDigit(strings[depth].charAt(m - (gap - gapCount)))) {
										strings[depth].setCharAt(m - (gap - gapCount), '_');	
										shift++;
									}
									
									gapCount++;
								}
								if(m - 1 < strings[depth + 1].length()) {
									strings[depth + 1].setCharAt(m - 1, '\\');									
								}
								else {
									strings[depth + 1].append('\\');
								}
								break;
							}
							else if(gap < -2) {
								int gapCount = 1;
								for(int j = 0; j < Math.abs(gap) - 2; j++) {
									if(valueLists.get(valueDepth).get(m + (Math.abs(gap) - gapCount)) == -1) {
										strings[depth].setCharAt(m + (Math.abs(gap) - gapCount), '_');	
									}
									
									gapCount++;
								}
								if(m + 1 < strings[depth + 1].length()) {
									strings[depth + 1].setCharAt(m + 1, '/');									
								}
								else {
									strings[depth + 1].append('/');
								}
								break;
							}		
						}
						
						if(gap == 1 && isRight) {
							if(!Character.isDigit(strings[depth].charAt(k + 1))) {
								strings[depth].setCharAt(k + 1, '_');
							}
							if(k + 1 < strings[depth + 1].length()) {
								strings[depth + 1].setCharAt(k + 1, '|');								
							}
							else {
								strings[depth + 1].append('|');	
							}
							shift++;
						}
						else if(gap == -1 && isLeft) {
							if(!Character.isDigit(strings[depth].charAt(k - 1))) {
								strings[depth].setCharAt(k - 1, '_');
							}
							if(k - 1 < strings[depth + 1].length()) {
								strings[depth + 1].setCharAt(k -1, '|');								
							}
							else {
								strings[depth + 1].append('|');
							}
						}
					}
					parentIndex++;
					if(depth == 0) {
						break;
					}
				}
			}			
			depth+= 2;
			valueDepth++;
			childIndex = 0;
		}
	}
	
	/**
	 * Helper for toString method
	 * @param t The tree to print
	 * @param level The level we are currently on
	 * @return The string representation of the tree
	 */
	private String toStringHelper(Tree t, int level) {
		String str_out = "";
		String indent = "  ";
		for(int i = 0; i < level; i++) {
			indent += "  ";
		}
		for(Tree child : t.getAllChildren()) {
			str_out += indent;
			str_out += child.getValue() + "\n";
			str_out += toStringHelper(child, level + 1);
		}
		return str_out;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str_out = "";
		String indent = "  ";
		str_out += getValue() + "\n";
		for(Tree t : getAllChildren()) {
			str_out += indent + t.getValue() + "\n";
			str_out += toStringHelper(t, 1);
		}
		str_out += "\n";
		return str_out;
	}
	
}
