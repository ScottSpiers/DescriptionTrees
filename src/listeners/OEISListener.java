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
	 * 
	 * Mac OS specific section source: http://alvinalexander.com/java/mac-java-open-url-browser-osascript
	 */
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		String os = System.getProperty("os.name");
		//get the uri
		String uri = "http://www.oeis.org/search?q="; 
		
		if(os.startsWith("Mac OS")) {
			Runtime rt = Runtime.getRuntime();
			String[] args = {"osascript", "-e", "open " + uri + view.getSequence()};
			try {
				Process p = rt.exec("open " + uri + view.getSequence());
			}
			catch (IOException ex) {
				view.displayError("Search Error", ex.getMessage());
			}
		}
		else {
			Desktop desktop = Desktop.getDesktop();
			if(Desktop.isDesktopSupported()) { //if supported
				try {
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

}
