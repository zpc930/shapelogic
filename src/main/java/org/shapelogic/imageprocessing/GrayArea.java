package org.shapelogic.imageprocessing;

import org.shapelogic.mathematics.StorelessDiscriptiveStatistic;

/** GrayArea holds the information.
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
public class GrayArea extends PixelArea {

	protected int _startColor;
	
	protected StorelessDiscriptiveStatistic _grayStatistic = new StorelessDiscriptiveStatistic();
	
    public GrayArea(int x, int y, int startColor) {
    	super(x,y);
    	_startColor = startColor;
    	_grayStatistic.increment(startColor);
    }
    
    public void addPoint(int x, int y, int inputColor) {
    	addPoint(x, y);
    	_grayStatistic.increment(inputColor);
    }
    
	public StorelessDiscriptiveStatistic getGrayStatistic() {
		return _grayStatistic;
	}
	
	public int getMeanGray() {
		return (int)_grayStatistic.getMean();
	}
}
