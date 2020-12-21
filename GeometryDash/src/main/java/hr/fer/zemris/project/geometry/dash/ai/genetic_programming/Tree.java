package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.EnumSet;
import java.util.Random;

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
		return root.getSize() + 1; //for root
	}

	/**
	 * Performs deep copy on the {@linkplain Tree}
	 * @return copied tree
	 */
	public Tree copy() {
		Tree tree = new Tree(this.root.copy());
		return tree;
	}
	
	/**
	 * Prints tree
	 */
	public void printTree() {
		//TreeUtil.dfsOnTree(root, null, 0);
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


	@Override
	public String toString() {
		return "Tree [root=" + root + "]";
	}
	
	
	
	
	
}
