package hr.fer.zemris.project.geometry.dash.model;

import java.util.Comparator;

import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;

/**
 * GameObject comparator 
 * @author Andi Škrgat
 *
 */
public class GameObjectComparator implements Comparator<GameObject>{

	/**
	 * Reference to the player
	 */
	private Player player;
	
	public GameObjectComparator(Player player) {
		this.player = player;
	}
	
	@Override
	public int compare(GameObject o1, GameObject o2) {
		return Double.compare(Math.sqrt(Math.pow(player.getCurrentPosition().getX() - o1.getCurrentPosition().getX(), 2)),
				Math.sqrt(Math.pow(player.getCurrentPosition().getY() - o1.getCurrentPosition().getY(), 2)));
	}

}
