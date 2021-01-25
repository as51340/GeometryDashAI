package hr.fer.zemris.project.geometry.dash.ai;

import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;

/**
 * Parametrized class using Java generics
 * @author Andi Škrgat
 *
 */
public class AiPair<T> {
	
	/**
	 * Player ref
	 */
	private Player player;
	
	/**
	 * Ai object 
	 */
	private T aiObject;
	
	public AiPair(Player player, T aiObject) {
		this.player = player;
		this.aiObject = aiObject;
	}
	
	public AiPair() {}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the aiObject
	 */
	public T getAiObject() {
		return aiObject;
	}

	/**
	 * @param aiObject the aiObject to set
	 */
	public void setAiObject(T aiObject) {
		this.aiObject = aiObject;
	}
	
	
	
	
	
}
