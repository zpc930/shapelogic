package org.shapelogic.color;

import org.shapelogic.mathematics.ArrayOperations;

/** GrayRange describes a range of colors in the Gray 8 bit.
 * <br />
 * Used for color clustering, and particle counter.<br />
 *
 * @author Sami Badawi
 *
 */
public class ColorRange extends ColorAndVariance implements IColorRange {
	
	/** Not sure if these are needed. */
	protected int _minColor = 256;
	protected int _maxColor = 0;
	
	/** Distance from colorCenter that will be accepted in this Range. */
	protected double _maxDistance;

	/** Color encoded in an int. */
	protected int _colorCenter;
	protected int[] _borderCount = new int[3];
	protected boolean _frozen;

	protected int _lastX = -1;
	protected int _lastY = -1;
	
	public static final int LOW_CONTRAST_POS = 0;
	public static final int MEDIUM_CONTRAST_POS = 1;
	public static final int HIGH_CONTRAST_POS = 2;
	
	/** Add the color for a given point (x,y). <br />
	 * 
	 * The points needs to be added in sequence.
	 */
	@Override
	public void putPixel(int x, int y, int color) {
		super.putPixel(x, y, color);
		_lastX = x;
		_lastY = y;
		if (_maxColor < color)
			_maxColor = color;
		if (color < _minColor)
			_minColor = color;
	}

	@Override
	public boolean colorInRange(int color) {
		return Math.abs(_colorCenter - color) <= _maxDistance;
	}

	@Override
	public double distanceFromRange(int color) {
		if (Math.abs(_colorCenter - color) <= _maxDistance) 
			return 0.;
		return Math.abs( Math.abs(_colorCenter - color) - _maxDistance);
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

	@Override
	public boolean isRangeFrozen() {
		return _frozen;
	}
	
	/** Count how many borders and the size of the border.
	 * 
	 * Should I count each pixel or should I aggregate an edge?
	 * 
	 * 
	 * 
	 * @param level low, medium or high
	 */
	public void addBorder(int level) {
		if (level < _borderCount.length)
			_borderCount[level]++;
	}

	@Override
	public double getMaxDistance() {
		return _maxDistance;
	}

	@Override
	public void setMaxDistance(double distance) {
		_maxDistance = distance;
	}

	@Override
	public int getColorCenter() {
		return _colorCenter;
	}

	@Override
	public void setColorCenter(int center) {
		_colorCenter = center;
	}

	@Override
	public double distanceFromRangeCenter(int color) {
		return Math.abs(_colorCenter - color);
	}

	@Override
	public void merge(IColorAndVariance colorAndVariance) {
		super.merge(colorAndVariance); //XXX add more
		
	}
}
