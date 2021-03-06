package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

/**
 * Lists all possible node actions
 * @author Andi Škrgat
 *
 */
public enum ActionType {
	EQUAL, LESS_EQUAL, GREATER_EQUAL, LESS, GREATER, POWER, PLUS, MINUS, MULTIPLY, DIVIDE, SQRT, SIN, COS, TAN,
	CTG, ASIN, ACOS, ATAN, ACTG, 
	SINH, COSH, TANH, COTH, IF_ELSE, IF_ELIF_ELSE;
}
