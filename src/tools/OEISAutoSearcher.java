package tools;

import java.util.ArrayList;
import java.util.List;

import model.BetaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;
import view.OEISAutoSearchView;

public class OEISAutoSearcher {

	private DescriptionTreeModel model;
	OEISAutoSearchView view;
	
	public OEISAutoSearcher(DescriptionTreeModel model) {
		this.model = model;
		run();
	}
	
	private void run() {
		List<String> seqs = new ArrayList<String>();
		int[] nodeSeq = new int[6];
		DescriptionTree t;
		List<DescriptionTree> newTrees = new ArrayList<DescriptionTree>();
		
		for(int a = 0; a < 6; a++) {
			for(int b = 0; b < 6; b++) {
				
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
				for(int j = 0; j < nodeSeq.length - 1 ; j++) {
					linkEnd += nodeSeq[j] + ",";				
				}				
				linkEnd += nodeSeq[nodeSeq.length - 1];
				
				seq = String.format("| %5s | %10d | %10d | %50s |%n", "0 to 5", a, b, "www.oeis.org/search?q=" + linkEnd);
				seqs.add(seq);
			}
		}
		printSeqs(seqs);
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
