package org.shapelogic.imageprocessing;

import org.apache.commons.math.stat.StatUtils;
import org.shapelogic.polygon.BBox;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.MultiLine;
import org.shapelogic.polygon.ObjectAnnotator;

/** Class that can find a circle in MultiLine. 
 * 
 * If it find a circle it will annotate the MultiLine as a circle 
 * and set the circle center
 * 
 * What is the input and output?
 * 
 * I think that it should take a circle
 * 
 * This class do not really need a state so I build a state for no good reason
 * Should this still implement ShapeAnnotator<MultiLine> or just have a static method?
 * 
 * I think that if I need to register a lot of them, then they need to 
 * implement an interface.
 * 
 * @author Sami Badawi
 *
 */
public class CircleFinder implements ObjectAnnotator<MultiLine> {
	private boolean _annotationsFound;
	private MultiLine _value;
	private boolean _dirty = true;
	
	public CircleFinder() {
		this(null);
	}

	public CircleFinder(MultiLine input) {
		_value = input;
	}
	
	/** For this no state is needed, so this does not build the state
	 * @return true if a circle was found 
	 */
	static public boolean findAnnotations(MultiLine multiLine) {
		if (multiLine == null)
			return false;
		if (!multiLine.isClosed())
			return false;
		BBox bBox = multiLine.getBBox();
		int numberOfPoints = multiLine.getPoints().size();
		double[] distanceArray = new double[numberOfPoints]; 
		IPoint2D center = bBox.getCenter();
		int i = 0;
		for (IPoint2D point: multiLine.getPoints()) {
			distanceArray[i] = center.distance(point);
			i++;
		}
		double mean = StatUtils.mean(distanceArray);
		double variance = StatUtils.variance(distanceArray);
//		double median = StatUtils.percentile(values,50.);
		double std = Math.sqrt(variance);
		if (Math.abs(std/mean) > 0.1) { 
			return false;
		}
		multiLine.setCenterForCircle(center);
		multiLine.putAnnotation(multiLine, GeometricType.CIRCLE); 
		return true;
	}

	@Override
	public boolean createdNewVersion() {
		return false;
	}

	@Override
	public MultiLine getInput() {
		return _value;
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return _dirty;
	}

	@Override
	public void setup() {
		_value = null;
		_annotationsFound = false;
		_dirty = true;
	}

	@Override
	public MultiLine calc() {
		_annotationsFound = findAnnotations(_value);
		_dirty = false;
		return _value;
	}

	@Override
	public MultiLine getValue() {
		if (isDirty())
			calc();
		return _value;
	}

	@Override
	public boolean isAnnotationsFound() {
		getValue();
		return _annotationsFound;
	}

	@Override
	public void setInput(MultiLine input) {
		_value = input;
	}

}
