package org.shapelogic.imageprocessing;
/** In order to handle both colors and grayscale have an interface that tells if 2 pixels are similar.
 *
 * @author Sami Badawi
 */
public interface SBPixelCompare {
	
	/** Test if a pixel at index is similar to the internal state
	 * 
	 * @param index to image
	 */
	boolean similar(int index);
	public boolean newSimilar(int index);
	void action(int index);
	boolean isHandled(int index);
	void setHandled(int index);
	int colorDistance(int color1, int color2);
	void init(ij.process.ImageProcessor ip) throws Exception;
	int getNumberOfPixels();
	void grabColorFromPixel(int startX, int startY);
}
