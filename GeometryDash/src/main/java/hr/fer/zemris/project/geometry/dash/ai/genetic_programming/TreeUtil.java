package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.List;

import org.codehaus.plexus.util.StringUtils;

/**
 * Class which we will use for performing genetic programming actions:
 * crossover, replication and mutation
 * 
 * @author Andi Škrgat
 *
 */
public class TreeUtil {

	/**
	 * Replicates existing tree
	 * 
	 * @param tree existing tree
	 * @return created tree
	 */
	public static Tree replicateTree(Tree tree) {
		return tree.copy();
	}

	/**
	 * Performs mutation on tree. Two types of mutations are possible: in the first kind a
	 * function can only replace a function or a terminal can only replace a terminal.
	 * In the second kind an entire subtree can replace another subtree.
	 * 
	 * @param tree existing tree
	 * @return tree after performed mutation
	 */
	public static Tree mutationOnTree(Tree tree) {
		
/*		List<TreeNode> level1 = tree.getRoot().getChildren();
		for(TreeNode node : level1) {
			for(TreeNode n : node.getChildren()) {					
				if(node.getAction() == null) {   // npr. za prve listove djece korijena radimo prvu vrstu mutacije
					node.setValue(...);
					break;
				}
			}
		}
*/
		return null;
	}

	/**
	 * Crossovers two trees and returns their newly created child
	 * 
	 * @param tree1 first tree
	 * @param tree2 second tree
	 * @return newly created tree
	 */
	public static Tree crossover(Tree tree1, Tree tree2) {
		return null;
	}

	/**
	 * Performs dfs on tree
	 * 
	 * @param root
	 */
	public static double dfsOnTree(TreeNode root, int level) {
//		System.out.print(StringUtils.repeat(" ", level) + root + "\n");
		if (root.getAction() == null) { // it is a leaf
			return root.getValue();
		}
		if(root.getAction().isUnary()) {
			if(root.getChildren().size() != 1) {
				System.out.println("Imamo problem, velicina nije 1!");
			}
			double x = dfsOnTree(root.getChildren().get(0), level +1);
		} else if(root.getAction().isBinary()) {
			
		} else if(root.getAction().isBranchingFun()) {
			
		} else {
			System.out.println("Niente dobro");
		}
		for (TreeNode child : root.getChildren()) {
			
			dfsOnTree(child, level + 1);
		}

	}

	public static void main(String[] args) {
		Tree tree = new Tree();
		TreeNode node1 = new TreeNode(ActionType.MINUS);
		tree.setRoot(node1);
		node1.addChild(new TreeNode(2));
		TreeNode node2 = new TreeNode(ActionType.PLUS);
		node2.addChild(new TreeNode(5));
		node2.addChild(new TreeNode(10));

		tree.printTree();
	}

}
