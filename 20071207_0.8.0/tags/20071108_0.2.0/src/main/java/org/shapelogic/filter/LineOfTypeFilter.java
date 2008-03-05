package org.shapelogic.filter;

import java.util.Set;

import org.shapelogic.polygon.CLine;

/** 
 * 
 * @author Sami Badawi
 *
 */
public class LineOfTypeFilter extends PolygonLineFilter {

	@Override
	public boolean evaluate(Object line) {
		Set<Object> set = getParent().getAnnotationForShapes((CLine)line);
		if (set == null && _constraint == null) return true;
		else if (_constraint != null && set != null)
			return getParent().getAnnotationForShapes((CLine)line).contains(_constraint);
		else if (set != null) return true;
		else return false;
	}
}
