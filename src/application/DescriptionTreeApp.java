package application;

import tools.FileManager;
import view.DescriptionTreeView;

/**
 * 
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 *
 * Main Runner for the application
 */
public class DescriptionTreeApp {
	
	public static void main(String[] args) {
		new FileManager();
		new DescriptionTreeView().display();
	}
}
