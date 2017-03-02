package listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import model.DescriptionTreeModel;
import view.DescriptionTreeView;

public class OEISListener implements ActionListener {

	
	DescriptionTreeModel model;
	DescriptionTreeView view;
	
	public OEISListener(DescriptionTreeModel model, DescriptionTreeView view) {
		this.model = model;
		this.view  = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Desktop desktop = Desktop.getDesktop();
		if(desktop.isDesktopSupported()) {
			try {
				String uri = "www.oeis.org/search?q=";
				uri += view.getSequence();
				desktop.browse(new URI(uri));
			}
			catch(URISyntaxException ex) {
				view.displayError("URI Error", ex.getMessage());
			}
			catch(IOException ex) {
				view.displayError("IO Error", ex.getMessage());
			}
		}
		
	}

}
