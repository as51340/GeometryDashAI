package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import org.codehaus.plexus.util.StringUtils;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;

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
	 * Performs mutation on tree. Two types of mutations are possible: in the first
	 * kind a function can only replace a function or a terminal can only replace a
	 * terminal. In the second kind an entire subtree can replace another subtree.
	 * 
	 * @param tree existing tree
	 * @return tree after performed mutation
	 */
	public static Tree mutate(Tree tree, int targetNode, Random r, List<Double> inputs) {
		TreeNode copiedRoot = tree.getRoot().copy();
		TreeNode nodeToChange = findNode(copiedRoot, 1, targetNode);
		if (nodeToChange == null) {
			throw new IllegalArgumentException("Could not find node that needs to be changed!");
		}
		int maxDepth = AIConstants.maxTreeDepth - nodeToChange.getDepth() + 1;
		if(maxDepth < 1) {
			throw new IllegalArgumentException("Invalid node to change because of max tree depht");
		}
		int randomDepth = r.nextInt(maxDepth) + 1;
		TreeNode generatedNode = null;
		if (randomDepth == 1) {
			double dRand = inputs.get(r.nextInt(inputs.size()));
			generatedNode = new TreeNode(dRand);
		} else {
			ActionType at = numberToActionType(r.nextInt(25) + 1); // 25 is number of possible actions
			// root can be anything
			generatedNode = new TreeNode(new Action(at));
			randomSubtree(generatedNode, randomDepth - 1, r, inputs, false);
		}
		if (targetNode == 1) {
			return new Tree(generatedNode);
		} else {
			replaceSubtree(copiedRoot, generatedNode, 1, targetNode);
			return new Tree(copiedRoot);
		}
	}

	/**
	 * Creates random subtree with some must have constraints
	 * 
	 * @param root
	 * @param depthToGo
	 * @param r
	 * @param inputs
	 */
	// ako je dubina dva root ne bi treba relational operator
	// debuganje
	// TODO
	public static boolean randomSubtree(TreeNode root, int depthToGo, Random r, List<Double> inputs, boolean fullTree) {
		if (depthToGo <= 1) { // why I create here only one terminal one when there can more of them
			createTerminalNodes(root, r, inputs);
			return true;
		}
		boolean global = false;
		int num = root.getAction().getChildrenSize();
		if (fullTree == false) {
			for (int j = 0; j < num; j++) {
				int term = r.nextInt(2);
				TreeNode child = null;
				if (global == false || term == 0) { // napravi node action
					ActionType at = null;
					if ((num == 3 && j == 0) || (num == 5 && j == 0) || (num == 5 && j == 2)) {
						at = numberToActionType(r.nextInt(5) + 1); // relational or if_else
					} else {
						at = numberToActionType(r.nextInt(20) + 6); // cannot be relational
					}
					child = new TreeNode(new Action(at));
					root.addChild(child);
					global |= randomSubtree(child, depthToGo - 1, r, inputs, fullTree);
				} else {
					if ((num == 3 && j == 0) || (num == 5 && j == 0) || (num == 5 && j == 2)) {
						int rnd = r.nextInt(2);
						if (rnd == 0) {
							child = new TreeNode(Double.MAX_VALUE);
						} else {
							child = new TreeNode(Double.MIN_VALUE);
						}
					} else {
						child = new TreeNode(inputs.get(r.nextInt(inputs.size())));
					}
					root.addChild(child);
				}
			}
			return global;

		} else {
			createActionChildren(root, r, num);
			for (int j = 0; j < num; j++) {
				randomSubtree(root.getChildren().get(j), depthToGo - 1, r, inputs, fullTree);
			}
			return false;
		}
	}

	/**
	 * Creates terminal nodes
	 * 
	 * @param root
	 * @param r
	 * @param inputs
	 */
	private static void createTerminalNodes(TreeNode root, Random r, List<Double> inputs) {
		int num = root.getAction().getChildrenSize();
		if (num == 5) {
			int branch = r.nextInt(2);
			TreeNode add = null;
			if (branch == 0) {
				add = new TreeNode(Double.MAX_VALUE);
			} else {
				add = new TreeNode(Double.MIN_VALUE);
			}
//			System.out.println("Value " + add.getValue());
			root.addChild(add);
			root.addChild(new TreeNode(inputs.get(r.nextInt(inputs.size()))));
			branch = r.nextInt(2);
			if (branch == 0) {
				root.addChild(new TreeNode(Double.MAX_VALUE));
			} else {
				root.addChild(new TreeNode(Double.MIN_VALUE));
			}
			num = 2;
		} else if (num == 3) {
			// first branch must be Double.MaxValue or Double.MinValue
			int branch = r.nextInt(2);
			if (branch == 0) {
				root.addChild(new TreeNode(Double.MAX_VALUE));
			} else {
				root.addChild(new TreeNode(Double.MIN_VALUE));
			}
			num--;
		}
		for (int i = 0; i < num; i++) {
			TreeNode node = new TreeNode(inputs.get(r.nextInt(inputs.size())));
			root.addChild(node);
		}
	}

	/**
	 * Create action children
	 * 
	 * @param root
	 * @param depthToGo
	 * @param r
	 * @param inputs
	 * @param fullTree
	 * @param global
	 */
	private static void createActionChildren(TreeNode root, Random r, int num) {
		if (num == 5) {
			ActionType at = numberToActionType(r.nextInt(5) + 1); // from 1 to 5
			TreeNode firstBranch = new TreeNode(new Action(at));
			root.addChild(firstBranch);
			at = numberToActionType(r.nextInt(20) + 6); // generate any action except relational
			TreeNode genNode = new TreeNode(new Action(at));
			root.addChild(genNode);
			at = numberToActionType(r.nextInt(5) + 1); // from 1 to 5
			firstBranch = new TreeNode(new Action(at));
			root.addChild(firstBranch);
			num = 2; // for if_elif_else
		} else if (num == 3) {
			// first branch can be if_else or relational
			ActionType at = numberToActionType(r.nextInt(5) + 1); // from 1 to 5
			TreeNode firstBranch = new TreeNode(new Action(at));
			root.addChild(firstBranch);
			// generate 2 more child that can be anything except relational
			num--;
		}
		for (int i = 0; i < num; i++) {
			ActionType at = numberToActionType(r.nextInt(20) + 6); // generate any action except relational
			TreeNode genNode = new TreeNode(new Action(at));
			root.addChild(genNode);
		}
	}

	public static int actionTuNumberType(ActionType at) {
		switch (at) {
		case EQUAL:
			return 1;
		case LESS_EQUAL:
			return 2;
		case GREATER_EQUAL:
			return 3;
		case LESS:
			return 4;
		case GREATER:
			return 5;
		case IF_ELSE:
			return 6;
		case IF_ELIF_ELSE:
			return 7;
		case MINUS:
			return 8;
		case MULTIPLY:
			return 9;
		case DIVIDE:
			return 10;
		case SQRT:
			return 11;
		case SIN:
			return 12;
		case COS:
			return 13;
		case TAN:
			return 14;
		case CTG:
			return 15;
		case ASIN:
			return 16;
		case ACOS:
			return 17;
		case ATAN:
			return 18;
		case ACTG:
			return 19;
		case SINH:
			return 20;
		case COSH:
			return 21;
		case TANH:
			return 22;
		case COTH:
			return 23;
		case POWER:
			return 24;
		case PLUS:
			return 25;
		default:
			throw new IllegalArgumentException("Cannot decode action type to number!");
		}
	}

	/**
	 * Num to action decoder
	 * 
	 * @param number
	 * @return
	 */
	public static ActionType numberToActionType(int number) {
		switch (number) {
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
			return ActionType.IF_ELSE;
		case 7:
			return ActionType.IF_ELIF_ELSE;
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
			return ActionType.POWER;
		case 25:
			return ActionType.PLUS;
		default:
			throw new IllegalArgumentException("No such action, random generator subtree!");
		}
	}

	/**
	 * Crossovers two trees and returns their newly created children
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
		// obradi slucaj ako mijenjam root
		if (firstTree == 1) {
			Tree newTree = new Tree(ptr2.copy());
			sol.add(newTree);
		} else {
			TreeNode newRoot = tree1.getRoot().copy();
			replaceSubtree(newRoot, ptr2, 1, firstTree);
			Tree newTree = new Tree(newRoot);
			sol.add(newTree);
		}
		if (secondTree == 1) {
			Tree newTree = new Tree(ptr1.copy());
			sol.add(newTree);
		} else {
			TreeNode newRoot = tree2.getRoot().copy();
			replaceSubtree(newRoot, ptr1, 1, secondTree);
			Tree newTree = new Tree(newRoot);
			sol.add(newTree);
		}
		return sol;
	}

	/**
	 * Replaces subtrees
	 * 
	 * @param root
	 * @param replaceNode
	 * @param currentNode
	 * @param targetNode
	 */
	private static void replaceSubtree(TreeNode root, TreeNode replaceNode, int currentNode, int targetNode) {
		int index = -1;
		TreeNode toRemove = null;
		for (int i = 0; i < root.getChildren().size(); i++) {
			TreeNode child = root.getChildren().get(i);
			currentNode++;
			if (currentNode == targetNode) {
				toRemove = child;
				index = i;
				break;
			}
			replaceSubtree(child, replaceNode, currentNode, targetNode);
			currentNode += child.getSize();
		}
		if (toRemove != null) {
			root.getChildren().set(index, replaceNode);
		}
	}

	/**
	 * Finds node with index target node
	 * 
	 * @param node
	 * @param currentNode
	 * @param targetNode
	 * @return
	 */
	private static TreeNode findNode(TreeNode node, int currentNode, int targetNode) {
		if (currentNode == targetNode) {
			return node;
		}
		for (TreeNode child : node.getChildren()) {
			currentNode++;
			TreeNode ret = findNode(child, currentNode, targetNode);
			currentNode += child.getSize();
			if (ret != null) {
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
		if (root.getAction().isUnary()) {
			if (root.getChildren().size() != 1) {
				System.out.println("Imamo problem, velicina nije 1, unary!");
			}
			double x = dfsOnTree(root.getChildren().get(0), level + 1);
//			root.setValue(AIConstants.activationFunction.applyAsDouble(root.getAction().calculateUnary(x)));
			root.setValue(root.getAction().calculateUnary(x));
		} else if (root.getAction().isBinary()) {
			if (root.getChildren().size() != 2) {
				System.out.println("Imamo problem, velicina nije 2, binary!");
			}
			double x = dfsOnTree(root.getChildren().get(0), level + 1);
			double y = dfsOnTree(root.getChildren().get(1), level + 1);
//			root.setValue(AIConstants.activationFunction.applyAsDouble(root.getAction().calculateBinary(x, y)));
			root.setValue(root.getAction().calculateBinary(x, y));
		} else if (root.getAction().isRelational()) {
			if (root.getChildren().size() != 2) {
				System.out.println("Imamo problem, velicina nije 2, relational!");
			}
			double x = dfsOnTree(root.getChildren().get(0), level + 1);
			double y = dfsOnTree(root.getChildren().get(1), level + 1);
			if (root.getAction().relations(x, y)) {
				root.setValue(Double.MAX_VALUE);
			} else {
				root.setValue(Double.MIN_VALUE);
			}
		} else if (root.getAction().isBranchingFun()) {
			if (root.getChildren().size() == 3) {
				double x = dfsOnTree(root.getChildren().get(0), level + 1);
				if (root.getAction().calculateIf_Else(x)) {
//					root.setValue(AIConstants.activationFunction
//							.applyAsDouble(dfsOnTree(root.getChildren().get(1), level + 1)));
					root.setValue(dfsOnTree(root.getChildren().get(1), level + 1));
				} else {
//					root.setValue(AIConstants.activationFunction
//							.applyAsDouble(dfsOnTree(root.getChildren().get(2), level + 1)));
					root.setValue(dfsOnTree(root.getChildren().get(2), level + 1));
				}
			} else if (root.getChildren().size() == 5) {
				double x = dfsOnTree(root.getChildren().get(0), level + 1);
				double y = dfsOnTree(root.getChildren().get(2), level + 1);
				if (root.getAction().calculaateIf_Elif_Else(x, y) == 0) {
//					root.setValue(AIConstants.activationFunction
//							.applyAsDouble(dfsOnTree(root.getChildren().get(1), level + 1)));
					root.setValue(dfsOnTree(root.getChildren().get(1), level + 1));
				} else if (root.getAction().calculaateIf_Elif_Else(x, y) == 1) {
//					root.setValue(AIConstants.activationFunction
//							.applyAsDouble(dfsOnTree(root.getChildren().get(3), level + 1)));
					root.setValue(dfsOnTree(root.getChildren().get(3), level + 1));
				} else {
//					root.setValue(AIConstants.activationFunction
//							.applyAsDouble(dfsOnTree(root.getChildren().get(4), level + 1)));
					root.setValue(dfsOnTree(root.getChildren().get(4), level + 1));
				}
			} else {
				System.out.println("Imamo problem veličine, branching!");
			}

		} else {
			System.out.println("Niente dobro");
		}
		return root.getValue();
	}

	private static boolean checkBranchingNode(TreeNode root) {
		if (root.getValue() != Double.MAX_VALUE
				&& root.getValue() != Double.MIN_VALUE) {
			if(root.getAction() == null) {
				return false;
			}
			ActionType at = root.getAction().getActionType();
			int decodedNumber = actionTuNumberType(at);
			if (decodedNumber < 1 || decodedNumber > 5) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkIfValid(TreeNode root) {
		if (root.getAction() == null)
			return true; // value je
		if (root.getAction().isUnary()) {
			if (root.getChildren().size() != 1) {
				return false;
			}
			return checkIfValid(root.getChildren().get(0));
		} else if (root.getAction().isBinary()) {
			if (root.getChildren().size() != 2) {
				return false;
			}
			return checkIfValid(root.getChildren().get(0)) && checkIfValid(root.getChildren().get(1));
		} else if (root.getAction().isRelational()) {
			if (root.getChildren().size() != 2) {
				return false;
			}
			return checkIfValid(root.getChildren().get(0)) && checkIfValid(root.getChildren().get(1));
		} else if (root.getAction().isBranchingFun()) {
			if (root.getChildren().size() == 3) {
				return checkBranchingNode(root.getChildren().get(0)) && checkIfValid(root.getChildren().get(1))
						&& checkIfValid(root.getChildren().get(2));
			} else if (root.getChildren().size() == 5) {
				return checkBranchingNode(root.getChildren().get(0)) && checkIfValid(root.getChildren().get(1))
						&& checkBranchingNode(root.getChildren().get(2)) && checkIfValid(root.getChildren().get(3))
						&& checkIfValid(root.getChildren().get(4));
			} else {
				return false; // unknown size
			}

		} else {
			return false; // unknown function
		}
	}

	/**
	 * Recursively prints tree
	 * 
	 * @param root
	 * @param ret
	 * @param spaces
	 */
	public static void printTree(TreeNode root, int spaces) {
		System.out.println("\t".repeat(spaces) + root.toString());
		for (TreeNode child : root.getChildren()) {
			printTree(child, spaces + 1);
		}
	}
	
	public static double calculateOutput(Tree tree) {
		try {
			double calc = dfsOnTree(tree.getRoot(), 1);
			double sol = AIConstants.activationFunction.applyAsDouble(calc);
//			System.out.println("Solution " + sol);
			return sol;
		} catch (IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//			TreeUtil.printTree(tree.getRoot(), 0);
//			System.exit(-1);
		}
		return 0;
	}


}
