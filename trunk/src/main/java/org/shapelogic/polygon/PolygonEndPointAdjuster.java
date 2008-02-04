package org.shapelogic.polygon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/** Take a polygon as input and a list of sets of point that potentially can be
 * combined. Create a new polygon. 
 *  
 * I think that if there have been no changes then just return the original polygon.
 * I think that polygons are supposed to be immutable.
 * 
 * @author Sami Badawi
 *
 */
public class PolygonEndPointAdjuster implements Improver<Polygon> {

	protected Polygon _inputPolygon;
	protected Polygon _value;
	protected boolean _dirty = true;
	protected List<Set<IPoint2D>> _endPointsMultiClusters;
	protected Map<Set<IPoint2D>,IPoint2D> _clustersToPointMapping;
	protected Map<IPoint2D,IPoint2D> _clusterPointToCommonPointMapping;
	protected boolean _createdNewVersion = false; //XXX not set yet 
	
	public PolygonEndPointAdjuster() {
		this(null);
	}
	
	public PolygonEndPointAdjuster(Polygon inputPolygon) {
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
		_clustersToPointMapping = new HashMap<Set<IPoint2D>,IPoint2D>();
		_clusterPointToCommonPointMapping = new TreeMap<IPoint2D,IPoint2D>();
	}

	@Override
	public Polygon invoke() {
		setup();
		List<Set<IPoint2D>> clusters = _inputPolygon.getEndPointsMultiClusters();
		if (clusters.size() == 0) {
			_value = _inputPolygon;
		}
		else {
			for (Set<IPoint2D> cluster: clusters) {
				testCluster(cluster);
			}
			_value = _inputPolygon.replacePointsInMap(_clusterPointToCommonPointMapping,_inputPolygon.getAnnotatedShape());
			_value.setVersion(_inputPolygon.getVersion() + 1);
		}
		_dirty = false;
		return _value;
	}

	@Override
	public Polygon getValue() {
		if (_dirty)
			invoke();
		return _value;
	}
	
	/** If all the points in a cluster can be combined to a single point 
	 * @return the point if it is possible otherwise null
	 * 
	 * This class has very strict criteria for joining points. 
	 * Only join if all lines are either very small or straight, 
	 * and the new point keep all the straight lines straight.  
	 * This might be relaxed in subclasses, by overriding this method
	 */
	protected IPoint2D testCluster(Set<IPoint2D> cluster){
		IPoint2D commonPoint = null;
		TreeSet<CLine> shortLinesTouchingCluster = new TreeSet<CLine>();
		TreeSet<CLine> longLinesTouchingCluster = new TreeSet<CLine>();
		TreeSet<CLine> linesTouchingCluster  = new TreeSet<CLine>();
		for (IPoint2D clusterPoint: cluster) {
			linesTouchingCluster.addAll(_inputPolygon.getLinesForPoint(clusterPoint));
			for (CLine line: linesTouchingCluster) {
				if (line.distance() < 2)
					shortLinesTouchingCluster.add(line);
				else
					longLinesTouchingCluster.add(line);
			}
		}
		TreeSet<IPoint2D> adjustmentCandidates = new TreeSet<IPoint2D>();
		Iterator<CLine> longLinesIterator = longLinesTouchingCluster.iterator();
		CLine fistLongLine = longLinesIterator.next();
		if (fistLongLine == null)
			return null;
		while (longLinesIterator.hasNext()) {
			CLine longLine = longLinesIterator.next();
			commonPoint = Calculator2D.intersectionOfLines(fistLongLine,longLine);
			if (commonPoint != null) 
				adjustmentCandidates.add(commonPoint);
		}
		if (adjustmentCandidates.size() == 1){
			commonPoint = adjustmentCandidates.first();
			for (CLine line: longLinesTouchingCluster) {
				if (!Calculator2D.pointIsOnLine(commonPoint, line)) {
					commonPoint = null;
					break;
				}
			}
		}
		if (commonPoint != null) {
			_clustersToPointMapping.put(cluster,commonPoint);
			for (IPoint2D point: cluster) {
				_clusterPointToCommonPointMapping.put(point,commonPoint);
			}
		}
		return commonPoint;
	}
	
	public static boolean startPointIsClosest(CLine line, IPoint2D clusterPoint) {
		return line.getStart().distance(clusterPoint) <
			line.getEnd().distance(clusterPoint);
	}
	
	public static IPoint2D extendLine(CLine line, IPoint2D clusterPoint) {
		IPoint2D extendedPoint = null;
		if (startPointIsClosest(line, clusterPoint)) {
			extendedPoint = line.getStart();
		}
		else {
			extendedPoint = line.getEnd();
		}
		return extendedPoint;
	}
	
	public boolean adjustmentPointOkForLine(CLine line, IPoint2D newPoint) {
		return false;
	}

	public List<Set<IPoint2D>> getEndPointsMultiClusters() {
		return _endPointsMultiClusters;
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
