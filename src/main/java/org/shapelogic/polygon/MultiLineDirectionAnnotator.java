package org.shapelogic.polygon;

import java.util.List;
import java.util.Set;

import org.shapelogic.imageprocessing.LineProperties;
import org.shapelogic.imageprocessing.PointProperties;
import org.shapelogic.util.DoubleCalculations;

/** Not this functionality is in ChainCodeHandler
 * 
 *  But this is interesting on its own, 
 *  now there are no annotation going on, I need to move this in too 
 * 
 * @author Sami Badawi
 *
 */
public class MultiLineDirectionAnnotator implements ObjectAnnotator<Polygon> {
	
	private Polygon _polygon;
	private boolean _dirty = true;
	private boolean _annotationsFound = false;
	private List<PointProperties> _pointPropertiesList;
	private List<LineProperties> _linePropertiesList;

	public MultiLineDirectionAnnotator(Polygon calcValue, 
			List<PointProperties> pointPropertiesList, List<LineProperties> linePropertiesList) {
		_polygon = calcValue;
		_pointPropertiesList = pointPropertiesList;
		_linePropertiesList = linePropertiesList;
	}

	@Override
	public boolean isAnnotationsFound() {
		return _annotationsFound;
	}

	@Override
	public boolean createdNewVersion() {
		return false;
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public void setup() {

	}

	@Override
	public Polygon calc() {
		findChangeOfDirectionForLines();
		_annotationsFound = true; //XXX maybe this should be done differently
		_dirty = false;
		return _polygon;
	}

	/** Change of direction in multi line, 
	 * The current line end at the current point
	 * So you can only determine the direction change of the last point
	 * And to determine the direction change sign difference you also need to 
	 * look at previous point, this will be set on the last line. 
	 * 
	 * maybe you have to go past the start point */
	private void findChangeOfDirectionForLines() {
		Set<? extends IPoint2D> points = _polygon.getPoints();
		int numberOfPoints = points.size();
		int firstPointNumber = 1;
		boolean closed = _polygon.isClosed();
		PointProperties lastPointProperties = null;
		PointProperties previousPointProperties = null;
		LineProperties lastLineProperties = null;
		lastPointProperties = _pointPropertiesList.get(0); //First point
		if (closed) {
			lastLineProperties = _linePropertiesList.get(0); //Line ending in first point
		}
		for (int i=firstPointNumber; i < numberOfPoints; i++) {
			LineProperties currentLineProperties = null; 
			PointProperties currentPointProperties = null; // Only used to pass on
			currentLineProperties = _linePropertiesList.get(i);
			currentPointProperties = _pointPropertiesList.get(i);
			if (currentLineProperties != null && lastLineProperties != null) {
				lastPointProperties.directionChange = Calculator2D.angleBetweenLines(lastLineProperties.angle, currentLineProperties.angle);
			}
			if (previousPointProperties != null) {
				if (!DoubleCalculations.sameSign(currentPointProperties.directionChange,lastPointProperties.directionChange))
					lastLineProperties.inflectionPoint = true;
			}
			lastLineProperties = currentLineProperties;
			previousPointProperties = lastPointProperties; 
			lastPointProperties = currentPointProperties;
		}
		if (closed) {
			PointProperties firstPointProperties = _pointPropertiesList.get(0);
			PointProperties returnPointProperties = _pointPropertiesList.get(numberOfPoints-2);
			LineProperties returnLineProperties = _linePropertiesList.get(0);
			if (!DoubleCalculations.sameSign(firstPointProperties.directionChange,returnPointProperties.directionChange))
				returnLineProperties.inflectionPoint = true; 
		}
	}

	@Override
	public Polygon getValue() {
		if (isDirty())
			calc();
		return _polygon;
	}

	@Override
	public Polygon getInput() {
		return _polygon;
	}

	@Override
	public void setInput(Polygon input) {
		_polygon = input;
	}

}
