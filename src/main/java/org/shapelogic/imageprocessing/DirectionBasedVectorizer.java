package org.shapelogic.imageprocessing;


import ij.plugin.filter.PlugInFilter;

import java.awt.geom.Point2D;
import java.util.Arrays;

import org.apache.commons.math.stat.StatUtils;
import org.shapelogic.polygon.BBox;
import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.MultiLine;
import org.shapelogic.polygon.ObjectAnnotator;
import org.shapelogic.util.Constants;

/** DirectionBasedVectorizer is a Vectorizer based on direction.
 * 
 *  This is special by using directions as the primary way of finding the path.
 *  
 * @author Sami Badawi
 *
 */
public class DirectionBasedVectorizer extends ShortLineBasedVectorizer implements PlugInFilter {
	protected static final int CHAIN_CODE_FOR_MULTI_LINE_MAX_LENGTH = 10000;
	protected static final int MAX_SHORT_LINE_COUNT = 10000;
	protected static final int NUMBER_OF_SAME_DIRECTION_TO_START_STRAIGHT =10;
	
	/** All the direction from _firstPointInLine, so the direction on index 0 
	 * is point one pixel away from _firstPointInLine.
	 */
	protected byte[] _chainCodeForMultiLine = new byte[CHAIN_CODE_FOR_MULTI_LINE_MAX_LENGTH];
	
	//index into _directionsWholeLine, not the same as _currentPixelIndex
	protected int[] _directionsCount = new int[Constants.DIRECTIONS_AROUND_POINT];

	protected int _shortLineCount = Constants.BEFORE_START_INDEX;
	protected double[] _shortLineAngles = new double[MAX_SHORT_LINE_COUNT];
	protected double[] _shortLineAngleSpeeds = new double[MAX_SHORT_LINE_COUNT];
	protected double[] _shortLineLength = new double[MAX_SHORT_LINE_COUNT];
	protected int[] _shortLinePointIndex = new int[MAX_SHORT_LINE_COUNT];
	
	int _currentDirectionCount = 0; 
	int _currentDirectionStart = 0; 
	int _currentDirectionEnd = 0; 

	protected int _lastPointInCurrentMultiLine = Constants.BEFORE_START_INDEX;
	
	/** Test that the current direction is close to the last direction. 
	 * */
	@Override
	protected boolean multiLineHasGlobalFitness() {
		//do big test
		if (_pointsInCurrentShortLine >= _maxPointsInShortLine) {
			newShortLine();
			if (_currentLineType == LineType.UNKNOWN) {
				if (isStraight()) {
					splitLine(_currentDirectionStart);
					if (_currentDirectionEnd != _lastPointInCurrentMultiLine)
						splitLine(_currentDirectionEnd);
					else
						_currentLineType = LineType.STRAIGHT_MULTI_LINE;
					
				}
			}
			if ((Double.isNaN(_currentAngle)) || _currentCircleInterval.intervalLength() < _angleLimit)
				return true;
			else
				return false;
		}
		return true;
	}
	
	@Override
	/** If you have straight line first other direction stops the line. 
	 * 
	 * To follow a multi line I think that this should always give true.
	 * But I need some way to stop other. Maybe do it on junction.
	 * Maybe stop short line for better post porcessing.
	 * 
	 * */
	protected boolean lastPixelOk(byte newDirection) {
		if (_currentLineType == LineType.STRAIGHT_MULTI_LINE && 
				newDirection != _firstUsedDirection) {
			return false;
		}
		else if (_pixelTypeCalculator.isLocalMaximum &&
//				! PixelType.PIXEL_EXTRA_NEIGHBOR.equalsIgnore(_pixels[_currentPixelIndex]) && //Do not stop on extra neighbor
				! PixelType.PIXEL_ON_LINE.equalsIgnore(_pixels[_currentPixelIndex + _cyclePoints[newDirection] ]) && //Do not stop when next is just a line point
				!_currentPoint.equals(_firstPointInLine))
			return false;
		else
			return super.lastPixelOk(newDirection);
	}

	/** Test if the last part of the line is straight.
	 */
	protected boolean isStraight() {
		if (NUMBER_OF_SAME_DIRECTION_TO_START_STRAIGHT > _lastPointInCurrentMultiLine - _firstPointInLineIndex) //XXX bad
			return false;
		byte currentDirection = _chainCodeForMultiLine[0];
		_currentDirectionCount = 0; 
		_currentDirectionStart = 0; 
		_currentDirectionEnd = 0; 
		for (int i = _firstPointInLineIndex; i <= _lastPointInCurrentMultiLine; i++) {
			if (_chainCodeForMultiLine[i] == currentDirection) {
				_currentDirectionCount++;
			}
			else {
				if (NUMBER_OF_SAME_DIRECTION_TO_START_STRAIGHT <= _currentDirectionCount) {
					_currentDirectionEnd = i - 1;
					return true;
				}
				currentDirection = _chainCodeForMultiLine[i];
				_currentDirectionCount = 0; 
				_currentDirectionStart = i; 
				_currentDirectionEnd = i;
			}
		}
		_currentDirectionEnd = _lastPointInCurrentMultiLine;
		return NUMBER_OF_SAME_DIRECTION_TO_START_STRAIGHT <= _currentDirectionCount;
	}
	
	/** Test how many of the 8 directions are represented. */
	protected int numberOfDirections(int[] directionsCount) {
		int result = 0;
		for (int i = 0; i < Constants.DIRECTIONS_AROUND_POINT; i++){
			if (directionsCount[i] > 0)
				result++;
		}
		return result;
	}

	@Override
	protected void moveCurrentPointForwards(byte newDirection) {
		if (newDirection == Constants.DIRECTION_NOT_USED)
			return;
		super.moveCurrentPointForwards(newDirection);
		_lastPointInCurrentMultiLine++;
		if (Constants.DIRECTION_NOT_USED != newDirection) {
			if (_lastPointInCurrentMultiLine<0)
				System.out.println("Problem with current point: " + _currentPoint );
			_chainCodeForMultiLine[_lastPointInCurrentMultiLine] = newDirection;
			_directionsCount[newDirection]++;
		}
	}
	
	@Override
	@Deprecated
	protected void moveCurrentPointBackwards(byte newDirection) {
		if (newDirection == Constants.DIRECTION_NOT_USED)
			return;
		super.moveCurrentPointBackwards(newDirection);
		_directionsCount[_chainCodeForMultiLine[_lastPointInCurrentMultiLine]]--;
	}
	
	@Override
	public void newShortLine(){
		super.newShortLine();
		_shortLineCount++;
		_shortLinePointIndex[_shortLineCount] = _lastPointInCurrentMultiLine;
		_shortLineAngles[_shortLineCount] = _currentAngle;
		_shortLineLength[_shortLineCount] = _currentVectorDirection.distanceFromOrigin();
		if (_shortLineCount > 0) {
			double rawAngleDiff = _currentAngle - _shortLineAngles[_shortLineCount-1];
			if (rawAngleDiff < - Math.PI)
				rawAngleDiff += Math.PI * 2;
			else if (Math.PI < rawAngleDiff) {
				rawAngleDiff -= Math.PI * 2;
			}
			_shortLineAngleSpeeds[_shortLineCount] = 
				rawAngleDiff/ _shortLineLength[_shortLineCount];
		}
	}

	@Override
	protected void makeNewPointPostProcess() {
		_firstPointInLineIndex = _lastPointInCurrentMultiLine + 1;
		super.makeNewPointPostProcess();
	}
	
	/** Get angle from direction byte array.
	 * 
	 * directionArray, byte array containing the 8 direction
	 * startDirection, number of first direction to accumulate 
	 * endDirection, number of first direction to accumulate
	 * @return vector as a point of accumulated line
	 */
	public static CPointInt angleFromDirectionArray(byte[] directionArray, 
			int startDirection, int endDirection) {
		if (directionArray == null || directionArray.length < endDirection)
			return null;
		CPointInt resultVector = new CPointInt(); 
		for (int i = startDirection; i <= endDirection; i++){
			byte direction = directionArray[i];
			resultVector.translate(Constants.CYCLE_POINTS_X[direction], Constants.CYCLE_POINTS_Y[direction]);
		}
		return resultVector;
	}

	/** I think that this is messing with the short lines.
	 * 
	 * @param splitIndex
	 */
	protected void splitLine(int splitIndex) 
	{
		if (splitIndex<=0)
			return;
		if (splitIndex < _lastPointInCurrentMultiLine);
//			_lastPointInCurrentMultiLine -= splitIndex;
		else {
			_errorMessage = "Error: _indexToCurrentDirection = " + 
			_lastPointInCurrentMultiLine + "; splitIndex= " + splitIndex;
			System.out.println(_errorMessage);
			Exception ex = new Exception(_errorMessage);
			ex.printStackTrace();
			return;
		}
		CPointInt relativeNewFirstPoint = 
			angleFromDirectionArray(_chainCodeForMultiLine, 0, splitIndex-1);
		CPointInt splitPoint = (CPointInt)_firstPointInMultiLine.copy().add(relativeNewFirstPoint);
		splitLine(splitPoint);
		
		_firstUsedDirection = _chainCodeForMultiLine[splitIndex];
		_secondUsedDirection = Constants.DIRECTION_NOT_USED;
	}
	
	@Override
	protected void findMultiLinePostProcess() {
		if (_pixelTypeCalculator.unusedNeighbors != 0) {
			if (!_currentPoint.equals(_firstPointInLine) && !_pixelTypeCalculator.isLocalMaximum)
				_unfinishedPoints.add((CPointInt)_currentPoint.copy());
		}
		super.findMultiLinePostProcess();
		_numberOfPointsInAllLines += _lastPointInCurrentMultiLine;
		_lastPointInCurrentMultiLine = Constants.BEFORE_START_INDEX;
		_firstPointInLineIndex = _lastPointInCurrentMultiLine + 1; 
//		IPoint2D centerOfCircle = testMultiLineForBeingWholeCircle(_shortLineAngleSpeeds, _shortLineCount);
		testMultiLineForBeingWholeCircle();
		_lastPointInCurrentMultiLine = Constants.BEFORE_START_INDEX; //Wrong if you are starting from last
		Arrays.fill(_directionsCount, 0);
	}

	/** Test if multi line is circle.
	 * 
	 * @param angleSpeedArray
	 * @param realValuesInArray
	 * @return center of circle
	 */
	private IPoint2D testMultiLineForBeingWholeCircle(double[] angleSpeedArray, int realValuesInArray) {
		if (realValuesInArray < 1)
			return null;
		double mean = StatUtils.mean(angleSpeedArray,0,realValuesInArray);
		double variance = StatUtils.variance(angleSpeedArray,0,realValuesInArray);
//		double median = StatUtils.percentile(values,50.);
		double std = Math.sqrt(variance);
		if (Math.abs(std/mean) < 3) { 
			//This is because the angle changes are small and the jumps are big 
			if (_firstPointInLine.distance((Point2D)_currentPoint) < 2 &&
					realValuesInArray >10) {
				BBox bBox = getPolygon().getCurrentMultiLine().getBBox();
				double x = bBox.minVal.getX() + bBox.maxVal.getX();
				x *= 0.5;
				double y = bBox.minVal.getY() + bBox.maxVal.getY();
				y *= 0.5;
				CPointDouble center = new CPointDouble(x,y);
				showMessage("Circle found. With center: " + center);
				return center;
			}
		}
		return null;
	}

	/** Simpler circle finder.
	 * */
	private void testMultiLineForBeingWholeCircle() {
		MultiLine multiLine = getPolygon().getCurrentMultiLine();
		ObjectAnnotator<MultiLine> circleFinder = new CircleFinder(multiLine); 
		if (circleFinder.isAnnotationsFound())
			showMessage("Circle found. With center: " + multiLine.getCenterForCircle());
	}
}
