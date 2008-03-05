package org.shapelogic.imageprocessing;

/** Not sure if I need this, but this is a little bit of structure build on top
 * of the ByteProcessor.
 * 
 * I think that the idea is that you just take one of them and use it in an adapter.
 * 
 * @author Sami Badawi
 *
 */
public interface PixelJumperByte {

	byte[] getPixels();

	int getMinX();
	int getMaxX();
	int getMinY();
	int getMaxY();

	/** What you need to add to the the index in the pixels array to get to the indexed point */
	int[] getCyclePoints();
}
