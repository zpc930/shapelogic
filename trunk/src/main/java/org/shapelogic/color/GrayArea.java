package org.shapelogic.color;

import org.shapelogic.imageutil.PixelArea;

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
public class GrayArea extends GrayRange implements ValueArea {

	protected int _startColor;
	
	protected PixelArea _pixelArea;
	
    public GrayArea(int x, int y, int startColor) {
    	_startColor = startColor;
    	_grayStatistic.increment(startColor);
    	_pixelArea = new PixelArea(x,y);
    }
    
    public void putPixel(int x, int y, int color) {
    	_pixelArea.addPoint(x, y);
    	super.putPixel(x, y, color);
    }
    
	@Override
	public void merge(IColorAndVariance colorAndVariance) {
		super.merge(colorAndVariance);
	}

	public PixelArea getPixelArea() {
		return _pixelArea;
	} 
}
