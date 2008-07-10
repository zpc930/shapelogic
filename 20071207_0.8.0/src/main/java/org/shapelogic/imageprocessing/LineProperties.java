package org.shapelogic.imageprocessing;

import java.util.HashSet;
import java.util.Set;

import org.shapelogic.logic.LazyCalc;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.Calculator2D;
import org.shapelogic.util.DoubleCalculations;
import org.shapelogic.util.LineType;

/** LineProperties contains properties that are important for a line
 * when you are dealing with curved multi line.
 * 
 * @author Sami Badawi
 *
 * Properties to keep track of
 * Min and max point
 * Number of positive, almost zero and negative pixels
 * Sum of positive and sum of negative distances
 * If direction change for the 2 adjacent point is different sign
 * 
 * Assumptions
 * I do not think that this class is going be reused
 * 
 */
public class LineProperties implements LazyCalc<Set<LineType> > {
	public static int STRAIGHT_LIMIT = 50; //straight 
	
	public int pixelsWithPositiveDistance;
	public int pixelsWithNegativeDistance;
	public int pixelsWithAlmostZeroDistance;
	public int areaPositiveDistance;
	public int areaNegativeDistance;
	private int _allPixels;
	public int maxPositiveDist = 0;
	public int maxPositiveIndex = 0;
	CPointInt maxPositivePoint = new CPointInt();
	public int maxNegativeDist = 0;
	public int maxNegativeIndex = 0;
	public CPointInt maxNegativePoint = new CPointInt();
	public double angle;
	private boolean _dirty = true;
	public LineType _lineType;
	private Set<LineType> _calcValue = new HashSet<LineType>();
	public CPointInt startPoint;
	public CPointInt relativeVector;
	public CPointInt orthogonalVector; // orthogonal
	public double lengthOfDistanceUnit;
	
	/** a box that is 10% of the length  */
	private int straightAreaLimit;
	private int archAreaLimit;

	public double lastDist;
	public double nextDist;

	public boolean inflectionPoint; //inflection
	
	@Override
	public void setup() {
	}
	
	void preCalc() {
		_allPixels = pixelsWithPositiveDistance	+ pixelsWithNegativeDistance + 
		pixelsWithAlmostZeroDistance;
		double lineLength = Math.max(relativeVector.distanceFromOrigin(), _allPixels * 0.5);
		
		straightAreaLimit = (int)(lengthOfDistanceUnit * lineLength * Math.max(1, lineLength * 0.03));
		archAreaLimit = straightAreaLimit/16; 
	}
	
	@Override
	public Set<LineType> calc() {
		preCalc();
		calcLineType();
		if (isConcaveArch()) {
			_calcValue.add(LineType.CONCAVE_ARCH);
		}
		if (inflectionPoint) {
			_calcValue.add(LineType.INFLECTION_POINT);
		}
		return _calcValue;
	}

	@Override
	public Set<LineType> getCalcValue() {
		if (isDirty())
			calc();
		return _calcValue;
	}
	
	/** The main LineType for a line there are 3 options: straight, arch, wave.
	 */
	public LineType calcLineType() {
		if (isStraight()) _lineType = LineType.STRAIGHT;
		else if (isCurveArch()) _lineType = LineType.CURVE_ARCH;
		else _lineType = LineType.WAVE;
		_dirty = false;
		_calcValue.add(_lineType);
		return _lineType;
	}

	/** Same unnormalized point distance to line used in splitting line.
	 * 
	 * @param point an input 
	 * @return unnormalized distance to the line from the star to end point
	 */
	public double distanceToPoint(CPointInt point) {
		if (orthogonalVector == null)
			orthogonalVector = (CPointInt) Calculator2D.hatPoint(relativeVector);
		double distanceOfStartPoint = Calculator2D.dotProduct(orthogonalVector,startPoint);
		double distanceOfPoint = Calculator2D.dotProduct(orthogonalVector,startPoint);
		return distanceOfPoint - distanceOfStartPoint;
	}
	
//Getter and setter part
	
	/** To be straight
	 * the diagonal variation can only be 0.1 time the length of the line.
	 * 
	 * This could done by 
	 * 1: Max distance
	 * 2: Average area outside the line
	 * 
	 * What is better?
	 * 2 should be more robust, let me start by doing that
	 * 
	 * So I need to know the length of the line and the length of the hat vector that should be the same
	 * 
	 */
	boolean isStraight() {
		if (areaNegativeDistance + areaPositiveDistance < straightAreaLimit)
			return true;
		return false;
	}

	/** To be an curve arch
	 * only one side can be represented.
	 * 
	 */
	boolean isCurveArch() {
		if ((areaNegativeDistance <= archAreaLimit) ^ (areaPositiveDistance <= archAreaLimit))
			return true;
		return false;
	}

	/** lineType needs to be set first. */
	public boolean isConcaveArch() {
		if (_lineType != LineType.CURVE_ARCH)
			return false;
		if (!DoubleCalculations.sameSign(lastDist,nextDist) )
			return true;
		if (areaPositiveDistance > areaNegativeDistance) {
			return DoubleCalculations.sameSign(areaPositiveDistance,lastDist);
		}
		else {
			return DoubleCalculations.sameSign(areaNegativeDistance,lastDist);
		}
	}
	
	@Override
	public boolean isDirty() {
		return _dirty;
	}
}
