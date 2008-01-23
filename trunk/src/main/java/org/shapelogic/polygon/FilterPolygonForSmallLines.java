package org.shapelogic.polygon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** Take a polygon as input and a list of sets of point that potentially can be
 * combined. Create a new polygon. 
 *  
 * I think that if there have been no changes then just return the original polygon.
 * I think that polygons are supposed to be immutable.
 * 
 * @author Sami Badawi
 *
 */
public class FilterPolygonForSmallLines implements Improver<Polygon> {
	public static double SMALL_LINE_LIMIT = 0.10;
	protected Polygon _inputPolygon;
	protected Polygon _value;
	protected boolean _dirty = true;
	protected List<Set<IPoint2D>> _endPointsMultiClusters;
	protected Map<Set<IPoint2D>,IPoint2D> _clustersToPointMapping;
	protected Map<IPoint2D,IPoint2D> _clusterPointToCommonPointMapping;
	protected boolean _createdNewVersion = false; //XXX not set yet
	protected double _smallLineCutOffLength = 1.;
	protected int _smallLinesFiltered = 0;
	
	public FilterPolygonForSmallLines() {
		this(null);
	}
	
	public FilterPolygonForSmallLines(Polygon inputPolygon) {
		this._inputPolygon = inputPolygon;
	}
	
	@Override
	public Polygon getInput() {
		return _inputPolygon;
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public void setup() {
		_dirty = true;
//		_endPointsMultiClusters;
		_clustersToPointMapping = new HashMap<Set<IPoint2D>,IPoint2D>();
		_clusterPointToCommonPointMapping = new TreeMap<IPoint2D,IPoint2D>();
		_createdNewVersion = false;
		_smallLineCutOffLength =  SMALL_LINE_LIMIT * _inputPolygon.getDiameter();
		_value = PolygonFactory.createSameType(_inputPolygon);
		_smallLinesFiltered = 0;
	}

	@Override
	public Polygon calc() {
		setup();
		for (CLine lines: _inputPolygon.getLines()) {
			handleLine(lines);
		}
		List<MultiLine> multiLines = _inputPolygon.getMultiLines();
		if (multiLines != null)
		for (MultiLine lines: multiLines) {
			handleMulitLine(lines);
		}
		if (_smallLinesFiltered == 0)
			_value = _inputPolygon;
		_value.getValue();
		_dirty = false;
		return _value;
	}

	@Override
	public Polygon getValue() {
		if (_dirty)
			calc();
		return _value;
	}
	
	/** I think that this need to be done differently for different types of polygons
	 * For Polygon: Just go through lines and filter
	 * For MultiLinePolygon: Run through both multi lines and independent lines 
	 * 
	 * How do I get this unified?
	 * Change so they have the same interface so now Polygon also return independent lines.
	 * 
	 * This can be optimized
	 */
	protected void handleLine(CLine line){
		boolean filter = false;
		if (line.distance() < _smallLineCutOffLength) {
			Map<IPoint2D, Integer> pointsCountMap = _inputPolygon.getPointsCountMap();
			if (pointsCountMap.get(line.getStart()) == 1 || pointsCountMap.get(line.getEnd()) == 1) {
				filter = true;
			}
		}
		if (filter) {
			_smallLinesFiltered++;
		}
		else {
			_value.addIndependentLine(line);
		}
	}
	
	private void handleMulitLine(MultiLine multiLine) {
		if (multiLine == null)
			return;
		int sizeOfMulitLine = multiLine.getPoints().size();
		if (sizeOfMulitLine < 2) {
			_smallLinesFiltered++;
			return;
		}
		if (sizeOfMulitLine == 2 && multiLine.getDiameter() < _smallLineCutOffLength){
			_smallLinesFiltered++;
			return;
		}
		//XXX this needs to be expanded to handling the start and end point
		boolean removeFirst = false;
		if (multiLine.getStart().distance(multiLine.getPoints().get(1)) < 
				_smallLineCutOffLength) {
			removeFirst = true;
		}
		boolean removeLast = false;
		int lastNumber = multiLine.getPoints().size() -1;
		IPoint2D secondLastPoint = multiLine.getPoints().get(lastNumber-1);
		if (multiLine.getEnd().distance(secondLastPoint) < _smallLineCutOffLength) {
			removeLast = true;
		}
		if (removeFirst || removeLast) {
			MultiLine result = new MultiLine(_value.getAnnotatedShape());
			for (int i=0; i<multiLine.getPoints().size(); i++) {
				if (i == 0) {
					if (!removeFirst)
						result.addAfterEnd(multiLine.getPoints().get(i));
				}
				else if (i == lastNumber) {
					if (!removeLast)
						result.addAfterEnd(multiLine.getPoints().get(i));
				}
				else
					result.addAfterEnd(multiLine.getPoints().get(i));
			}
			_value.addMultiLine(result);
			return;
		}
		_value.addMultiLine(multiLine);
	}

	@Override
	public boolean createdNewVersion() {
		return _createdNewVersion;
	}

	@Override
	public void setInput(Polygon input) {
		_inputPolygon = input;
	}

}
