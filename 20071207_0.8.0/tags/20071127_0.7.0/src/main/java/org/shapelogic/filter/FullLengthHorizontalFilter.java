package org.shapelogic.filter;

import org.shapelogic.polygon.CLine;

/** 
 * 
 * @author Sami Badawi
 *
 */
public class FullLengthHorizontalFilter extends PolygonLineFilter {

	@Override
	public boolean evaluate(Object line) {
		return 
		((CLine)line).isHorizontal() &&
		getParent().getBBox().getDiagonalVector().getY() * 0.9 < ((CLine)line).relativePoint().getY();
	}
}
