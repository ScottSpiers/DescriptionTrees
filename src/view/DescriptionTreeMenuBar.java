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
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Build JMenus and add them to the JMenuBar (this)
 * to be used in DescriptionTreeView
 */
public class DescriptionTreeMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = -7933268113368431375L;

	private DescriptionTreeModel model;
	private DescriptionTreeView view;
	
	/**
	 *  Adds menus to this MenuBar
	 * @param model The DescriptionTreeModel being used
	 * @param view The DescriptionTreeView being used
	 */
	public DescriptionTreeMenuBar(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view = view;
		this.add(createFileMenu());
		this.add(createToolMenu());	
	}
	
	/**
	 * Creates the file menu
	 * @return the file menu complete with menu items
	 */
	private JMenu createFileMenu() {
		//create menu and items
		JMenu file = new JMenu("File");
		JMenuItem save = new JMenuItem("Save Restrictions");
		JMenuItem load = new JMenuItem("Load Restrictions");
		JMenuItem saveProvided = new JMenuItem("Save Provided Shape");
		JMenuItem loadProvided = new JMenuItem("Load Provided Shape");
		JMenuItem print = new JMenuItem("Print to File");
		
		//add listeners
		save.addActionListener(new SavePrefsListener(model, view));
		load.addActionListener(new LoadPrefsListener(model, view));
		saveProvided.addActionListener(new SaveShapeListener(model, view));
		loadProvided.addActionListener(new LoadShapeListener(model, view));
		print.addActionListener(new PrintListener(model, view));
		
		//add items
		file.add(save);
		file.add(load);
		file.add(saveProvided);
		file.add(loadProvided);
		file.add(print);
		return file;
	}
	
	/**
	 * Creates the tool menu
	 * @return the tool menu complete with menu items
	 */
	private JMenu createToolMenu() {
		//create menu and items
		JMenu tool = new JMenu("Tool");
		JMenuItem shape = new JMenuItem("Provide Shape");
		JMenuItem restrict = new JMenuItem("Add Restriction");
		JMenuItem oeis = new JMenuItem("OEIS Search");
		JMenuItem oeisAuto = new JMenuItem("OEIS Auto Search");
		
		//add listeners
		restrict.addActionListener(new AddRestrictionListener(model, view));
		shape.addActionListener(new ProvideShapeListener(model, view));
		oeis.addActionListener(new OEISListener(model, view));
		oeisAuto.addActionListener(new AutoSearchToolListener(model));
		
		//add items
		tool.add(shape);
		tool.add(restrict);
		tool.add(oeis);
		tool.add(oeisAuto);
		return tool;		
	}
}
