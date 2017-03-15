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

public class AutoSearchResultsView extends JFrame {

	private static final long serialVersionUID = -7713142573397836176L;
	
	private JTextArea txt_output;
	
	
	public AutoSearchResultsView(List<String> seqs, List<Restrictor> rs) {
		super("Auto Search Results");
		display(seqs, rs);
	}
	
	
	private void display(List<String> seqs, List<Restrictor> rs) {
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
	
	private void setText(List<String> seqs, List<Restrictor> rs) {
		String formatString = "| %7s | %10s | %10s | ";
		
		for(Restrictor r : rs) {
			formatString += "%25s | ";
		}
		
		formatString += "%50s |%n";
		
		Object[] args = new Object[4 + rs.size()];
		args[0] = "Nodes";
		args[1] = "a";
		args[2] = "b";
		
		for(int m = 3; m < args.length - 1; m++) {
			args[m] = rs.get(m - 3).getName();
			
		}
		args[args.length -1] = "Link";
		
		String strOut = "";
		strOut += String.format(formatString, args);
		for(String s : seqs) {
			strOut += s;
		}
		txt_output.setText(strOut);
	}
	
	private class SaveResultsListener implements ActionListener {
		private List<String> seqs;
		private List<Restrictor> rs;
		
		public SaveResultsListener(List<String> seqs, List<Restrictor> rs) {
			this.seqs = seqs;
			this.rs = rs;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
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
				JOptionPane.showMessageDialog(null, "There was a problem with saving the file", "File Error", JOptionPane.ERROR_MESSAGE);
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
					String formatString = "| %10s | %10s | %10s | ";
					for(Restrictor r : rs) {
						formatString += "%25s | ";
					}
					
					formatString += "%100s |%n";
					Object[] args = new Object[4 + rs.size()];
					args[0] = "Nodes";
					args[1] = "a";
					args[2] = "b";
					
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
					JOptionPane.showMessageDialog(null, "There was a problem with saving the file", "File Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}		
	}
}
