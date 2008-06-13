package org.shapelogic.imageprocessing;

import java.util.BitSet;

import org.shapelogic.imageutil.SLImage;

/** Abstract class for compare.<br />
 * 
 * @author Sami Badawi
 *
 */
public abstract class SBSimpleCompare implements SBPixelCompare {
	protected int _currentColor;
	protected int handledColor;
	protected int mask;
	protected SLImage _slImage;
	protected int _maxDistance = 10;
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
		return _currentColor;
	}
	/**
	 * @param currentColor The currentColor to set.
	 */
	public void setCurrentColor(int currentColor) {
		this._currentColor = currentColor;
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
		return _maxDistance;
	}
	/**
	 * @param maxDist The maxDist to set.
	 */
	public void setMaxDist(int maxDist) {
		this._maxDistance = maxDist;
	}
	
	public void grabColorFromPixel(int startX, int startY) {
		_currentColor = _slImage.get(startX, startY) & mask;
		if (fillWithOwnColor)
			handledColor = _currentColor;
	}
	
	/** Call at start, this might also work as a reset	 */
	public void init(ij.process.ImageProcessor ip) throws Exception
	{
		bitSet = new BitSet(ip.getWidth()*ip.getHeight());
		numberOfPixels = 0;
	}
	
    /** Call at start, this might also work as a reset	 */
	public void init(SLImage image) throws Exception
	{
		bitSet = new BitSet(image.getWidth()*image.getHeight());
		numberOfPixels = 0;
		_slImage = image;
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
    
    @Override
    public void setMaxDistance(int maxDistance) {
        _maxDistance = maxDistance;
    }
}
