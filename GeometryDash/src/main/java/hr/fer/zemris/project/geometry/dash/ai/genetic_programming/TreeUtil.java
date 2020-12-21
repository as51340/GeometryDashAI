package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.codehaus.plexus.util.StringUtils;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;

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
	public static TreeNode mutate(TreeNode root, int currentNode, int targetNode) {
		if(currentNode + 1 == targetNode) { //ako je sljedeci to znaci da moram ovome dodat novo dijete
			
		}
		for(TreeNode child: root.getChildren()) {
			mutate(child, currentNode+1, targetNode);
		}
		return null;
	}
	
	public static TreeNode randomSubtree() {
		Random r = new Random();
		int maxSize = 5; //for now just for testing, this is number of new actions allocated
		int size = r.nextInt() + 1; //[1, maxSize]
		
		int actNumber = r.nextInt(AIConstants.numOfActions) + 1; //we need to create to root before everything else
		System.out.println("Action number: " + actNumber);
		Action action = new Action(numberToActionType(actNumber));
		TreeNode root = new TreeNode(action);
		return null;
	}
	
	public static TreeNode recursiveCreationOfSubtree(TreeNode root, int actionsCreated, int targetNumber) {
//		if(root.get)
		return null;
	}
	
	private static ActionType numberToActionType(int number) {
		switch(number) {
			case 1: 
				return ActionType.EQUAL;
			case 2:
				return ActionType.LESS_EQUAL;
			case 3:
				return ActionType.GREATER_EQUAL;
			case 4:
				return ActionType.LESS;
			case 5:
				return ActionType.GREATER;
			case 6:
				return ActionType.POWER;
			case 7:
				return ActionType.PLUS;
			case 8:
				return ActionType.MINUS;
			case 9:
				return ActionType.MULTIPLY;
			case 10:
				return ActionType.DIVIDE;
			case 11:
				return ActionType.SQRT;
			case 12:
				return ActionType.SIN;
			case 13:
				return ActionType.COS;
			case 14:
				return ActionType.TAN;
			case 15:
				return ActionType.CTG;
			case 16:
				return ActionType.ASIN;
			case 17:
				return ActionType.ACOS;
			case 18:
				return ActionType.ATAN;
			case 19:
				return ActionType.ACTG;
			case 20:
				return ActionType.SINH;
			case 21:
				return ActionType.COSH;
			case 22:
				return ActionType.TANH;
			case 23:
				return ActionType.COTH;
			case 24:
				return ActionType.IF_ELSE;
			case 25:
				return ActionType.IF_ELIF_ELSE;
			default:
				throw new IllegalArgumentException("No such action, random generator subtree!");
		}
			
	}
	

	/**
	 * Crossovers two trees and returns their newly created child
	 * 
	 * @param tree1 first tree
	 * @param tree2 second tree
	 * @return 2 new trees
	 */
	public static List<Tree> crossover(Tree tree1, Tree tree2, int firstTree, int secondTree) {
//		Random r = new Random();
//		int firstTree = r.nextInt(tree1.getSize()) + 1;
//		int secondTree = r.nextInt(tree2.getSize()) + 1;
		List<Tree> sol = new ArrayList<Tree>();
		TreeNode ptr1 = findNode(tree1.getRoot(), 1, firstTree);
		TreeNode ptr2 = findNode(tree2.getRoot(), 1, secondTree);
		//obradi slucaj ako mijenjam root
		if(firstTree == 1) {
			Tree newTree = new Tree(ptr2.copy());
			sol.add(newTree);
		} else {
			TreeNode newRoot = tree1.getRoot().copy();
			replaceSubtree(newRoot, ptr2, 1, firstTree);
			Tree newTree = new Tree(newRoot);
			sol.add(newTree);
		}
		if(secondTree == 1) {
			Tree newTree = new Tree(ptr1.copy());
			sol.add(newTree);
		} else  {
			TreeNode newRoot = tree2.getRoot().copy();
			replaceSubtree(newRoot, ptr1, 1, secondTree);
			Tree newTree = new Tree(newRoot);		
			sol.add(newTree);
		}
		return sol;
	}
	
	private static void replaceSubtree(TreeNode root, TreeNode replaceNode, int currentNode, int targetNode) {
		int index = -1;
		TreeNode toRemove = null;
		for(int i = 0; i< root.getChildren().size(); i++) {
			TreeNode child = root.getChildren().get(i);
			currentNode++;
			if(currentNode == targetNode) {
				toRemove = child;
				index = i;
				break;
			}
			if(index == -1) {
				replaceSubtree(child, replaceNode, currentNode, targetNode);
			}
		}
		if(toRemove != null) {
			root.getChildren().set(index, replaceNode);
		}
	}
	
	private static TreeNode findNode(TreeNode node, int currentNode, int targetNode) {
		if(currentNode == targetNode) {
			return node;
		}
		for(TreeNode child: node.getChildren()) {
			TreeNode ret = findNode(child, ++currentNode, targetNode);
			if(ret != null) {
				return ret;
			}
		}
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
