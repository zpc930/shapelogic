package org.shapelogic.imageprocessing;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.util.BitSet;

/** Abstract class fro compare.
 * 
 * @author Sami Badawi
 *
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
	protected  boolean _modifying;
	
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

	public static PixelAreaFactory segmentAreaFactory(ImageProcessor ip) throws Exception
	{
		PixelAreaFactory result = null;
		if (ip instanceof ByteProcessor) {
			result = new GrayAreaFactory();
		}
		else if (ip instanceof ColorProcessor) {
			result = new ColorAreaFactory();
		}
		return result;
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

	/** Should pixels be modified. */
	@Override
	public boolean isModifying() {
		return _modifying;
	}

	@Override
	public void setModifying(boolean input) {
		_modifying = input;
	}
}
