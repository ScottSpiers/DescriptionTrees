package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import listeners.AddRestrictionListener;
import listeners.AutoSearchToolListener;
import listeners.LoadPrefsListener;
import listeners.LoadShapeListener;
import listeners.OEISListener;
import listeners.PrintListener;
import listeners.ProvideShapeListener;
import listeners.SavePrefsListener;
import listeners.SaveShapeListener;
import model.DescriptionTreeModel;

/**
 * 
 * @author Scott Spiers
 *	University of Strathclyde
 *
 * Build JMenus and add them to the JMenuBar (this)
 * to be used in DescriptionTreeView
 */
public class DescriptionTreeMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = -7933268113368431375L;

	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	public DescriptionTreeMenuBar(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
		this.add(createFileMenu());
		this.add(createToolMenu());	
	}
	
	private JMenu createFileMenu() {
		JMenu file = new JMenu("File");
		JMenuItem save = new JMenuItem("Save Preferences");
		JMenuItem load = new JMenuItem("Load Preferences");
		JMenuItem saveProvided = new JMenuItem("Save Provided Shape");
		JMenuItem loadProvided = new JMenuItem("Load Provided Shape");
		JMenuItem print = new JMenuItem("Print to File");
		save.addActionListener(new SavePrefsListener(model, view));
		load.addActionListener(new LoadPrefsListener(model, view));
		saveProvided.addActionListener(new SaveShapeListener(model, view));
		loadProvided.addActionListener(new LoadShapeListener(model, view));
		print.addActionListener(new PrintListener(model, view));
		file.add(save);
		file.add(load);
		file.add(saveProvided);
		file.add(loadProvided);
		file.add(print);
		return file;
	}
	
	private JMenu createToolMenu() {
		JMenu tool = new JMenu("Tool");
		JMenuItem shape = new JMenuItem("Provide Shape");
		JMenuItem restrict = new JMenuItem("Add Restriction");
		JMenuItem oeis = new JMenuItem("OEIS Search");
		JMenuItem oeisAuto = new JMenuItem("OEIS Auto Search");
		restrict.addActionListener(new AddRestrictionListener(model, view));
		shape.addActionListener(new ProvideShapeListener(model, view));
		oeis.addActionListener(new OEISListener(model, view));
		oeisAuto.addActionListener(new AutoSearchToolListener(model));
		tool.add(shape);
		tool.add(restrict);
		tool.add(oeis);
		tool.add(oeisAuto);
		return tool;		
	}
}
