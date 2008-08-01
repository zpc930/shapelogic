package org.shapelogic.filter;

import org.shapelogic.polygon.CLine;

/** Filter lines that are both horizontal and also full length of the multi line it is part of.
 * 
 * @author Sami Badawi
 *
 */
public class FullLengthHorizontalFilter extends PolygonLineFilter {

	@Override
	public boolean evaluate(Object line) {
		return 
		((CLine)line).isHorizontal() &&
		Math.abs(getParent().getBBox().getDiagonalVector().getY()) * 0.9 < 
		Math.abs(((CLine)line).relativePoint().getY());
	}
}
