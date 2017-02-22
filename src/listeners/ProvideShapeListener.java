package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CH.ifa.draw.application.DrawApplication;
import view.TreeCreatorView;

public class ProvideShapeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DrawApplication shapeProvider = new TreeCreatorView();
		shapeProvider.open();		
	}
	
}
