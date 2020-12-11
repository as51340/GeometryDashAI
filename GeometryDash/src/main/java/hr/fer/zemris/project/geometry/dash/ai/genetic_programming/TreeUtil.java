package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import org.codehaus.plexus.util.StringUtils;

/**
 * Class which we will use for performing genetic programming actions: crossover, replication and mutation
 * @author Andi Škrgat
 *
 */
public class TreeUtil {
	
	/**
	 * Replicates existing tree
	 * @param tree existing tree
	 * @return created tree
	 */
	public static Tree replicateTree(Tree tree) {
		return tree.copy();
	}
	
	/**
	 * Perform mutation on tree
	 * @param tree existing tree
	 * @return tree after performed mutation
	 */
	public static Tree mutationOnTree(Tree tree) {
		return null;
	}
	
	/**
	 * Crossovers two trees and returns their newly created child
	 * @param tree1 first tree
	 * @param tree2 second tree
	 * @return newly created tree
	 */
	public static Tree crossover(Tree tree1, Tree tree2) {
		return null;
	}
	
	/**
	 * Performs dfs on tree
	 * @param root
	 */
	public static void dfsOnTree(TreeNode root, TreeNode parent, int level) {
		// process this node
		// for example print out
		System.out.print(StringUtils.repeat(" ", level) + root + "\n");
		for(TreeNode child: root.getChildren()) {
			if(!child.equals(parent)) {
				dfsOnTree(child, root, level+1);
			}
		}
		
	}
	
	public static void main(String[] args) {
		Tree tree = new Tree();
		TreeNode node1 = new TreeNode("Node1");
		tree.setRoot(node1);
		node1.addChild(new TreeNode("Node2"));
		node1.addChild(new TreeNode("Node3"));
		node1.addChild(new TreeNode("Node4"));
		node1.addChild(new TreeNode("Node5"));
		node1.getChildren().get(3).addChild(new TreeNode("Node6"));
		tree.printTree();
	}

}
