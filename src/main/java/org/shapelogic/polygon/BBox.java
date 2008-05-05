package org.shapelogic.polygon;

import static org.shapelogic.util.DoubleCalculations.isEven;

import java.awt.Rectangle;
/** This BBox should work for all underlying types
 * 
 *  I think that this should be immutable
 *  I would have to change the fields to be private and have getters
 * 
 * @author Sami Badawi
 *
 */
public class BBox implements GeometricShape2D {
    public IPoint2D minVal;
    public IPoint2D maxVal;

    public boolean isEmpty() {
    	return minVal == null;
    }

    public void addPoint(IPoint2D pointIn) {
        if (isEmpty()) {
        	minVal = pointIn.copy();
        	maxVal = pointIn.copy();
        }
        else {
          min(pointIn);
          max(pointIn);
        }
    }
    
    public void addPoint(int x, int y) {
    	CPointInt pointIn = new CPointInt(x,y);
        if (isEmpty()) {
        	minVal = pointIn.copy();
        	maxVal = pointIn.copy();
        }
        else {
          min(pointIn);
          max(pointIn);
        }
    }
    
    private void min(IPoint2D pointIn) {
		double minX = Math.min(pointIn.getX(),minVal.getX());
		double minY = Math.min(pointIn.getY(),minVal.getY());
			minVal.setLocation(minX, minY);
	}

    private void max(IPoint2D pointIn) {
		double maxX = Math.max(pointIn.getX(),maxVal.getX());
		double maxY = Math.max(pointIn.getY(),maxVal.getY());
			maxVal.setLocation(maxX, maxY);
	}

	public IPoint2D getCenter() {
    	IPoint2D center = minVal.copy().add(maxVal);
    	if (center instanceof CPointInt) {
    		long x = (int) center.getX();
    		long y = (int) center.getY();
    		if (isEven(x) || isEven(y)) {
    			return new CPointDouble(x * 05, y * 0.5);
    		}
    	}
    	center.multiply(0.5);
    	return center;
    }

    public IPoint2D getDiagonalVector() {
    	IPoint2D diagonal = maxVal.copy().minus(minVal);
    	return diagonal;
    }

    /** A point on the diagonal line 
     * 
     * @param fraction 0 -> minVal, 1 -> maxVal, 0.5 -> middle point
     */
    public IPoint2D getDiagonalVector(double fraction) {
    	IPoint2D diagonal = Calculator2D.spannedPoint(minVal, maxVal, fraction);
    	return diagonal;
    }
  
    @Override 
    public String toString() {
    	StringBuffer sb = new StringBuffer("bbox:");
        sb.append("min=");
        sb.append(minVal.toString());
        sb.append("max=");
        sb.append(maxVal.toString());
        return sb.toString();
    }

	@Override
	public double getDiameter() {
		return getDiagonalVector().distanceFromOrigin();
	}
	
	public Rectangle getRectangle() {
		if (minVal == null)
			return null;
		return new Rectangle(
				(int)minVal.getX(),(int)minVal.getY(),
				(int)maxVal.getX(),(int)maxVal.getY());
	}
	
	/** Defined as x/y */
	public double getAspectRatio() {
		double x =getDiagonalVector().getX();
		double y =getDiagonalVector().getY();
		if (y != 0.0)
			return x/y;
		else
			return Double.POSITIVE_INFINITY;
	}
    
    public void add(BBox bBox) {
        if (isEmpty()) {
        	minVal = bBox.minVal.copy();
        	maxVal = bBox.maxVal.copy();
        }
        else {
            addPoint(bBox.minVal);
            addPoint(bBox.maxVal);
        }
    }
    
}
