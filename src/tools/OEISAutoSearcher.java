package tools;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.AlphaTree;
import model.BetaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;
import model.Restrictor;
import view.AutoSearchResultsView;
import view.OEISAutoSearchView;

public class OEISAutoSearcher {

	private DescriptionTreeModel model;
	private OEISAutoSearchView view;
	private List<Restrictor> selectedRestrictors;
	private List<DescriptionTree> newTrees;
	private List<String> seqs;
	private DescriptionTree t;
	private int[] nodeSeq;
	private boolean isAlpha;
	private boolean isBeta;
	private int nodeMin;
	private int nodeMax;
	private int a;
	private int aMin;
	private int aMax;
	private int b;
	private int bMin;
	private int bMax;
	
	
	public OEISAutoSearcher(DescriptionTreeModel model, OEISAutoSearchView view) {
		this.model = model;
		this.view = view;
		run();
	}
	
	private void run() {
		seqs = new ArrayList<String>();
		newTrees = new ArrayList<DescriptionTree>();
		
		nodeMin = view.getNodeMin();
		nodeMax = view.getNodeMax();
		nodeSeq = new int[(nodeMax - nodeMin) + 1];
		
		
		if(nodeMin > nodeMax) {
			JOptionPane.showMessageDialog(view, "Minimum node number is greater than maximum", "Node Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		aMin = view.getAMin();
		aMax = view.getAMax();
		
		if(aMin > aMax) {
			JOptionPane.showMessageDialog(view, "Minimum 'a' value is greater than maximum",  "Value Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		bMin = view.getBMin();
		bMax = view.getBMax();
		
		if(bMin > bMax) {
			JOptionPane.showMessageDialog(view, "Minimum 'b' value is greater than maximum", "Value Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		isAlpha = view.isAlphaChecked();
		isBeta = view.isBetaChecked();
		if(!isAlpha && !isBeta) {
			JOptionPane.showMessageDialog(view, "Please select either Alpha or Beta tree", "Tree Type Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		selectedRestrictors = view.getSelectedRestrictors();
		
		
		for(a = aMin; a <= aMax; a++) {
			for(b = bMin; b <= bMax; b++) {
				if(selectedRestrictors.isEmpty()) {
					int i = 0;
					for(int n = nodeMin; n <= nodeMax; n++) {
						if(isAlpha) {
							t = new AlphaTree(a, b);
						}
						else {
							t = new BetaTree(a,b);							
						}
						newTrees.clear();
						for(DescriptionTree dt : model.genTrees(t, n)) {
							newTrees.addAll(dt.evaluateTree(dt.getNodes().size()-1));
						}										
							
						nodeSeq[i] = newTrees.size();
						i++;
					}
					String seq = "| 0 to 5 | a: " + a + " | b: " + b + " | Link: www.oeis.org/search?q=";
					String linkEnd = "";
					for(int l = 0; l < nodeSeq.length - 1 ; l++) {
						linkEnd += nodeSeq[l] + ",";				
					}				
					linkEnd += nodeSeq[nodeSeq.length - 1];
					
					seq = String.format("| %10s | %10d | %10d | %50s |%n", nodeMin + " to " + nodeMax, a, b, "www.oeis.org/search?q=" + linkEnd);
					seqs.add(seq);
				}
				else {
					restrictionLoop(selectedRestrictors.size()-1);
				}
				
			}
		}
		printSeqs(seqs);
	}
	
	private void restrictionLoop(int i) {
		if(i == 0) {
			int min = selectedRestrictors.get(0).getMin();
			int max = selectedRestrictors.get(0).getMax();
			if(min > max) {
				JOptionPane.showMessageDialog(view, "Minimum value is greater than maximum", selectedRestrictors.get(0).getName() + " Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			for(int j = min; j <= max; j++) {
				selectedRestrictors.get(0).setMax(j);
				int k = 0;
				for(int n = nodeMin; n <= nodeMax; n++) {
					if(isAlpha) {
						t = new AlphaTree(a, b);
					}
					else {
						t = new BetaTree(a,b);							
					}
					newTrees.clear();
					for(DescriptionTree dt : model.genTrees(t, n)) {
						newTrees.addAll(dt.evaluateTree(dt.getNodes().size()-1));
					}
						
					for(Restrictor r : selectedRestrictors) {
						newTrees = r.applyRestriction(newTrees);
					}
						
					nodeSeq[k] = newTrees.size();
					k++;
				}
				String seq = "| 0 to 5 | a: " + a + " | b: " + b + " | Link: http://www.oeis.org/search?q=";
				String linkEnd = "";
				for(int l = 0; l < nodeSeq.length - 1 ; l++) {
					linkEnd += nodeSeq[l] + ",";				
				}				
				linkEnd += nodeSeq[nodeSeq.length - 1];
				
				String formatString = "| %10s | %10d | %10d | ";
				for(Restrictor r : selectedRestrictors) {
					formatString += "%25s | ";
				}
				formatString += "%50s |%n";
				Object[] args = new Object[4 + selectedRestrictors.size()];
				args[0] = nodeMin + " to " + nodeMax;
				args[1] = a;
				args[2] = b;
				for(int m = 3; m < args.length - 1; m++) {
					args[m] = " Min: " + selectedRestrictors.get(m - 3).getMin() + " to Max: " + selectedRestrictors.get(m - 3).getMax();
				}
				args[args.length - 1] = "www.oeis.org/search?q=" + linkEnd;
				seq = String.format(formatString, args);
				seqs.add(seq);
			}
		}
		else {
			int min = selectedRestrictors.get(i).getMin();
			int max = selectedRestrictors.get(i).getMax();
			
			if(min > max) {
				JOptionPane.showMessageDialog(view, selectedRestrictors.get(i).getName() + " Error", "Minimum value is greater than maximum", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			for(int j = min; j <= max; j++) {
				selectedRestrictors.get(i).setMax(j);
				restrictionLoop(i-1);
			}
		}
	}
	
	private void printSeqs(List<String> seqs) {
		new AutoSearchResultsView(seqs, selectedRestrictors);
	}
}
