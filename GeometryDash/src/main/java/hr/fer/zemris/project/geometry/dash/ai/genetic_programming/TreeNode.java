package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Node in tree. It keeps data and reference to all nodes it's pointing to
 * @author Andi Škrgat
 *
 */
public class TreeNode {
	
	/**
	 * Hashing constant
	 */
	private static final long HASHING_CONSTANT = 911382323;

	/**
	 * So far we have string representation of data, maybe we will change something
	 */
	private String data = null;
	
	/**
	 * It stores children in {@linkplain ArrayList}.
	 */
	private List<TreeNode> children;
	
	/**
	 * Stores node's data
	 * @param data node's data
	 */
	public TreeNode(String data) {
		this.data = data;
		children = new ArrayList<TreeNode>();
	}
	
	/**
	 * So far just for compiling
	 */
	public TreeNode() {
		children = new ArrayList<TreeNode>();
	}
	
	/**
	 * Adds child
	 * @param child child
	 */
	public void addChild(TreeNode child) {
		children.add(child);
	}
	
	@Override
	public String toString() {
		return this.data;
	}
	
	/**
	 * @return children
	 */
	public List<TreeNode> getChildren() {
		return this.children;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TreeNode)) {
			return false;
		}
		TreeNode node = (TreeNode) obj;
		if(!node.data.equals(this.data)) {
			return false;
		}
		int size = node.children.size(); 
		if(size != this.children.size()) {
			return false;
		}
		for(int i = 0; i < size; i++) {
			if(!node.children.get(i).equals(children.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int dataHash = data.hashCode();
		int size = children.size();
		for(int i = 0; i < size; i++) {
			dataHash += children.get(i).hashCode();
		}
		dataHash %= HASHING_CONSTANT;
		return dataHash;
	}
	
	/**
	 * Deep copy of the node
	 * @param node node 
	 * @return copied node
	 */
	public TreeNode copy() {
		TreeNode copied = new TreeNode(this.data);
		for(TreeNode child: this.getChildren()) {
			copied.addChild(child);
		}
		return copied;
	}
}
