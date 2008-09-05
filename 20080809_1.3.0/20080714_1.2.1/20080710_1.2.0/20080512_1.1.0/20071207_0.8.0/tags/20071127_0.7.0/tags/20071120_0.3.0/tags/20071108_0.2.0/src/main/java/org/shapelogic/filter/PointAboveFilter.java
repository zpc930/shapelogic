package org.shapelogic.filter;

import org.shapelogic.polygon.IPoint2D;

/** Filter points above a given level of the bounding box of a polygon
 * 
 * @author Sami Badawi
 *
 */
public class PointAboveFilter extends PolygonPointFilter {
	private double _minLimit = Double.NaN; 

	@Override
	public boolean evaluate(Object obj) {
		IPoint2D point = (IPoint2D) obj;
		if (_minLimit == Double.NaN) 
			return true;
		else 
			return _minLimit <= point.getY();
	}
	
	@Override
	public void setup() throws Exception {
		if (_constraint instanceof String)
			_minLimit = Double.parseDouble((String)_constraint); 
		else if (_constraint instanceof Number)
			_minLimit = ((Number)_constraint).doubleValue();
		if (_minLimit != Double.NaN) 
			_minLimit *= getParent().getBBox().getDiagonalVector().getY();
	}
}
