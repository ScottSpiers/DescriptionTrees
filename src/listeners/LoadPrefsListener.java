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

public class LoadPrefsListener implements ActionListener {

	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	public LoadPrefsListener(DescriptionTreeModel model, DescriptionTreeView view) {
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
			
			List<Restrictor> restrictors = (List<Restrictor>) ois.readObject();
			ois.close();
			fis.close();
			
			for(Restrictor r : restrictors) {
				model.addRestrictor(r);
			}
			return;
			
		}
		catch (IOException | ClassNotFoundException ex) {
			view.displayError("Load Restrictors Error", ex.getMessage());
			return;
		}
	}
}
