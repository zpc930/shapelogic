package org.shapelogic.imageprocessing;

import org.shapelogic.color.ColorUtil;
import org.shapelogic.imageutil.SLImage;

/** Compare implementations for RGB.
 * 
 * @author Sami Badawi
 * 
 */
public class SBColorCompare extends SBSimpleCompare {

	private int[] pixels;
	public static final int MASK = 0xffffff;

	/** Tells if the color at index is close enought the set color to
	 * be considered part of the segmented area.
     * 
     * XXX very slow split currentColor into components
	 */
	public boolean similar(int index) {
		int localColor = pixels[index] & mask;
		//localColor
		int diff = colorDistance(localColor,_currentColor);
		return diff <= maxDist;
	}

	public void init(SLImage ipIn) throws Exception {
		_slImage = ipIn;
		if (_slImage == null) {
			throw new Exception("ImageProcessor == null");
		}
		pixels = (int[]) _slImage.getPixels();
		mask = MASK;
		handledColor = 0xf0f0f0; // light gray
		super.init(_slImage);
	}

    /** split color coded as int into 3 int */
	public int colorDistance(int color1, int color2) {
		int [] rgb1 = ColorUtil.splitColor(color1);
		int [] rgb2 = ColorUtil.splitColor(color2);
		int dist = 0;
		for (int i = 0; i < rgb1.length; i++ ) {
			dist += Math.abs(rgb1[i] - rgb2[i]);
		}
		dist = dist / 3; // to make it fit with grayscale
		return dist;
	}

	/** This used for changes to other images or say modify all colors 
	 * to the first found.
	 */
	public void action(int index) {
		if (!isModifying())
			return;
		int dist = colorDistance(pixels[index], handledColor);
		if (dist <= maxDist)
			pixels[index] = handledColor;
		else {
			boolean debugStop = true;
		}
	}

	public int getColorAsInt(int index) {
		return pixels[index];
	}

    @Override
    public void setCurrentColor(int color) {
        _currentColor = color;
    }
}
