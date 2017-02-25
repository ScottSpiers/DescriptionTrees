package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CH.ifa.draw.application.DrawApplication;
import model.DescriptionTreeModel;
import view.TreeCreatorView;

public class ProvideShapeListener implements ActionListener {
	
	DescriptionTreeModel model;
	
	public ProvideShapeListener(DescriptionTreeModel model) {
		this.model = model;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DrawApplication shapeProvider = new TreeCreatorView(model);
		shapeProvider.open();
	}
	
}
