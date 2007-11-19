package org.shapelogic.filter;

import org.shapelogic.polygon.IPoint2D;

/** Filter points above a given level of the bounding box of a polygon
 * 
 * @author Sami Badawi
 *
 */
public class PointAboveFilter extends PolygonSpatialPointFilter {
	
	@Override
	public boolean evaluate(Object obj) {
		IPoint2D point = (IPoint2D) obj;
		if (_limit == Double.NaN) 
			return true;
		else 
			return point.getY() <= _limit ;
	}

	@Override
	protected double coordinateChoser(IPoint2D diagonalVector) {
		return diagonalVector.getY();
	}
}
