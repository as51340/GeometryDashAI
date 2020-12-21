package hr.fer.zemris.project.geometry.dash.ai;

import java.util.Comparator;

import hr.fer.zemris.project.geometry.dash.model.GameObject;

/**
 * Remembers all important constants for AI
 * @author Andi Škrgat
 *
 */
public class AIConstants {
	
	/**
	 * Maximum number of generations for genetic algorithm
	 */
	public static int maxGenerations = 500;
	
	/**
	 * Number of action in tree
	 */
	public static int numOfActions = 25;
	
	/**
	 * Comparator for game objects, it sorts first by x then by y
	 */
	public static final Comparator<GameObject> obstaclesLevelComparator = new Comparator<GameObject>() {

		@Override
		public int compare(GameObject o1, GameObject o2) {
			if(Math.abs(o1.getInitialPosition().getX() - o2.getInitialPosition().getX()) < 1e-7) {
				return Double.compare(o1.getInitialPosition().getY(), o2.getInitialPosition().getY());
			}
			return Double.compare(o1.getInitialPosition().getX(), o2.getInitialPosition().getX());
		}		
	};
	
	/**
	 * Default number of closest obstacles AI is seeing
	 */
	public static final int numOfClosestObstacles = 4;

}
