package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.scala.Empty;
import model.scala.Tree;

public abstract class DescriptionTree implements Cloneable {
	
	protected Tree descriptionTree;
	protected int alpha;
	protected int beta;
	
	public DescriptionTree() {
		descriptionTree = new Empty();
	}
	
	public DescriptionTree(Tree t) {
		descriptionTree = t;
	}
	
	public DescriptionTree(int a, int b) {
		descriptionTree = new Empty();
		alpha = a;
		beta = b;
	}
	
	public DescriptionTree(Tree t, int a, int b) {
		descriptionTree = t;
		alpha = a;
		beta = b;
	}
	
	public int getValue() {
		return descriptionTree.getValue();
	}
	
	public List<Tree> getLeaves() {
		return descriptionTree.getLeaves();
	}
	
	public List<Tree> getNodes() {
		//System.out.println(descriptionTree.getNodes());
		return descriptionTree.getNodes();
	}
	
	public Tree getChild(int i) {
		return descriptionTree.getChild(i);
	}
	
	public List<Tree> getAllChildren() {
		return descriptionTree.getAllChildren();
	}
	
	public int getNumVertices() {
		return descriptionTree.getNumVertices();
	}
	
	public int getNumChildren() {
		return descriptionTree.getNumChildren();
	}
	
	public void addRoot() {
		descriptionTree = descriptionTree.addRoot();
	}
	
	public void addLeaf() {
		descriptionTree = descriptionTree.addLeaf();
	}
	
	public void addLeafToLeaf(int i) {
		descriptionTree = descriptionTree.addLeafToLeaf(i);
	}
	
	public void addLeafToNode(int i) {
		descriptionTree = descriptionTree.addLeafToNode(i);
	}
	
	public void addLeafAsFirstChild() {
		descriptionTree = descriptionTree.addLeafAsFirstChild();
	}
	
	public void addChildToChildAt(int i) {
		descriptionTree = descriptionTree.addLeafAsChildAt(i);
	}
	
	public void addNode(DescriptionTree t) {
		descriptionTree = descriptionTree.addNode(t.descriptionTree);
	}	
	
	public abstract List<DescriptionTree> evaluateTree(int n);
	protected abstract void setAllLeafValues();
	
	public void setNodeValue(int n, int i) {
		descriptionTree = descriptionTree.setNodeValue(n, i);
	}
	
	public int getNumLeaves() {
		return descriptionTree.getNumLeaves(descriptionTree);
	}
	
	public int getWidth(int i) {
		return descriptionTree.getWidth(i);
	}
	
	public int getDepth() {
		return descriptionTree.getDepth();
	}
	
	public int getMaxWidth() {
		int width = getWidth(0);
		int max = 0;
		for(int i = 1; i <= getDepth(); i++) {
			max = getWidth(i);
			if(max > width) {
				width = max;
			}
		}
		return width;
	}
	
	@Override
	public Object clone() {		
		try {
			return super.clone();		
		}
		catch (CloneNotSupportedException ex) {
			return null;
		}
	}
	
	@Override
	public boolean equals(Object t) {
		if(t instanceof DescriptionTree) {
			return (descriptionTree.getNodes().equals(((DescriptionTree) t).getNodes()));
		}
		return false;
	}
	
	public String printTreeTest() {
		String treeString = descriptionTree.toString();
		String strOut = "";
		int fullDepth = getDepth();
		int[] depths = new int[getNumVertices()];
		int width = getMaxWidth();
		if(width % 2 == 1) {
			width += 1;
		}
		List<ArrayList<Integer>> valueLists = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < fullDepth; i++) {
			valueLists.add(new ArrayList<Integer>());
			for(int j = 0; j < width; j++) {
				valueLists.get(i).add(-1);				
			}
		}
		Tree[] verts = new Tree[getNumVertices()];
		
		int numStrings = (getDepth() * 2) + 1;
		StringBuilder[] strings = new StringBuilder[numStrings];
		
		for(int i = 0; i < strings.length; i ++) {
			strings[i] = new StringBuilder();
		}
		
		int depthIndex = 1;
		depths[0] = 0;
		int i = 0;
		int curNode = 0;
		Queue<Tree> q = new LinkedList<Tree>();
		q.add(descriptionTree);
		
		while(!q.isEmpty()) {
			Tree t = q.remove();
			
			valueLists.get(depths[curNode]).add(i, t.getValue());
			verts[curNode] = t;
			i++;
			
			int maxDepth = 0;
			for(Tree child : t.getAllChildren()) {
				int childDepth = child.getDepth();
				depths[depthIndex] = depths[curNode] + 1;
				depthIndex++;
				
				q.add(child);
			}
			
			if(curNode < getNumVertices() - 1) {				
				if(depths[curNode + 1] == depths[curNode]) {
					valueLists.get(depths[curNode]).add(i, -1);
					i++;
				}
				else {
					i = 0;
				}
			}
			
			curNode++;			
		}
		
		int nodeCount = (valueLists.get(valueLists.size() - 1).size() - width) / 2;
		int count = 0;
		int vertice = 1;
		int index = 0;
		int depth = valueLists.size() - 2;
		for(int j = 0; j <= valueLists.size() - 3; j++) {
			vertice += (valueLists.get(j).size() - getMaxWidth()) / 2;
		}
		index = vertice - 1;
		
		
		while(nodeCount < getNumVertices()) {
			int elementCount = 1;
			int parentIndex = 0;
			int curIndex = 0;
			
			while(elementCount <= (valueLists.get(depth).size() - width) / 2) {
				Tree curTree = verts[index];
				
				if(elementCount == 1) {
					parentIndex = 0;
				}
				
				int startIndex = 0;
				int[] indices = new int[curTree.getNumChildren()];
				int childIndex = 0;
				int endIndex;
				
				while(count < curTree.getNumChildren()) {
					if(count == 0) {
						startIndex = curIndex;
					}
					if(valueLists.get(depth).get(curIndex) != -1) {
						indices[count] = curIndex;
						count++;						
					}
					else {
						curIndex++;
						continue;
					}
				}
				
				endIndex = curIndex;
				if(curTree.getNumChildren() == 0) {
					//do nothing
					parentIndex += 2;
					curIndex = endIndex + 1;
					elementCount++;
					vertice++;
					nodeCount++;
					index = vertice - 1;
					continue;
				}
				if(curTree.getNumChildren() == 1) {			
					//do stuff
					if(parentIndex < endIndex) {
						for(int j = 0; j < endIndex - parentIndex; j++) {
							valueLists.get(depth).add(parentIndex, -1);							
						}
						parentIndex += endIndex - parentIndex;
					}
					else {
						for(int j = 0; j < parentIndex - endIndex; j++) {
							valueLists.get(depth).add(indices[0], -1);							
						}
						indices[0] += parentIndex - endIndex;
					}
					parentIndex += 2;
					curIndex = endIndex + 1;
					elementCount++;
					vertice++;
					nodeCount++;
					index = vertice - 1;
					continue;
				}
				else {
					int locForParent = (indices[0] + endIndex) / 2;
					if(locForParent < parentIndex) {
						for(int j = 0; j < locForParent; j++) {
							valueLists.get(depth + 1).add(indices[0], -1);						
						}
						endIndex += locForParent;
						for(int j = 0; j < curTree.getNumChildren(); j++) {
							indices[j] += locForParent;
						}
					}
					else {
						for(int j = 0; j < locForParent; j++) {
							valueLists.get(depth).add(parentIndex, -1);
							parentIndex++;
							
						}
						//endIndex += locForParent;					
					}					
				}
				
				parentIndex += 2;
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
			vertice -= (((valueLists.get(depth).size() - width) / 2) + (((valueLists.get(depth + 1).size() - width) / 2) - 1));
			index = vertice - 1;
		}
		System.out.println(valueLists);
		
		for(StringBuilder s : strings) {
			strOut += s + "\n";
		}
		
		return strOut;
	}
	
	public String printTree() {
		int numStrings = (getDepth() * 2) + 1;
		StringBuilder[] strings = new StringBuilder[numStrings];
		int startIndex = 0;
		int numVertices = getNumVertices() - 1;
		boolean oddChildren = (numVertices + 1) % 2 == 1;
		int level = 0;
		int stringLength = 0;
		
		if(oddChildren) {
			stringLength = numVertices + 2;
		}
		else {
			stringLength = numVertices + 1;
		}
		
		for(int i = 0; i < strings.length; i++) {
			strings[i] = new StringBuilder();
			strings[i].setLength(stringLength * 2);
	
			for(int j = 0; j < stringLength * 2; j++) {
				strings[i].setCharAt(j, ' ');
			}
		}
		
		strings[0].insert((stringLength / 2) + 1, Integer.toString(getValue()));
		level += 2;
		
		int[] depths = new int[numVertices + 1];
		depths[0] = 0;
		int depthIndex = 1;
		int[] endIndices = new int[numVertices + 1];
		endIndices[0] = stringLength - 1;
		int[] divisions = new int[numVertices + 1];
		divisions[0] = stringLength;
		int[] parents = new int[numVertices + 1];
		parents[0] = 0;
		int[] indices = new int[numVertices + 1];
		indices[0] = (stringLength / 2) + 1;
		
		Queue<Tree> q_vertices = new LinkedList<Tree>();
		
		for(Tree t : getAllChildren()) {
			float initDiv = (float) t.getNumVertices() * (((float) stringLength - (float) startIndex) / (float) numVertices);
			int divide = Math.round(initDiv);
			int endIndex = startIndex + (divide - 1);
			endIndices[depthIndex] = endIndex;
			divisions[depthIndex] = divide;
			depths[depthIndex] = 1;
			depthIndex += 1;
			numVertices -= t.getNumVertices();
			startIndex = endIndex + 1;
			q_vertices.add(t);
		}
		
		int curIndex = 1;
		while(!q_vertices.isEmpty()) {
			Tree t = q_vertices.remove();
			
			numVertices = t.getNumVertices() - 1;
			startIndex = endIndices[curIndex] - (divisions[curIndex] - 1);
			
			int prevDepth = depths[curIndex];
			for(Tree child : t.getAllChildren()) {
				float initDiv = (float) child.getNumVertices() * ((((float) endIndices[curIndex] - startIndex) + 1) / (float) numVertices);
				int divide = Math.round(initDiv);
				int endIndex = startIndex + (divide - 1);
				endIndices[depthIndex] = endIndex;
				divisions[depthIndex] = divide;
				depths[depthIndex] = prevDepth + 1;
				parents[depthIndex] = curIndex;
				depthIndex += 1;
				numVertices -= child.getNumVertices();
				startIndex = endIndex + 1;
				q_vertices.add(child);
			}
			
			startIndex = endIndices[curIndex] - (divisions[curIndex] - 1);
			level = depths[curIndex] * 2;
			int index = (startIndex + endIndices[curIndex]) / 2;
			int parentNode = parents[curIndex];
			
			int numSpaces = Integer.toString(t.getValue()).length() - 1;
			
			if(index > 0) {
				if(Character.isDigit(strings[level].charAt(index - 1))) {
					strings[level].insert(index, " " + t.getValue());
					index += 1;
					endIndices[parentNode] += 1;
					divisions[parentNode] += 1;
					for(int i = 0; i < indices.length; i++) {
						if(((endIndices[i] - (divisions[i] - 1)) + endIndices[i]) / 2 > indices[curIndex]) {
							if(i > curIndex) {
								endIndices[i] += 1;
								indices[i] += 1;
							}
							
						}
						/*if(parents[i] == parentNode) {
							if(i > curIndex) {
								endIndices[i] += 1;
							}
						}	*/
					}
				}				
				else {
					strings[level].insert(index, t.getValue() + " ");						
				}				
			}
			else {				
				strings[level].insert(index, t.getValue() + " ");						
				
			}
			String spaces = "";
			for(int i = numSpaces; i > 0; i--) {
				spaces += " ";
			}
			for(int i = 0; i < strings.length; i++) {
				if(i > level) {
					strings[i].insert(index, spaces);				
				}
			}
			
			for(int i = 0; i < indices.length; i++) {
				if(((endIndices[i] - (divisions[i] - 1)) + endIndices[i]) / 2 > indices[curIndex]) {
					if(i > curIndex) {
						endIndices[i] += numSpaces;	
						indices[i] += numSpaces;
					}
					
				}
			}
			
			indices[curIndex] = index;
			
			int parentIndex = indices[parentNode];
			int left = parentIndex;
			int right = parentIndex;
			int gap = 0;
			boolean isLeft = false;
			boolean isRight = false;
			
			if(Integer.toString(t.getValue()).length() > 1) {
				if(parentIndex > 0) {
					while(Character.isDigit(strings[level - 2].charAt(left - 1))) {
						left--;
						if(left <= 0) {
							break;
						}
					}
				}
				while(Character.isDigit(strings[level - 2].charAt(right + 1))) {
					right++;
				}				
			}
			
			for(int i = left; i <= right; i++) {
				if(left == right) {
					isLeft = true;
					isRight = true;
					gap = index - i;
				}
				else if(i == left) {
					isLeft = true;
					isRight = false;
					gap = index - left;
				}
				else if(i == right) {
					isLeft = false;
					isRight = true;
					gap = index - right;
				}
				else {
					isLeft = false;
					isRight = false;
					gap = index - i;
				}
				
				if(gap == 0) {
					strings[level -1].setCharAt(index, '|');
					break;
				}
				else if(gap == 2) {
					strings[level - 1].setCharAt(index - 1, '\\');
					break;
				}
				else if(gap == -2) {
					strings[level - 1].setCharAt(index + 1, '/');
					break;
				}
				else if(gap > 2) {
					int count = 1;
					for(int j = 0; j < gap-2; j++) {
						if(!Character.isDigit(strings[level - 2].charAt(index - (gap - count)))) {
							strings[level-2].setCharAt(index - (gap - count), '_');					
						}
						
						count++;
					}
					strings[level - 1].setCharAt(index - 1, '\\');
					break;
				}
				else if(gap < -2) {
					int count = 1;
					for(int j = 0; j < Math.abs(gap) - 2; j++) {
						if(!Character.isDigit(strings[level - 2].charAt(index + (Math.abs(gap) - count)))) {
							strings[level - 2].setCharAt(index + (Math.abs(gap) - count), '_');				
						}
						
						count++;
					}
					strings[level - 1].setCharAt(index + 1, '/');
					break;
				}		
			}
			
			if(gap == 1 && isRight) {
				if(!Character.isDigit(strings[level-2].charAt(index))) {
					if(index < strings[level - 2].length()) {
						if(Character.isDigit(strings[level - 2].charAt(index + 1))) {
							strings[level - 2].insert(index + 1, ' ');
							strings[level - 2].setCharAt(index, '_');
							/*for(int i = 0; i < strings.length; i++) {
								if(i % 2 == 0 && i < level - 2) {
									strings[i].insert(index, '_');
								}
								else if(i % 2 == 1 && i < level - 1) {
									strings[i].insert(index, ' ');
								}
							}*/
						}
						else {
							strings[level - 2].setCharAt(index, '_');		
						}
					}
					else {
						strings[level - 2].setCharAt(index, '_');						
					}
					strings[level - 1].setCharAt(index, '|');
				}
				
			}
			else if(gap == -1 && isLeft) {
				if(!Character.isDigit(strings[level-2].charAt(index))) {
					if(index > 0) {
						if(Character.isDigit(strings[level - 2].charAt(index - 1))) {
							strings[level - 2].insert(index - 1, ' ');
							strings[level - 2].setCharAt(index, ' ');
							/*for(int i = 0; i < strings.length; i++) {
								if(i % 2 == 0 && i < level - 2) {
									strings[i].insert(index, '_');
								}
								else if(i % 2 == 1 && i < level - 1) {
									strings[i].insert(index, ' ');
								}
							}*/
						}
						else {
							strings[level - 2].setCharAt(index, '_');								
						}
					}
					else {
						strings[level - 2].setCharAt(index, '_');
					}
					strings[level - 1].setCharAt(index, '|');				
				}
			}
		
			curIndex++;
		}
		
		String str_out = "";
		for(StringBuilder s : strings) {
			str_out += s + "\n";
		}
		return str_out;
	}
	
	
	public String printString() {
		int numStrings = (getDepth() * 2) + 1;
		StringBuilder[] strings = new StringBuilder[numStrings];
		int startIndex = 0;
		int numVertices = getNumVertices();
		boolean oddChildren = numVertices % 2 == 1;
		int level = 0;
		int stringLength = 0;
		
		if(oddChildren) {
			stringLength = numVertices + 2;
		}
		else {
			stringLength = numVertices + 1;
		}
		
		for(int i = 0; i < strings.length; i++) {
			strings[i] = new StringBuilder();
			strings[i].setLength(stringLength);
		}
		
		strings[0].setCharAt((stringLength / 2) + 1, Integer.toString(getValue()).charAt(0));
		
		for(Tree t : getAllChildren()) {
			printString(t, level + 2, startIndex, strings, numVertices);
		}
		
		System.out.println("      __________6__________\n     /      /   |   \\      \\\n  __3__    4  __5__  6      7\n / / \\ \\     / / \\ \\       /|\\\n1 2   3 4   1 2   3 4     1 2 3\n       /|\\\n      1 2 3");
		String str_out = "";
		for(StringBuilder s : strings) {
			str_out += s + "\n";
		}
		return str_out;
	}
	
	private void printString(Tree t, int level, int startIndex, StringBuilder[] strings, int numVertices) {
		int numVerts = numVertices;
		int indexStart = startIndex;
		int divide = t.getNumVertices() * ((strings[0].length() - startIndex) / numVerts);
		int index = startIndex + (divide / 2);
		
		strings[level].setCharAt(index, Integer.toString(t.getValue()).charAt(0));
		indexStart += divide;
		numVerts -= t.getNumVertices();
		
		for(Tree child : t.getAllChildren()) {
			printString(child, level + 2, indexStart, strings, numVerts);
		}
		
	}
	
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
