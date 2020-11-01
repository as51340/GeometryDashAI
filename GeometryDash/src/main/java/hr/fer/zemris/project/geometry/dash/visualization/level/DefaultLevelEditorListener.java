package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.listeners.LevelEditorListener;
import javafx.scene.image.Image;

public class DefaultLevelEditorListener implements LevelEditorListener{

	private GridAttaching gridAttaching;
	
	public DefaultLevelEditorListener(GridAttaching gridAttaching) {
		this.gridAttaching = gridAttaching;
	}
	
	@Override
	public void newActionSelected(Image newImage, String objectName) {
		gridAttaching.setCurrImgObj(newImage);
		gridAttaching.setCurrNameObj(objectName);
	}
	
	
	
	

}
