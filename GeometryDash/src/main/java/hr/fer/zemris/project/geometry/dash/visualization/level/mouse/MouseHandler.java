package hr.fer.zemris.project.geometry.dash.visualization.level.mouse;

import javafx.scene.input.MouseButton;

/**
 * Stores mouse position and length of drag when level editor is active. Implements Singleton design pattern
 * @author Andi Škrgat
 *
 */
public class MouseHandler {

	/**
	 * Private reference to this object
	 */
	private static final MouseHandler mouseHandler = new MouseHandler();
	
	/**
	 * This way, it is always returned same object
	 * @return instance of mouse handler
	 */
	public static MouseHandler getInstance() {
		return mouseHandler;
	}
	
	/**
	 * Private constructor
	 */
	private MouseHandler() {
		
	}
	
	/**
	 * Saves which button was pressed. If no button was pressed then value is null
	 */
	private MouseButton mousePressedButton = null;
	
	/**
	 * Saves if mouse was dragged
	 */
	private boolean mouseDragged = false;
	
	
	/**
	 * Mouse position x
	 */
	private double mouse_x;
	
	/**
	 * Mouse position y
	 */
	private double mouse_y;
	
	/**
	 * Length of drag in x direction
	 */
	private double deltaDrag_x;
	
	/**
	 * Length of drag in y direction
	 */
	private double deltaDrag_y;

	/**
	 * @return the mouse_x
	 */
	public double getMouse_x() {
		return mouse_x;
	}

	/**
	 * @param mouse_x the mouse_x to set
	 */
	public void setMouse_x(double mouse_x) {
		this.mouse_x = mouse_x;
	}

	/**
	 * @return the mouse_y
	 */
	public double getMouse_y() {
		return mouse_y;
	}

	/**
	 * @param mouse_y the mouse_y to set
	 */
	public void setMouse_y(double mouse_y) {
		this.mouse_y = mouse_y;
	}

	/**
	 * @return the deltaDrag_x
	 */
	public double getDeltaDrag_x() {
		return deltaDrag_x;
	}

	/**
	 * @param deltaDrag_x the deltaDrag_x to set
	 */
	public void setDeltaDrag_x(double deltaDrag_x) {
		this.deltaDrag_x = deltaDrag_x;
	}

	/**
	 * @return the deltaDrag_y
	 */
	public double getDeltaDrag_y() {
		return deltaDrag_y;
	}

	/**
	 * @param deltaDrag_y the deltaDrag_y to set
	 */
	public void setDeltaDrag_y(double deltaDrag_y) {
		this.deltaDrag_y = deltaDrag_y;
	}

	/**
	 * @return the mousePressedButton
	 */
	public MouseButton getMousePressedButton() {
		return mousePressedButton;
	}

	/**
	 * @param mousePressedButton the mousePressedButton to set
	 */
	public void setMousePressedButton(MouseButton mousePressedButton) {
		this.mousePressedButton = mousePressedButton;
	}

	/**
	 * @return the mouseDragged
	 */
	public boolean isMouseDragged() {
		return mouseDragged;
	}

	/**
	 * @param mouseDragged the mouseDragged to set
	 */
	public void setMouseDragged(boolean mouseDragged) {
		this.mouseDragged = mouseDragged;
	}
	
	
}
