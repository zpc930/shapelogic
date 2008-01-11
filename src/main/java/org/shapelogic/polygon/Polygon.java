package org.shapelogic.polygon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.shapelogic.calculation.LazyCalc;
import org.shapelogic.filter.FilterFactory;
import org.shapelogic.filter.IFilter;

/** Polygon is the bottom class for polygon works for points on any kind
 * 
 * Almost immutable: you can extend it by adding more
 * but you cannot change the element, and the underlying points are mutable.
 * So point should not be changed after they are added.
 * 
 * Note that this and the sub class MultiLinePolygon has the exactly same interface 
 * 
 * @author Sami Badawi
 *
 */
public class Polygon extends BaseAnnotatedShape 
	implements IPolygon2D, LazyCalc<Polygon>, Cloneable, PointReplacable<Polygon>
{
	private static final double MAX_DISTANCE_BETWEEN_CLUSTER_POINTS = 2;
	protected BBox _bBox; 
	protected Set<CLine> _lines;
	protected Set<IPoint2D> _points;
	protected boolean _dirty = true;
	protected double _aspectRatio;
	protected boolean _closed;
	protected int _endPointCount = -1;
	protected Map<IPoint2D,Integer> _pointsCountMap;
	protected Map<IPoint2D,Set<CLine>> _pointsToLineMap;
	protected int _version; 
	protected MultiLine _currentMultiLine;
	protected List<Set<IPoint2D> > _endPointsClusters;
	//I could make this lazy
	protected AnnotatedShapeImplementation _annotatedShape; 
	protected List<Improver<Polygon> > _polygonImprovers;

	public Polygon() {
		this(null);
	}
	
	public Polygon(AnnotatedShapeImplementation annotatedShape) {
		super(annotatedShape);
		setup();
		internalFactory();
	}
	
	/** All the objects that needs special version should be created here. */
	protected void internalFactory() {
		_polygonImprovers = new ArrayList<Improver<Polygon> >();
		_polygonImprovers.add(new FilterPolygonForSmallLines());
		_polygonImprovers.add(new PolygonAnnotator());
	}
	
	@Override
	public BBox getBBox() {
		getCalcValue();
		return _bBox;
	}

	@Override
	public Set<CLine> getLines() {
		return _lines;
	}

	@Override
	public Set<IPoint2D> getPoints() {
		return _points;
	}

	@Override
	public double getAspectRatio() {
		getCalcValue();
		return _aspectRatio;
	}

	@Override
	/** Does this make sense for a polygon or only for multi line */
	public boolean isClosed() {
		return _closed;
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public void setup() {
		_lines = new TreeSet<CLine>();
		_points = new TreeSet<IPoint2D>();
		_bBox = new BBox();
	}

    public boolean containsPoint(IPoint2D point) {
        return _points.contains(point);
    }

    public boolean containsLine(ILine2D line) {
        return _lines.contains(line);
    }

    private void addPoint(IPoint2D point) {
        if (!containsPoint(point))
            _points.add(point);
    }
    
    /** this should not be used use addIndependentLine() instead */
    public CLine addLine(IPoint2D point1, IPoint2D point2) {
        CLine line = CLine.makeUnordered(point1, point2);
        if (!containsLine(line))
            _lines.add(line);
        addPoint(point1);
        addPoint(point2);
        return line;
    }

    /** this should not be used use addIndependentLine() instead */
    @Deprecated
    protected CLine addLine(CLine line) {
        if (!containsLine(line)) {
            _lines.add(line);
            addPoint(line.getStart());
            addPoint(line.getEnd());
        }
        return line;
    }
    
	@Override
	public Polygon calc() {
        _bBox = findBbox();
        double lenX = _bBox.maxVal.getX() - _bBox.minVal.getX();
        double lenY = _bBox.maxVal.getY() - _bBox.minVal.getY();
        if (lenX > 0)
            _aspectRatio = lenY / lenX;
        else
            _aspectRatio = Double.POSITIVE_INFINITY;
        findPointCount();
        _dirty = false;
        return this;
    }

    private BBox findBbox() {
        _bBox = new BBox();
        for (IPoint2D pointInPolygon : _points )
            _bBox.addPoint(pointInPolygon);
        return _bBox;
    }
    
    /** Return a cleaned up polygon 
     * 
     * @param onlyInt Change all coordinates to integers
     * @param procentage of diagonal of b box that should be considered as same point
     */
    public Polygon cleanUp(boolean onlyInt, double procentage) {
        findBbox();
        double threshold = _bBox.getDiameter() * procentage;
        List<IPoint2D> roundedPoints = new ArrayList<IPoint2D>();
        Map<IPoint2D, IPoint2D> pointMap = new HashMap<IPoint2D, IPoint2D>();
        for (IPoint2D point : _points) {
        	IPoint2D roundedPoint = point;
        	if (onlyInt) {
        		roundedPoint = point.copy().round();
        		if (roundedPoint.equals(point) )
        			roundedPoint = point; //uses the same point, do not create new 
        	}
        	Iterator<IPoint2D> roundPointIterator = roundedPoints.iterator();
        	boolean moreRoundedPoints = roundPointIterator.hasNext();
        	IPoint2D foundPoint = null;
        	while (moreRoundedPoints) {
        		IPoint2D point2 = roundPointIterator.next(); 
        		pointMap.put(point, point);
        		if (roundedPoint.distance(point2) < threshold) {
        			foundPoint = point2;
        			moreRoundedPoints = false;
        		}
        		else
        			moreRoundedPoints = roundPointIterator.hasNext();
        	}
        	if (foundPoint == null) {
        		roundedPoints.add(roundedPoint);
        		pointMap.put(point, roundedPoint);
        	}
        	else { 
        		pointMap.put(point, foundPoint);
        	}
          
        }
        return replacePointsInMap(pointMap,null);
    }

    /** register a list of improvers and call them here */
    public Polygon improve() {
    	if (_polygonImprovers == null)
    		return this;
    	Polygon result = this;
    	for (Improver<Polygon> improver: _polygonImprovers) {
    		improver.setInput(result);
    		result = improver.getCalcValue(); 
    	}
    	return result;
    }
    
	@Override
	public Polygon getCalcValue() {
		if (isDirty())
			calc();
		return this;
	}
	
	public List<CLine> getVerticalLines() {
		return CLine.filterVertical(_lines);
	}
	
	public List<CLine> getHorizontalLines() {
		return CLine.filterHorizontal(_lines);
	}
	
	/** Find how many lines each point is part of by making a map */
	public Map<IPoint2D,Integer> getPointsCountMap() {
		if (_pointsCountMap == null) {
			_pointsCountMap = new TreeMap<IPoint2D,Integer>();
			for (CLine line: _lines) {
				Integer startCount = _pointsCountMap.get(line.getStart());
				if (startCount == null)
					startCount = 1;
				else
					startCount++;
				_pointsCountMap.put(line.getStart(),startCount);
				if (line.isPoint())
					continue;
				Integer endCount = _pointsCountMap.get(line.getEnd());
				if (endCount == null)
					endCount = 1;
				else
					endCount++;
				_pointsCountMap.put(line.getEnd(),endCount);
			}
		}
		return _pointsCountMap;
	}
	
	/** Find how many lines each point is part of by making a map */
	public Map<IPoint2D,Set<CLine>> getPointsToLineMap() {
		if (_pointsToLineMap == null) {
			_pointsToLineMap = new TreeMap<IPoint2D,Set<CLine>>();
			for (CLine line: _lines) {
				Set<CLine> lineSetForStartPoint = _pointsToLineMap.get(line.getStart());
				if (lineSetForStartPoint == null) {
					lineSetForStartPoint = new TreeSet<CLine>();
					_pointsToLineMap.put(line.getStart(),lineSetForStartPoint);
				}
				lineSetForStartPoint.add(line);
				Set<CLine> lineSetForEndPoint = _pointsToLineMap.get(line.getEnd());
				if (lineSetForEndPoint == null) {
					lineSetForEndPoint = new TreeSet<CLine>();
					_pointsToLineMap.put(line.getEnd(),lineSetForEndPoint);
				}
				lineSetForEndPoint.add(line);
			}
		}
		return _pointsToLineMap;
	}
	
	public int findPointCount() {
		getPointsCountMap();
		Integer ONE = 1;
		_endPointCount = 0;
		for (IPoint2D point: _pointsCountMap.keySet()) {
			if (ONE.equals(_pointsCountMap.get(point) ))
				_endPointCount++;
		}
		return _endPointCount;
	}

	public int getEndPointCount() {
		getCalcValue();
		return _endPointCount;
	}
	
	public Collection<CLine> getLinesForPoint(IPoint2D point) {
		TreeSet<CLine> result = new TreeSet<CLine>();  
		if (point == null)
			return result;
		getCalcValue();
		if (!_points.contains(point))
			return result;
		for (CLine line: _lines) {
			if (line.getStart().equals(point) || line.getEnd().equals(point))
			result.add(line);
		}
		return result;
	}

	public int getVersion() {
		return _version;
	}

	public void setVersion(int version) {
		_version = version;
	}

	public void startMultiLine() {
		_currentMultiLine = new MultiLine(this.getAnnotatedShape());
	}
	
	public void addBeforeStart(IPoint2D newPoint) {
		_currentMultiLine.addBeforeStart(newPoint);
	}

	public void addAfterEnd(IPoint2D newPoint) {
		_currentMultiLine.addAfterEnd(newPoint);
	}

	/** Add all the lines segments in the multi line to _lines */
	public void endMultiLine() {
		if (_currentMultiLine != null && _currentMultiLine.getPoints().size() > 0)
			addMultiLine(_currentMultiLine);
		_currentMultiLine = null;
	}

	public void addMultiLine(MultiLine multiLine) {
		IPoint2D lastPoint = null;
		int linesAdded = 0;
		for (IPoint2D point: multiLine.getPoints()) {
			if (lastPoint == null) {
				lastPoint = point;
			}
			else {
				addLine(lastPoint, point);
				linesAdded++;
			}
			lastPoint = point;
		}
		if (linesAdded == 0 && lastPoint != null)
			addLine(lastPoint, lastPoint);
	}

	public MultiLine getCurrentMultiLine() {
		return _currentMultiLine;
	}

	public List<Set<IPoint2D>> getEndPointsClusters() {
		if (_endPointsClusters == null) {
			_endPointsClusters = new ArrayList<Set<IPoint2D>>(); 
			for (IPoint2D point: getPoints()) {
				inner_loop: for (Set<IPoint2D> cluster : _endPointsClusters) {
					if (point.distance(cluster.iterator().next()) <= MAX_DISTANCE_BETWEEN_CLUSTER_POINTS) {
						cluster.add(point);
						break inner_loop;
					}
				}
				TreeSet<IPoint2D> cluster = new TreeSet<IPoint2D>();
				cluster.add(point);
				_endPointsClusters.add(cluster);
			}
		}
		return _endPointsClusters;
	}

	public List<Set<IPoint2D>> getEndPointsMultiClusters() {
		List<Set<IPoint2D>> result = new ArrayList<Set<IPoint2D>>();
		for (Set<IPoint2D> cluster: getEndPointsClusters()) {
			if (cluster.size()>1)
				result.add(cluster);
		}
		return result;
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** No filtering in first version */
	@Override
	public Polygon replacePointsInMap(Map<IPoint2D,IPoint2D> pointReplacementMap,
			AnnotatedShapeImplementation annotatedShape) {
		Polygon replacedPolygon = new Polygon(annotatedShape);
		replacedPolygon.setup();
	    for (CLine line : _lines) {
	    	CLine newLine = line.replacePointsInMap(pointReplacementMap,annotatedShape);
			if (!newLine.isPoint()) {
				replacedPolygon.addLine(newLine);
			}
		}
		Set<Object> annotationForOldPolygon = null; 
		if (annotatedShape != null)
			annotationForOldPolygon = annotatedShape.getAnnotationForShapes(this);
		if (annotationForOldPolygon != null) {
			annotatedShape.putAllAnnotation(replacedPolygon, annotationForOldPolygon); 
		}
		return replacedPolygon;
	}

	@Override
	public IPoint2D getCenter() {
		return _bBox.getCenter();
	}

	@Override
	public double getDiameter() {
		return getBBox().getDiameter();
	}
	
	public void setPolygonImprovers(List<Improver<Polygon>> improvers) {
		_polygonImprovers = improvers;
	}

	/** To have the same interface as MultiLinePolygon */
	public CLine addIndependentLine(IPoint2D point1, IPoint2D point2) {
		return addLine(point1, point2);
	}

    /** Most of the time this should not be used use the version taking input 
     * points instead */
    protected CLine addIndependentLine(CLine line) {
        return addLine(line);
    }
    
	/** To have the same interface as MultiLinePolygon */
	public Set<CLine> getIndependentLines() {
		return getLines();
	}

	/** To have the same interface as MultiLinePolygon 
	 * returns null 
	 * since this and the independent lines are supposed to be all the lines
	 * */
	public List<MultiLine> getMultiLines() {
		return null;
	}
	
	public <Element> Collection<Element> filter(IFilter<Polygon, Element> filterObject) {
		filterObject.setParent(this);
		return filterObject.filter();
	}
	
	public <Element> Collection<Element> filter(String inputExpression) {
		IFilter<Polygon, Element> filterObject = FilterFactory.makeTreeFilter(inputExpression);
		filterObject.setParent(this);
		return filterObject.filter();
	}
	
	public int getHoleCount() {
		return getLines().size() + 1 - getPoints().size();
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Lines:\n");
		for (CLine line: getLines()) {
			result.append(line);
		}
		result.append("\nPoints:\n");
		for (IPoint2D point: getPoints()) {
			result.append(point);
		}
		result.append("\nAnnotations:\n");
		Map<Object, Set<GeometricShape2D>> map = getAnnotatedShape().getMap();
		for (Entry<Object, Set<GeometricShape2D>> entry: map.entrySet())
			result.append(entry.getKey() +":\n" + entry.getValue() + "\n");
		result.append("\naspectRatio: " + getBBox().getAspectRatio());
		return result.toString();
	}
}
