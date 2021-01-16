package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Obstacle;
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
	 * Index for changing inputs
	 */
	private int cngIndex = 0;
	
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
	
	public void changeInputs(List<Obstacle> inputs, Player p) {
		this.cngIndex = 0;
		List<Double> inp = new ArrayList<Double>();
		for(Obstacle ob: inputs) {
			inp.add(ob.getCurrentPosition().getX());
			inp.add(ob.getCurrentPosition().getY());
			inp.add(Obstacle.decodeObstacleType(ob));
		}
		inp.add(p.getCurrentPosition().getY());
		changeInputs(this.root, inp);
	}
	
	private void changeInputs(TreeNode root, List<Double> inputs) {
		if(this.cngIndex == inputs.size()) {
			return;
		}
		if(root.getAction() == null) {
			root.setValue(inputs.get(this.cngIndex++));
		}
		for(TreeNode child: root.getChildren()) {
			changeInputs(child, inputs);
		}
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
