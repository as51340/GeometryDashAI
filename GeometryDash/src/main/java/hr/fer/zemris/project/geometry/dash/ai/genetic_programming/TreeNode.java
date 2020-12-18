package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Node in tree. It keeps data and reference to all nodes it's pointing to
 * 
 * @author Andi Škrgat
 *
 */
public class TreeNode {

	/**
	 * Hashing constant
	 */
	private static final long HASHING_CONSTANT = 911382323;

	/**
	 * In intern node we have actions, if is null that means that this node is leaf
	 */
	private Action action = null;

	/**
	 * Value in leaf, input
	 */
	private double value;

	/**
	 * It stores children in {@linkplain ArrayList}.
	 */
	private List<TreeNode> children;

	/**
	 * Constructor for intern node
	 * 
	 * @param actio
	 */
	public TreeNode(Action action) {
		this.action = action;
		children = new ArrayList<TreeNode>();
	}

	/**
	 * Constructor for lead
	 */
	public TreeNode(double value) {
		this.value = value;
		children = new ArrayList<TreeNode>();
	}

	/**
	 * For deep copy of node
	 * 
	 * @param action node action
	 * @param value  double value
	 */
	public TreeNode(Action action, double value) {
		this.action = action;
		this.value = value;
	}

	/**
	 * Adds child
	 * 
	 * @param child child
	 */
	public void addChild(TreeNode child) {
		children.add(child);
	}

	@Override
	public String toString() {
		if (action == null) {
			return Double.toString(value);
		} else {
			return action.toString() + " " + Double.toString(value);
		}
	}

	/**
	 * @return children
	 */
	public List<TreeNode> getChildren() {
		return this.children;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TreeNode)) {
			return false;
		}
		TreeNode node = (TreeNode) obj;
		if (!(node.getAction() == this.getAction()) || !(node.getValue() == this.getValue())) {
			return false;
		}
		int size = node.children.size();
		if (size != this.children.size()) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (!node.children.get(i).equals(children.get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int dataHash = 0;
		dataHash = (int) this.value;
		if (action != null) {
			dataHash += action.hashCode();
		}
		int size = children.size();
		for (int i = 0; i < size; i++) {
			dataHash += children.get(i).hashCode();
		}
		dataHash %= HASHING_CONSTANT;
		return dataHash;
	}

	/**
	 * Deep copy of the node
	 * 
	 * @param node node
	 * @return copied node
	 */
	public TreeNode copy() {
		TreeNode copied = new TreeNode(action, value);
		for (TreeNode child : this.getChildren()) {
			copied.addChild(child.copy());
		}
		return copied;
	}
}
