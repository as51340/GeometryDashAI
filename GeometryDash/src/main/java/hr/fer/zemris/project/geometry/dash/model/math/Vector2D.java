package hr.fer.zemris.project.geometry.dash.model.math;

import com.google.gson.annotations.Expose;

import static java.lang.Math.*;

/**
 * Represents a position on the screen, using the x and y coordinates
 * @author Damjan, Andi
 */
public class Vector2D {

    /**
     * x coordinate of the point
     */
	@Expose
    private double x;
    
    /**
     * y coordinate of the point
     */
	@Expose
    private double y;

    /**
     * Inits position
     * @param x x coordinate
     * @param y y coordinate
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

//    dragi bože ovo je neefikasno
//
//    /**
//	 * Rotates vector in counterclockwise direction if angle is > 0 or clockwise if angle is < 0. Magnitude of vector remains the same.
//	 * @param angle angle in radians.
//	 */
//	public void rotate(double angle) {
//		double a1 = 0;
//		if(Math.abs(x)< 1e-7 ) {
//			if(y > 0) {
//				a1 = Math.PI / 2;
//			} else if(y < 0) {
//				a1 = 1.5 * Math.PI;
//			}
//		} else {
//			a1 = (Math.atan(y / x));
//			//System.out.println(a1);
//			if(x < 0 && y > 0) {
//				a1 += Math.PI;
//			} else if(x < 0 && y < 0) {
//				a1 += Math.PI;
//			} else if(x > 0 && y < 0) {
//				a1 += 2* Math.PI;
//			} else if(y == 0 && x < 0) {
//				a1 += Math.PI;
//			}
//		}
//		double a2 = a1 + angle;
//		if(a2 > 2  * Math.PI) {
//			while(a2 > 2 * Math.PI) {
//				a2 -= 2 * Math.PI;
//			}
//		} else if(a2 < 0) {
//			while(a2 < 0) {
//				a2 += 2* Math.PI;
//			}
//		}
//		double length = Math.sqrt(x * x + y * y);
//		if(Math.abs(Math.PI / 2 - a2) < 1e-7) {
//			this.x  = 0;
//			this.y = length;
//			return;
//		} else if(Math.abs(Math.PI * 1.5 - a2) < 1e-5) {
//			this.x = 0;
//			this.y = -length;
//			return;
//		} else {
//			double x2 = length / Math.sqrt(1 + Math.tan(a2) * Math.tan(a2));
//			//System.out.println(x2);
//			double y2 = (Math.abs(Math.tan(a2)) * length) / Math.sqrt(1 + Math.tan(a2) * Math.tan(a2));
//			if(Math.abs(a2) < 1e-7) {
//				this.x = x2;
//				this.y = 0;
//				return;
//			} else if(Math.abs(Math.PI - a2) < 1e-7) {
//				this.x = -x2;
//				this.y = 0;
//				return;
//			} else {
//				if(a2 > Math.PI / 2 && a2 < Math.PI) {
//					x2 *= -1;
//				} else if(a2 > Math.PI && a2 < Math.PI * 1.5) {
//					x2 *= -1;
//					y2 *= -1;
//				} else if(a2 > Math.PI * 1.5 && a2 < 2 * Math.PI) {
//					y2 *= -1;
//				}
//			}
//			this.x = x2;
//			this.y = y2;
//		}
//	}

	/**
	 * Returns the length (absolute value) of the vector
	 * @return the length of the vector
	 */
	public double getLength() {
		return hypot(x, y);
	}

	/**
	 * Returns the angle of the vector
	 * @return angle of the vector in radians
	 */
	public double getAngle() {
		double angle = atan2(y, x);
		if (angle < 0) {
			angle += (2 * PI);
		}
		return angle;
	}

	/**
	 * Rotates vector in counterclockwise direction if angle is > 0 or clockwise if angle is < 0.
	 * Magnitude of vector remains the same.
	 * @param angle angle in radians.
	 */
	public void rotate(double angle) {
		double currentAngle = getAngle();
		double currentLength = getLength();
		currentAngle += angle;
		x = currentLength * cos(currentAngle);
		y = currentLength * sin(currentAngle);
	}
	
	/**
	 * @param angle angle in radians, if > 0 than rotation is in counterclockwise direction, else in clockwise direction.
	 * @return new Vector2D that is rotated by angle radians.
	 */
	public Vector2D rotated(double angle) {
		Vector2D v = new Vector2D(this.x, this.y);
		v.rotate(angle);
		return v;
	}
	
	/**
	 * Scaling vector means keeping its orientation but changing its length by a scale factor.
	 * @param scaler scale factor
	 */
	public void scale(double scaler) {
		this.x = this.x * scaler;
		this.y = this.y * scaler;
	}
	
	/**
	 * Scales vector by a scaler but doesn't change this one.
	 * @param scaler scale factor
	 * @returns new scaled vector.
	 */
	public Vector2D scaled(double scaler) {
		Vector2D v = new Vector2D(this.x, this.y);
		v.scale(scaler);
		return v;	
	}
	
	/**
	 * Translation of vector by another vector doesn't do anything to the vector. However coordinates of point are translated. 
	 * @param offset vector with which are translating vector
	 */
	public void translate(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * Translated vector is always the same vector so method doesn't do anything concrete to vector but changes coordinates of given point. 
	 * @param offset another vector with which we are translating this vector
	 * @returns new translated vector
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D v = new Vector2D(this.x,this.y);
		v.translate(offset);
		return v;
	}
		
 
    /**
     * Sets both coordinates of the point to the given values
     * @param x new x coordinate
	 * @param y new y coordinate
     */
    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets x coordinate
     * @param x x coordinate
     */
    public void setX(double x) {
    	this.x = x;
    }
    
    /**
     * Sets y coordinate
     * @param y y coordinate
     */
    public void setY(double y) {
    	this.y = y;
    }

    /**
     * Returns both coordinates of the point
     * @return array with two items - x and y coordinate
     */
    public double[] getPos() {
		return new double[]{x, y};
    }
    
    /**
	 * Returns a copy of this {@link Vector2D}
	 * @return new Vector2D with same real components
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

    /**
	 * Returns a new {@link Vector2D} with components opposite to this one
	 * @return new Vector2D with components opposite to this one
	 */
	public Vector2D reversed() {
		return scaled(-1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}
	
	
}

