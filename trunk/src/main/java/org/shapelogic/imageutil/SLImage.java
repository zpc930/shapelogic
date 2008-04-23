package org.shapelogic.imageutil;

import java.awt.Rectangle;

/** Very thin abstraction around ImageJ.<br />
 * 
 * You should be able to open based on different things.<br /> 
 * 
 * What type of images should you have and how should the be accessible?<br />
 * 
 * I think that maybe having a small subset of what is in an ImageJ 
 * ImageProcessor should be enough for most things in ShapeLogic.<br /> 
 * 
 * @author Sami Badawi
 *
 */
public interface SLImage extends PixelHandler {
	/** Returns the width of this image in pixels. */
	int getWidth();
	
	/** Returns the height of this image in pixels. */
	int getHeight();
	
	void setRoi(Rectangle roi);
	
	void setRoi(int x, int y, int rwidth, int rheight);
	
	Rectangle getRoi();

	int getPixelCount();

	/** This is a faster version of getPixel() that does not do bounds checking. */
	int get(int x, int y);
	
	int get(int index);

	/** This is a faster version of putPixel() that does not clip  
		out of range values and does not do bounds checking. */
	void set(int x, int y, int value);

	void set(int index, int value);

	/** Stores the specified value at (x,y). Does
	nothing if (x,y) is outside the image boundary.
	For 8-bit and 16-bit images, out of range values
	are clipped. For RGB images, the
	argb values are packed in 'value'. For float images,
	'value' is expected to be a float converted to an int
	using Float.floatToIntBits(). */
	void putPixel(int x, int y, int value);
	
	/** Returns a reference to this image's pixel array. The
	array type (byte[], short[], float[] or int[]) varies
	depending on the image type. */
	Object getPixels();
	
	/** Sets a new pixel array for the image. The length of the array must be equal to width*height.
	Use setSnapshotPixels(null) to clear the snapshot buffer. */
	void setPixels(Object pixels);
	
	/** Returns true if this image uses an inverting LUT
	that displays zero as white and 255 as black. */
	boolean isInvertedLut();
	
	/** Returns the number of color channels in the image. The color channels can be
	*  accessed by toFloat(channelNumber, fp) and written by setPixels(channelNumber, fp).
	* @return 1 for grayscale images, 3 for RGB images
	*/
	int getNChannels();
	
	boolean isEmpty();
	
	boolean isGray();
	
	boolean isRgb();
	
	Rectangle getActiveRectangle();
	
	int getLineStride();
}
