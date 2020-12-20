package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TreeTests {

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
		TreeNode root = new TreeNode(new Action(ActionType.LESSS_EQUAL));
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
	
}
