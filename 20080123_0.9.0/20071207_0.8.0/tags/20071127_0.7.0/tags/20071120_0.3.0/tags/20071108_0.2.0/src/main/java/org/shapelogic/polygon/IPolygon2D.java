package org.shapelogic.polygon;

import java.util.Set;

/** there is only one class implementing this I should probably take it out 
 * 
 * @author Sami Badawi
 *
 */
public interface IPolygon2D extends GeometricShape2D {
	Set<? extends IPoint2D> getPoints();
	Set<CLine> getLines();
	BBox getBBox();
	boolean isClosed() ;
    double getAspectRatio();
}
