package org.shapelogic.imageprocessing;

import org.shapelogic.polygon.Polygon;

/** Interface for edge tracers.
 * 
 * @author Sami Badawi
 *
 */
public interface IEdgeTracer {

	Polygon autoOutline(int startX, int startY);

}
