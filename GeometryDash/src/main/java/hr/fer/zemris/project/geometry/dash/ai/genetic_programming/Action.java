package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

/**
 * Action in one tree node
 * @author Andi Škrgat
 *
 */
public class Action {
	
	/**
	 * Action type
	 */
	private ActionType actionType;
	
	/**
	 * Constructor for action
	 * @param actionType
	 */
	public Action(ActionType actionType) {
		this.actionType = actionType;
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
		} else if(actionType == ActionType.ROOT) {
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
				|| actionType == ActionType.TANH || actionType == ActionType.COTH || actionType == ActionType.ROOT)
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

}
