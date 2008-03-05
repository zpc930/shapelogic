package org.shapelogic.filter;

import org.shapelogic.polygon.IPoint2D;

/** Filter points below a given level of the bounding box of a polygon
 * 
 * The constraint is should be between 0 and 1.
 * 0 is the top point
 * 1 is the bottom point
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
			return  _limit < point.getY();
	}
	
	@Override
	protected double coordinateChoser(IPoint2D diagonalVector) {
		return diagonalVector.getY();
	}
}
