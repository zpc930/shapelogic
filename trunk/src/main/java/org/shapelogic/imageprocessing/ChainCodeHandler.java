package org.shapelogic.imageprocessing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.shapelogic.calculation.CalcInvoke;
import org.shapelogic.polygon.AnnotatedShapeImplementation;
import org.shapelogic.polygon.BBox;
import org.shapelogic.polygon.BaseAnnotatedShape;
import org.shapelogic.polygon.CLine;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.Calculator2D;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.MultiLine;
import org.shapelogic.util.Constants;
import org.shapelogic.util.DoubleCalculations;
import org.shapelogic.util.LineType;
import org.shapelogic.util.PointType;

import static org.shapelogic.polygon.Calculator2D.oppositeDirection;

/** Chain Code For MultiLine.
 * 
 * Assume that there is no intersection.
 * 
 * @author Sami Badawi
 */
public class ChainCodeHandler extends BaseAnnotatedShape implements CalcInvoke<MultiLine>
{
	private static final double SQRT_2 = Math.sqrt(2);
	protected static final int CHAIN_CODE_FOR_MULTI_LINE_MAX_LENGTH = 10000;
	protected static final int SHORT_LINE_LENGTH = 3;
	protected static final double LIMIT_FOR_HARD_CORNER = 60 * Math.PI /180; //30 degrees

	protected byte[] _chainCodeForMultiLine = new byte[CHAIN_CODE_FOR_MULTI_LINE_MAX_LENGTH];
	protected int _lastChain;
	protected CPointInt _firstPoint = new CPointInt();
	protected CPointInt _lastPoint; 
	protected MultiLine _multiLine;
	protected boolean _dirty = true;
	protected BBox _bBox;
	protected TreeMap<Integer,CPointInt> _pointMap = new TreeMap<Integer,CPointInt>();
	protected int _accumulatedDirectionChange;
	protected int _accumulatedAbsoluteDirectionChange;
	protected int _diagonalElementCount = 0;
	
	/** What should happen to the last point if the first and the last point is 
	 * the same.
	 * 
	 * I think that there should be 2 points, in the list of points, but as far as
	 * PointProperties I think that there should only be one, maybe I could just 
	 * use the same.
	 * 
	 * How would that work?
	 * I could have a map
	 * 
	 * Another thing is if I count the number of point in a closed multi line 
	 * it will be one too many.
	 */
	protected List<PointProperties> _pointPropertiesList = new ArrayList<PointProperties>();

	/** Line number N should have end point on point number N.
	 *  
	 * The first line should be null if the multi line is open 
	 * and from the last to the first if closed
	 * 
	 * I could possibly also have the first LineProperties have an extra reference at the end   
	 */
	protected List<LineProperties> _linePropertiesList = new ArrayList<LineProperties>();
	protected double _perimeter;
	

	public ChainCodeHandler(AnnotatedShapeImplementation annotatedShape) {
		super(annotatedShape);
	} 
	
	@Override
	/** This reset all the transient values, but not the startPoint.
	 * 
	 * Needs to be called manually
	 * So startPoint always need to be reset, if it is set in the first place.
	 */
	public void setup() {
		_lastChain = Constants.BEFORE_START_INDEX;
		_lastPoint = new CPointInt(); 
		_multiLine = new MultiLine(null); //XXX should be synchronized with polygon
		_pointMap.clear();
		_dirty = true;
		_bBox = new BBox();
		_accumulatedDirectionChange = 0;
		_accumulatedAbsoluteDirectionChange = 0;
		_perimeter = 0;
		_pointPropertiesList.clear();
		_linePropertiesList.clear();
	}

	@Override
	public MultiLine invoke() {
		_pointMap.put(Constants.BEFORE_START_INDEX, _firstPoint);
		addPropertiesForNewPoint(Constants.BEFORE_START_INDEX, _firstPoint, null); 
		_pointMap.put(_lastChain, (CPointInt)_lastPoint.copy());
		handleInterval(Constants.BEFORE_START_INDEX, _lastChain);
		findAccumulatedDirectionChange();
		addPointsToMultiLine();
		handleClosedMultiLines();
		postProcessing();
		_multiLine.invoke();
		_dirty = false;
		return _multiLine;
	}

	@Override
	public MultiLine getValue() {
		if (isDirty())
			invoke();
		return _multiLine;
	}

	/** Extra passes to find more properties.
	 * 
	 * This differs depending on whether the multi line is closed or open */
	private void postProcessing() {
		findChangeOfDirectionForLines();
		findConcaveArches();
		findCornerPoints();
		annotatePointsAndLines();
	}

//find method part
	
	/** Change of direction in multi line.
	 *  
	 * The current line end at the current point
	 * So you can only determine the direction change of the last point
	 * And to determine the direction change sign difference you also need to 
	 * look at previous point, this will be set on the last line. 
	 * 
	 * maybe you have to go past the start point */
	private void findChangeOfDirectionForLines() {
		List<? extends IPoint2D> points = _multiLine.getPoints();
		int numberOfPoints = points.size();
		int firstPointNumber = 1;
		boolean closed = isClosed();
		PointProperties lastPointProperties = null;
		PointProperties previousPointProperties = null;
		LineProperties lastLineProperties = null;
		lastPointProperties = _pointPropertiesList.get(0); //First point
		if (closed) {
			lastLineProperties = _linePropertiesList.get(0); //Line ending in first point
		}
		for (int i=firstPointNumber; i < numberOfPoints; i++) {
			LineProperties currentLineProperties = null; 
			PointProperties currentPointProperties = null; // Only used to pass on
			currentLineProperties = _linePropertiesList.get(i);
			currentPointProperties = _pointPropertiesList.get(i);
			if (currentLineProperties != null && lastLineProperties != null) {
				lastPointProperties.directionChange = Calculator2D.angleBetweenLines(lastLineProperties.angle, currentLineProperties.angle);
			}
			if (previousPointProperties != null) {
				if (DoubleCalculations.oppositeSign(previousPointProperties.directionChange,lastPointProperties.directionChange))
					lastLineProperties.inflectionPoint = true;
			}
			lastLineProperties = currentLineProperties;
			previousPointProperties = lastPointProperties; 
			lastPointProperties = currentPointProperties;
		}
		if (closed && 2 < numberOfPoints) {
			PointProperties firstPointProperties = _pointPropertiesList.get(0);
			PointProperties returnPointProperties = _pointPropertiesList.get(numberOfPoints-2);
			LineProperties returnLineProperties = _linePropertiesList.get(0);
			if (!DoubleCalculations.sameSign(firstPointProperties.directionChange,returnPointProperties.directionChange))
				returnLineProperties.inflectionPoint = true; 
		}
	}

	/** Find the accumulated direction change, the sum of all turns.   
	 */
	public void findAccumulatedDirectionChange() {
		_accumulatedDirectionChange = 0;
		_accumulatedAbsoluteDirectionChange = 0;
        if (_lastChain < 0) //Empty list
            return;
		byte lastDirection = _chainCodeForMultiLine[_lastChain];
		int lastDirectionChange = 0;
        boolean lastDiagonal = false;
		for (int i = 0; i <= _lastChain; i++) {
			byte direction = _chainCodeForMultiLine[i];
			if ((direction & 1) != 0)//Odd number
				_perimeter += SQRT_2;
			else
				_perimeter += 1;
			int directionChange = direction - lastDirection;
			if (4 < directionChange)
				directionChange -= 8;
			else if (directionChange <= -4)
				directionChange += 8;
			//-4 and 4 are both correct, so you have to look at the last direction
			if (4 == directionChange && 0 < lastDirectionChange)
				directionChange -= 8;
            // To prevent over counting perimiter 
            boolean diagonal = ((direction & 1) == 0) && (directionChange == 2 || directionChange == -2);
            if (diagonal && !lastDiagonal) {
                _perimeter += SQRT_2 - 2;
                lastDiagonal = true;
            }
            else
                lastDiagonal = false;
			lastDirectionChange = directionChange; 
			_accumulatedDirectionChange += directionChange;
			_accumulatedAbsoluteDirectionChange  += Math.abs(directionChange);
			lastDirection = direction;
		}
		if (isClosed()) {
			_multiLine.setClosedLineClockWise(_accumulatedDirectionChange > 0);
			if (Math.abs(_accumulatedDirectionChange) != 8 && _accumulatedDirectionChange != 0) {//XXX not sure if 0 is OK
				String message = "Closed curve should have direction change -8 or 8, found: " + _accumulatedDirectionChange;
				if (false)
					throw new RuntimeException(message);
				else
					System.out.println(message);
			}
		}
		else
			_multiLine.setClosedLineClockWise(null);
	}

	/** Concave arch, based on both neighbors. */
	private void findConcaveArches() {
		List<? extends IPoint2D> points = _multiLine.getPoints();
		int lastPointNumber = points.size();
		int firstPointNumber = 0;
		CPointInt lastPoint = null;
		CPointInt nextPoint = null;
		if (isClosed()) {
			lastPointNumber--;
            if (lastPointNumber < 1)
                return;
			lastPoint = (CPointInt) points.get(lastPointNumber - 1);
		}
		for (int i=firstPointNumber; i<lastPointNumber; i++) {
			LineProperties lineProperties = null; 
			if (i < _linePropertiesList.size())
				lineProperties = _linePropertiesList.get(i);
			if (lineProperties == null)
				continue;
			double lastDist = lineProperties.distanceToPoint(lastPoint);
			double nextDist = lineProperties.distanceToPoint(nextPoint);
			lineProperties.lastDist = lastDist;
			lineProperties.nextDist = nextDist;
		}
	}

    /** It is a corner point if you go some pixels away and see how the 
     * distance or direction changes sharply.
     */
	private void findCornerPoints() {
		for (Entry<Integer,CPointInt> entity: _pointMap.entrySet()) {
			int currentPointIndex = entity.getKey();
			CPointInt currentPoint = entity.getValue();
			PointType pointType = findCornerPoint(currentPointIndex);
			if (!PointType.UNKNOWN.equals(pointType)) {
				getAnnotatedShape().putAnnotation(currentPoint, pointType );
			}
		}
		if (isClosed()) {
			boolean hardCorner = false;
			CPointInt inVector = findIntervalVector(_lastChain - SHORT_LINE_LENGTH, _lastChain);
			CPointInt outVector = findIntervalVector(0, 0 + SHORT_LINE_LENGTH);
			double angle = Calculator2D.angleBetweenLines(inVector.angle(), outVector.angle());
			hardCorner = LIMIT_FOR_HARD_CORNER <= Math.abs(angle);
			if (hardCorner)
				getAnnotatedShape().putAnnotation(_firstPoint, PointType.HARD_CORNER );
			else 
				getAnnotatedShape().putAnnotation(_firstPoint, PointType.SOFT_POINT );
		}
	}
		
	/** Distinguish between soft and hard corner point. 
	 * 
	 * This might not work well for very short lines.
	 * 
	 * @param index in chain code
	 * @return true if this is a hard corner point
	 */
	private PointType findCornerPoint(int index) {
		if (SHORT_LINE_LENGTH < index && index < _lastChain - SHORT_LINE_LENGTH) {
			CPointInt inVector = findIntervalVector(index - SHORT_LINE_LENGTH, index);
			CPointInt outVector = findIntervalVector(index, index + SHORT_LINE_LENGTH);
			double angle = Calculator2D.angleBetweenLines(inVector.angle(), outVector.angle());
			if (Math.abs(angle) < LIMIT_FOR_HARD_CORNER)
				return PointType.SOFT_POINT;
			else
				return PointType.HARD_CORNER;
		} 
		return PointType.UNKNOWN;
	}

	/** Find the vector from the start index to the last index. */
	private CPointInt findIntervalVector(Integer startIndex, Integer endIndex) {
		CPointInt currentPoint = new CPointInt();
		for (int i = startIndex + 1; i <= endIndex; i++) {
			byte direction = _chainCodeForMultiLine[i];
			currentPoint.x += Constants.CYCLE_POINTS_X[direction];
			currentPoint.y += Constants.CYCLE_POINTS_Y[direction];
		}
		return currentPoint;
	}

	private void addPointsToMultiLine() {
		int pointsInMultiLine = Constants.BEFORE_START_INDEX;
		for (Entry<Integer,CPointInt> entity: _pointMap.entrySet()) {
			pointsInMultiLine++;
			CPointInt currentPoint = entity.getValue();
			_multiLine.addAfterEnd(currentPoint);
		}
	}
	
	/** For closed there the start and the end point is the same.
	 * 
	 * So the first and the last PointProperties and LineProperties should also me the same
	 */
	private void handleClosedMultiLines() {
		if (!isClosed())
			return;
		List<? extends IPoint2D> points = _multiLine.getPoints();
		int numberOfPoints = points.size();
		numberOfPoints--; //Start point also end point
        if (numberOfPoints < 1)
            return;
		_linePropertiesList.set(Constants.ZERO,_linePropertiesList.get(numberOfPoints));
		_pointPropertiesList.set(Constants.ZERO,_pointPropertiesList.get(numberOfPoints));
	}

	/** When information have been collected use them for annotations.
	 *  
	 * Just run through all the lines and calculate the annotations and set 
	 * them in the annotation structure for the polygon
	 */
	private void annotatePointsAndLines() {
		CPointInt lastPoint = null;
		List<? extends IPoint2D> points = _multiLine.getPoints();
		int numberOfPoints = points.size();
        if (numberOfPoints == 0)
            return;
		int firstPointNumber = 0;
		if (isClosed()) {
			numberOfPoints--; //Start point also end point
            if (numberOfPoints == 0)
                return;
			lastPoint = (CPointInt) points.get(numberOfPoints-1);
		}
		for (int i=firstPointNumber; i < numberOfPoints; i++) {
			CPointInt currentPoint = (CPointInt) points.get(i);
			if (lastPoint != null) {
				CLine currentLine = CLine.makeUnordered(currentPoint, lastPoint);
				LineProperties currentLineProperties = _linePropertiesList.get(i);
				if (currentLineProperties != null) {
					Set<LineType> lineTypes = currentLineProperties.getValue();
					getAnnotatedShape().putAllAnnotation(currentLine, lineTypes);
				}
			}
			lastPoint = currentPoint; 
		}
	}

	/** All lengths are unnormalized.
	 *  
	 * For the orthogonal vector I use the hat operator: (x,y) -> (y,-x) 
	 */
	private Integer handleInterval(Integer startIndex, Integer endIndex) {
		// Do not split anything that is less than 5, unless it is the first
		boolean doNotSplit = false;
		if (endIndex - startIndex < 5 && 
				!startIndex.equals(Constants.BEFORE_START_INDEX) && !endIndex.equals(_lastChain)) 
			doNotSplit = true;
		CPointInt startPoint = _pointMap.get(startIndex);
		CPointInt endPoint = _pointMap.get(endIndex);
		CPointInt relativeVector = (CPointInt) endPoint.copy().minus(startPoint);
		int xCoordinate = endPoint.x - startPoint.x;
		int yCoordinate = endPoint.y - startPoint.y;
//		int unnormalizedDistanceCoordinate = yCoordinate * startPoint.x - xCoordinate * startPoint.y; 
		int currentDist = 0;
		LineProperties lineProperties = new LineProperties();
		lineProperties.angle = relativeVector.angle();
		lineProperties.relativeVector = relativeVector;
		lineProperties.startPoint = startPoint;
		CPointInt splitPoint = null;
		Integer splitPointIndex = null;
		if (!doNotSplit) {
			CPointInt maxNegativePoint = new CPointInt();
			CPointInt currentPoint = (CPointInt) startPoint.copy();
			int[] distanceDifference = null;
			double lengthOfDistanceUnit = relativeVector.distanceFromOrigin();
			if (xCoordinate == 0 && yCoordinate == 0) {
				lengthOfDistanceUnit = 1;
			}
			else {
				distanceDifference = new int[Constants.DIRECTIONS_AROUND_POINT];
				for (int i = 0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
					distanceDifference[i] = Constants.CYCLE_POINTS_X[i] * yCoordinate - 
					Constants.CYCLE_POINTS_Y[i] * xCoordinate;
				}
			}
			lineProperties.lengthOfDistanceUnit = lengthOfDistanceUnit;
			int unnormalizedDistanceInt = (int) Math.ceil(lengthOfDistanceUnit); 
			lengthOfDistanceUnit *= getDistLimit(endIndex + 1 - startIndex);  
			for (int i = startIndex + 1; i <= endIndex; i++) {
				byte direction = _chainCodeForMultiLine[i];
				currentPoint.x += Constants.CYCLE_POINTS_X[direction];
				currentPoint.y += Constants.CYCLE_POINTS_Y[direction];
				if (distanceDifference != null)
					currentDist += distanceDifference[direction];
				else
					currentDist = (int)(((Point)startPoint).distance(currentPoint) * unnormalizedDistanceInt);
				if (Math.abs(currentDist) < unnormalizedDistanceInt) {
					lineProperties.pixelsWithAlmostZeroDistance++;
				}
				else if (currentDist < 0) { 
					lineProperties.pixelsWithNegativeDistance++;
					lineProperties.areaNegativeDistance -= currentDist; 
					if (currentDist < lineProperties.maxNegativeDist) {
						lineProperties.maxNegativeDist = currentDist; 
						maxNegativePoint.setLocation(currentPoint);
						lineProperties.maxNegativeIndex = i;
					}
				}
				else {
					lineProperties.pixelsWithPositiveDistance++;
					lineProperties.areaPositiveDistance += currentDist; 
					if (lineProperties.maxPositiveDist < currentDist) {
						lineProperties.maxPositiveDist = currentDist;
						lineProperties.maxPositivePoint.setLocation(currentPoint);
						lineProperties.maxPositiveIndex = i;
					}
				}
			}
			if (-lineProperties.maxNegativeDist < lineProperties.maxPositiveDist) {
				if (lengthOfDistanceUnit < lineProperties.maxPositiveDist) {
					splitPoint = lineProperties.maxPositivePoint;
					splitPointIndex = lineProperties.maxPositiveIndex;
				}
			}
			else {
				if (lengthOfDistanceUnit < -lineProperties.maxNegativeDist) {
					splitPoint = maxNegativePoint;
					splitPointIndex = lineProperties.maxNegativeIndex;
				}
				
			}
			assert endPoint.equals(currentPoint);
		}
		if (splitPointIndex != null) {
			_pointMap.put(splitPointIndex, splitPoint);
			handleInterval(startIndex, splitPointIndex);
			handleInterval(splitPointIndex, endIndex);
		}
		else {
			addPropertiesForNewPoint(endIndex, endPoint, lineProperties);
		}
		return splitPointIndex;
	}
	
	private void addPropertiesForNewPoint(Integer endPointIndex, 
			CPointInt endPoint, LineProperties lineProperties) {
		_pointMap.put(endPointIndex, (CPointInt)endPoint.copy());
		_linePropertiesList.add(lineProperties);
		PointProperties pointProperties = new PointProperties(); 
		_pointPropertiesList.add(pointProperties);
	}
	
	private void swap(int i) {
		byte swapHolder = _chainCodeForMultiLine[i];
		int otherIndex = _lastChain - i;
		_chainCodeForMultiLine[i] = oppositeDirection(_chainCodeForMultiLine[otherIndex]);
		if (otherIndex != i)
			_chainCodeForMultiLine[otherIndex] = oppositeDirection(swapHolder); 
	}

//Simple interaction part
		
	public boolean addChainCode(byte chainCode) {
		_lastChain++;
		if (_lastChain == _chainCodeForMultiLine.length) //XXX should probably expand the array
		{
			if (false) {
				int maxPoints = _chainCodeForMultiLine.length;
				byte[] xtemp = new byte[maxPoints*2];
				System.arraycopy(_chainCodeForMultiLine, 0, xtemp, 0, maxPoints);
				_chainCodeForMultiLine = xtemp;
			}
			else {
				_lastChain--;
				return false;
			}
		}	
		_chainCodeForMultiLine[_lastChain] = chainCode;
		_lastPoint.translate(Constants.CYCLE_POINTS_X[chainCode], Constants.CYCLE_POINTS_Y[chainCode]);
		_bBox.addPoint(_lastPoint);
		return true;
	}

	/** Opposite Direction.
	 */
	public void swapChainCodeInOppositeDirection() {
		for (int i = 0; i <= _lastChain/2; i++) {
			swap(i);
		}
		CPointInt swapHolder = (CPointInt) _firstPoint.copy();
		_firstPoint.setLocation(_lastPoint);
		_lastPoint.setLocation(swapHolder);
		_accumulatedDirectionChange = -_accumulatedDirectionChange;
	}

//Getter and setters part
	
	@Override
	public boolean isDirty() {
		return _dirty;
	}

	/** DistLimit is half based on the diameter of the polygon half on the length 
	 * of the line. */
	protected double getDistLimit(int pixelCountInCurrentLineInterval) {
		double distLimit = (_lastChain + pixelCountInCurrentLineInterval) / 20.; 
		return Math.max(distLimit, 1.);
	}
	
	public void setFirstPoint(IPoint2D firstPoint)
	{
		_firstPoint.setLocation(firstPoint.getX(),firstPoint.getY());
		_lastPoint.setLocation(_firstPoint); 
		_bBox.addPoint(_firstPoint);
	}

	public CPointInt getFirstPoint()
	{
		return _firstPoint;
	}

	public byte[] getChainCodeForMultiLine() {
		return _chainCodeForMultiLine;
	}

	public void setMultiLine(MultiLine line) {
		_multiLine = line;
	}

	/** Last point is a running point so this will not always work. */
	public boolean isClosed() {
		return _lastChain > 2 && _firstPoint.equals(_lastPoint);
	}

	public int getLastChain() {
		return _lastChain;
	}

	public CPointInt getLastPoint() {
		return _lastPoint;
	}

	public double getPerimeter() {
		return _perimeter;
	}

	public void setPerimeter(double perimeter) {
		_perimeter = perimeter;
	}
}
