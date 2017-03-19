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

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Listener for printing results to text
 *
 */
public class PrintListener implements ActionListener {

	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public PrintListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<DescriptionTree> trees = model.getTrees(); //get the list of trees
		String type;
		
		//if the list is empty
		if(trees.isEmpty()) {
			//inform the user
			view.displayError("Save Error", "There are no trees to print. Please calculate the number of trees for your input parameters first");
			return;
		}
		else { //otherwise
			//if the trees are alpha trees
			if(trees.get(0) instanceof AlphaTree) {
				type = "Alpha"; //set the type the alpha
			}
			else { //otherwise
				//set it to alpha
				type = "Beta";
			}
		}
		
		File file;		
		FileWriter fw;
		BufferedWriter bw;		
		
		//create a file chooser
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		//create a file filter
		FileFilter ff = new FileNameExtensionFilter("Text File (.txt)", "txt", "text");
		
		//set the selection mode
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//set the file filter
		jfc.setFileFilter(ff);
		int response = jfc.showSaveDialog(null); //open the file chooser and get the result
		
		if(response == JFileChooser.CANCEL_OPTION) { //if the user cancels
			return; //do nothing
		}
		else if(response == JFileChooser.ERROR_OPTION) { //if there's an error
			//inform the user
			view.displayError("File Error", "There was a problem with saving the file");
			return;
		}
		else if(response == JFileChooser.APPROVE_OPTION) { //otherwise
			file = jfc.getSelectedFile(); //get the sleected file
			try {
				String fileName = file.getPath(); //get the path
				if(!fileName.endsWith(".txt")) { //if we don't end in .txt
					fileName += ".txt"; //add it
				}
				fw = new FileWriter(fileName); //init the filewriter
				bw = new BufferedWriter(fw); //init the buffered writer
				
				bw.write("Tree type: " + type + "\n"); //prin the tree type
				//print num nodes
				bw.write("Nodes: Minimum: " + view.getNodeMin() + " Maximum: " + view.getNodeMax() + "\n");	//print he node nums
				//print a and b
				bw.write("First Parameter (a): " + view.getParamA() + " Second Parameter (b): " + view.getParamB() + "\n"); //print the value params
				//print restrictions
				for(Restrictor r : model.getRestrictors()) {
					bw.write(r.toString() + "\n");
				}
				
				bw.write("\n"); //add a newline
				
				//print he trees
				for(DescriptionTree dt : trees) {
					bw.write(dt.printTree() + "\n\n");
				}
				bw.flush();
				bw.close();
				
			}
			catch(IOException ex) {
				//if we get an error, inform the user
				view.displayError("Save File Error", ex.getMessage());
			}			
			
		}		
		JOptionPane.showMessageDialog(null, "File Saved"); //tell the user the file has been saved		
	}	
}
