package org.shapelogic.polygon;

import java.util.Map;
import java.util.Set;

/** Interface of an annotated shape.
 * 
 * Polygon, MultiLine and ChainCodeHander implement this.<p> 
 * 
 * Currently there are a few base classes that implements the functionality in this.
 * And the other subclass them .
 * 
 * @author Sami Badawi
 *
 */
public interface AnnotatedShape {

	Map<Object, Set<GeometricShape2D>> getMap();
	void putAnnotation(GeometricShape2D shape, Object annotation);
	void setup();
	AnnotatedShapeImplementation getAnnotatedShape();
	Set<GeometricShape2D> getShapesForAnnotation(Object annotation);
	Set<Object> getAnnotationForShapes(GeometricShape2D shape);
	void putAllAnnotation(GeometricShape2D shape, Set<Object> annotationKeySet);
}
