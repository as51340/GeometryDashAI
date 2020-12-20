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
	 * Number of nodes in tree
	 */
	private int size = 0;
	
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
	/**
	 * Creates tree with random root
	 */
	public Tree() {
		EnumSet<ActionType> types = EnumSet.allOf(ActionType.class);
		ActionType[] actionTypes = (ActionType[]) types.toArray();
		Random r = new Random();
		int index = r.nextInt(actionTypes.length);
		
		this.root = new TreeNode(new Action(actionTypes[index]));
		size++;
	}
	
	/**
	 * Creates tree with its root
	 * @param root root of the tree
	 */
	public Tree(TreeNode root) {
		this.root = root;
		size++;
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
		return size;
	}

	/**
	 * Performs deep copy on the {@linkplain Tree}
	 * @return copied tree
	 */
	public Tree copy() {
		Tree tree = new Tree(this.root.copy());
		tree.size = this.size;
		return tree;
	}
	
	/**
	 * Prints tree
	 */
	public void printTree() {
		//TreeUtil.dfsOnTree(root, null, 0);
	}
}
