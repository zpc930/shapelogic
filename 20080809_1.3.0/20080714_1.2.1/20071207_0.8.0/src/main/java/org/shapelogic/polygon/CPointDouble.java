package org.shapelogic.polygon;

import static org.shapelogic.util.MapOperations.getPointWithDefault;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Set;

import org.shapelogic.util.Constants;

/** Simple point that is comparable and has arithmetic operations. 
 * 
 * Sub classed from Java2D Point2D.Double
 * 
 * @author Sami Badawi
 *
 */
public class CPointDouble extends Point2D.Double implements IPoint2D {
	private static final long serialVersionUID = 1L;

	public CPointDouble(Point2D point) {
		super(point.getX(),point.getY());
	}
	public CPointDouble(double x, double y) {
		super(x,y);
	}
	public CPointDouble() {
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
	
	/* (non-Javadoc)
	 * @see org.shapelogic.polygon.CPoint2D#minus(java.awt.geom.Point2D)
	 */
	public IPoint2D minus(IPoint2D that) {
		translate(-that.getX(), -that.getY());
		return this;
	}

	private void translate(double x, double y) {
		setLocation(getX()+x, getY()+y);
	}
	/* (non-Javadoc)
	 * @see org.shapelogic.polygon.CPoint2D#add(java.awt.geom.Point2D)
	 */
	public IPoint2D add(IPoint2D that) {
		translate((int)that.getX(), (int)that.getY());
		return this;
	}

	/* (non-Javadoc)
	 * @see org.shapelogic.polygon.CPoint2D#multiply(double)
	 */
	public IPoint2D multiply(double multiplier) {
		setLocation((int)getX() * multiplier, (int)(getY() * multiplier));
		return this;
	}

	/* (non-Javadoc)
	 * @see org.shapelogic.polygon.CPoint2D#isNull()
	 */
	public boolean isNull() {
		return Math.abs(getX()) <= Constants.PRECISION && Math.abs(getX()) <= Constants.PRECISION;
	}
	
	/* (non-Javadoc)
	 * @see org.shapelogic.polygon.CPoint2D#toDoubleArray()
	 */
	public double[] toDoubleArray() {
		return new double[] {getX(),getY()};
	}

	@Override
	public IPoint2D max(IPoint2D that) {
		if (this.compareTo(that) >= 0)
			return this;
		else
			return that;
	}
	@Override
	public IPoint2D min(IPoint2D that) {
		if (this.compareTo(that) <= 0)
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
		setLocation(Math.round(getX()),Math.round(getY()));
		return this;
	}
	@Override
	public IPoint2D copy() {
		return (IPoint2D) clone();
	}

	@Override
	public String toString() {
		return "[CPointDouble: " + getX() + ", " + getY() + "]"; 
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
		return new CPointDouble(-getY(), getX());
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
}
