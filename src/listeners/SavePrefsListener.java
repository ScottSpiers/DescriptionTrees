package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import model.DescriptionTreeModel;
import view.DescriptionTreeView;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Listener to save restriction preferences
 */
public class SavePrefsListener implements ActionListener {
	
	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public SavePrefsListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		File file;
		//create a file chooser
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		
		jfc.showSaveDialog(null); //open the file chooser
		try {
			file = jfc.getSelectedFile(); //get the selected file
			FileOutputStream fos = new FileOutputStream(file.getPath() + ".ser"); //init the file stream
			ObjectOutputStream oos = new ObjectOutputStream(fos); //init the object stream
			
			oos.writeObject(model.getRestrictors()); //write the restrictor list
			oos.close();
			fos.close();
		}
		catch(IOException ex) {
			// If there was an error, inform the user
			view.displayError("Save restrictions error", "There was an issue when saving preferences: " + ex.getMessage());
			return;
		}
		
	}

}
