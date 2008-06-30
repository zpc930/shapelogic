package org.shapelogic.imageprocessing;

import org.shapelogic.imageutil.SLImage;

/** Compare implementations for gray scale.
 * 
 * @author Sami Badawi
 *
 */
public class SBShortCompare extends SBSimpleCompare {

	private short[] _pixels;
	public static final int MASK = 0xffff;

	/** Tells if the color at index is close enough the set color to
	 * be considered part of the segmented area.
	 */
	public boolean similar(int index) {
		int localColor = _pixels[index] & MASK;
		int diff = Math.abs(localColor - _currentColor);
		return diff <= _maxDistance;
	}

	public void init(SLImage ipIn) throws Exception {
		_slImage = ipIn;
		if (_slImage == null) {
			throw new Exception("ImageProcessor == null");
		}
		_pixels = (short[]) _slImage.getPixels();
		mask = MASK;
		handledColor = 200;
		super.init(_slImage);
	}

	public int colorDistance(int color1, int color2) {
		return Math.abs(color1 - color2);
	}

	/** This used for changes to other images or say modify all colors 
	 * to the first found.
	 */
	public void action(int index) {
		if (!isModifying())
			return;
		_pixels[index] = (short)handledColor;
	}

	public int getColorAsInt(int index) {
		return _pixels[index] & MASK;
	}
}
