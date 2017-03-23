package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.DescriptionTreeModel;
import model.Restrictor;
import tools.FileManager;
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
		Object restrs = FileManager.loadObject(view.getFrame());
		
		if(restrs != null) {
			try {
				List<Restrictor> restrictors = (List<Restrictor>) restrs;
				for(Restrictor r : restrictors) {
					model.addRestrictor(r);				
				}			
			}
			catch(ClassCastException ex) {
				view.displayError("Load Error", "Could not load: Check the file is of the correct type.\n"  + ex.getMessage());
			}			
		}
	}
}
