package org.shapelogic.filter;

import org.shapelogic.polygon.CLine;

/** 
 * 
 * @author Sami Badawi
 *
 */
public class FullLengthVerticalFilter extends PolygonLineFilter {

	@Override
	public boolean evaluate(Object line) {
		return 
		((CLine)line).isVertical() &&
		getParent().getBBox().getDiagonalVector().getY() * 0.9 < ((CLine)line).relativePoint().getY();
	}
}
