package org.shapelogic.polygon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.shapelogic.util.DoubleCalculations.doubleZero;
import static org.shapelogic.util.DoubleCalculations.isEven;
import static org.shapelogic.util.MapOperations.getPointWithDefault;

/** For an undirected line
 * 
 * I think this is made to be immutable, but points are not.
 * But in order to have the sort work for lines I have to assume that the 
 * points in it are not moving. 
 * 
 * @author Sami Badawi
 *
 */
public class CLine implements ILine2D, PointReplacable<CLine> {
	private static final int HORIZONTAL_VERTICAL_RELATION = 10;
	private IPoint2D _start;
	private IPoint2D _end;
	
	public CLine(IPoint2D p1, IPoint2D p2) {
		_start = p1;
		_end = p2;
	}

	static public CLine makeUnordered(IPoint2D p1, IPoint2D p2) {
		if (p1.compareTo(p2) < 1) {
			return new CLine(p1,p2);
		}
		else {
			return new CLine(p2,p1);
		}
	}

	@Override
	public IPoint2D getEnd() {
		return _end;
	}

	@Override
	public IPoint2D getStart() {
		return _start;
	}

	@Override
	public int compareTo(ILine2D that) {
		int result = getStart().compareTo(that.getStart());
		if (result != 0)
			return result;
		result = getEnd().compareTo(that.getEnd());
		return result;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		if (_start != null)
			result = _start.hashCode(); 
		if (_end != null)
			result = _end.hashCode() * 17; 
		return result;
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (!(that instanceof CLine))
			return false;
		CLine thatCLine = (CLine) that;
		return _start.equals(thatCLine.getStart()) && _end.equals(thatCLine.getEnd());
	}
	
	public IPoint2D relativePoint() {
		return _end.copy().minus(_start);
	}
	
	/** Should return numbers in the range 0 to PI
	 * 
	 * @return
	 */
	public double angle() {
		IPoint2D relativePoint = relativePoint();
		return relativePoint.angle();
	}
	
	public boolean isVertical() {
		IPoint2D relativePoint = relativePoint();
		if (Math.abs(relativePoint.getX()) * HORIZONTAL_VERTICAL_RELATION <= Math.abs(relativePoint.getY()) )
			return true;
		return false;
	}
	
	public boolean isHorizontal() {
		IPoint2D relativePoint = relativePoint();
		if (Math.abs(relativePoint.getX()) >= Math.abs(relativePoint.getY()) * HORIZONTAL_VERTICAL_RELATION)
			return true;
		return false;
	}
	
	public boolean isPoint() {
		IPoint2D relativePoint = relativePoint();
		return  doubleZero(relativePoint.getX()) && doubleZero(relativePoint.getY());
	}
	
	public double distance() {
		double xDist = getStart().getX() - getEnd().getX();
		double yDist = getStart().getY() - getEnd().getY();
		return Math.sqrt(xDist*xDist + yDist*yDist);
	}
	
	@Override
	public String toString() {
		return "[Line: " + _start.toString() + "," + _end.toString() + "]"; 
	}
	
	public static List<CLine> filterHorizontal(Collection<CLine> inputLines) {
		List<CLine> result = new ArrayList<CLine>();
		for (CLine line: inputLines) {
			if (line.isHorizontal())
				result.add(line);
		}
		return result;
	} 
	
	public static List<CLine> filterVertical(Collection<CLine> inputLines) {
		List<CLine> result = new ArrayList<CLine>();
		for (CLine line: inputLines) {
			if (line.isVertical())
				result.add(line);
		}
		return result;
	}

	@Override
	public CLine replacePointsInMap(Map<IPoint2D, IPoint2D> pointReplacementMap,
			AnnotatedShapeImplementation annotatedShape) {
		IPoint2D newStartPoint = getPointWithDefault(pointReplacementMap,getStart());
		IPoint2D newEndPoint = getPointWithDefault(pointReplacementMap,getEnd());
		CLine newLine = null;
		if (newStartPoint == getStart() && newEndPoint == getEnd())
			newLine = this;
		else
			newLine = CLine.makeUnordered(newStartPoint,newEndPoint);
		if (!newLine.isPoint()) {
			getStart().replacePointsInMap(pointReplacementMap,annotatedShape); 
			getEnd().replacePointsInMap(pointReplacementMap,annotatedShape);
			Set<Object> annotationForLine = null; 
			if (annotatedShape != null)
				annotationForLine = annotatedShape.getAnnotationForShapes(this);
			if (annotationForLine != null) {
				annotatedShape.putAllAnnotation(newLine, annotationForLine); 
			}
		}
		return newLine;
	}
	
	/** Just order the points in this line alphabetically */
	public CLine orderedLine() {
		if (getStart().compareTo(getEnd()) < 1) 
			return this;
		else
			return new CLine(getEnd(),getStart());
	}
	
	/** Check if the points in this line is ordered alphabetically */
	public boolean isLineOrdered() {
		return getStart().compareTo(getEnd()) < 1; 
	}

	@Override
    public IPoint2D getCenter() {
    	IPoint2D center = _start.copy().add(_end);
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

	@Override
	public double getDiameter() {
		return distance();
	}

	public CLine lineStartingAtPoint(IPoint2D point) {
		if (getStart().equals(point))
			return this;
		else
			return oppositeDirectionLine(); // opposite Direction
	}

	public CLine oppositeDirectionLine() {
		return new CLine(_end,_start);
	}
}
