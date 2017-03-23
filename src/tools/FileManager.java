package tools;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileManager {
	
	private static JFileChooser jfc;
	
	public FileManager() {
		jfc = new JFileChooser(System.getProperty("user.dir"));
	}

	public static boolean saveObject(Component frame, Object o, String fileType) {
		File file;
		//create a file chooser		
		int response = jfc.showSaveDialog(frame); //open the file chooser
		
		if(response == JFileChooser.CANCEL_OPTION) { //if the user cancels
			return false; //do nothing
		}
		else if(response == JFileChooser.ERROR_OPTION) { //if there's an error
			//inform the user
			JOptionPane.showMessageDialog(frame, "Could not save: please check storage space and/or security permissions.\n", "Save Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(response == JFileChooser.APPROVE_OPTION) { //otherwise
			try {
				file = jfc.getSelectedFile(); //get the selected file
				FileOutputStream fos = new FileOutputStream(file.getPath() + fileType); //init the file stream
				ObjectOutputStream oos = new ObjectOutputStream(fos); //init the object stream
				
				oos.writeObject(o); //write the object
				oos.close();
				fos.close();
				return true;
			}
			catch(IOException ex) {
				// If there was an error, inform the user
				JOptionPane.showMessageDialog(frame, "Could not save: please check storage space and/or security permissions.\n" + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return false;
	}
	
	public static boolean saveAsText(Component frame, String text) {
		File file;		
		FileWriter fw;
		BufferedWriter bw;		
		
		//create a file filter
		FileFilter ff = new FileNameExtensionFilter("Text File (.txt)", "txt", "text");
		
		//set the selection mode
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//set the file filter
		jfc.setFileFilter(ff);
		int response = jfc.showSaveDialog(frame); //open the file chooser and get the result
		
		if(response == JFileChooser.CANCEL_OPTION) { //if the user cancels
			return false; //do nothing
		}
		else if(response == JFileChooser.ERROR_OPTION) { //if there's an error
			//inform the user
			JOptionPane.showMessageDialog(frame, "Could not save: please check storage space and/or security permissions.\n", "Save Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(response == JFileChooser.APPROVE_OPTION) { //otherwise
			file = jfc.getSelectedFile(); //get the selected file
			try {
				String fileName = file.getPath(); //get the path
				if(!fileName.endsWith(".txt")) { //if we don't end in .txt
					fileName += ".txt"; //add it
				}
				fw = new FileWriter(fileName); //init the filewriter
				bw = new BufferedWriter(fw); //init the buffered writer
				
				bw.write(text); //print the text
				
				bw.flush();
				bw.close();
				return true;
				
			}
			catch(IOException ex) {
				//if we get an error, inform the user
				JOptionPane.showMessageDialog(frame, "Could not save: please check storage space and/or security permissions.\n" + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}		
		}
		return false;
	}
	
	public static Object loadObject(Component frame) {
		FileInputStream fis;
		ObjectInputStream ois;
		File file;
		
		jfc.setFileFilter(jfc.getAcceptAllFileFilter());
		//open the file chooser
		int response = jfc.showOpenDialog(null);
		
		if(response == JFileChooser.CANCEL_OPTION) { //if the user cancels
			return null; //do nothing
		}
		else if(response == JFileChooser.ERROR_OPTION) { //if there's an error
			//inform the user
			JOptionPane.showMessageDialog(frame, "Could not load: Check the file is of the correct type.\n", "Load Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else if(response == JFileChooser.APPROVE_OPTION) { //otherwise
			//get the selected file
			file = jfc.getSelectedFile();
			
			try {
				fis = new FileInputStream(file); //initialise the file stream
				ois = new ObjectInputStream(fis); //initialise the object stream
				Object o = ois.readObject(); //read the object
				ois.close();
				fis.close();
				
				return o;
			}
			catch (IOException | ClassNotFoundException ex) {
				//if we get an error display it
				JOptionPane.showMessageDialog(frame, "Could not load: Check the file is of the correct type.\n"  + ex.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		return null;		
	}
}
