package org.shapelogic.polygon;

/**   
 * 
 * @author Sami Badawi
 *
 */
public interface IPoint2D extends Comparable<IPoint2D>, Cloneable, GeometricShape2D, PointReplacable<IPoint2D> {
	
	public void setLocation(double x, double y);

	public IPoint2D minus(IPoint2D that);

	public IPoint2D add(IPoint2D that);

	public IPoint2D multiply(double multiplier);

	public boolean isNull();

	public double[] toDoubleArray();

	public double getX();

	public double getY();
	
	public IPoint2D min(IPoint2D that);

	public IPoint2D max(IPoint2D that);

	public double distance(IPoint2D that);

	public double distanceFromOrigin();

	public IPoint2D round();

	public IPoint2D copy();
	
	public double angle();

	public boolean isOnAxis();

	public boolean isOnDiagonal();

	public IPoint2D turn90();
}