package org.shapelogic.color;

/** GrayRange describes a range of colors in the Gray 8 bit.
 * <br />
 * Used for color clustering, and particle counter.<br />
 *
 * @author Sami Badawi
 *
 */
public class ColorRange extends ColorAndVariance implements IColorRange {
	
	/** Distance from colorCenter that will be accepted in this Range. */
	protected double _maxDistance;
	protected IColorDistance _distance = ColorDistance1.INSTANCE;

	/** Color encoded in an int. */
	protected int _colorCenter;
	protected int[] _colorCenterInChannels = new int[3];
	protected boolean _frozen;

	@Override
	public double distanceFromRangeCenter(int color) {
    	ColorUtil.splitColor(color, _splitColors);
		return _distance.distance(_colorCenterInChannels,_splitColors);
	}

	@Override
	public boolean colorInRange(int color) {
        double dist = distanceFromRangeCenter(color);
		return dist <= _maxDistance;
	}

	@Override
	public double distanceFromRange(int color) {
        double dist = distanceFromRangeCenter(color);
		if (Math.abs(dist) <= _maxDistance) 
			return 0.;
		return dist - _maxDistance;
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
        ColorUtil.splitColor(center, _colorCenterInChannels);
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

	@Override
	public int[] getColorChannels() {
		return _colorCenterInChannels;
	}
}
