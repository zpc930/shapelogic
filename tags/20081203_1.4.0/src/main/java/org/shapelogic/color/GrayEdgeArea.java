package org.shapelogic.color;

import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.mathematics.ArrayOperations;

/** GrayEdgeArea holds the information.
 * <br />
 * 
 * Should this have getter and setters?<br />
 * 
 * Maybe start without.<br />
 * 
 * Should this be split in color and gray?<br />
 * 
 * I think that maybe it should.<br />
 * 
 * @author Sami Badawi
 *
 */
public class GrayEdgeArea extends GrayRange implements IColorEdgeArea {

    //XXX should be moved to Constants if they are really needed
    public static final int LOW_CONTRAST_POS = 0;
	public static final int MEDIUM_CONTRAST_POS = 1;
	public static final int HIGH_CONTRAST_POS = 2;
	
	protected int[] _borderCount = new int[3];
    
	protected int _lastX = -1;
	protected int _lastY = -1;
	
    public GrayEdgeArea(int x, int y, int startColor) {
    	_colorCenter= startColor;
//    	_grayStatistic.increment(startColor);
    	_pixelArea = new PixelArea(x,y);
    }
    
	/** Add the color for a given point (x,y). <br />
	 * 
	 * The points needs to be added in sequence.
	 */
	@Override
	public void putPixel(int x, int y, int color) {
		super.putPixel(x, y, color);
		_lastX = x;
		_lastY = y;
	}

	@Override
	public void merge(IColorAndVariance colorAndVariance) {
		super.merge(colorAndVariance);
	}

    @Override
	public PixelArea getPixelArea() {
		return _pixelArea;
	}
    
	@Override
	public int getAllContrastBorders() {
		return (int) ArrayOperations.sum(_borderCount);
	}

	@Override
	public int getHighContrastBorders() {
		return _borderCount[HIGH_CONTRAST_POS];
	}

	@Override
	public int getMediumContrastBorders() {
		return _borderCount[MEDIUM_CONTRAST_POS];
	}

	@Override
	public int getLowContrastBorders() {
		return _borderCount[LOW_CONTRAST_POS];
	}

	/** Count how many borders and the size of the border.<br />
	 * 
	 * @param level low, medium or high
	 */
	@Override
	public void addBorder(int level) {
		if (level < _borderCount.length)
			_borderCount[level]++;
	}
}
