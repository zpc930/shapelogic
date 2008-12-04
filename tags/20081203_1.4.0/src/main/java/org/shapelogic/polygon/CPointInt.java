package org.shapelogic.polygon;

import static org.shapelogic.util.MapOperations.getPointWithDefault;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Set;

/** Simple point that is comparable and has arithmetic operations. 
 * 
 * C Point Int means Comparable Point based on integers
 * 
 * Sub classed from Java2D Point
 * 
 * @author Sami Badawi
 *
 */
public class CPointInt extends Point implements IPoint2D {
	private static final long serialVersionUID = 1L;

	public CPointInt(Point2D point) {
		super((int)point.getX(),(int)point.getY());
	}
	public CPointInt(int x, int y) {
		super(x,y);
	}
	public CPointInt() {
		super();
	}

	@Override
	public int compareTo(IPoint2D that) {
		if (getX() > that.getX()) return 1;
		else if (getX() < that.getX()) return -1;
		else if (getY() > that.getY()) return 1;
		else if (getY() < that.getY()) return -1;
		else return 0;
	}
	
	/** Subtract other point from this */
	public IPoint2D minus(IPoint2D that) {
		translate((int)-that.getX(), (int)-that.getY());
		return this;
	}

	/** Add other point into this */
	public IPoint2D add(IPoint2D that) {
		translate((int)that.getX(), (int)that.getY());
		return this;
	}

	/** Multiply number with each coordinate of this */
	public IPoint2D multiply(double multiplier) {
		setLocation((int)getX() * multiplier, (int)(getY() * multiplier));
		return this;
	}

	/** Test if point is (0,0) */
	public boolean isNull() {
		return 0 == getX() && 0 == getY();
	}
	
	/** Create double array with this info in */
	public double[] toDoubleArray() {
		return new double[] {getX(),getY()};
	}

	@Override
	public IPoint2D max(IPoint2D that) {
		if (this.compareTo(that) <= 0)
			return this;
		else
			return that;
	}
	@Override
	public IPoint2D min(IPoint2D that) {
		if (this.compareTo(that) >= 0)
			return this;
		else
			return that;
	}
	@Override
	public double distance(IPoint2D that) {
		double xDist = getX() - that.getX();
		double yDist = getY() - that.getY();
		return Math.sqrt(xDist * xDist + yDist * yDist);
	}
	@Override
	public IPoint2D round() {
		return this;
	}
	@Override
	public IPoint2D copy() {
		return (IPoint2D) clone();
	}

	public double angle() {
		if (getX() == 0 && getY() == 0 )
			return java.lang.Double.NaN;
		return Math.atan2(getY(), getX());
	}

	@Override
	public boolean isOnAxis() {
		return getX() == 0 || getY() == 0;
	}
	@Override
	public boolean isOnDiagonal() {
		return Math.abs(getX()) == Math.abs(getY());
	}

	@Override
	public double distanceFromOrigin() {
		double xDist = getX();
		double yDist = getY();
		return Math.sqrt(xDist * xDist + yDist * yDist);
	}

	@Override
	public IPoint2D turn90() {
		return new CPointInt((int)-getY(), (int)getX());
	}

	@Override
	public IPoint2D getCenter() {
		return this;
	}
	@Override
	public double getDiameter() {
		return 1.;
	}
	
	@Override
	public IPoint2D replacePointsInMap(
			Map<IPoint2D, IPoint2D> pointReplacementMap,
			AnnotatedShapeImplementation annotatedShape) {
		IPoint2D newPoint = getPointWithDefault(pointReplacementMap,this);
		Set<Object> annotationForOldPoint = null; 
		if (annotatedShape != null)
			annotationForOldPoint = annotatedShape.getAnnotationForShapes(this);
		if (annotationForOldPoint != null) {
			annotatedShape.putAllAnnotation(newPoint, annotationForOldPoint); 
		}
		return newPoint;
	}

	@Override
    public String toString() {
        return "[CPointInt " + x + ", " + y + "]";
    }
}
