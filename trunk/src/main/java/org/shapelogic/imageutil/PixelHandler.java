package org.shapelogic.imageutil;

public interface PixelHandler {
	
	/** Handle a pixel with a color and a coordinate. */
	void putPixel(int x, int y, int color);
	
}
