package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Restrictor;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * The view for the auto search results
 */
public class AutoSearchResultsView extends JFrame {

	private static final long serialVersionUID = -7713142573397836176L;
	
	private JTextArea txt_output;
	
	/**
	 * 
	 * @param seqs The list of sequences resulting from the calculations
	 * @param rs The list of selected restrictors
	 */
	public AutoSearchResultsView(List<String> seqs, List<Restrictor> rs) {
		super("Auto Search Results");
		display(seqs, rs);
	}
	
	/**
	 * 
	 * @param seqs The list of sequences resulting from the calculations
	 * @param rs The list of selected restrictors
	 */
	private void display(List<String> seqs, List<Restrictor> rs) {
		//create and set menubar
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem save = new JMenuItem("Save Results");
		save.addActionListener(new SaveResultsListener(seqs, rs));
		fileMenu.add(save);
		menu.add(fileMenu);
		this.setJMenuBar(menu);
		
		BorderLayout layout = new BorderLayout();
		JPanel bg = new JPanel(layout);
		
		JPanel pnl_output = new JPanel();
		JScrollPane scrl_output = new JScrollPane(pnl_output);
		txt_output = new JTextArea();
		txt_output.setEditable(false);
		txt_output.setMinimumSize(new Dimension(400, 500));
		
		setText(seqs, rs);
		pnl_output.add(txt_output);
		
		bg.add(BorderLayout.CENTER, scrl_output);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.getContentPane().add(bg);
		this.setLocation((int) screenSize.getWidth() / 3, (int) screenSize.getHeight() / 3); 
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Sets the text of the output
	 * @param seqs The list of sequences resulting from the calculations
	 * @param rs The list of selected restrictors
	 */
	@SuppressWarnings("unused")
	private void setText(List<String> seqs, List<Restrictor> rs) {
		String formatString = "| %7s | %10s | %10s | "; //init the format string
		
		//for all restrictors
		for(Restrictor r : rs) {
			formatString += "%25s | "; //add to the format string
		}
		
		formatString += "%50s |%n"; //add the format for the links
		
		//create an array for arguments
		Object[] args = new Object[4 + rs.size()];
		args[0] = "Nodes";
		args[1] = "a";
		args[2] = "b";
		
		//for each restriction
		for(int m = 3; m < args.length - 1; m++) {
			args[m] = rs.get(m - 3).getName(); //add its name to the array
			
		}
		args[args.length -1] = "Link";
		
		String strOut = "";
		strOut += String.format(formatString, args);
		for(String s : seqs) {
			strOut += s;
		}
		txt_output.setText(strOut);
	}
	
	/**
	 * 
	 * @author Scott Spiers
	 * University of Strathclyde
	 * Final Year Project: Description Trees
	 * Supervisor: Sergey Kitaev
	 *
	 * Listener to save the results from the calculations to text file
	 */
	private class SaveResultsListener implements ActionListener {
		private List<String> seqs;
		private List<Restrictor> rs;
		
		/**
		 * 
		 * @param seqs The list of sequences resulting from the calculations
		 * @param rs The list of selected restrictors
		 */
		public SaveResultsListener(List<String> seqs, List<Restrictor> rs) {
			this.seqs = seqs;
			this.rs = rs;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent e) {
			File file;		
			FileWriter fw;
			BufferedWriter bw;		
			
			//create a file chooser
			JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
			//create a file filter
			FileFilter ff = new FileNameExtensionFilter("Text File (.txt)", "txt", "text");
			
			//set selection mode and filter
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jfc.setFileFilter(ff);
			int response = jfc.showSaveDialog(null); //show file chooser and get response
			
			if(response == JFileChooser.CANCEL_OPTION) { //if the user cancels
				return; //do nothing
			}
			else if(response == JFileChooser.ERROR_OPTION) { //if there is an error
				//inform the user
				JOptionPane.showMessageDialog(null, "There was a problem with saving the file", "File Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else if(response == JFileChooser.APPROVE_OPTION) { //otherwise
				file = jfc.getSelectedFile(); //get the selected file
				try {
					String fileName = file.getPath(); //get the path
					if(!fileName.endsWith(".txt")) { //if we don't end in .txt
						fileName += ".txt"; //add it
					}
					fw = new FileWriter(fileName); //init the file writer
					bw = new BufferedWriter(fw); //init the buffered writer
					
					String formatString = "| %10s | %10s | %10s | "; //set the format strings
					//for every restrictor
					for(Restrictor r : rs) {
						formatString += "%25s | "; //add to the format string
					}
					
					formatString += "%100s |%n"; //set space for the link
					//create an array for arguments
					Object[] args = new Object[4 + rs.size()];
					args[0] = "Nodes";
					args[1] = "a";
					args[2] = "b";
					
					//add every restrictors name as an argument
					for(int m = 3; m < args.length - 1; m++) {
						args[m] = rs.get(m - 3).getName();
						
					}
					args[args.length -1] = "Link";
					
					String strOut = "";
					bw.write(String.format(formatString, args));
					
					for(String s : seqs) {
						bw.write(s);
					}
					bw.flush();
					bw.close();
					
				}
				catch (IOException ex) {
					//There was a problem, inform the user
					JOptionPane.showMessageDialog(null, "There was a problem with saving the file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}		
	}
}
