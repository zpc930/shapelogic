package org.shapelogic.imageprocessing;

import static org.shapelogic.polygon.Calculator2D.directionBetweenNeighborPoints;
import ij.process.ImageProcessor;

import org.shapelogic.logic.LetterTaskFactory;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.Calculator2D;
import org.shapelogic.util.Constants;

/** Vectorizer that is splitting lines based on max distance to line between end points.
 * <br />
 * <p>
 * The main idea is that this will read a whole multi line at a time.
 * Then later it will split it according to max distance of pixels to the line 
 * between start and end point of the multi line.
 * </p> <p>
 * Maybe this could be completely abstracted out, maybe but at that point I 
 * will just take most of this class and turn it into a base class.
 * </p> <p>
 * Always stop on junctions, if there is one junction point use that, but stop after.
 * N points are chosen last.
 * Never go from one N point to another, 
 * unless that the N point is the first point, to handle an X, where you have 4 
 * N points in the center.
 * If you are at a start point then just chose one direction.
 * Can I delegate this to a different object. I always need to find all the 
 * neighbors first. 
 * I might have to know how many N points there are if there are more just
 * add all to _unfinishedPoints. 
 * </p> <p>
 * Treatment of different points:
 * Junction: add to new point, move to first junction. 
 * N points: count, keep track of first.
 * Other: count, keep track of first.
 * Unused: count, keep track of first. I think that is already done.
 * Used: count, keep track of first.
 * </p> <p>
 * For each junction add to unfinished. Go to first junction.
 * If other points are available take first and go to it.
 * If only N point is available, if current point an N and not the first point 
 * stop else go to that.
 * </p> <p>
 * When coming to a new point check if it is a junction if stop if not on 
 * first point. It does not matter if the start point is used or not.
 * I think that at the end check to see if you can go to either a junction 
 * point or to the start point.
 * Also stop if you do not know what to do, at the end of handleProblematicPoints().
 * </p>
 * @author Sami Badawi
 *
 */
public class BaseMaxDistanceVectorizer extends BaseVectorizer {
	protected ChainCodeHandler _chainCodeHandler = null; //new ChainCodeHandler(_annotatedShapeImplementation); 

	/** Take point off _unfinishedPoints try to start line from that, if nothing is found the remove point  
	 * */
	@Override
	protected void findMultiLine() {
		if (!findMultiLinePreProcess())
			return;
		for (int i = 0; true; i++) {
			if (!findNextLinePoint())
				break;
		}
		if (startedInTheMiddleTurnOpposite()) {
			for (int i = 0; true; i++) {
				if (!findNextLinePoint())
					break;
			}
		}
		findMultiLinePostProcess();
	}

	/** started In The Middle Turn Opposite
	 * If there are 2 neighbors and 1 of them are unused 
	 * @return
	 */
	private boolean startedInTheMiddleTurnOpposite() {
		int firstPointInMultiLineIndex = this.pointToPixelIndex(_firstPointInMultiLine);
		PixelTypeCalculator startPixelTypeCalculator = findPointType(firstPointInMultiLineIndex, null);
		byte color = _pixels[firstPointInMultiLineIndex];
//		if (PixelType.
		if (startPixelTypeCalculator.neighbors != 2 || startPixelTypeCalculator.unusedNeighbors != 1)
			return false;
		_chainCodeHandler.swapChainCodeInOppositeDirection();
		CPointInt swapHolder = (CPointInt) _firstPointInMultiLine.copy();
		_firstPointInMultiLine.setLocation(_currentPoint);
		_currentPoint.setLocation(swapHolder);
		_currentPixelIndex = firstPointInMultiLineIndex; 
		return true;
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
		findPointType(_currentPixelIndex, _pixelTypeCalculator);
		//Stop at any junction unless you are just starting
		if (PixelType.PIXEL_JUNCTION.equals(_pixelTypeCalculator.getPixelType())
				&& _chainCodeHandler.getLastChain() > Constants.BEFORE_START_INDEX) {
			newDirection = handleJunction(); 
		} 
		else if (_pixelTypeCalculator.unusedNeighbors == 1) {
			newDirection =  _pixelTypeCalculator.firstUnusedNeighbor;
		}
		else if (_pixelTypeCalculator.unusedNeighbors == 0) {
			newDirection = handleLastUnused();
		} else {
			newDirection = handleProblematicPoints();
			if (newDirection == Constants.DIRECTION_NOT_USED)
				addToUnfinishedPoints((CPointInt)_currentPoint.copy());
		}
		if (newDirection == Constants.DIRECTION_NOT_USED)
			return false;
		_currentDirection = newDirection; //XXX redundant
		moveCurrentPointForwards(_currentDirection);
		_chainCodeHandler.addChainCode(newDirection);
		return true;
	}

	/** Get here if there are no unused directions left
	 * 
	 * Ways to go further:
	 * 
	 * First point is 1 away 
	 * A junction is 1 away
	 * 
	 * And you are not standing on the on the second pixel trying to go back
	 */
	private byte handleLastUnused() {
		boolean isEndPoint = true;
		byte newDirection = Constants.DIRECTION_NOT_USED;
		newDirection = directionBetweenNeighborPoints(_currentPoint,_firstPointInMultiLine);
		if (newDirection != Constants.DIRECTION_NOT_USED && 
				_chainCodeHandler.getLastChain() != Constants.START_INDEX) 
			isEndPoint = false;
		if (isEndPoint) {
			NeighborChecker pointHandle = 
				new NeighborChecker(this,_currentPixelIndex);
			pointHandle.checkNeighbors();
			//If you have taken more than 2 steps you can go back to any junction point
			//maybe this could be expanded 
			//or I could put a constraint in that it cannot go back to the start point
			if (0 < pointHandle.junction.countUsed && 
					Constants.START_INDEX < _chainCodeHandler.getLastChain()) {
				isEndPoint = false;
				newDirection = pointHandle.junction.firstUsedDirection; 
			}
		}
		if (isEndPoint)
		{
			CPointInt endPoint = (CPointInt) _currentPoint.copy();
			getPolygon().putAnnotation(endPoint, GeometricType.PIXEL_LINE_END); 
		}
		return newDirection;
	}

	private byte handleJunction() {
		NeighborChecker pointHandle = 
			new NeighborChecker(this,_currentPixelIndex);
		pointHandle.checkNeighbors();
		if (pointHandle.falseJunction()) {
			//Too complicated set an extra point unless at start
			if (pointHandle.vCornerPoint.count != 1 && pointHandle.extraNeighborPoint.count != 2 
					&& Constants.BEFORE_START_INDEX < _chainCodeHandler.getLastChain())
				return Constants.DIRECTION_NOT_USED;
			byte directionBackToPrevious = Calculator2D.oppositeDirection(_currentDirection); 
			boolean comesFromVPoint = pointHandle.vCornerPoint.firstDirection == directionBackToPrevious;
			if (!comesFromVPoint)
				return pointHandle.vCornerPoint.firstDirection;
			//Coming from the V point select the unused point with biggest 
			//distance from V or distance that is not 90 degrees
			byte directionToVPoint = pointHandle.vCornerPoint.firstDirection;
			for (byte i=0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
				int pixelIndexI = _currentPixelIndex + _cyclePoints[i];
				byte pixel = _pixels[pixelIndexI];
				if (PixelType.isUnused(pixel)) {
					if (2 != Math.abs(Calculator2D.directionDifference(directionToVPoint,(byte)i))) {
						return i;
					}
				}
			}
		}
		CPointInt junctionPoint = (CPointInt)_currentPoint.copy();
		addToUnfinishedPoints(junctionPoint);
		getPolygon().putAnnotation(junctionPoint, GeometricType.PIXEL_JUNCTION);
		return Constants.DIRECTION_NOT_USED;
	}

	@Override
	/**
	 * Junction: add to new point, move to first junction. 
	 * N points: count, keep track of first.
	 * Other: count, keep track of first.
	 * Used: count, keep track of first.
	 * 
	 * Unused: count, keep track of first. This is done in the point finder.
	 */
	protected byte handleProblematicPoints() {
		NeighborChecker pointHandle = 
			new NeighborChecker(this,_currentPixelIndex);
		pointHandle.checkNeighbors();
		//XXX problematic with 2 points next to each other
		if (pointHandle.junction.count > 0 && Constants.START_INDEX != _chainCodeHandler.getLastChain() && 
				_currentDirection != oppesiteDirection(pointHandle.junction.firstDirection)) {
			if (pointHandle.falseJunction())
				return pointHandle.vCornerPoint.firstDirection;
			int pixelIndexI = _currentPixelIndex + _cyclePoints[pointHandle.junction.firstDirection];
			byte pixel = _pixels[pixelIndexI];
			if (PixelType.isUnused(pixel))
				return pointHandle.junction.firstDirection;
		}
		if (pointHandle.vCornerPoint.count > 0)
			return pointHandle.vCornerPoint.firstDirection;
		else if (pointHandle.other.count > 0) 
			return pointHandle.other.firstDirection;
		else if (0 < pointHandle.extraNeighborPoint.count &&
				(_chainCodeHandler.getLastChain() <= 0 ||
				!PixelType.PIXEL_EXTRA_NEIGHBOR.equals(_pixelTypeCalculator.getPixelType())))
			return pointHandle.extraNeighborPoint.firstDirection;
		else if (pointHandle.used.countUsed > 0)
			//Only works if at end of closed curve
			return directionBetweenNeighborPoints(_currentPoint,_firstPointInMultiLine); 
		return Constants.DIRECTION_NOT_USED;
	}

	/** Everything is always OK. Stop only on junctions and end points. */
	@Override
	protected boolean lastPixelOk(byte newDirection) {
		return true;
	}

	@Override
	protected void internalFactory() {
		_pixelTypeFinder = new SimplePixelTypeFinder(this);
		_rulesArrayForLetterMatching = LetterTaskFactory.getSimpleNumericRuleForAllLetters(LetterTaskFactory.POLYGON);
	}

	@Override
	protected boolean findMultiLinePreProcess() {
		boolean result = super.findMultiLinePreProcess();
		if (!result)
			return result;
		_chainCodeHandler = new ChainCodeHandler(getPolygon().getAnnotatedShape());
		_chainCodeHandler.setup();
		_chainCodeHandler.setMultiLine(this.getPolygon().getCurrentMultiLine());
		_chainCodeHandler.setFirstPoint(_firstPointInMultiLine);
		return result;
	}

	@Override
	protected void findMultiLinePostProcess() {
		_chainCodeHandler.getValue();
		super.findMultiLinePostProcess();
	}
	
	@Override
	public void init(ImageProcessor ip) {
		super.init(ip);
		_chainCodeHandler = new ChainCodeHandler(getPolygon().getAnnotatedShape());
	}
}
