package hr.fer.zemris.project.geometry.dash.visualization;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class GameSceneController extends MainOptionsController {
	
	@FXML
	private ImageView background1;

	@FXML
	private ImageView background2;

	@FXML
	private ImageView background3;
	
    @FXML
    private ImageView floor1;

    @FXML
    private ImageView floor2;

    @FXML
    private ImageView floor3;
	
	@FXML
	private Rectangle overlay;

	@FXML
	private Canvas canvas;
	
	@FXML
    private StackPane rootPane;
	
	@FXML
	public void initialize() {
		Utils.animateBackground(overlay, background1, background2, background3);
		Utils.animateFloor(overlay, floor1, floor2, floor3);
	}
	
	public void init() {
		GameEngine.getInstance().getGameWorld().getRenderer().setGraphicsContext(canvas.getGraphicsContext2D());
	}
	
}
