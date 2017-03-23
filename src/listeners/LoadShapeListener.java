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
		Object tree = FileManager.loadObject(view.getFrame());
		
		if(tree != null) {
			try {
				Tree t = (Tree) tree;			
				model.setProvidedTree(t);
			}
			catch (ClassCastException ex) {
				view.displayError("Load Error", "Could not load: Check the file is of the correct type.\n"  + ex.getMessage());
			}			
		}				
	}
}
