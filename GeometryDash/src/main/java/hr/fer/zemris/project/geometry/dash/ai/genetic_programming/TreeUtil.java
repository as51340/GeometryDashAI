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
	 * Perform mutation on tree
	 * 
	 * @param tree existing tree
	 * @return tree after performed mutation
	 */
	public static Tree mutationOnTree(Tree tree) {
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
				System.out.println("Imamo problem, velicina nije 1, unary!");
			}
			double x = dfsOnTree(root.getChildren().get(0), level +1);
			root.setValue(root.getAction().calculateUnary(x));
		} else if(root.getAction().isBinary()) {
			if(root.getChildren().size() != 2) {
				System.out.println("Imamo problem, velicina nije 2, binary!");
			}
			double x = dfsOnTree(root.getChildren().get(0), level +1);
			double y = dfsOnTree(root.getChildren().get(1), level +1);
			root.setValue(root.getAction().calculateBinary(x, y));
		} else if(root.getAction().isRelational()) {
			if(root.getChildren().size() != 2) {
				System.out.println("Imamo problem, velicina nije 2, relational!");
			}
			double x = dfsOnTree(root.getChildren().get(0), level +1);
			double y = dfsOnTree(root.getChildren().get(1), level +1);
			if(root.getAction().relations(x, y)) {
				root.setValue(Double.MAX_VALUE);
			} else {
				root.setValue(Double.MIN_VALUE);
			}
		} else if(root.getAction().isBranchingFun()) {
			if(root.getChildren().size() == 3) {
				double x = dfsOnTree(root.getChildren().get(0), level +1);
				if(root.getAction().calculateIf_Else(x)) {
					root.setValue(dfsOnTree(root.getChildren().get(1), level +1));
				} else {
					root.setValue(dfsOnTree(root.getChildren().get(2), level +1));
				}
			} else if(root.getChildren().size() == 5) {
				double x = dfsOnTree(root.getChildren().get(0), level +1);
				double y = dfsOnTree(root.getChildren().get(2), level +1);
				if(root.getAction().calculaateIf_Elif_Else(x, y) == 0) {
					root.setValue(dfsOnTree(root.getChildren().get(1), level +1));
				} else if(root.getAction().calculaateIf_Elif_Else(x, y) == 1) {
					root.setValue(dfsOnTree(root.getChildren().get(3), level +1));
				} else {
					root.setValue(dfsOnTree(root.getChildren().get(4), level +1));
				}
			} else {
				System.out.println("Imamo problem veličine, branching!");
			}
			
		} else {
			System.out.println("Niente dobro");
		}
		return root.getValue();
	}

}
