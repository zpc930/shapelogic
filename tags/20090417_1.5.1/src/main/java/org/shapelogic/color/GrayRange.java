package org.shapelogic.color;

/** GrayRange describes a range of colors in the Gray 8 bit.
 * <br />
 * Used for color clustering, and particle counter.<br />
 *
 * @author Sami Badawi
 *
 */
public class GrayRange extends GrayAndVariance implements IColorRange {
	
	/** Distance from colorCenter that will be accepted in this Range. */
	protected IColorDistance _distance = new ColorDistance1();

	/** Color encoded in an int. */
	protected int _colorCenter;
	protected boolean _frozen;

	@Override
	public void putPixel(int x, int y, int color) {
		super.putPixel(x, y, color);
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
	public boolean isRangeFrozen() {
		return _frozen;
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
	
	@Override
	public IColorDistance getDistance() {
		return _distance;
	}

	@Override
	public void setDistance(IColorDistance distance) {
		_distance = distance;
	}
}
