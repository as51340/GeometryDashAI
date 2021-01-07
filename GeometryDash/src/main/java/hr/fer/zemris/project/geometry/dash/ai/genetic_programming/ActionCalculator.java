package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

/**
 * Provides functionality for calculating different operations
 * @author Andi Škrgat
 * We'll need to add many different conditions for operations
 */
public class ActionCalculator {

	/**
	 * Sums two numbers
	 * @param x param 1
	 * @param y param 2
	 * @return result
	 */
	public static double sum(double x, double y) {
		return Double.sum(x, y);
	}
	
	public static double diff(double x, double y) {
		return x - y;
	}
	
	public static double mul(double x, double y) {
		return x * y;
	}
	
	public static double div(double x, double y) {
		return x / y;
	}
	
	public static double root(double x) {
		double d = Math.sqrt(x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d;
	}
	
	public static double sin(double x) {
		return Math.sin(x);
	}
	
	public static double cos(double x) {
		return Math.cos(x);
	}
	
	public static double tan(double x) {
		return Math.tan(x);
	}

	public static double ctg(double x) {
		double d = Math.cos(x) / Math.sin(x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d;
	}
	
	public static double asin(double x) {
		double d = Math.asin(x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d; 
	}
	
	public static double acos(double x) {
		double d = Math.acos(x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d; 
	}
	
	public static double atan(double x) {
		double d = Math.atan(x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d; 
	}
	
	public static double actg(double x) {
		double d = Math.atan(1.0 / x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d; 
	}

	public static double sinh(double x) {
		double d = Math.sinh(x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d; 
	}
	
	public static double cosh(double x) {
		double d = Math.cosh(x);
		if(Double.isNaN(d)) {
			return x;
		} 
		return d; 
	}
	
	public static double tanh(double x) {
		double d = Math.tanh(x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d; 
	}
	
	public static double coth(double x) {
		double d = 1.0 / tanh(x);
		if(Double.isNaN(d)) {
			return x;
		}
		return d; 
	}
	
	public static double power(double x, double y) {
		double d = Math.pow(x, y);
		if(Double.isNaN(d)) {
			return x;
		} 
		return d; 
	}
	
	public static boolean less(double x, double y) {
		if(x < y) return true;
		return false;
	}
	
	public static boolean greater(double x, double y) {
		return less(y, x);
	}
	
	public static boolean lessEqual(double x, double y) {
		if(x <= y) return true;
		return false;
	}
	
	public static boolean greaterEqual(double x, double y) {
		return lessEqual(y, x);
	}
	
	public static boolean equals(double x, double y) {
		if(Math.abs(x -y) < 1e-7) {
			return true;
		}
		return false;
	}
	
	public static boolean if_else(double x) {
		if(x == Double.MAX_VALUE) {
			return true;
		} else if(x == Double.MIN_VALUE) {
			return false;
		} else {
			throw new IllegalArgumentException("Cannot calculate if_else operation");
		}
	}
	
	public static int if_elif_else(double x, double y) { //0 for first, 1 for second 2 for third
		if(x == Double.MAX_VALUE) {
			return 0;
		} else if(y == Double.MAX_VALUE) {
			return 1;
		} else {
			return 2;
		}
	}
	
}
