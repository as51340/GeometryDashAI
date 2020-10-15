import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        
    	Parent root = null;
		try {
			root = FXMLLoader.<Parent>load(getClass().getResource(("Background.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Scene scene = new Scene(root, 600, 400);
    	
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}