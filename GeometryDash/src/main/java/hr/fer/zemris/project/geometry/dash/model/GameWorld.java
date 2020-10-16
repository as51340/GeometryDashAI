package hr.fer.zemris.project.geometry.dash.model;

import java.util.List;

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
	 * @param obstacles ili ce ih primit u konstruktoru ili ce ih nekako citat iz level managera
	 */
	public GameWorld(List<Obstacle> obstacles) {
		charactersSelector = new CharactersSelector();
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
