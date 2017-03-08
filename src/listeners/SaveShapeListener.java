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

public class SaveShapeListener implements ActionListener {
	
	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	public SaveShapeListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(model.getProvidedTree() == null) {
			view.displayError("Save Shape Error", "No tree provided to save");
			return;
		}
		
		FileOutputStream fos;
		ObjectOutputStream oos;
		File file;
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		jfc.showSaveDialog(null);
		file = jfc.getSelectedFile();
		
		try {
			fos = new FileOutputStream(file.getPath() + ".ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(model.getProvidedTree());
			oos.close();
			fos.close();
		}
		catch (IOException ex) {
			view.displayError("Save Shape Error", ex.getMessage());
		}
	}

}
