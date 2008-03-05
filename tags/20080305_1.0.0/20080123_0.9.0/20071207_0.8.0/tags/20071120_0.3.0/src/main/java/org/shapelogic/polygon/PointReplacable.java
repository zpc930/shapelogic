package org.shapelogic.polygon;

import java.util.Map;

/**   
 * 
 * @author Sami Badawi
 *
 */
public interface PointReplacable<Result> {
	public Result replacePointsInMap(Map<IPoint2D,IPoint2D> pointReplacementMap, AnnotatedShapeImplementation annotatedShape);

}
