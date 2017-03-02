package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import listeners.AddRestrictionListener;
import listeners.OEISListener;
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
		this.add(createToolMenu());
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
	
	private JMenu createToolMenu() {
		JMenu tool = new JMenu("Tool");
		JMenuItem shape = new JMenuItem("Provide Shape");
		JMenuItem restrict = new JMenuItem("Add Restriction");
		JMenuItem oeis = new JMenuItem("OEIS Search");
		restrict.addActionListener(new AddRestrictionListener(model, view));
		shape.addActionListener(new ProvideShapeListener(model, view));
		oeis.addActionListener(new OEISListener(model, view));
		tool.add(shape);
		tool.add(restrict);
		tool.add(oeis);
		return tool;		
	}
	
	private JMenu createHelpMenu() {
		JMenu help = new JMenu("Help");
		JMenuItem helpRestrict = new JMenuItem("Help: Add Restriction");
		help.add(helpRestrict);
		return help;
	}
}
