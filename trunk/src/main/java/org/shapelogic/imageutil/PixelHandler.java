package org.shapelogic.imageutil;

/** Interface for anything that can handle an isolated pixel.<br />
 * 
 * @author Sami Badawi
 */
public interface PixelHandler {
	
	/** Handle a pixel with a color and a coordinate. */
	void putPixel(int x, int y, int color);
	
}
