package org.shapelogic.filter;

public abstract class PolygonSpatialPointFilter extends PolygonPointFilter {
	protected double _limit = Double.NaN;

	@Override
	public void setup() throws Exception {
		if (_constraint instanceof String)
			_limit = Double.parseDouble((String)_constraint); 
		else if (_constraint instanceof Number)
			_limit = ((Number)_constraint).doubleValue();
		if (_limit != Double.NaN) 
			_limit *= getParent().getBBox().getDiagonalVector().getY();
	}
}
