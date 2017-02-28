package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import listeners.AddRestrictionListener;
import listeners.ProvideShapeListener;
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
		this.add(createEditMenu());
		this.add(createHelpMenu());		
	}
	
	private JMenu createFileMenu() {
		JMenu file = new JMenu("File");
		JMenuItem save = new JMenuItem("Save As Prefernce");
		JMenuItem load = new JMenuItem("Load Prefernces");
		file.add(save);
		file.add(load);
		return file;
	}
	
	private JMenu createEditMenu() {
		JMenu edit = new JMenu("Edit");
		JMenuItem shape = new JMenuItem("Provide Shape");
		JMenuItem restrict = new JMenuItem("Add Restriction");
		restrict.addActionListener(new AddRestrictionListener(model));
		shape.addActionListener(new ProvideShapeListener(model, view));
		edit.add(shape);
		edit.add(restrict);
		return edit;		
	}
	
	private JMenu createHelpMenu() {
		JMenu help = new JMenu("Help");
		JMenuItem helpRestrict = new JMenuItem("Help: Add Restriction");
		help.add(helpRestrict);
		return help;
	}
}
