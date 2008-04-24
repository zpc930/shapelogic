package org.shapelogic.imageutil;

import org.shapelogic.polygon.BBox;
import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.IPoint2D;

/** SegmentArea holds the information.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class PixelArea implements PixelHandler
{

	protected CPointDouble _aggregationPoint;
  
	protected int _startX, _startY;
	
	protected BBox _boundingBox;
  
    /** Number of pixels. */
	protected int _area;
	
    /** Area is background, value null means not known. */
	protected Boolean _background;
  
    /** If any line in the area has a line that was split. */
	protected boolean _gapInLine;
    
    public PixelArea(int x, int y) {
    	_startX = x;
    	_startY = y;
    	_area = 0;
    	_boundingBox = new BBox();
    	_gapInLine = false;
    	_aggregationPoint = new CPointDouble(0,0);
    	addPoint(x, y);
    }
  
    public void addPoint(int x, int y) {
    	_boundingBox.addPoint(x,y);
//    	_gapInLine = false;
    	_aggregationPoint.setLocation(_aggregationPoint.x+x, _aggregationPoint.y+y);
    	_area++;
    }
    
    public IPoint2D getCenterPoint() {
    	IPoint2D result = _aggregationPoint.copy().multiply(1. / _area);
    	return result;
    }

    /** Number of pixels. */
	public int getArea() {
		return _area;
	}

    /** If any line in the area has a line that was split. */
	public boolean isGapInLine() {
		return _gapInLine;
	}

	public BBox getBoundingBox() {
		return _boundingBox;
	}

	public Boolean getBackground() {
		return _background;
	}

	public void setBackground(Boolean background) {
		_background = background;
	}

	@Override
	public void putPixel(int x, int y, int color) {
		addPoint(x, y);
	}
	
}
