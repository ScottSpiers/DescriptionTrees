package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DescriptionTreeModel;
import model.scala.Tree;
import tools.FileManager;
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
		
		Tree t = model.getProvidedTree();
		
		boolean saved = FileManager.saveObject(view.getFrame(), t, ".tree");
		if(saved) {
			view.displayMessage("File Saved", "File Saved");
		}
	}

}
