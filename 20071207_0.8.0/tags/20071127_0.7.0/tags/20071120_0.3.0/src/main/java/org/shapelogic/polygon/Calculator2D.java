package org.shapelogic.polygon;

import org.apache.commons.math.linear.InvalidMatrixException;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.shapelogic.util.Constants;
import org.shapelogic.util.DoubleCalculations;
import static org.shapelogic.util.DoubleCalculations.*;

/** Calculator for simple 2D
 * 
 * There are a lot of small calculations connected with 2D that should not be 
 * thrown in Point and Line classes, but belong in a utility class.
 * 
 * I might move more stuff into this.
 * 
 * @author Sami Badawi
 *
 */
public class Calculator2D {
	
	/** Hat is really a vector operator, I consider a Point a vector here
	 *  
	 * This is maybe simple enough to be on Point classes */
	static public IPoint2D hatPoint(IPoint2D inPoint) {
		IPoint2D outPoint = inPoint.copy();
		outPoint.setLocation(-inPoint.getY(), inPoint.getX());
		return outPoint;
	}
	
	/** A cosine to the angle between times the 2 vector lengths */
	static public double dotProduct(IPoint2D inPoint1, IPoint2D inPoint2) {
		return inPoint1.getX() * inPoint2.getX() + inPoint1.getY() * inPoint2.getY();
	}
	
	/** A sine from the first to the second times the 2 vector lengths  
	 * Not a real cross product, but the length of the cross product vector */
	static public double crossProduct(IPoint2D inPoint1, IPoint2D inPoint2) {
		return dotProduct(hatPoint(inPoint1), inPoint2);
	}
	
	/** This is signed */
	static public double distanceOfPointToLine(IPoint2D point, CLine line) {
		IPoint2D orthogonalVector = hatPoint(line.relativePoint());
		return dotProduct(orthogonalVector, point.copy().minus(line.getStart()));
	}
	
	static public CLine scaleLineFromStartPoint(CLine line, double length) {
		CLine resultLine = new CLine(line.getStart(), line.getStart().copy().add(line.relativePoint().multiply(length)));
		return resultLine;
	}
	
	static public CLine pointToLine(IPoint2D point) {
		return new CLine(point.copy().minus(point),point);
	}

	static public IPoint2D projectionOfPointOnLine(IPoint2D point, CLine line) {
		CLine lineFromStartToPoint = new CLine(line.getStart(), point);
		IPoint2D relLine = line.relativePoint();
		double dot = dotProduct(lineFromStartToPoint.relativePoint(), relLine);
		CLine projectedLine = scaleLineFromStartPoint(line, dot / line.distance());
		IPoint2D projectedPoint = projectedLine.getEnd();
		if (point instanceof CPointInt)
			return toCPointIntIfPossible(projectedPoint);
		return projectedPoint;
	}
	
	static public CLine inverseLine(CLine line) {
		return new CLine(line.getEnd(),line.getStart());
	}

	static public CLine addLines(CLine line1, CLine line2) {
		return new CLine(line1.getStart(), line1.getEnd().copy().add(line2.relativePoint()));
	}
	
	/** 
	 * What should I do about integer based points that does not have a  
	 * @param point
	 * @return
	 */
	static public IPoint2D unitVector(IPoint2D point) {
		IPoint2D result = null;
		if (point instanceof CPointInt) {
			if (point.isOnAxis())
				result = point.copy();
			else
				result = new CPointDouble(point.getX(),point.getY());
		}
		return result.multiply(1/point.distanceFromOrigin());
	}
	
	static boolean isPointIntBased(IPoint2D point) {
		if (point instanceof CPointInt) 
			return true;
		int x = (int) point.getX();
		int y = (int) point.getY();
		return doubleEquals(point.getX(),x) && doubleEquals(point.getY(),y);
	}
	
	static CPointDouble toCPointDouble(IPoint2D point) {
		return new CPointDouble(point.getX(),point.getY());
	}
	
	static CPointInt toCPointInt(IPoint2D point) {
		if (point instanceof CPointInt) 
			return (CPointInt) point;
		return new CPointInt((int)point.getX(),(int)point.getY());
	}
	
	static IPoint2D toCPointIntIfPossible(IPoint2D point) {
		if (point instanceof CPointInt) 
			return point;
		if (isPointIntBased(point))
			return new CPointInt((int)point.getX(),(int)point.getY());
		return point;
	}
	
	static public boolean linesParallel(CLine line1, CLine line2) {
		return doubleZero(dotProduct(line1.relativePoint(), hatPoint(line2.relativePoint())));
	}
	
	/** This version is complicated and has a bug. 
	 * 
	 * Make a projection of the start point of line1 on line2 
	 * 
	 * This passes some tests
	 * */
	@Deprecated
	static public IPoint2D intersectionOfLinesGeometric(CLine activeLine, CLine projectionLine) {
		if (activeLine.distance()==0.) {
			return projectionOfPointOnLine(activeLine.getStart(),projectionLine);
		}
		else if (projectionLine.distance()==0.) {
			return projectionOfPointOnLine(projectionLine.getStart(),activeLine);
		}
		CLine startActiveToProjection = new CLine(activeLine.getStart(),projectionLine.getStart());
		if (startActiveToProjection.distance() == 0.0)
			return activeLine.getStart(); //XXX there can be more result points if they are parallel
		if (linesParallel(activeLine,projectionLine))
			return null;
		IPoint2D orthogonalProjection = hatPoint(projectionLine.relativePoint());
		double startActiveToProjectionDistanceuct = dotProduct(startActiveToProjection.relativePoint(), orthogonalProjection);
		double cosine = dotProduct(activeLine.relativePoint(), orthogonalProjection);;
		startActiveToProjectionDistanceuct /= projectionLine.distance();
		CLine start1ToProjectionOn2 = scaleLineFromStartPoint(activeLine, startActiveToProjectionDistanceuct/cosine);
		IPoint2D intersectionPoint = start1ToProjectionOn2.getEnd();
		if (activeLine.getStart() instanceof CPointInt)
			return toCPointIntIfPossible(intersectionPoint);
		return intersectionPoint;
	}
	
	/** Very simple turn the 2 line into a line equation: a * x + b * y = c  
	 *   So a and b is just the hat vector. While c is what you get when you put one 
	 *   of the point into this.
	 * */
	static public IPoint2D intersectionOfLines(CLine line1, CLine line2) {
		IPoint2D intersectionPoint = null;
		IPoint2D vector1 = line1.relativePoint();
		IPoint2D vector2 = line2.relativePoint();
		IPoint2D hat1 = vector1.turn90();
		IPoint2D hat2 = vector2.turn90();
		if (vector1.isNull()) {
			if (vector2.isNull()) {
				if (line1.getStart().equals(line2.getStart()))
					return line1.getStart();
				else
					return null;
			}
			else {
				
			}
		}
		else {
			if (vector2.isNull()) {
				if (line1.getStart().equals(line2.getStart()))
					return line1.getStart();
				else
					return null;
			}
			else {
				double[][] coefficientsData = 
				{{hat1.getX(), hat1.getY()}, 
				 {hat2.getX(), hat2.getY()},};
				RealMatrix coefficients = new RealMatrixImpl(coefficientsData);
				double[] constants = {dotProduct(hat1,line1.getStart()),
						dotProduct(hat2,line2.getStart())};
				double[] solution = null;
				try {
					solution = coefficients.solve(constants);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvalidMatrixException e) {
				}
				if (solution != null) {
					double x = solution[0];
					double y = solution[1];
					if (doubleIsInt(x) && doubleIsInt(y))
						return new CPointInt((int)x,(int)y);
					else
						return new CPointDouble(x,y);
				}
			}
		}
		return intersectionPoint;
	}

	static public boolean pointIsOnLine(IPoint2D point, CLine line) {
		IPoint2D lineVector = line.relativePoint();
		if (lineVector.isNull()) {
			return point.equals(line.getStart());
		}
		IPoint2D orthogonalVector = lineVector.turn90();
		return DoubleCalculations.doubleEquals(dotProduct(orthogonalVector,point), 
				dotProduct(orthogonalVector,line.getStart()));
	}
	
	static public byte directionBetweenNeighborPoints(IPoint2D startPoint, IPoint2D endPoint) {
		IPoint2D directionVector = endPoint.copy().minus(startPoint);
		if (directionVector.isNull())
			return Constants.DIRECTION_NOT_USED;
		if (Math.abs(directionVector.getX()) <= 1 &&
				Math.abs(directionVector.getY()) <= 1) {
			long result = Math.round(directionVector.angle() * 4 / Math.PI);
			if (result < 0)
				result += 8;
			return (byte) result;
		}
		return Constants.DIRECTION_NOT_USED;
	}

	static public byte oppositeDirection(byte direction) {
		direction += 4;
		if (Constants.DIRECTIONS_AROUND_POINT <= direction)
			direction -= Constants.DIRECTIONS_AROUND_POINT;
		return direction;
	}
	
	/**
	 * @return should be between -4 and 4
	 */
	static public byte directionDifference(byte direction1, byte direction2) {
		byte diff = (byte) (direction2 - direction1);
		if (4 < diff )
			diff -= Constants.DIRECTIONS_AROUND_POINT;
		else if (diff < 4)
			diff += Constants.DIRECTIONS_AROUND_POINT;
		return diff; 
	}
	
	static public double angleBetweenLines(double firstAngle, double nextAngle) {
		double result = nextAngle - firstAngle;
		if (result < -Math.PI)
			result += Math.PI * 2;
		else if (Math.PI < result )
			result -= Math.PI * 2;
		return result;
	}

    /** Find a point on a line spanned by 2 other points 
     * 
     * @param part 0 -> minVal, 1 -> maxVal, 0.5 -> middle point
     */
	static public IPoint2D spannedPoint(IPoint2D point1, IPoint2D point2, double fraction) {
		IPoint2D spanned = point2.copy().multiply(fraction).add(point1.copy().multiply(1-fraction));
		return spanned;
	}
}
