package org.shapelogic.imageprocessing;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.util.BitSet;

import org.shapelogic.color.ColorAreaFactory;
import org.shapelogic.color.ValueAreaFactory;
import org.shapelogic.color.GrayAreaFactory;
import org.shapelogic.imageutil.SLImage;

/** Abstract class fro compare.
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

	public static SBSimpleCompare factory(SLImage ip) throws Exception
	{
		SBSimpleCompare result = null;
		int channels = ip.getNChannels();
		if (ip.isGray()) {
			result = new SBByteCompare();
		}
		else if (ip.isRgb()) {
			result = new SBColorCompare();
		}
		else {
			System.out.println("Error: could not create SBSimpleCompare. ip.getNChannels()=" + ip.getNChannels());
		}
			
		if (result != null) {
			result.init(ip);
		}
		return result;
	}

	public static ValueAreaFactory segmentAreaFactory(ImageProcessor ip) throws Exception
	{
		ValueAreaFactory result = null;
		if (ip instanceof ByteProcessor) {
			result = new GrayAreaFactory();
		}
		else if (ip instanceof ColorProcessor) {
			result = new ColorAreaFactory();
		}
		return result;
	}

	public static ValueAreaFactory segmentAreaFactory(SLImage image) throws Exception
	{
		ValueAreaFactory result = null;
		int channels = image.getNChannels(); 
		if (image.isGray()) {
			result = new GrayAreaFactory();
		}
		else if (image.isRgb()) {
			result = new ColorAreaFactory();
		}
		else {
			System.out.println("Error: could not create PixelAreaFactory. image.getNChannels()=" + image.getNChannels());
		}
		return result;
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
