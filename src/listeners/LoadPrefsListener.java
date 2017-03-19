package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javax.swing.JFileChooser;

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
 * Listener to load restriction preferences
 */
public class LoadPrefsListener implements ActionListener {

	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public LoadPrefsListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		FileInputStream fis;
		ObjectInputStream ois;
		File file;
		//create a file chooser
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		//open it
		jfc.showOpenDialog(null);
		//get the selected file
		file = jfc.getSelectedFile();
		try {
			fis = new FileInputStream(file); //initialise the file stream
			ois = new ObjectInputStream(fis); //initialise the object stream
			
			List<Restrictor> restrictors = (List<Restrictor>) ois.readObject(); //read the object and cast
			ois.close();
			fis.close();
			
			//add the restrictors to the model
			for(Restrictor r : restrictors) {
				model.addRestrictor(r);
			}
			return;
			
		}
		catch (IOException | ClassNotFoundException ex) {
			//if there was an error display it
			view.displayError("Load Restrictors Error", ex.getMessage());
			return;
		}
	}
}
