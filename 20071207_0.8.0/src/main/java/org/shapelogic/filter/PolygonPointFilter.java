package org.shapelogic.filter;

import java.util.Collection;

import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.Polygon;

/** 
 * 
 * @author Sami Badawi
 *
 */
public abstract class PolygonPointFilter extends  BaseFilter<Polygon,IPoint2D> {

	@Override
	public Collection<IPoint2D> getCollection() {
		if (_collection == null && _parent != null) {
			_collection = _parent.getPoints();
		}
		return _collection;
	}
}
