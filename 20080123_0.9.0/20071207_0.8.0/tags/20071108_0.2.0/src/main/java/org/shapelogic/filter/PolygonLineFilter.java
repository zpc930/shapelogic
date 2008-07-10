package org.shapelogic.filter;

import java.util.Collection;

import org.shapelogic.polygon.CLine;
import org.shapelogic.polygon.Polygon;

/** 
 * 
 * @author Sami Badawi
 *
 */
public abstract class PolygonLineFilter extends  BaseFilter<Polygon,CLine> {

	@Override
	public Collection<CLine> getCollection() {
		if (_collection == null && _parent != null) {
			_collection = _parent.getLines();
		}
		return _collection;
	}
}
