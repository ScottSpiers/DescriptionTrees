package tools;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.BetaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;
import model.Restrictor;
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
	private int b;
	
	
	public OEISAutoSearcher(DescriptionTreeModel model, OEISAutoSearchView view) {
		this.model = model;
		this.view = view;
		run();
	}
	
	private void run() {
		seqs = new ArrayList<String>();
		nodeSeq = new int[6];
		newTrees = new ArrayList<DescriptionTree>();
		
		nodeMin = view.getNodeMin();
		nodeMax = view.getNodeMax();
		
		if(nodeMin > nodeMax) {
			JOptionPane.showMessageDialog(view, "Node Input Error", "Minimum node number is greater than maximum", JOptionPane.ERROR_MESSAGE);
			return;
		}
		isAlpha = view.isAlphaChecked();
		isBeta = view.isBetaChecked();
		if(!isAlpha && !isBeta) {
			JOptionPane.showMessageDialog(view, "Tree Type Input Error", "Please select either Alpha or Beta tree", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		selectedRestrictors = view.getSelectedRestrictors();
		
		
		for(a = 0; a < 6; a++) {
			for(b = 0; b < 6; b++) {
				if(selectedRestrictors.isEmpty()) {
					int i = 0;
					for(int n = 0; n < 6; n++) {
						t = new BetaTree(a,b);
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
					
					seq = String.format("| %5s | %10d | %10d | %50s |%n", "0 to 5", a, b, "www.oeis.org/search?q=" + linkEnd);
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
			for(int j = min; j <= max; j++) {
				selectedRestrictors.get(0).setMax(j);
				int k = 0;
				for(int n = 0; n < 6; n++) {
					t = new BetaTree(a,b);
					newTrees.clear();
					for(DescriptionTree dt : model.genTrees(t, n)) {
						newTrees.addAll(dt.evaluateTree(dt.getNodes().size()-1));
					}
						
					for(Restrictor r : selectedRestrictors) {
						r.applyRestriction(newTrees);
					}
						
					nodeSeq[k] = newTrees.size();
					k++;
				}
				String seq = "| 0 to 5 | a: " + a + " | b: " + b + " | Link: www.oeis.org/search?q=";
				String linkEnd = "";
				for(int l = 0; l < nodeSeq.length - 1 ; l++) {
					linkEnd += nodeSeq[l] + ",";				
				}				
				linkEnd += nodeSeq[nodeSeq.length - 1];
				
				seq = String.format("| %5s | %10d | %10d | %50s |%n", "0 to 5", a, b, "www.oeis.org/search?q=" + linkEnd);
				seqs.add(seq);
			}
		}
		else {
			for(int j = selectedRestrictors.get(i).getMin(); j <= selectedRestrictors.get(i).getMax(); j++) {
				selectedRestrictors.get(i).setMax(j);
				restrictionLoop(i-1);
			}
		}
	}
	
	private void printSeqs(List<String> seqs) {
		int lastLength = 0;
		System.out.println("---------------------");
		System.out.format("| %6s | %10s | %10s | %50s |%n", "Nodes", "a", "b", "Link");
		for(String s : seqs) {
			System.out.println(s);
		}
	}
}
