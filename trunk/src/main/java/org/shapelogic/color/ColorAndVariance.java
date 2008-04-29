package org.shapelogic.color;

import org.shapelogic.mathematics.StorelessDiscriptiveStatistic;

/** GrayAndVariance describes an average color with variance for gray 8 bit.
 * <br />
 * Used for color clustering, and particle counter.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorAndVariance implements IColorAndVariance {
	
	protected StorelessDiscriptiveStatistic[] _colorStatistics;

	protected int[] _splitColors = new int[3];  
	
	public ColorAndVariance() {
		int numberOfChannels = 3;
		_colorStatistics = new StorelessDiscriptiveStatistic[numberOfChannels];
		for (int i=0; i < numberOfChannels; i++) {
			_colorStatistics[i] = new StorelessDiscriptiveStatistic();
		}
	}
	
	/** Add the color for a given point (x,y). <br />
	 * 
	 * The points needs to be added in sequence.
	 */
	@Override
	public void putPixel(int x, int y, int color) {
    	ColorUtil.splitColor(color, _splitColors);
    	for (int i = 0; i < 3; i++) {
    		_colorStatistics[i].increment(_splitColors[i]);
    	}
	}

	@Override
	public int getArea() {
		return _colorStatistics[0].getCount();
	}

	@Override
	public double getStandardDeviation() {
		return _colorStatistics[0].getStandardDeviation();
	}

	@Override
	public void merge(IColorAndVariance colorAndVariance) {
		if (!(colorAndVariance instanceof ColorAndVariance))
			return;
    	for (int i = 0; i < 3; i++) {
			_colorStatistics[i].merge(((ColorAndVariance)colorAndVariance)._colorStatistics[i]);
    	}
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

    public int getMeanRed() {
    	return (int) _colorStatistics[ColorUtil.RED_POS].getMean();
    }
    
    public int getMeanGreen() {
    	return (int) _colorStatistics[ColorUtil.GREEN_POS].getMean();
    }
    
    public int getMeanBlue() {
    	return (int) _colorStatistics[ColorUtil.BLUE_POS].getMean();
    }
    
    public int getMeanColor() {
    	return ColorUtil.packColors(
    			(int)_colorStatistics[ColorUtil.RED_POS].getMean(),
    			(int)_colorStatistics[ColorUtil.GREEN_POS].getMean(),
    			(int)_colorStatistics[ColorUtil.BLUE_POS].getMean());
    }
}
