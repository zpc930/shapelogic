package org.shapelogic.imageprocessing;

import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

/** Compare implementations for gray scale.
 * 
 * @author Sami Badawi
 *
 */
public class SBByteCompare extends SBSimpleCompare {

	private byte[] _pixels;

	/** Tells if the color at index is close enough the set color to
	 * be considered part of the segmented area.
	 */
	public boolean similar(int index) {
		int localColor = _pixels[index] & mask;
		int diff = Math.abs(localColor - currentColor);
		return diff <= maxDist;
	}

	public void init(ImageProcessor ipIn) throws Exception {
		ip = ipIn;
		if (ip == null) {
			throw new Exception("ImageProcessor == null");
		}
		if (!(ip instanceof ByteProcessor)) {
			throw new Exception("Currently SBSimpleCompare only handles gray scale images.");
		}
		_pixels = (byte[]) ip.getPixels();
		mask = 0xff;
		handledColor = 200;
		super.init(ip);
	}

	public int colorDistance(int color1, int color2) {
		return Math.abs(color1 - color2);
	}

	/** This used for changes to other images or say modify all colors 
	 * to the first found.
	 */
	public void action(int index) {
		_pixels[index] = (byte)handledColor;
	}
}
