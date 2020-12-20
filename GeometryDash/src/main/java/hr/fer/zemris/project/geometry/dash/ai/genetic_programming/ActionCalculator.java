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
		return Math.sqrt(x);
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
		double d = 1 / atan(x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d;
	}
	
	public static double asin(double x) {
		double d = Math.asin(x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d; 
	}
	
	public static double acos(double x) {
		double d = Math.acos(x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d; 
	}
	
	public static double atan(double x) {
		double d = Math.atan(x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d; 
	}
	
	public static double actg(double x) {
		double d = Math.atan(1 / x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d; 
	}

	public static double sinh(double x) {
		double d = Math.sinh(x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d; 
	}
	
	public static double cosh(double x) {
		double d = Math.cosh(x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d; 
	}
	
	public static double tanh(double x) {
		double d = Math.tanh(x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d; 
	}
	
	public static double coth(double x) {
		double d = 1 / tanh(x);
		if(Double.valueOf(d).isNaN()) {
			return x;
		} 
		return d; 
	}
	
	public static double power(double x, double y) {
		double d = Math.pow(x, y);
		if(Double.valueOf(d).isNaN()) {
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
	
}
