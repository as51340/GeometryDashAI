package hr.fer.zemris.project.geometry.dash.model;

import java.util.List;

import hr.fer.zemris.project.geometry.dash.model.drawables.Point;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Obstacle;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;

/**
 * Manages with all current objects on scene, imports level 
 * @author Andi Škrgat
 *
 */
public class GameWorld {
	
	/**
	 * Reference to the all existing obstacles on the scene
	 */
	private List<Obstacle> obstacles; 
	
	/**
	 * Reference to the {@linkplain LevelManager}
	 */
	private LevelManager levelManager;
	
	/**
	 * {@linkplain CharactersSelector}
	 */
	private CharactersSelector charactersSelector;
	
	/**
	 * Reference to player
	 */
	private Player player;
	
	/**
	 * @param obstacles ili ce ih primit u konstruktoru ili ce ih nekako citat iz level managera
	 */
	public GameWorld(List<Obstacle> obstacles) {
		charactersSelector = new CharactersSelector();
		player = new Player(new Point(200, 200));
		player.setCharacter(charactersSelector.getSelectedCharacter());
		this.obstacles = obstacles;
	}
	
	/**
	 * Calculates new positions for this obstacles
	 */
	public void CalculatePositions() {
		for(Obstacle obstacle: obstacles) {
			obstacle.calculateNewPosition();
		}
	}
	
	/**
	 * Updates GUI
	 */
	public void UpdateGUI() {
		for(Obstacle obstacle: obstacles) {
			obstacle.drawTheirPosition();
		}
	}
	
	/**
	 * Checks if some object is out of the scene and deletes him if neccessary
	 */
	public void CleanObjectsFromScene() {
		
	}
	

}
