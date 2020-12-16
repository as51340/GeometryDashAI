package hr.fer.zemris.project.geometry.dash.model.drawables.player;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.listeners.PlayerListener;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.PlayerDeathSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * A {@link PlayerListener} implementation
 */
public class WorldPlayerListener implements PlayerListener {

	/**
	 * Reference to the player
	 */
	private Player player;
	
	public WorldPlayerListener(Player player) {
		this.player = player;
	}
	
    /**
     * Player is in the air
     */
    @Override
    public void playerIsInAir() {
        // TODO otkriti kako ovo iskoristiti
    }

    /**
     * Player is on the platform
     */
    @Override
    public void playerIsOnPlatform() {
        // TODO otkriti kako ovo iskoristiti
    }

    /**
     * Player is on the floor
     */
    @Override
    public void playerIsOnFloor() {
        // TODO otkriti kako ovo iskoristiti
    }


    /**
     * Player is dead
     *
     * @throws IOException
     */
    @Override
    public void playerIsDead(double time) throws IOException {
       
    }

    @Override
    public void playerJumped() {
        player.jump();
    }

    @Override
    public void playerCreated(PlayingMode playingMode) {
        player.setPlayingMode(playingMode);
    }

}
