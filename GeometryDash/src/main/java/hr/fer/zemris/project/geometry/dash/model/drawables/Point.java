package hr.fer.zemris.project.geometry.dash.model.drawables;

/**
 * Represents a point on the screen, using the x and y coordinates
 * @author Damjan
 */
public class Point {

    /**
     * x and y coordinates of the point
     */
    private int x, y;

    /**
     * Returns the x coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Assigns the given value to the x coordinate of the point
     * @param x value to set the x coordinate to
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Assigns the given value to the y coordinate of the point
     * @param y value to set the y coordinate to
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets both coordinates of the point to the given values
     * @param coords array with two items - x and y coordinate
     */
    public void setPos(int[] coords) {
        this.x = coords[0];
        this.y = coords[1];
    }

    /**
     * Returns both coordinates of the point
     * @return array with two items - x and y coordinate
     */
    public int[] getPos() {
        int[] retVal = {x, y};
        return retVal;
    }
}
