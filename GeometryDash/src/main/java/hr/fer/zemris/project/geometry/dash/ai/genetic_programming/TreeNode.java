package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Node in tree. It keeps data and reference to all nodes it's pointing to
 * 
 * @author Andi Škrgat
 *
 */
public class TreeNode {

	/**
	 * In intern node we have actions, if is null that means that this node is leaf
	 */
	@Expose
	private Action action = null;

	/**
	 * Value in leaf, input
	 */
	@Expose
	private double value;

	/**
	 * It stores children in {@linkplain ArrayList}.
	 */
	@Expose
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	/**
	 * Depth level of node
	 */
	private int depth = 0;
	
	/**
	 * Size of subtree
	 */
	private int size = -1;

	/**
	 * Constructor for intern node
	 * 
	 * @param actio
	 */
	public TreeNode(Action action) {
		this.action = action;
	}

	/**
	 * Constructor for leaf
	 */
	public TreeNode(double value) {
		this.value = value;
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
			return action.toString();
		}
	}

	/**
	 * @return children
	 */
	public List<TreeNode> getChildren() {
		return this.children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<TreeNode> children) {
		this.children = children;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		TreeNode other = (TreeNode) obj;
		if (action == null) {
			if (other.action != null) {;
				return false;
			}
		} else if (!action.equals(other.action)) {
			return false;
		}
		if (children == null) {
			if (other.children != null) {
				return false;
			}
		} else if (!children.equals(other.children)) {			
			return false;
		}
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		return true;
	}

	/**
	 * Deep copy of the node
	 * 
	 * @param node node
	 * @return copied node
	 */
	public TreeNode copy() {
		TreeNode copied = new TreeNode(action, value);
		copied.setDepth(getDepth());
		if(size == -1) {
			throw new IllegalArgumentException("Size is -1 something is wrong");
		}
		copied.setSize(size);
		for (TreeNode child : this.getChildren()) {
			copied.addChild(child.copy());
		}
		return copied;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Calculates size only during tree initialization
	 * @param depth
	 * @return
	 */
	public int calculateSize(int depth) {
		this.depth = depth;
		size = 0;
		size += children.size();
		for (TreeNode child : children) {
			size += child.calculateSize(depth+1);
		}
		return size;
	}
	
	/**
	 * Sets size
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	

}
