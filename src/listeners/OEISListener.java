package listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import model.DescriptionTreeModel;
import view.DescriptionTreeView;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 *
 * Listener for running a web search for an OEIS search
 */
public class OEISListener implements ActionListener {

	
	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	/**
	 * 
	 * @param model The DescriptionTreeModel being used
	 * @param view the DescriptionTreeView being used
	 */
	public OEISListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view  = view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Desktop desktop = Desktop.getDesktop();
		if(Desktop.isDesktopSupported()) { //if supported
			try {
				//get the uri
				String uri = "www.oeis.org/search?q="; 
				uri += view.getSequence();
				desktop.browse(new URI(uri)); //initiate the search
			}
			catch(URISyntaxException ex) {
				view.displayError("URI Error", ex.getMessage());
			}
			catch(IOException ex) {
				view.displayError("IO Error", ex.getMessage());
			}
		}
		else { //otherwise
			//display that this is not supported
			view.displayError("Browser Error", "Unfortunately this tool is incompatible with this platform");
		}
		
	}

}
