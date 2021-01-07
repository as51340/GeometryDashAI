package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.EnumSet;
import java.util.Random;

import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;

/**
 * Implementation of tree in java
 * @author Andi Škrgat
 *
 */
public class Tree {
	
	/**
	 * Root of the tree
	 */
	private TreeNode root = null;
	
	/**
	 * Size of tree
	 */
	private int size = -1;
	
	/**
	 * Every tree stores reference to the player
	 */
	private Player player;
	
	/**
	 * Simple constructor for now
	 */
//	public Tree() {		
//	}
	
	// Možda ovo ili napisat još i neku metodu koja stvara stablo s random čvorovima do zadane dubine?
//	/**
//	 * Creates tree with random root
//	 */
//	public Tree() {
//		EnumSet<ActionType> types = EnumSet.allOf(ActionType.class);
//		ActionType[] actionTypes = (ActionType[]) types.toArray();
//		Random r = new Random();
//		int index = r.nextInt(actionTypes.length);
//		
//		this.root = new TreeNode(new Action(actionTypes[index]));
//		size++;
//	}
	
	/**
	 * Creates tree with its root
	 * @param root root of the tree
	 */
	public Tree(TreeNode root) {
		this.root = root;
		calculateSize();
	}
	
	
	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return this.size;
	}
	
	private void calculateSize() {
		this.size =  root.calculateSize(1) + 1; //for root
	}
	
	/**
	 * sets size
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

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
	 * Performs deep copy on the {@linkplain Tree}
	 * @return copied tree
	 */
	public Tree copy() {
		Tree tree = new Tree(this.root.copy());
		tree.setSize(size);
		return tree;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tree other = (Tree) obj;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}
	
}
