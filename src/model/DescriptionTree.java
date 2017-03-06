package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	/*public String printTree() {
		String strOut = "";
		
		int numStrings = (getDepth() * 2) + 1;
		StringBuilder[] strings = new StringBuilder[numStrings];
		
		for(int i = 0; i < strings.length; i ++) {
			strings[i] = new StringBuilder();
		}
		
		printCurNode(strings);
		
		for(StringBuilder s : strings) {
			strOut += s + "\n";
		}
		
		return strOut;
	}*/
	
	private void printCurNode(StringBuilder[] strings) {
		float stringLength = 0;
		int numVertices = getNumVertices();
		boolean oddChildren = numVertices % 2 == 1;
		
		if(oddChildren) {
			stringLength = numVertices + 2;
		}
		else {
			stringLength = numVertices + 1;
		}
		
		
		float length = stringLength;
		for(Tree child : getAllChildren()) {
			length = printNode(child, length, strings);			
		}
	}
	
	private int printNode(Tree t, float startIndex, StringBuilder[] strings) {
		return 0;
	}
	
	
	public String printTree() {
		int numStrings = (getDepth() * 2) + 1;
		StringBuilder[] strings = new StringBuilder[numStrings];
		int startIndex = 0;
		int numVertices = getNumVertices() - 1;
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
			
			indices[curIndex] = index;
			int gap = index - indices[parentNode];
			
			if(gap == 0) {
				strings[level -1].insert(index, '|');
			}
			else if(gap == 2) {
				strings[level - 1].setCharAt(index - 1, '\\');
			}
			else if(gap == -2) {
				strings[level - 1].setCharAt(index + 1, '/');
			}
			else if(gap == 1) {
				if(!Character.isDigit(strings[level-2].charAt(index))) {
					strings[level - 2].setCharAt(index, '_');						
				}
				strings[level - 1].setCharAt(index, '|');
			}
			else if(gap == -1) {
				if(!Character.isDigit(strings[level-2].charAt(index))) {
					strings[level - 2].setCharAt(index, '_');						
				}
				strings[level - 1].setCharAt(index, '|');
			}
			else if(gap > 2) {
				int count = 1;
				for(int i = 0; i < gap-2; i++) {
					if(!Character.isDigit(strings[level - 2].charAt(index - (gap - count)))) {
						strings[level-2].setCharAt(index - (gap - count), '_');					
					}
					
					count++;
				}
				strings[level - 1].setCharAt(index - 1, '\\');
			}
			else if(gap < -2) {
				int count = 1;
				for(int i = 0; i < Math.abs(gap) - 2; i++) {
					if(!Character.isDigit(strings[level - 2].charAt(index + (Math.abs(gap) - count)))) {
						strings[level - 2].setCharAt(index + (Math.abs(gap) - count), '_');				
					}
					
					count++;
				}
				strings[level - 1].setCharAt(index + 1, '/');
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
