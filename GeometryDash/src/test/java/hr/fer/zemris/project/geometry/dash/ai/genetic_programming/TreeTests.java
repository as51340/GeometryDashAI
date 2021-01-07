package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TreeTests {

	//binary testing
	
	@Test
	void simpleTestPlus() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(3, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleTestMinus() {
		TreeNode root = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(-1, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleTestMultiply() {
		TreeNode root = new TreeNode(new Action(ActionType.MULTIPLY));
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(2, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleTestDivide() {
		TreeNode root = new TreeNode(new Action(ActionType.DIVIDE));
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(0.5, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleTestPower() {
		TreeNode root = new TreeNode(new Action(ActionType.POWER));
		TreeNode node1 = new TreeNode(5);
		TreeNode node2 = new TreeNode(3);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(125, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void twoLevelSimplePlusMultiply() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(new Action(ActionType.MULTIPLY));
		TreeNode node2 = new TreeNode(1);
		TreeNode node3 = new TreeNode(2);
		TreeNode node4 = new TreeNode(3);
		node1.addChild(node3);
		node1.addChild(node4);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(7, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void complicatedBinary() {
		TreeNode root = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node1 = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node2 = new TreeNode(new Action(ActionType.DIVIDE));
		root.addChild(node1);
		root.addChild(node2);
		TreeNode node3 = new TreeNode(new Action(ActionType.MULTIPLY));
		TreeNode node4 = new TreeNode(new Action(ActionType.POWER));
		node1.addChild(node3);
		node1.addChild(node4);
		TreeNode node5 = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node6 = new TreeNode(new Action(ActionType.DIVIDE));
		node2.addChild(node5);
		node2.addChild(node6);
		TreeNode node7 = new TreeNode(3);
		TreeNode node8 = new TreeNode(2);
		node5.addChild(node7);
		node5.addChild(node8);
		TreeNode node9 = new TreeNode(8);
		TreeNode node10 = new TreeNode(4);
		node6.addChild(node9);
		node6.addChild(node10);
		TreeNode node11 = new TreeNode(4);
		TreeNode node12 = new TreeNode(3);
		node3.addChild(node11);
		node3.addChild(node12);
		TreeNode node13 = new TreeNode(4);
		TreeNode node14 = new TreeNode(2);
		node4.addChild(node13);
		node4.addChild(node14);
		assertEquals(27.5, TreeUtil.dfsOnTree(root, 1));
	}
	
	//unary operations testing

	@Test
	void simpleUnaryCos() {
		TreeNode root = new TreeNode(new Action(ActionType.COS));
		TreeNode node1 = new TreeNode(Math.toRadians(30));
		root.addChild(node1);
		assertEquals(Math.cos(Math.toRadians(30)), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnarySin() {
		TreeNode root = new TreeNode(new Action(ActionType.SIN));
		TreeNode node1 = new TreeNode(Math.toRadians(30));
		root.addChild(node1);
		assertEquals(Math.sin(Math.toRadians(30)), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnaryTan() {
		TreeNode root = new TreeNode(new Action(ActionType.TAN));
		TreeNode node1 = new TreeNode(Math.toRadians(30));
		root.addChild(node1);
		assertEquals(Math.tan(Math.toRadians(30)), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnaryCtg() {
		TreeNode root = new TreeNode(new Action(ActionType.CTG));
		TreeNode node1 = new TreeNode(Math.toRadians(30));
		root.addChild(node1);
		assertEquals((double) 1.0 / Math.tan(Math.toRadians(30)), TreeUtil.dfsOnTree(root, 1), 1e-9);
	}

	@Test
	void simpleUnaryAsin() {
		TreeNode root = new TreeNode(new Action(ActionType.ASIN));
		TreeNode node1 = new TreeNode(0.5);
		root.addChild(node1);
		assertEquals(Math.asin(0.5), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnaryAcos() {
		TreeNode root = new TreeNode(new Action(ActionType.ACOS));
		TreeNode node1 = new TreeNode(0.5);
		root.addChild(node1);
		assertEquals(Math.acos(0.5), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnaryAtan() {
		TreeNode root = new TreeNode(new Action(ActionType.ATAN));
		TreeNode node1 = new TreeNode(0.5);
		root.addChild(node1);
		assertEquals(Math.atan(0.5), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnaryActg() {
		TreeNode root = new TreeNode(new Action(ActionType.ACTG));
		TreeNode node1 = new TreeNode(0.5);
		root.addChild(node1);
		assertEquals(Math.atan(2), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnarySinh() {
		TreeNode root = new TreeNode(new Action(ActionType.SINH));
		TreeNode node1 = new TreeNode(0.5);
		root.addChild(node1);
		assertEquals(Math.sinh(0.5), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnaryCosh() {
		TreeNode root = new TreeNode(new Action(ActionType.COSH));
		TreeNode node1 = new TreeNode(0.5);
		root.addChild(node1);
		assertEquals(Math.cosh(0.5), TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleUnaryTanh() {
		TreeNode root = new TreeNode(new Action(ActionType.TANH));
		TreeNode node1 = new TreeNode(0.5);
		root.addChild(node1);
		assertEquals(Math.tanh(0.5), TreeUtil.dfsOnTree(root, 1));
	}
	
	//relations testing

	@Test
	void simpleLessTest() {
		TreeNode root = new TreeNode(new Action(ActionType.LESS));
		TreeNode node1 = new TreeNode(0.5);
		TreeNode node2 = new TreeNode(1);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(Double.MAX_VALUE, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleLessEqualTest() {
		TreeNode root = new TreeNode(new Action(ActionType.LESS_EQUAL));
		TreeNode node1 = new TreeNode(0.5);
		TreeNode node2 = new TreeNode(0.5);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(Double.MAX_VALUE, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleGreaterTest() {
		TreeNode root = new TreeNode(new Action(ActionType.GREATER));
		TreeNode node1 = new TreeNode(0.5);
		TreeNode node2 = new TreeNode(1);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(Double.MIN_VALUE, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleGreaterEqualTest() {
		TreeNode root = new TreeNode(new Action(ActionType.GREATER_EQUAL));
		TreeNode node1 = new TreeNode(0.5);
		TreeNode node2 = new TreeNode(0.5);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(Double.MAX_VALUE, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleEqualTest1() {
		TreeNode root = new TreeNode(new Action(ActionType.EQUAL));
		TreeNode node1 = new TreeNode(0.5);
		TreeNode node2 = new TreeNode(0.5);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(Double.MAX_VALUE, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleEqualTest2() {
		TreeNode root = new TreeNode(new Action(ActionType.EQUAL));
		TreeNode node1 = new TreeNode(0.5);
		TreeNode node2 = new TreeNode(0.55);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(Double.MIN_VALUE, TreeUtil.dfsOnTree(root, 1));
	}
	
	//branching testing

	@Test
	void simpleBranchingIf_Else1() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELSE));
		TreeNode node1 = new TreeNode(new Action(ActionType.LESS));
		TreeNode node2 = new TreeNode(4);
		TreeNode node3 = new TreeNode(5);
		root.addChild(node1);
		root.addChild(node2);
		root.addChild(node3);
		TreeNode node4 = new TreeNode(2);
		TreeNode node5 = new TreeNode(3);
		node1.addChild(node4);
		node1.addChild(node5);
		assertEquals(4, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void simpleBranchingIf_Else2() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELSE));
		TreeNode node1 = new TreeNode(new Action(ActionType.GREATER_EQUAL));
		TreeNode node2 = new TreeNode(4);
		TreeNode node3 = new TreeNode(5);
		root.addChild(node1);
		root.addChild(node2);
		root.addChild(node3);
		TreeNode node4 = new TreeNode(2);
		TreeNode node5 = new TreeNode(3);
		node1.addChild(node4);
		node1.addChild(node5);
		assertEquals(5, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void complicatedBranchingIf_Else2() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELSE));
		TreeNode node1 = new TreeNode(new Action(ActionType.EQUAL));
		TreeNode node2 = new TreeNode(new Action(ActionType.DIVIDE));
		TreeNode node3 = new TreeNode(new Action(ActionType.MULTIPLY));
		root.addChild(node1);
		root.addChild(node2);
		root.addChild(node3);
		TreeNode node4 = new TreeNode(0.5);
		TreeNode node5 = new TreeNode(0.5);
		node1.addChild(node4);
		node1.addChild(node5);
		TreeNode node6 = new TreeNode(2);
		TreeNode node7 = new TreeNode(8);
		TreeNode node8 = new TreeNode(3);
		TreeNode node9 = new TreeNode(2);
		node2.addChild(node6);
		node2.addChild(node7);
		node3.addChild(node8);
		node3.addChild(node9);
		assertEquals(0.25, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void complicatedBranchingIf_Elif_Else1() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELIF_ELSE));
		TreeNode node1 = new TreeNode(new Action(ActionType.EQUAL));
		TreeNode node2 = new TreeNode(2);
		TreeNode node3 = new TreeNode(new Action(ActionType.LESS));
		TreeNode node4 = new TreeNode(4);
		TreeNode node5 = new TreeNode(6);
		root.addChild(node1);
		root.addChild(node2);
		root.addChild(node3);
		root.addChild(node4);
		root.addChild(node5);
		TreeNode node6 = new TreeNode(0.5);
		TreeNode node7 = new TreeNode(0.5);
		node1.addChild(node6);
		node1.addChild(node7);
		TreeNode node8 = new TreeNode(0.5);
		TreeNode node9 = new TreeNode(0.5);
		node3.addChild(node8);
		node3.addChild(node9);
		assertEquals(2, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void complicatedBranchingIf_Elif_Else2() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELIF_ELSE));
		TreeNode node1 = new TreeNode(new Action(ActionType.LESS));
		TreeNode node2 = new TreeNode(2);
		TreeNode node3 = new TreeNode(new Action(ActionType.EQUAL));
		TreeNode node4 = new TreeNode(4);
		TreeNode node5 = new TreeNode(6);
		root.addChild(node1);
		root.addChild(node2);
		root.addChild(node3);
		root.addChild(node4);
		root.addChild(node5);
		TreeNode node6 = new TreeNode(0.5);
		TreeNode node7 = new TreeNode(0.5);
		node1.addChild(node6);
		node1.addChild(node7);
		TreeNode node8 = new TreeNode(0.5);
		TreeNode node9 = new TreeNode(0.5);
		node3.addChild(node8);
		node3.addChild(node9);
		assertEquals(4, TreeUtil.dfsOnTree(root, 1));
	}

	@Test
	void complicatedBranchingIf_Elif_Else3() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELIF_ELSE));
		TreeNode node1 = new TreeNode(new Action(ActionType.GREATER));
		TreeNode node2 = new TreeNode(2);
		TreeNode node3 = new TreeNode(new Action(ActionType.LESS));
		TreeNode node4 = new TreeNode(4);
		TreeNode node5 = new TreeNode(6);
		root.addChild(node1);
		root.addChild(node2);
		root.addChild(node3);
		root.addChild(node4);
		root.addChild(node5);
		TreeNode node6 = new TreeNode(0.5);
		TreeNode node7 = new TreeNode(0.5);
		node1.addChild(node6);
		node1.addChild(node7);
		TreeNode node8 = new TreeNode(0.5);
		TreeNode node9 = new TreeNode(0.5);
		node3.addChild(node8);
		node3.addChild(node9);
		assertEquals(6, TreeUtil.dfsOnTree(root, 1));
	}
	
	//size testing

	@Test
	void sizeTest1() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELIF_ELSE));
		TreeNode node1 = new TreeNode(new Action(ActionType.GREATER));
		TreeNode node2 = new TreeNode(2);
		TreeNode node3 = new TreeNode(new Action(ActionType.LESS));
		TreeNode node4 = new TreeNode(4);
		TreeNode node5 = new TreeNode(6);
		root.addChild(node1);
		root.addChild(node2);
		root.addChild(node3);
		root.addChild(node4);
		root.addChild(node5);
		TreeNode node6 = new TreeNode(0.5);
		TreeNode node7 = new TreeNode(0.5);
		node1.addChild(node6);
		node1.addChild(node7);
		TreeNode node8 = new TreeNode(0.5);
		TreeNode node9 = new TreeNode(0.5);
		node3.addChild(node8);
		node3.addChild(node9);
		Tree tree = new Tree(root); //for size
		assertEquals(9, tree.getRoot().getSize());
	}
	
	@Test
	void sizeTest2() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELSE));
		TreeNode node1 = new TreeNode(new Action(ActionType.EQUAL));
		TreeNode node2 = new TreeNode(new Action(ActionType.DIVIDE));
		TreeNode node3 = new TreeNode(new Action(ActionType.MULTIPLY));
		root.addChild(node1);
		root.addChild(node2);
		root.addChild(node3);
		TreeNode node4 = new TreeNode(0.5);
		TreeNode node5 = new TreeNode(0.5);
		node1.addChild(node4);
		node1.addChild(node5);
		TreeNode node6 = new TreeNode(2);
		TreeNode node7 = new TreeNode(8);
		TreeNode node8 = new TreeNode(3);
		TreeNode node9 = new TreeNode(2);
		node2.addChild(node6);
		node2.addChild(node7);
		node3.addChild(node8);
		node3.addChild(node9);
		Tree tree = new Tree(root); //for size
		assertEquals(9, tree.getRoot().getSize());
	}
	
	@Test
	void sizeTest3() {
		TreeNode root = new TreeNode(new Action(ActionType.IF_ELSE));
		Tree tree = new Tree(root); //for size
		assertEquals(0, tree.getRoot().getSize());
	}
	
	@Test
	void sizeTest4() {
		Tree tree = createTreeForSimpleTest1();
		assertEquals(3, tree.getSize());
	}
	
	//equals Tree testing
	
	@Test
	void equalsOnSimpleTreeSuccess1() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		Tree tree1 = new Tree(root);
		Tree tree2 = tree1.copy();
		assertEquals(true, tree1.equals(tree2));
	}
	
	@Test
	void equalsOnSimpleTreeSuccess2() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.addChild(node1);
		root.addChild(node2);
		Tree tree1 = new Tree(root);
		Tree tree2 = tree1.copy();
		assertEquals(true, tree1.equals(tree2));
	}
	
	
	//crossover testing jej
	
	@Disabled
	@Test
	void crossoverSimpleTest1() {
		Tree tree1 = createTreeForSimpleTest1();
		Tree tree2 = createTreeForSimpleTest2();
		int firstTree = 2;
		int secondTree = 2;
		List<Tree> results = TreeUtil.crossover(tree1, tree2, firstTree, secondTree);
		assertEquals(5, results.get(0).getSize());
		assertEquals(3, results.get(1).getSize());
		assertEquals(results.get(0), createResultingTreeCrossoverSimpleTest1Tree1());
		assertEquals(results.get(1), createResultingTreeCrossoverSimpleTest1Tree2());
	}
	
	@Disabled
	@Test
	void crossoverSimpleTest2() {
		Tree tree1 = createTreeForSimpleTest1();
		Tree tree2 = createTreeForSimpleTest2();
		int firstTree = 3;
		int secondTree = 2;
		List<Tree> results = TreeUtil.crossover(tree1, tree2, firstTree, secondTree);
		assertEquals(results.get(0), createResultingTreeCrossoverSimpleTest2Tree1());
		assertEquals(results.get(1), createResultingTreeCrossoverSimpleTest2Tree2());
	}
	
	@Disabled
	@Test
	void crossoverSimpleTest3() { //lagano swapanje
		Tree tree1 = createTreeForSimpleTest1();
		Tree tree2 = createTreeForSimpleTest2();
		int firstTree = 1;
		int secondTree = 1;
		List<Tree> results = TreeUtil.crossover(tree1, tree2, firstTree, secondTree);
		assertEquals(results.get(0), tree2);
		assertEquals(results.get(1), tree1);
	}
	
	@Disabled
	@Test
	void crossoverSimpleTest4() {
		Tree tree1 = createTreeForSimpleTest1();
		Tree tree2 = createTreeForSimpleTest2();
		int firstTree = 2;
		int secondTree = 1;
		List<Tree> results = TreeUtil.crossover(tree1, tree2, firstTree, secondTree);
		assertEquals(results.get(0), createResultingTreeCrossoverSimpleTest4Tree1());
		assertEquals(results.get(1), createResultingTreeCrossoverSimpleTest4Tree2());
	}
	
	@Test
	void depthTest1() {
		Tree tree = createResultingTreeCrossoverSimpleTest1Tree1();
		assertEquals(1, tree.getRoot().getDepth());
	}
	
	@Test
	void depthTest2() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node2 = new TreeNode(4.0);
		root.addChild(node1);
		root.addChild(node2);
		TreeNode node3 = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node4 = new TreeNode(1.0);
		node1.addChild(node3);
		node1.addChild(node4);
		TreeNode node5 = new TreeNode(2.0);
		TreeNode node6 = new TreeNode(3.0);
		node3.addChild(node5);
		node3.addChild(node6);
		Tree tree = new Tree(root); //we need this because with this method we calculate size and depth
		assertEquals(1, root.getDepth());
		assertEquals(2, node1.getDepth());
		assertEquals(2, node2.getDepth());
		assertEquals(3, node3.getDepth());
		assertEquals(3, node4.getDepth());
		assertEquals(4, node5.getDepth());
		assertEquals(4, node6.getDepth());
	}
	
	@Test
	void sizeTestAfterCopying() {
		Tree tree = createResultingTreeCrossoverSimpleTest1Tree1();
		assertEquals(tree.getSize(), tree.copy().getSize());
	}
	
	@Test
	void depthTestAfterCopying() {
		Tree tree = createResultingTreeCrossoverSimpleTest1Tree1();
		assertEquals(tree.getRoot().getDepth(), tree.copy().getRoot().getDepth());
	}
	
	@Test
	void fullTreeCopying() {
		Tree tree = createResultingTreeCrossoverSimpleTest1Tree1();
		assertEquals(tree, tree.copy());
	}
	
	@Test
	void nodeCopyingTest() {
		Tree tree = createResultingTreeCrossoverSimpleTest1Tree1();
		assertEquals(tree.getRoot(), tree.copy().getRoot().copy());
	}
	
	@Test
	void printTreeTest1() {
		Tree tree = createResultingTreeCrossoverSimpleTest4Tree1();
		//TreeUtil.printTree(tree.getRoot(), 0);
		//no need for that
	}
	
	@Test
	void mutationTest1() {
		Tree tree = createResultingTreeCrossoverSimpleTest4Tree1();
//		TreeUtil.printTree(tree.getRoot(), 0);
//		System.out.println();
//		System.out.println();
		List<Double> inputs = Arrays.asList(1.0, 2.0, 3.0, 4.0);
		Random r = new Random();
		int targetNode = r.nextInt(tree.getSize()) + 1;
		assertDoesNotThrow(()-> {
			Tree newTree = TreeUtil.mutate(tree, 1, r, inputs);
			TreeUtil.printTree(newTree.getRoot(), 0);
		});
	}
	
	/**
	 * Resulting tree for crossover simple test 4 tree 2
	 * @return
	 */
	private Tree createResultingTreeCrossoverSimpleTest4Tree2() {
		return new Tree(new TreeNode(5.0));
	}
	
	/**
	 * Method for creating tree
	 * @return
	 */
	private Tree createResultingTreeCrossoverSimpleTest4Tree1() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node2 = new TreeNode(4.0);
		root.addChild(node1);
		root.addChild(node2);
		TreeNode node3 = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node4 = new TreeNode(1.0);
		node1.addChild(node3);
		node1.addChild(node4);
		TreeNode node5 = new TreeNode(2.0);
		TreeNode node6 = new TreeNode(3.0);
		node3.addChild(node5);
		node3.addChild(node6);
		return new Tree(root);
	}
	
	/**
	 * creates simples tree
	 * @return
	 */
	private Tree createResultingTreeCrossoverSimpleTest2Tree1() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(5.0);
		TreeNode node2 = new TreeNode(new Action(ActionType.PLUS));
		root.addChild(node1);
		root.addChild(node2);
		TreeNode node3 = new TreeNode(2.0);
		TreeNode node4 = new TreeNode(3.0);
		node2.addChild(node3);
		node2.addChild(node4);
		return new Tree(root);
	}
	
	/**
	 * simple tree creation
	 * @return
	 */
	private Tree createResultingTreeCrossoverSimpleTest2Tree2() {
		TreeNode root = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node1 = new TreeNode(4.0);
		TreeNode node2 = new TreeNode(1.0);
		root.addChild(node1);
		root.addChild(node2);
		return new Tree(root);
	}
	
	/**
	 * simple tree
	 * @return
	 */
	private Tree createResultingTreeCrossoverSimpleTest1Tree2() {
		TreeNode root = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node1 = new TreeNode(5.0);
		TreeNode node2 = new TreeNode(1.0);
		root.addChild(node1);
		root.addChild(node2);
		return new Tree(root);
	}
	
	/**
	 * simple tree
	 * @return
	 */
	private Tree createResultingTreeCrossoverSimpleTest1Tree1() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node2 = new TreeNode(4.0);
		root.addChild(node1);
		root.addChild(node2);
		TreeNode node3 = new TreeNode(2.0);
		TreeNode node4 = new TreeNode(3.0);
		node1.addChild(node3);
		node1.addChild(node4);
		return new Tree(root);
	}
	
	/**
	 * simple tree
	 * @return
	 */
	private Tree createTreeForSimpleTest1() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(5);
		TreeNode node2 = new TreeNode(4);
		root.addChild(node1);
		root.addChild(node2);
		return new Tree(root);
	}
	
	/**
	 * simple tree
	 * @return
	 */
	private Tree createTreeForSimpleTest2() {
		TreeNode root = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node1 = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node2 = new TreeNode(1);
		root.addChild(node1);
		root.addChild(node2);
		TreeNode node3 = new TreeNode(2);
		TreeNode node4 = new TreeNode(3);
		node1.addChild(node3);
		node1.addChild(node4);
		return new Tree(root);
	}

}
