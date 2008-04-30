package org.shapelogic.color;

import org.shapelogic.mathematics.StorelessDiscriptiveStatistic;

/** GrayAndVariance describes an average color with variance for gray 8 bit.
 * <br />
 * Used for color clustering, and particle counter.<br />
 * 
 * @author Sami Badawi
 *
 */
public class GrayAndVariance implements IColorAndVariance {
	
	protected StorelessDiscriptiveStatistic _grayStatistic = new StorelessDiscriptiveStatistic();

	/** Not sure if these are needed. */
	protected int _minColor = 256;
	protected int _maxColor = 0;
	
	/** Distance from colorCenter that will be accepted in this Range. */
	protected double _maxDistance;
	
	/** Add the color for a given point (x,y). <br />
	 * 
	 * The points needs to be added in sequence.
	 */
	@Override
	public void putPixel(int x, int y, int color) {
		_grayStatistic.increment(color);
		if (_maxColor < color)
			_maxColor = color;
		if (color < _minColor)
			_minColor = color;
	}

	@Override
	public int getArea() {
		return _grayStatistic.getCount();
	}

	@Override
	public int getMeanColor() {
		return (int) _grayStatistic.getMean();
	}

	@Override
	public double getStandardDeviation() {
		return _grayStatistic.getStandardDeviation();
	}

	@Override
	public void merge(IColorAndVariance colorAndVariance) {
		if (!(colorAndVariance instanceof GrayAndVariance))
			return;
		GrayAndVariance grayRange = (GrayAndVariance) colorAndVariance;
		_grayStatistic.merge(grayRange._grayStatistic);
	}

	/** Color vector to be used for color distance.<br />
	 *  
	 * Should I use the center color or the mean color?<br />
	 * 
	 * I will start by using the mean color.<br />
	 * 
	 * I think that I will start without including the standard deviation.
	 */
	@Override
	public int[] getColorChannels() {
		return new int[] {getMeanColor()};
	}

	public int getMeanGray() {
		return (int)_grayStatistic.getMean();
	}
}
