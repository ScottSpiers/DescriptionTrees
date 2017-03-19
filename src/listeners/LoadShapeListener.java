package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import model.DescriptionTreeModel;
import model.scala.Tree;
import view.DescriptionTreeView;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 *
 * Listener for loading a provided shape
 */
public class LoadShapeListener implements ActionListener {
	
	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public LoadShapeListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		FileInputStream fis;
		ObjectInputStream ois;
		File file;
		//create a file chooser
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		//open the file chooser
		jfc.showOpenDialog(null);
		//get the selected file
		file = jfc.getSelectedFile();
		
		try {
			fis = new FileInputStream(file); //initialise the file stream
			ois = new ObjectInputStream(fis); //initialise the object stream
			Tree t = (Tree) ois.readObject(); //read the object and cast
			ois.close();
			fis.close();
			
			model.setProvidedTree(t); //set the tree in the model
		}
		catch (IOException | ClassNotFoundException ex) {
			//if we get an error display it
			view.displayError("Load Shape Error", ex.getMessage());
		}
		
	}

}
