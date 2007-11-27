package org.shapelogic.filter;

import java.util.Set;

import org.shapelogic.imageprocessing.PointType;
import org.shapelogic.polygon.IPoint2D;

/** Filtering based on annotation for points
 * Note that this only works for filters of point type
 * 
 * @author Sami Badawi
 *
 */
public class PointOfTypeFilter extends PolygonPointFilter {

	@Override
	public boolean evaluate(Object point) {
		Set<Object> set = getParent().getAnnotationForShapes((IPoint2D)point);
		if (set == null && _constraint == null) return true;
		else if (_constraint != null && set != null)
			return getParent().getAnnotationForShapes((IPoint2D)point).contains(_constraint);
		else if (set != null) return true;
		else return false;
	}
	
	@Override
	/** Set what type of Point that you want to filter
	 * 
	 * When you specify a logical expression like this
	 * This will create an filter PointOfTypeFilter
	 * and then call setConstraint("T_JUNCTION") 
	 * PointOfTypeFilter(\"T_JUNCTION\")
	 * 
	 * If this is a string try to parse it to a PointType first 
	 */
	public void setConstraint(Object constraint){
		PointType typeConstraint; 
		if (constraint instanceof String) {
			String stringType = (String)constraint;
			typeConstraint = PointType.valueOf(stringType);
			if (typeConstraint != null) {
				super.setConstraint(typeConstraint);
				return;
			}
		}
		super.setConstraint(constraint);
	}
}
