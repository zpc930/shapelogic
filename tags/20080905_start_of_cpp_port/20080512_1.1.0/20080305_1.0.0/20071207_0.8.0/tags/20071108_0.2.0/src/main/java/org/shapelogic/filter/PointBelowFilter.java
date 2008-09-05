package org.shapelogic.filter;

import org.shapelogic.polygon.IPoint2D;

/** Filter points above a given level of the bounding box of a polygon
 * 
 * @author Sami Badawi
 *
 */
public class PointBelowFilter extends PolygonPointFilter {
	private double _maxLimit = Double.NaN; 

	@Override
	public boolean evaluate(Object obj) {
		IPoint2D point = (IPoint2D) obj;
		if (_maxLimit == Double.NaN) 
			return true;
		else 
			return point.getY() < _maxLimit;
	}
	
	@Override
	public void setup() throws Exception {
		if (_constraint instanceof String)
			_maxLimit = Double.parseDouble((String)_constraint); 
		else if (_constraint instanceof Number)
			_maxLimit = ((Number)_constraint).doubleValue();
		if (_maxLimit != Double.NaN) 
			_maxLimit *= getParent().getBBox().getDiagonalVector().getY();
	}
}
