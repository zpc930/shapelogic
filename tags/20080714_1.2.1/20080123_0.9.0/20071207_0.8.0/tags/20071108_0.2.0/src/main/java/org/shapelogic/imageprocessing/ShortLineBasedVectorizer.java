package org.shapelogic.imageprocessing;

import static org.shapelogic.polygon.Calculator2D.directionBetweenNeighborPoints;

import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.CircleInterval;
import org.shapelogic.util.Constants;
/** Base class for vectorizers that are using a local short line, to determine when to set point on multi line.
 * This approach was not very successful.
 * @author Sami Badawi
 *
 */
public abstract class ShortLineBasedVectorizer extends BaseVectorizer {

	public static final int MAX_NUMBER_OF_POINTS_IN_SHORT_LINE = 5;
	public static final double ANGLE_DIFFERENCE_LIMIT = Math.PI * 15. / 180.;

	public double _angleLimit = ANGLE_DIFFERENCE_LIMIT;
	protected double _currentAngle;
	protected int _pointsInCurrentShortLine = 0;
	public int _maxPointsInShortLine = MAX_NUMBER_OF_POINTS_IN_SHORT_LINE;
	protected byte _secondUsedDirection = Constants.DIRECTION_NOT_USED;
	/** Try to do short lines and */
	protected CPointInt _startOfShortLinePoint = new CPointInt();
	/** Short to current point */
	protected CPointInt _currentVectorDirection;
	protected PixelTypeCalculator _pixelTypeCalculatorNextPoint = new PixelTypeCalculator();
	protected CircleInterval _currentCircleInterval = new CircleInterval();
	/** Start of current line, this is also the last point saved in the multi line */
	protected CPointInt _firstPointInLine;
	protected byte _firstUsedDirection = Constants.DIRECTION_NOT_USED;

	protected LineType _currentLineType = LineType.UNKNOWN;

	/** Take point off _unfinishedPoints try to start line from that, if nothing is found the remove point  
	 * */
	protected void findMultiLine() {
		if (!findMultiLinePreProcess())
			return;
		while (findNextLinePoint()) {
			if (!multiLineHasGlobalFitness())
				makeNewPoint();
		}
		findMultiLinePostProcess();
	}

	/** Additional check for if new new point to be created
	 * 
	 * @return true if a new point should be created
	 */
	abstract boolean multiLineHasGlobalFitness();
	
	
	protected void findMultiLinePostProcess() {
		makeNewPoint();
		super.findMultiLinePostProcess();
	}

	protected boolean findMultiLinePreProcess() {
		boolean result = super.findMultiLinePreProcess();
		_currentLineType = LineType.UNKNOWN;
		if (result) 
			makeNewPoint();
		return result;
	}

	/** Find the maximum if there is more than one the add them all the unknown list
	 */
	protected byte handleProblematicPoints() {
		//Find and set the type of all the neighbor points
		PixelTypeCalculator localPixelTypeCalculator = new PixelTypeCalculator();
		for (byte i=0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
			int pixelIndexI = _currentPixelIndex + _cyclePoints[i];
			byte pixel = _pixels[pixelIndexI];
			if (pixel == PixelType.PIXEL_FOREGROUND_UNKNOWN.color) {
				findPointType(pixelIndexI, localPixelTypeCalculator);
				pixel = localPixelTypeCalculator.getPixelType().color;
				_pixels[pixelIndexI] = pixel; 
			}
		}
		findPointType(_currentPixelIndex , _pixelTypeCalculator);
		if (_pixelTypeCalculator.highestRankedUnusedIsUnique || _currentPoint.equals(_firstPointInMultiLine))
			return _pixelTypeCalculator.highestRankedUnusedNeighbor;
		else {
			_unfinishedPoints.add(_currentPoint);
		}
		return Constants.DIRECTION_NOT_USED;
	}

	/** Find the maximum if there is more than one the add them all the unknown list
	 */
	private byte handleProblematicPointsAddNeighborPoints() {
		//Find and set the type of all the neighbor points
		PixelTypeCalculator localPixelTypeCalculator = new PixelTypeCalculator();
		for (byte i=0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
			localPixelTypeCalculator.setup();
			int pixelIndexI = _currentPixelIndex + _cyclePoints[i];
			byte pixel = _pixels[pixelIndexI];
			if (pixel == PixelType.PIXEL_FOREGROUND_UNKNOWN.color) {
				findPointType(pixelIndexI, localPixelTypeCalculator);
				pixel = localPixelTypeCalculator.getPixelType().color;
				_pixels[pixelIndexI] = pixel; 
			}
		}
		findPointType(_currentPixelIndex , _pixelTypeCalculator);
		if (_pixelTypeCalculator.highestRankedUnusedIsUnique)
			return _pixelTypeCalculator.highestRankedUnusedNeighbor;
		else {
			for (byte i=0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
				int pixelIndexI = _currentPixelIndex + _cyclePoints[i];
				byte pixel = _pixels[pixelIndexI];
				if ((Constants.BYTE_MASK & pixel) == _pixelTypeCalculator.highestRankedUnusedPixelTypeColor)
					_unfinishedPoints.add(pixelIndexToPoint(pixelIndexI));
			}
			_pixels[_currentPixelIndex] = PixelType.toUsed(_pixels[_currentPixelIndex]); 
		}
		return Constants.DIRECTION_NOT_USED;
	}

	private byte handleProblematicPointsOld() {
		if (_pixelTypeCalculator.getPixelType() == PixelType.PIXEL_JUNCTION &&
			!_currentPoint.equals(_firstPointInLine)) {
		return Constants.DIRECTION_NOT_USED;
		}
		else {
			//Find the type of all the neighbor points, the chose the best one
			PixelTypeCalculator localPixelTypeCalculator = new PixelTypeCalculator();
			byte bestDirection = Constants.DIRECTION_NOT_USED;
			PixelType typeOfBestDirection = PixelType.PIXEL_FOREGROUND_UNKNOWN;
			for (byte i=0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
				int pixelIndexI = _currentPixelIndex + _cyclePoints[i];
				byte pixel = _pixels[pixelIndexI];
				if (pixel == PixelType.PIXEL_FOREGROUND_UNKNOWN.color) {
					findPointType(pixelIndexI, localPixelTypeCalculator);
					pixel = localPixelTypeCalculator.getPixelType().color;
					_pixels[pixelIndexI] = pixel; 
				}
				if (PixelType.isUnused(pixel)) {
					if (bestDirection == Constants.DIRECTION_NOT_USED) {
						bestDirection = i;
						typeOfBestDirection = localPixelTypeCalculator.getPixelType();
					}
					else {
						if (typeOfBestDirection == PixelType.PIXEL_EXTRA_NEIGHBOR) {
							bestDirection = i;
							typeOfBestDirection = localPixelTypeCalculator.getPixelType();
						} 
					}
				}
			}
			return bestDirection;
		}
	}

	@Deprecated
	protected void moveCurrentPointBackwards(byte newDirection) {
		byte startPixelValue = _pixels[_currentPixelIndex];
		_pixels[_currentPixelIndex] = PixelType.toUnused(startPixelValue);
		_currentPixelIndex += _cyclePoints[newDirection];
//		_lastPointInCurrentMultiLine--;
		_pointsInCurrentShortLine--;
		_currentPoint.x += Constants.CYCLE_POINTS_X[newDirection];
		_currentPoint.y += Constants.CYCLE_POINTS_Y[newDirection];
	}

	/** Make a new point in a multi line after moving one pixel back first
	 * Then move one pixel forwards again at the end.
	 * */
	@Deprecated
	protected void makeNewPointFromLastDirection() {
		byte forwardsDirection = Constants.DIRECTION_NOT_USED;
		moveCurrentPointBackwards(oppesiteDirection(_currentDirection));
		forwardsDirection = _currentDirection;
		_currentPoint = pixelIndexToPoint(_currentPixelIndex); //XXX should not be needed
		getPolygon().addAfterEnd(_currentPoint.copy());
		_firstPointInLine = pixelIndexToPoint(_currentPixelIndex);
		_startOfShortLinePoint.setLocation(_firstPointInLine);
		makeNewPointPostProcess();
		if (forwardsDirection != Constants.DIRECTION_NOT_USED) {
			moveCurrentPointForwards(forwardsDirection);
			_currentDirection = forwardsDirection; 
		}
		_currentPoint = pixelIndexToPoint(_currentPixelIndex);
	}

	/** Hook for creating new short line */
	public void newShortLine() {
		_currentVectorDirection = (CPointInt) _currentPoint.copy().minus(_startOfShortLinePoint);
		_currentAngle = _currentVectorDirection.angle();
		if (!Double.isNaN(_currentAngle))
			_currentCircleInterval.addClosestAngle(_currentAngle);
		resetShortLine();
	}

	protected void resetShortLine() {
		_pointsInCurrentShortLine = 0;
		_startOfShortLinePoint.setLocation(_currentPoint);
	}

	public int getMaxSinceLast() {
		return _maxPointsInShortLine;
	}

	public void setMaxSinceLast(int maxSinceLast) {
		this._maxPointsInShortLine = maxSinceLast;
	}

	public void setAngleLimit(double angleLimit) {
		this._angleLimit = angleLimit;
	}

	/** Change to be an up front check */
	protected boolean lastPixelOk(byte newDirection) {
		if (newDirection == _firstUsedDirection) return true;
		else if (newDirection == _secondUsedDirection || _secondUsedDirection == Constants.DIRECTION_NOT_USED) return true;
		else return false;
	}

	public double getAngleLimit() {
		return _angleLimit;
	}

	@Override
	protected void moveCurrentPointForwards(byte newDirection) {
		super.moveCurrentPointForwards(newDirection);
		if (_firstUsedDirection == Constants.DIRECTION_NOT_USED) {
			_firstUsedDirection = newDirection;
		}
		_pointsInCurrentShortLine++;
	}

	protected void makeNewPoint() {
		getPolygon().addAfterEnd(_currentPoint.copy());
		_firstPointInLine = pixelIndexToPoint(_currentPixelIndex); //If the line has been moved 1 back
		if (_pixels[_currentPixelIndex] == PixelType.BACKGROUND_POINT.color)
			_errorMessage = "Setting point at background point: " + _firstPointInLine;
		makeNewPointPostProcess();
		_currentPoint = pixelIndexToPoint(_currentPixelIndex); //If the line has been moved 1 back
		resetShortLine();
	}

	protected void makeNewPointPostProcess() {
		_firstUsedDirection = Constants.DIRECTION_NOT_USED;
		_currentDirection = Constants.DIRECTION_NOT_USED;
		_currentLineType = LineType.UNKNOWN;
		_secondUsedDirection = Constants.DIRECTION_NOT_USED;
		_pointsInCurrentShortLine = 0;
		_currentCircleInterval = new CircleInterval();
	}

	/** All the objects that needs special version should be created here. */
	protected void internalFactory() {
		_pixelTypeFinder = new PriorityBasedPixelTypeFinder(this);
	}

	/** Get the next point to investigate from _currentPoint
	 * This also contains check if this should cause a new new point to be created.
	 * 
	 * if there is more than one point to chose from add the point to: 
	 * _unfinishedPoints that is a list of points that need to be revisited.
	 *  assumes that _pixelTypeCalculator is set to current point
	 * @return true if there are more points
	 */
	protected boolean findNextLinePoint() {
		//If there is only one direction to go in then do it
		byte newDirection = Constants.DIRECTION_NOT_USED;
		boolean backToStart = false;
		findPointType(_currentPixelIndex, _pixelTypeCalculator);
		if (_pixelTypeCalculator.unusedNeighbors == 1) {
			newDirection =  _pixelTypeCalculator.firstUnusedNeighbor;
			findPointType(_currentPixelIndex + _cyclePoints[newDirection], _pixelTypeCalculatorNextPoint);
		}
		else if (_pixelTypeCalculator.unusedNeighbors == 0) {
			newDirection = directionBetweenNeighborPoints(_currentPoint,_firstPointInMultiLine);
			if (newDirection != Constants.DIRECTION_NOT_USED)
				backToStart = true;
		} else
			newDirection = handleProblematicPoints();
		if (newDirection == Constants.DIRECTION_NOT_USED)
			return false;
		if (lastPixelOk(newDirection)) {
			_currentDirection = newDirection;
			moveCurrentPointForwards(_currentDirection);
		}
		else {
			makeNewPoint();
			_currentDirection = newDirection;
			moveCurrentPointForwards(_currentDirection);
		}
		if (backToStart)
			makeNewPoint();
		return true;
	}

	/** Insert point between current _firstPointInLine and newFirstPoint  
	 * There are currently several things that I am not sure how to handle
	 * look in makeNewPoint()
	 */
	protected void splitLine(CPointInt splitPoint) {
		if (_firstPointInLine != null)
			getPolygon().getCurrentMultiLine().addAfterEnd(splitPoint);
	}
}
