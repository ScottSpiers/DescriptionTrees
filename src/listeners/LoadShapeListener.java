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

public class LoadShapeListener implements ActionListener {
	
	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	public LoadShapeListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		FileInputStream fis;
		ObjectInputStream ois;
		File file;
		
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		jfc.showOpenDialog(null);
		file = jfc.getSelectedFile();
		
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			Tree t = (Tree) ois.readObject();
			ois.close();
			fis.close();
			
			model.setProvidedTree(t);
		}
		catch (IOException | ClassNotFoundException ex) {
			view.displayError("Load Shape Error", ex.getMessage());
		}
		
	}

}
