package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import com.google.gson.annotations.Expose;

/**
 * Action in one tree node
 * @author Andi Škrgat
 *
 */
public class Action {
	
	/**
	 * Action type
	 */
	@Expose
	private ActionType actionType;
	
	/**
	 * Constructor for action
	 * @param actionType
	 */
	public Action(ActionType actionType) {
		this.actionType = actionType;
	}
	
	/**
	 * Creates new action based on text
	 * @param text 
	 * @return new action
	 */
	public static Action parse(String text) {
		if(text.equals("EQUAL")) {
			return new Action(ActionType.EQUAL);
		} else if(text.equals("LESS_EQUAL")) {
			return new Action(ActionType.LESS_EQUAL);
		} else if(text.equals("GREATER_EQUAL")) {
			return new Action(ActionType.GREATER_EQUAL);
		} else if(text.equals("LESS")) {
			return new Action(ActionType.LESS);
		} else if(text.equals("GREATER")) {
			return new Action(ActionType.GREATER);
		} else if(text.equals("POWER")) {
			return new Action(ActionType.POWER);
		} else if(text.equals("PLUS")) {
			return new Action(ActionType.PLUS);
		} else if(text.equals("MINUS")) {
			return new Action(ActionType.MINUS);
		} else if(text.equals("MULTIPLY")) {
			return new Action(ActionType.MULTIPLY);
		} else if(text.equals("DIVIDE")) {
			return new Action(ActionType.DIVIDE);
		} else if(text.equals("SQRT")) {
			return new Action(ActionType.SQRT);
		} else if(text.equals("SIN")) {
			return new Action(ActionType.SIN);
		} else if(text.equals("COS")) {
			return new Action(ActionType.COS);
		} else if(text.equals("TAN")) {
			return new Action(ActionType.TAN);
		} else if(text.equals("CTG")) {
			return new Action(ActionType.CTG);
		} else if(text.equals("ASIN")) {
			return new Action(ActionType.ASIN);
		} else if(text.equals("ACOS")) {
			return new Action(ActionType.ACOS);
		} else if(text.equals("ATAN")) {
			return new Action(ActionType.ATAN);
		} else if(text.equals("ACTG")) {
			return new Action(ActionType.ACTG);
		} else if(text.equals("SINH")) {
			return new Action(ActionType.SINH);
		} else if(text.equals("COSH")) {
			return new Action(ActionType.COSH);
		} else if(text.equals("TANH")) {
			return new Action(ActionType.TANH);
		} else if(text.equals("COTH")) {
			return new Action(ActionType.COTH);
		} else if(text.equals("IF_ELSE")) {
			return new Action(ActionType.IF_ELSE);
		} else if(text.equals("IF_ELIF_ELSE")) {
			return new Action(ActionType.IF_ELIF_ELSE);
		} else {
			return null;
		}
	}
	
	/**
	 * Performs calculation for this action
	 * @return value
	 */
	public double calculateUnary(double x) {
		if(actionType == ActionType.SIN) {
			return ActionCalculator.sin(x);
		} else if(actionType == ActionType.COS) {
			return ActionCalculator.cos(x);
		}  else if(actionType == ActionType.TAN) {
			return ActionCalculator.tan(x);
		}  else if(actionType == ActionType.CTG) {
			return ActionCalculator.ctg(x);
		}  else if(actionType == ActionType.ASIN) {
			return ActionCalculator.asin(x);
		}  else if(actionType == ActionType.ACOS) {
			return ActionCalculator.acos(x);
		}  else if(actionType == ActionType.SINH) {
			return ActionCalculator.sinh(x);
		}  else if(actionType == ActionType.COSH) {
			return ActionCalculator.cosh(x);
		}  else if(actionType == ActionType.TANH) {
			return ActionCalculator.tanh(x);
		}  else if(actionType == ActionType.COTH) {
			return ActionCalculator.coth(x);
		}  else if(actionType == ActionType.ATAN) {
			return ActionCalculator.atan(x);
		} else if(actionType == ActionType.ACTG) {
			return ActionCalculator.actg(x);
		}  else if(actionType == ActionType.SQRT) {
			return ActionCalculator.root(x);
		} else {
			throw new IllegalArgumentException("No such action, unary");
		}
	}
	
	/**
	 * Performs calculation for this action
	 * @return value
	 */
	public double calculateBinary(double x, double y) {
		if(actionType == ActionType.PLUS) {
			return ActionCalculator.sum(x, y);
		} else if(actionType == ActionType.MINUS) {
			return ActionCalculator.diff(x, y);
		} else if(actionType == ActionType.MULTIPLY) {
			return ActionCalculator.mul(x, y);
		} else if(actionType == ActionType.DIVIDE) {
			return ActionCalculator.div(x, y);
		} else if(actionType == ActionType.POWER) {
			return ActionCalculator.power(x, y);
		} else {
			throw new IllegalArgumentException("No such action, binary");
		}
	}
	
	/**
	 * @return if action is relational
	 */
	public boolean isRelational() {
		if(actionType == ActionType.EQUAL || actionType == ActionType.LESS || actionType == ActionType.LESS_EQUAL || 
				actionType == ActionType.GREATER || actionType == ActionType.GREATER_EQUAL) {
			return true;
		}  
		return false;
	}
	
	/**
	 * Relations action
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean relations(double x, double y) {
		if(actionType == ActionType.LESS) {
			return ActionCalculator.less(x, y);
		} else if(actionType == ActionType.GREATER) {
			return ActionCalculator.greater(x, y);
		} else if(actionType == ActionType.LESS_EQUAL) {
			return ActionCalculator.lessEqual(x, y);
		} else if(actionType == ActionType.GREATER_EQUAL) {
			return ActionCalculator.greaterEqual(x, y);
		} else if(actionType == ActionType.EQUAL) {
			return ActionCalculator.equals(x, y);
		} else {
			throw new IllegalArgumentException("No such action, relations!");
		}
	}
	
//	public boolean calculateBranching(double )
	
	/**
	 * @return true if action is unary
	 */
	public boolean isUnary() {
		if(actionType ==  ActionType.SIN || actionType == ActionType.COS || actionType == ActionType.TAN 
				|| actionType == ActionType.ATAN || actionType == ActionType.SINH || actionType == ActionType.COSH 
				|| actionType == ActionType.TANH || actionType == ActionType.COTH || actionType == ActionType.CTG 
				|| actionType == ActionType.ASIN || actionType == ActionType.ACOS || actionType == ActionType.ATAN
				|| actionType == ActionType.ACTG || actionType == ActionType.SQRT
				)
		{
			return true;
		} 
		return false;
	}
	
	/**
	 * @return true if action is binary
	 */
	public boolean isBinary() {
		if(actionType == ActionType.PLUS || actionType == ActionType.MINUS || actionType == ActionType.POWER || 
			actionType == ActionType.DIVIDE || actionType == ActionType.MULTIPLY) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return true if action is branching
	 */
	public boolean isBranchingFun() {
		if(actionType == ActionType.IF_ELSE || actionType == ActionType.IF_ELIF_ELSE) {
			return true;
		}
		return false;
	}
	
	/**
	 * Number of children
	 * @return num of children
	 */
	public int getChildrenSize() {
		if(isUnary()) {
			return 1;
		} 
		if(isBinary() || isRelational()) {
			return 2;
		}
		if(actionType == ActionType.IF_ELSE) {
			return 3;
		} 
		if(actionType == ActionType.IF_ELIF_ELSE) {
			return 5;
		} 	
		throw new IllegalArgumentException("Unknown action");
		
	}
	
	public boolean calculateIf_Else(double x) {
		return ActionCalculator.if_else(x);
	}
	
	public int calculaateIf_Elif_Else(double x, double y) {
		return ActionCalculator.if_elif_else(x, y);
	}

	@Override
	public String toString() {
		return actionType.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionType == null) ? 0 : actionType.hashCode());
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
		Action other = (Action) obj;
		if (actionType != other.actionType)
			return false;
		return true;
	}
	
}
