package org.shapelogic.imageprocessing;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.util.BitSet;

/**
 * @author Sami Badawi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class SBSimpleCompare implements SBPixelCompare {
	protected int currentColor;
	protected int handledColor;
	protected int mask;
	protected ImageProcessor ip;
	protected int maxDist = 10;
	protected BitSet bitSet;
	protected boolean fillWithOwnColor = true;
	protected int numberOfPixels;
	
	/** Similar and not handled
	 */
	public boolean newSimilar(int index) {
		return !isHandled(index) && similar(index);
	}

	/**
	 * @return Returns the currentColor.
	 */
	public int getCurrentColor() {
		return currentColor;
	}
	/**
	 * @param currentColor The currentColor to set.
	 */
	public void setCurrentColor(int currentColor) {
		this.currentColor = currentColor;
	}
	/**
	 * @return Returns the handledColor.
	 */
	public int getHandledColor() {
		return handledColor;
	}
	/**
	 * @param handledColor The handledColor to set.
	 */
	public void setHandledColor(int handledColor) {
		this.handledColor = handledColor;
	}
	/**
	 * @return Returns the maxDist.
	 */
	public int getMaxDist() {
		return maxDist;
	}
	/**
	 * @param maxDist The maxDist to set.
	 */
	public void setMaxDist(int maxDist) {
		this.maxDist = maxDist;
	}
	
	public void grabColorFromPixel(int startX, int startY) {
		currentColor = ip.getPixel(startX, startY) & mask;
		if (fillWithOwnColor)
			handledColor = currentColor;
	}
	
	public static SBSimpleCompare factory(ImageProcessor ip) throws Exception
	{
		SBSimpleCompare result = null;
		if (ip instanceof ByteProcessor) {
			result = new SBByteCompare();
		}
		else if (ip instanceof ColorProcessor) {
			result = new SBColorCompare();
		}
			
		if (result != null) {
			result.init(ip);
		}
		return result;
	}

    /** split color coded as int into 3 int 
     * sould probably be moved to util class
     * */
	static public int[] splitColor(int colorIn) {
		int[] iArray = new int[3];
		iArray[0] = (colorIn&0xff0000)>>16;
		iArray[1] = (colorIn&0xff00)>>8;
		iArray[2] = colorIn&0xff;
		return iArray;
	}
	
	/** Call at start, this might also work as a reset	 */
	public void init(ij.process.ImageProcessor ip) throws Exception
	{
		bitSet = new BitSet(ip.getWidth()*ip.getHeight());
		numberOfPixels = 0;
	}
	/** Check if pixel at index already have been handled. 
	 */
	public boolean isHandled(int index) {
		return bitSet.get(index);
	}

	/** Mark that pixel at index has been handled
	 */
	public void setHandled(int index) {
		bitSet.set(index,true);
		numberOfPixels++; //this assumes that each pixel is only called once 
	}
	
	public int getNumberOfPixels() {
		return numberOfPixels;
	}
}
