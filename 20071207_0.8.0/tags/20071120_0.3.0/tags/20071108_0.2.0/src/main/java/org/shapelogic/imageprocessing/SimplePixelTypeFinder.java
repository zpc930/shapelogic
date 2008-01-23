package org.shapelogic.imageprocessing;

import org.shapelogic.util.Constants;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class SimplePixelTypeFinder implements IPixelTypeFinder {
	PixelJumperByte _parent;
	private byte[] _pixels;
	private int[] _cyclePoints;
	
	public SimplePixelTypeFinder(PixelJumperByte parent)
	{
		_parent = parent;
		_pixels = getPixels();
		_cyclePoints = getCyclePoints();
	}

	/** From the current point find direction
	 * A problem with finding maximum is that the neighbor might not be known.
	 * Should the maximum only be calculated among unused?
	 * I think that if you have a V point and a junction
	 * If I already know a pixel should I do the calculation again?
	 * */
	public PixelTypeCalculator findPointType(int pixelIndex, PixelTypeCalculator reusedPixelTypeCalculator) {
		PixelTypeCalculator pixelTypeCalculator = reusedPixelTypeCalculator; 
		if (pixelTypeCalculator == null)
			pixelTypeCalculator = new PixelTypeCalculator();
		else
			pixelTypeCalculator.setup();
		int neighbors = 0;
		int countRegionCrossings = 0;
		byte firstUnusedNeighbor = Constants.DIRECTION_NOT_USED;
		byte lastDirection = Constants.DIRECTION_NOT_USED;
		byte previousDirection = Constants.DIRECTION_NOT_USED;
		boolean isBackground;
		boolean wasBackground = PixelType.BACKGROUND_POINT.color == _pixels[pixelIndex + _cyclePoints[Constants.DIRECTIONS_AROUND_POINT-1]];
		int unusedNeighbors = 0;
		for (byte i=0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
			int pixelIndexI = pixelIndex + _cyclePoints[i];
			byte currentPixel = _pixels[pixelIndexI];
			isBackground = PixelType.BACKGROUND_POINT.color == currentPixel;
			if (!isBackground) {
				neighbors++;
				if (!PixelType.isUsed(_pixels[pixelIndexI])) {
					unusedNeighbors++;
					if (firstUnusedNeighbor == Constants.DIRECTION_NOT_USED)
						firstUnusedNeighbor = i;
				}
				previousDirection = lastDirection; 
				lastDirection = i;
			}
			if (wasBackground != isBackground) {
				countRegionCrossings++;
			}
			wasBackground = isBackground;
		}
		pixelTypeCalculator.regionCrossings = countRegionCrossings;
		pixelTypeCalculator.neighbors = neighbors;
		pixelTypeCalculator.firstUnusedNeighbor = firstUnusedNeighbor;
		pixelTypeCalculator.unusedNeighbors = unusedNeighbors;
		pixelTypeCalculator.pixelIndex=pixelIndex;
		if (previousDirection != Constants.DIRECTION_NOT_USED)
			pixelTypeCalculator.distanceBetweenLastDirection = lastDirection - previousDirection;
		if (PixelType.isUnused(_pixels[pixelIndex]))
			_pixels[pixelIndex] = pixelTypeCalculator.getPixelType().color;
		else 
			_pixels[pixelIndex] = PixelType.toUsed(pixelTypeCalculator.getPixelType());
		return pixelTypeCalculator;
	}

	@Override
	public int[] getCyclePoints() {
		return _parent.getCyclePoints();
	}

	@Override
	public int getMaxX() {
		return _parent.getMaxX();
	}

	@Override
	public int getMaxY() {
		return _parent.getMaxY();
	}

	@Override
	public int getMinX() {
		return _parent.getMinX();
	}

	@Override
	public int getMinY() {
		return _parent.getMinY();
	}

	@Override
	public byte[] getPixels() {
		return _parent.getPixels();
	}
}
