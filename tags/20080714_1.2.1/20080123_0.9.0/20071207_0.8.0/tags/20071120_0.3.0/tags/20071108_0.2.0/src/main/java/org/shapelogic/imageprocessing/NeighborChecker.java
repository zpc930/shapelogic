package org.shapelogic.imageprocessing;

import org.shapelogic.util.Constants;

/** Neighbor Checker 
 * 
 * runs around a point and find what type all the neighbor points have
 * 
 * @author Sami Badawi
 *
 */
public class NeighborChecker implements IPixelTypeFinder {
	//Find and set the type of all the neighbor points
	FirstDirectionForType extraNeighborPoint = new FirstDirectionForType();
	FirstDirectionForType junction = new FirstDirectionForType();
	FirstDirectionForType other = new FirstDirectionForType();
	FirstDirectionForType used = new FirstDirectionForType();
	FirstDirectionForType vCornerPoint = new FirstDirectionForType();
	PixelTypeCalculator localPixelTypeCalculator = new PixelTypeCalculator();

	IPixelTypeFinder _parent;
	private byte[] _pixels;
	private int[] _cyclePoints;
	private int _currentPixelIndex;
	
	public NeighborChecker(IPixelTypeFinder parent, int currentPixelIndex)
	{
		_parent = parent;
		_pixels = getPixels();
		_cyclePoints = getCyclePoints();
		_currentPixelIndex = currentPixelIndex;
	}

	/** Run over the neighbors points and put them in categories */
	protected void checkNeighbors() {
		for (byte i=0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
			int pixelIndexI = _currentPixelIndex + _cyclePoints[i];
			byte pixel = _pixels[pixelIndexI];
			if (pixel == PixelType.PIXEL_FOREGROUND_UNKNOWN.color) {
				localPixelTypeCalculator.setup();
				findPointType(pixelIndexI, localPixelTypeCalculator);
				pixel = localPixelTypeCalculator.getPixelType().color;
				_pixels[pixelIndexI] = pixel; 
			}
			boolean isUsed = PixelType.isUsed(pixel);
			if (isUsed) {
				used.addDirection(i, isUsed);
			}

			if (PixelType.BACKGROUND_POINT.color == pixel)
				continue;
			else if (PixelType.PIXEL_JUNCTION.equalsIgnore(pixel)) { 
				junction.addDirection(i,isUsed);
			}
			else if (PixelType.PIXEL_EXTRA_NEIGHBOR.equalsIgnore(pixel)) { 
				extraNeighborPoint.addDirection(i,isUsed);
			}
			else if (PixelType.PIXEL_V_CORNER.equalsIgnore(pixel)) { 
				vCornerPoint.addDirection(i, isUsed);
			}
			else {
				other.addDirection(i, isUsed);
			}
		}
	}
	
	public int allNeighbors() {
		return 
			extraNeighborPoint.count + 
			junction.count + 
			other.count + 
			used.count + 
			vCornerPoint.count; 
	}
	
	public boolean falseJunction() {
		return 0 < vCornerPoint.count && allNeighbors() - vCornerPoint.count <= 2;
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

	@Override
	public PixelTypeCalculator findPointType(int pixelIndex,
			PixelTypeCalculator reusedPixelTypeCalculator) {
		return _parent.findPointType(pixelIndex,reusedPixelTypeCalculator);
	}
}
