package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class LevelEditorSceneController {

	private DragCameraControls dragCameraControls = new DragCameraControls();

	@FXML
	private Canvas grid;

	@FXML
	private Button addBlockButton;

	@FXML
	private Button addPlatformButton;

	@FXML
	private Button addSpikeButton;

	@FXML
	private Button addGrassButtton;

	@FXML
	public void initialize() {
		drawYLines();
		drawXLines();
	}

	/**
	 * Draws vertical lines on grid
	 */
	private void drawYLines() {
		double x_start = Math.floor(dragCameraControls.getCamera().getPosition().getX() / GameConstants.cell_width)
				* GameConstants.cell_width - dragCameraControls.getCamera().getPosition().getX();
		GraphicsContext gc = grid.getGraphicsContext2D();
		gc.setLineWidth(1);
		gc.setStroke(Color.DIMGRAY);
		for (int i = 0; i < GameConstants.linesLevelEditor_Y; i++) {
			gc.moveTo(x_start, 0);
			gc.lineTo(x_start, GameConstants.floorPosition_Y);
			gc.stroke();
			x_start += GameConstants.cell_width;
		}
	}

	/**
	 * Draws horizontal lines on grid
	 */
	private void drawXLines() {
		double y_start = Math.floor(dragCameraControls.getCamera().getPosition().getY() / GameConstants.cell_height)
				* GameConstants.cell_height - dragCameraControls.getCamera().getPosition().getY();
		GraphicsContext gc = grid.getGraphicsContext2D();
		gc.setLineWidth(1);
		gc.setStroke(Color.DIMGRAY);
		for (int i = 0; i < GameConstants.linesLevelEditor_X; i++) {
			gc.moveTo(0, y_start);
			gc.lineTo(GameConstants.WIDTH, y_start);
			gc.stroke();
			y_start += GameConstants.cell_height;
		}
	}

//    public void setListener() {
//       	grid.getScene().setOnMousePressed((e) -> {
//    		if(e.getButton() == MouseButton.PRIMARY) {
//    			dragCameraControls.setPressed(e);
//    		}
//    	});
//       	grid.getScene().setOnMouseReleased((e) -> {
//       		grid.getGraphicsContext2D().clearRect(0, 0, grid.getWidth(), grid.getHeight());
//			grid.getGraphicsContext2D().beginPath();
//       		if(e.getButton() == MouseButton.PRIMARY) {
//       			dragCameraControls.updateCameraPosition(e.getScreenX(), e.getScreenY());
//    			drawYLines();
//    			drawXLines();
//       		}
//       	});
//    }

	public void setListener() {
//		grid.getScene().setOnDragDetected((e) -> {
//
//			
///			
//			if (e.getButton() == MouseButton.PRIMARY) {
//				System.out.println("dragging started");
//				dragCameraControls.setPressed(e);
////       			dragCameraControls.updateCameraPosition();
////    			drawYLines();
////    			drawXLines();
//			}
//		});
//		grid.getScene().setOnDragDropped((e) -> {
//			System.out.println("done");
//			grid.getGraphicsContext2D().clearRect(0, 0, grid.getWidth(), grid.getHeight());
//			grid.getGraphicsContext2D().beginPath();
//			dragCameraControls.updateCameraPosition(e.getScreenX(), e.getScreenY());
//			drawYLines();
//			drawXLines();
//		});

	grid.getScene().setOnMouseDragged((e) -> {
		grid.getGraphicsContext2D().clearRect(0, 0, grid.getWidth(), grid.getHeight());
		grid.getGraphicsContext2D().beginPath();
		dragCameraControls.updateCamera(new Vector2D(-e.getScreenX(), -e.getScreenY()));
		drawYLines();
		drawXLines();
	});
	}
}
