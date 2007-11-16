package org.shapelogic.filter;

import org.shapelogic.polygon.IPoint2D;

/** Filter points below a given level of the bounding box of a polygon
 * 
 * @author Sami Badawi
 *
 */
public class PointBelowFilter extends PolygonSpatialPointFilter {

	@Override
	public boolean evaluate(Object obj) {
		IPoint2D point = (IPoint2D) obj;
		if (_limit == Double.NaN) 
			return true;
		else 
			return point.getY() < _limit;
	}
	
}
