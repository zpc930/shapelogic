package org.shapelogic.imageprocessing;

import ij.plugin.filter.PlugInFilter;

import org.shapelogic.polygon.CPointInt;
import org.shapelogic.util.Constants;

/** LineVectorizer is a vectorizer using short line of default length 5.
 * 
 * You do a sequence of small lines, if 2 consecutive lines are close in direction you can merge them.
 * But if they are far away in angle you create a new line.
 * Also you can only have 2 direction within the same line, they are stored by cycle index.
 * 
 * @author Sami Badawi
 *
 */
public class LineVectorizer extends ShortLineBasedVectorizer implements PlugInFilter {

	/** Test that the current direction is close to the last direction.
	 * */
	protected boolean multiLineHasGlobalFitness() {
		//do big test
		if (_pointsInCurrentShortLine >= _maxPointsInShortLine) {
			_currentVectorDirection = (CPointInt) _currentPoint.copy().minus(_startOfShortLinePoint);
			double currentAngel = _currentVectorDirection.angle();
			if (!Double.isNaN(currentAngel))
				_currentCircleInterval.addClosestAngle(currentAngel);
			newShortLine();
			return (Double.isNaN(currentAngel)) || _currentCircleInterval.intervalLength() < _angleLimit;
		}
		if (_currentDirection != _firstUsedDirection) {
			if (_secondUsedDirection == Constants.DIRECTION_NOT_USED) {
				_secondUsedDirection = _currentDirection;
			}
			else if (_currentDirection != _secondUsedDirection) 
			{
				return false;
			}
		}
		return true;
	}

}
