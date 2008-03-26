package org.shapelogic.imageprocessing;

import org.shapelogic.polygon.BBox;
import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.IPoint2D;

/** SegmentArea holds the information.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
abstract class PixelArea {

	protected CPointDouble _aggregationPoint;
  
	protected int _startX, _startY;
	
	protected BBox _boundingBox;
  
    /** Number of pixels. */
	protected int _area;
  
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
  
    abstract void addPoint(int x, int y, int color);
    
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
	
}
