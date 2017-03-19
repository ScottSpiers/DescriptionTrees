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
 * Listener for saving a provided shape
 */
public class SaveShapeListener implements ActionListener {
	
	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public SaveShapeListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(model.getProvidedTree() == null) { //if there is a no shape
			//inform the user
			view.displayError("Save Shape Error", "No tree provided to save");
			return;
		}
		
		FileOutputStream fos;
		ObjectOutputStream oos;
		File file;
		// create a file chooser
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		//open it
		jfc.showSaveDialog(null);
		//get the selected file
		file = jfc.getSelectedFile();
		
		try {
			fos = new FileOutputStream(file.getPath() + ".ser"); //init the filestream
			oos = new ObjectOutputStream(fos); // init the object Stream
			oos.writeObject(model.getProvidedTree()); //write the tree
			oos.close();
			fos.close();
		}
		catch (IOException ex) {
			//if there was an error, inform the user
			view.displayError("Save Shape Error", ex.getMessage());
		}
	}

}
