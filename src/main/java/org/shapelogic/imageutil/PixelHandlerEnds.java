package org.shapelogic.imageutil;

public interface PixelHandlerEnds extends PixelHandler {
	
	/** Handle a pixel with a color and a coordinate. */
	void handlePixelStart(int x, int y, int color);
	
	/** Handle a pixel with a color and a coordinate. */
	void handlePixelEnd(int x, int y, int color);
	
}
