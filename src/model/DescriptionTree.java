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
	
	public void addLeafAsChildAt(int i) {
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
		else if(t instanceof Tree) {
			return descriptionTree.equals((Tree) t); 
		}
		return false;
	}
	
	public String printTree() {
		String strOut = "";
		int fullDepth = getDepth() + 1;
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
		Queue<Tree> q = new LinkedList<Tree>();
		q.add(descriptionTree);
		
		while(!q.isEmpty()) {
			Tree t = q.remove();
			
			valueLists.get(depths[curNode]).add(i, t.getValue());
			verts[curNode] = t;
			i++;
			
			for(Tree child : t.getAllChildren()) {
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
		
		int nodeCount = ((valueLists.get(valueLists.size() - 1).size() - width + 1) / 2);
		int count = 0;
		int vertice = 1;
		int index = 0;
		int depth = valueLists.size() - 2;
		for(int j = 0; j <= valueLists.size() - 3; j++) {
			int size = (valueLists.get(j).size() - getMaxWidth());
			if(size % 2 == 1) {
				size++;
			}
			vertice += size / 2;
		}
		index = vertice - 1;
		
		
		while(nodeCount < getNumVertices()) {
			int elementCount = 1;
			int parentIndex = 0;
			int shift = 0;
			int curIndex = 0;
			int numElements = valueLists.get(depth).size();
			while(elementCount <= ((numElements - width + 1) / 2)) {
				Tree curTree = verts[index];
				
				if(elementCount == 1) {
					parentIndex = 0;
				}
				
				int[] indices = new int[curTree.getNumChildren()];
				int endIndex;
				count = 0;
				while(count < curTree.getNumChildren()) {					
					if(valueLists.get(depth + 1).get(curIndex) != -1) {
						indices[count] = curIndex;
						count++;
						curIndex++;
					}
					else {
						curIndex++;
						continue;
					}
				}
				
				endIndex = curIndex;
				if(curTree.getNumChildren() == 0) {
					valueLists.get(depth).add(parentIndex + 1, -1);
					parentIndex += 3;
					elementCount++;
					vertice++;
					nodeCount++;
					index = vertice - 1;
					continue;
				}
				else if(curTree.getNumChildren() == 2) {
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
						
						
						
						shift += (locForParent - parentIndex);							
					}
					
					valueLists.get(depth).add(parentIndex + 1, -1);
					parentIndex++;
				}
				else {
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
			vertice -= (((valueLists.get(depth).size() - (width + shift) + 1) / 2) - 1) + ((((numElements - width) + 1)/ 2));
			index = vertice - 1;
		}
		
		convertAndConnect(valueLists, verts, strings);
		
		for(StringBuilder s : strings) {			
			strOut += s + "\n";
		}
		
		return strOut;
	}
	
	private void convertAndConnect(List<ArrayList<Integer>> valueLists, Tree[] verts, StringBuilder[] strings) {
		int depth = 0;
		int valueDepth = 0;
		int childIndex = 0;
		int parentIndex = 0;
		int shift = 0;
		
		for(ArrayList<Integer> l : valueLists) {
			for(int k = 0; k < l.size(); k++) {
				if(l.get(k) == -1) {
					if(k + shift < strings[depth].length()) {
						strings[depth].insert(k + shift, " ");						
					}
					else {
						strings[depth].append(" ");
					}
				}
				else {
					strings[depth].insert(k, l.get(k));
					
					if(Integer.toString(l.get(k)).length() > 1) {
						k += Integer.toString(l.get(k)).length() - 1;
					}
					
					Tree t = verts[parentIndex];
					int children = t.getNumChildren();
					int[] indices = new int[children];
					int count = 0;
					
					while(count < children) {
							if(valueLists.get(valueDepth + 1).get(childIndex) != -1) {
								indices[count] = childIndex;
								int numDigits = Integer.toString(valueLists.get(valueDepth + 1).get(childIndex)).length();
								if(numDigits > 1) {
									childIndex += numDigits - 1;
									valueLists.get(valueDepth + 1).add(childIndex, -1);
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
					
					if(Integer.toString(t.getValue()).length() > 1) {
						if(k > 0) {
							while(Character.isDigit(strings[depth].charAt(left - 1))) {
								left--;
								if(left <= 0) {
									break;
								}
							}
						}
						if(k < valueLists.get(valueDepth).size()) {
							while(Character.isDigit(strings[depth].charAt(right + 1))) {
								right++;
							}							
						}
					}
					
					for(int m : indices) {						
						for(int i = left; i <= right; i++) {
							if(left == right) {
								isLeft = true;
								isRight = true;
								gap = m - i;
							}
							else if(i == left) {
								isLeft = true;
								isRight = false;
								gap = m - left;
							}
							else if(i == right) {
								isLeft = false;
								isRight = true;
								gap = m - right;
							}
							else {
								isLeft = false;
								isRight = false;
								gap = m - i;
							}
							
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
