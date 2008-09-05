package org.shapelogic.polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** Not sure if I really need this or if I am going to move it into Polygon
 *  
 * @author Sami Badawi
 *
 */
public class MultiLinePolygon extends Polygon {

	public MultiLinePolygon() {
		this(null);
	}
	
	public MultiLinePolygon(AnnotatedShapeImplementation annotatedShape) {
		super(annotatedShape);
	}

	protected List<MultiLine> _multiLines = new ArrayList<MultiLine>();
	protected Set<CLine> _independentLines = new TreeSet<CLine>();
	
	public void addMultiLine(MultiLine multiLine) {
		super.addMultiLine(multiLine);
		_multiLines.add(multiLine);
	}

	@Override
	public void endMultiLine() {
		CLine simpleLine = _currentMultiLine.toCLine();
		if (simpleLine != null)
			addIndependentLine(simpleLine.getStart(),simpleLine.getEnd());
		else {
			if (_currentMultiLine != null && _currentMultiLine.getPoints().size() > 0)
				addMultiLine(_currentMultiLine);
		}
	}
	
	@Override
    public CLine addIndependentLine(IPoint2D point1, IPoint2D point2) {
        CLine line = addLine(point1, point2);
        _independentLines.add(line);
        return line;
    }

	@Override
	public Set<CLine> getIndependentLines() {
		return _independentLines;
	}

	@Override
	public List<MultiLine> getMultiLines() {
		return _multiLines;
	}

	/** This is a little problematic
	 * 
	 */
	@Override
	public Polygon replacePointsInMap(Map<IPoint2D,IPoint2D> pointReplacementMap, 
			AnnotatedShapeImplementation annotatedShape) {
		MultiLinePolygon  replacedPolygon = new MultiLinePolygon(annotatedShape);
		replacedPolygon.setup();
	    for (CLine line : _independentLines) {
	    	CLine newLine = line.replacePointsInMap(pointReplacementMap, annotatedShape);
	    	if (!newLine.isPoint()) {
	    		replacedPolygon.addIndependentLine(newLine);
	    	}
	    }
	    for (MultiLine line : _multiLines) {
	    	MultiLine newMultiLine = line.replacePointsInMap(pointReplacementMap, annotatedShape);
	    	replacedPolygon.addMultiLine(newMultiLine);
	    }
		Set<Object> annotationForOldPolygon = null; 
		if (annotatedShape != null)
			annotationForOldPolygon = annotatedShape.getAnnotationForShapes(this);
		if (annotationForOldPolygon != null) {
			annotatedShape.putAllAnnotation(replacedPolygon, annotationForOldPolygon); 
		}
		return replacedPolygon;
	}

}
