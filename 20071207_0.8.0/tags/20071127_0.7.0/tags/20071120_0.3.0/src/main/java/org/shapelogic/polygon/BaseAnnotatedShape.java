package org.shapelogic.polygon;

import java.util.Map;
import java.util.Set;

/** This is an adapter class for AnnotatedShape, working as an abstract base
 * class for classes that need to implement AnnotatedShape
 * 
 * Can make lazy init later
 * @author Sami Badawi
 *
 */
public abstract class BaseAnnotatedShape implements AnnotatedShape {
	protected AnnotatedShapeImplementation _annotatedShape;

	public BaseAnnotatedShape() {
		this(null);
	}
	
	public BaseAnnotatedShape(AnnotatedShapeImplementation annotatedShape) {
		super();
		if (annotatedShape != null)
			_annotatedShape = annotatedShape;
		else
			_annotatedShape = new AnnotatedShapeImplementation();
	}
	
	@Override
	public Map<Object, Set<GeometricShape2D>> getMap() {
		return _annotatedShape.getMap();
	}
	
	@Override
	public void putAnnotation(GeometricShape2D shape, Object annotationKey) {
		_annotatedShape.putAnnotation(shape, annotationKey);
	}

	@Override
	public void putAllAnnotation(GeometricShape2D shape, Set<Object> annotationKeySet) {
		_annotatedShape.putAllAnnotation(shape,annotationKeySet);
	}

	@Override
	public AnnotatedShapeImplementation getAnnotatedShape() {
		return _annotatedShape;
	}

	@Override
	public void setup() {
		_annotatedShape.setup();
	}

	@Override
	public Set<GeometricShape2D> getShapesForAnnotation(Object annotation) {
		return _annotatedShape.getShapesForAnnotation(annotation);
	}

	@Override
	public Set<Object> getAnnotationForShapes(GeometricShape2D shape) {
		return _annotatedShape.getAnnotationForShapes(shape);
	}

}
