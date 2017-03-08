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

public class SavePrefsListener implements ActionListener {
	
	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	public SavePrefsListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		File file;
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		
		jfc.showSaveDialog(null);
		try {
			file = jfc.getSelectedFile();
			FileOutputStream fos = new FileOutputStream(file.getPath() + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(model.getRestrictors());
			oos.close();
			fos.close();
		}
		catch(IOException ex) {
			view.displayError("Save restrictions error", "There was an issue when saving preferences: " + ex.getMessage());
			return;
		}
		
	}

}
