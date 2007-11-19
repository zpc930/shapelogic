package org.shapelogic.filter;

import org.shapelogic.polygon.IPoint2D;

public abstract class PolygonSpatialPointFilter extends PolygonPointFilter {
	protected double _limit = Double.NaN;
	protected double _constraintNumber = Double.NaN;

	@Override
	public void setup() throws Exception {
		if (_constraint instanceof String)
			_constraintNumber = Double.parseDouble((String)_constraint); 
		else if (_constraint instanceof Number)
			_constraintNumber = ((Number)_constraint).doubleValue();
		if (_constraintNumber != Double.NaN) 
			_limit = coordinateChoser(getParent().getBBox().getDiagonalVector(_constraintNumber));
	}
	
	protected abstract double coordinateChoser(IPoint2D diagonalVector);
}
