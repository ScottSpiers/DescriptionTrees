package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.AlphaTree;
import model.DescriptionTree;
import model.DescriptionTreeModel;
import model.Restrictor;
import view.DescriptionTreeView;

public class PrintListener implements ActionListener {

	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	public PrintListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		List<DescriptionTree> trees = model.getTrees();
		String type;
		
		if(trees.isEmpty()) {
			view.displayError("Save Error", "There are no trees to print. Please calculate the number of trees for your input parameters first");
			return;
		}
		else {
			if(trees.get(0) instanceof AlphaTree) {
				type = "Alpha";
			}
			else {
				type = "Beta";
			}
		}
		
		File file;		
		FileWriter fw;
		BufferedWriter bw;		
		
		
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		FileFilter ff = new FileNameExtensionFilter("Text File (.txt)", "txt", "text");
		
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.addChoosableFileFilter(ff);
		int response = jfc.showSaveDialog(null);
		
		if(response == JFileChooser.CANCEL_OPTION) {
			return;
		}
		else if(response == JFileChooser.ERROR_OPTION) {
			view.displayError("File Error", "There was a problem with saving the file");
			return;
		}
		else if(response == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
			try {
				String fileName = file.getPath();
				if(!fileName.endsWith(".txt")) {
					fileName += ".txt";
				}
				fw = new FileWriter(fileName);
				bw = new BufferedWriter(fw);
				
				bw.write("Tree type: " + type + "\n");
				//print num nodes
				bw.write("Nodes: Minimum: " + view.getNodeMin() + " Maximum: " + view.getNodeMax() + "\n");				
				//print a and b
				bw.write("First Parameter (a): " + view.getParamA() + " Second Parameter (b): " + view.getParamB() + "\n");
				//print restrictions
				for(Restrictor r : model.getRestrictors()) {
					bw.write(r.toString() + "\n");
				}
				
				bw.write("\n");
				
				for(DescriptionTree dt : trees) {
					bw.write(dt.printTree() + "\n\n");
				}
				bw.flush();
				bw.close();
				
			}
			catch(IOException ex) {
				view.displayError("Save File Error", ex.getMessage());
			}			
			
		}		
		JOptionPane.showMessageDialog(null, "File Saved");
		
	}
	
	
}
