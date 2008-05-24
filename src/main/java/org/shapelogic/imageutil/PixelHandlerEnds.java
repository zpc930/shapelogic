package org.shapelogic.imageutil;

/** PixelHandler with hooks for calling method at the beginning and ends of each
 * line. <br />
 * 
 * @author Sami Badawi
 */
public interface PixelHandlerEnds extends PixelHandler {
    
    /** Called before starting to scan the image. */
    void setup();
	
	/** Handle a pixel with a color and a coordinate. */
	void handlePixelStart(int x, int y, int color);
	
	/** Handle a pixel with a color and a coordinate. */
	void handlePixelEnd(int x, int y, int color);
	
    /** Called after scan of the image. */
    void postProcess();
}
