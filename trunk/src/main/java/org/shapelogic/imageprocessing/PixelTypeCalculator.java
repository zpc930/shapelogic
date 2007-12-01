package org.shapelogic.imageprocessing;

import org.shapelogic.logic.LazyCalc;
import org.shapelogic.util.Constants;

/** PixelTypeCalculator stores some values for points and 
 * calculated the type of points based on them.
 * 
 * @author Sami Badawi
 *
 */
public class PixelTypeCalculator implements LazyCalc<PixelType> 
{
	int neighbors;
	int unusedNeighbors;
	int regionCrossings;
	byte firstUnusedNeighbor = Constants.DIRECTION_NOT_USED;
	int distanceBetweenLastDirection;
	private PixelType pixelType = PixelType.PIXEL_FOREGROUND_UNKNOWN;

	/** This is for debugging so you can see what pixel this was last run for*/
	public int pixelIndex;
	
	/** Only some of the pixel type finders are finding the highestRanked 
	 * values and no calculations are done on them. Could be split out in sub 
	 * class, but does not seem to be worth it. */
	int highestRankedUnusedPixelTypeColor = 0;
	byte highestRankedUnusedNeighbor = Constants.DIRECTION_NOT_USED;
	boolean highestRankedUnusedIsUnique = true;
	boolean isLocalMaximum = false;
	boolean _dirty = true;
	
	PixelType getPixelType(){
		return getCalcValue();
	}

	@Override
	public void setup() {
		neighbors = 0;
		unusedNeighbors = 0;
		regionCrossings = 0;
		firstUnusedNeighbor = Constants.DIRECTION_NOT_USED;
		distanceBetweenLastDirection = 0;
		pixelType = PixelType.PIXEL_FOREGROUND_UNKNOWN;
		pixelIndex = Constants.BEFORE_START_INDEX; 
		highestRankedUnusedPixelTypeColor = 0;
		highestRankedUnusedNeighbor = Constants.DIRECTION_NOT_USED;
		highestRankedUnusedIsUnique = true;
		isLocalMaximum = false;
		_dirty = true;
	}

	/** Just look at the center point and see if different from foreground unknown
	 * I do not think this is right for the priority based version. 
	 * Since you can run more times. */
	@Override
	public boolean isDirty() {
		return _dirty;
//		return pixelType == PixelType.PIXEL_FOREGROUND_UNKNOWN;
	}

	@Override
	public PixelType calc() {
		distanceBetweenLastDirection %= Constants.DIRECTIONS_AROUND_POINT;
		if (regionCrossings == 4) {
			if (distanceBetweenLastDirection == 2 || distanceBetweenLastDirection == 6) 
				pixelType = PixelType.PIXEL_L_CORNER; 
			else if (neighbors == 2) pixelType = PixelType.PIXEL_ON_LINE;
			else if (neighbors > 2) pixelType = PixelType.PIXEL_EXTRA_NEIGHBOR;
		}
		else if (regionCrossings > 4) pixelType = PixelType.PIXEL_JUNCTION; //Junction point, more than cross index of 4
		else if (regionCrossings == 2) {
			if (neighbors == 1)
				pixelType = PixelType.PIXEL_LINE_END;
			else if (neighbors == 2)
				pixelType = PixelType.PIXEL_V_CORNER;
			else
				pixelType = PixelType.PIXEL_BORDER; //Edge of solid, cross index of 2
		}
		else if (regionCrossings == 0) pixelType = PixelType.PIXEL_SOLID; //Inner point, 8 neighbors or 7 where the last on is an even number.
		else pixelType = PixelType.PIXEL_FOREGROUND_UNKNOWN; //Before it is calculated
		_dirty = false;
		return pixelType;
	}

	@Override
	public PixelType getCalcValue() {
		if (isDirty())
			calc();
		return pixelType;
	}

}
