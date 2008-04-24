package org.shapelogic.color;

import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.mathematics.StorelessDiscriptiveStatistic;

/** ColorArea holds the information.
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
public class ColorArea extends ColorRange implements ValueArea {

	protected int _startColor;
	
	protected PixelArea _pixelArea;
	
    public ColorArea(int x, int y, int startColor) {
    	_pixelArea = new PixelArea(x,y);
    	_startColor = startColor;
    	ColorUtil.splitColor(startColor, _splitColors);
    	for (int i = 0; i < 3; i++) {
    		_colorStatistics[i] = new StorelessDiscriptiveStatistic();
    		_colorStatistics[i].increment(_splitColors[i]);
    	}
    }
    
    public void putPixel(int x, int y, int color) {
    	_pixelArea.addPoint(x, y);
    	super.putPixel(x, y, color);
    }
    
	@Override
	public void merge(IColorAndVariance colorAndVariance) {
		super.merge(colorAndVariance); //XXX add more 
	}

	public PixelArea getPixelArea() {
		return _pixelArea;
	} 
}
