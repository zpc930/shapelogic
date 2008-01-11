package org.shapelogic.polygon;

import static org.shapelogic.util.MapOperations.getPointWithDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.shapelogic.calculation.LazyCalc;
import org.shapelogic.util.LineType;


/** A list of point on a continues line that does not have any intersections.
 * 
 * But it can contain turns.
 * 
 * Should this be mutable or immutable?
 * This is just a list of already existing point so immutable should be fine.
 * I can change this later.
 * 
 * @author Sami Badawi
 *
 */
public class MultiLine extends BaseAnnotatedShape 
	implements ILine2D, LazyCalc<MultiLine>, PointReplacable<MultiLine>, AnnotatedShape 
{
	/** UNKNOWN means not tested for round, but treat as a multi line
	 * NOT_ROUND means that it is tested for any of the round categories and it is not, treat it as a multi line
	 * STRAIGHT lines combined to a straight line, say for an F
	 * CIRCLE_ARCH_FORWARDS, from the first point move in increasing angle to get to second point
	 */
	protected ArrayList<IPoint2D> _points = new ArrayList<IPoint2D>();
	/** Should be set if the multi line turns out to be a circle */
	protected IPoint2D _centerForCircle;
	protected BBox _bBox = new BBox();
	protected boolean _dirty = true;
	protected LineType _lineType = LineType.UNKNOWN;
	//I could make this lazy
//	protected AnnotatedShape _annotatedShape;
	protected Boolean _closedLineClockWise = null;
	
	public MultiLine(AnnotatedShapeImplementation annotatedShape) {
		super(annotatedShape);
	}

	@Override
	public IPoint2D getEnd() {
		if (_points.size() > 0)
			return _points.get(_points.size() - 1);
		return null;
	}

	@Override
	public IPoint2D getStart() {
		if (_points.size() > 0)
			return _points.get(0);
		return null;
	}
	
	public void addBeforeStart(IPoint2D newPoint) {
		_points.add(0, newPoint);
	}

	public void addAfterEnd(IPoint2D newPoint) {
		if (!newPoint.equals(getEnd()))
			_points.add(newPoint);
	}

	@Override
	/** Sorts all points alphabetically.
	 * 
	 * This is not very reliable, could cause problems
	 */
	public int compareTo(ILine2D other) {
		MultiLine that = (MultiLine) other;
		int thisLenght = this._points.size();
		int thatLenght = that._points.size();
		int minPointLenght = Math.min(thisLenght,thatLenght);
		for (int i=0; i < minPointLenght; i++) {
			int result = _points.get(i).compareTo(that.getPoints().get(i));
			if (result != 0)
				return result;
		}
		if (thisLenght > thatLenght) return 1;
		else return -1;
	}
	
	@Override
	/** Overriden equals.
	 * Problems with this method: 
	 * Change to be able to compare with CLine
	 * hashcode() should also be changed
	 */
	public boolean equals(Object obj){
		if (!(obj instanceof MultiLine)) {
			if (obj instanceof CLine) {
				CLine line = (CLine)obj;
				return line.equals(toCLine());
			}
			return false;
		}
		MultiLine that = (MultiLine) obj; 
		return compareTo(that) == 0;
	}

	public List<? extends IPoint2D> getPoints() {
		return _points;
	}

	public void setPoints(List<? extends IPoint2D> points) {
		if (points instanceof ArrayList)
			_points = (ArrayList)points;
		else {
			_points.clear();
			_points.addAll(points);
		}
			
	}
	
	/** Not sure what to return.
	 * Maybe an array of CMultiLine 
	 * 
	 * @param splitPoint
	 */
	public MultiLine[] split(IPoint2D splitPoint) {
		int splitIndex = _points.indexOf(splitPoint);
		return split(splitIndex);
	}

	/** Not sure what to return.
	 * Maybe an array of CMultiLine 
	 * 
	 * @param splitIndex
	 */
	public MultiLine[] split(int splitIndex) {
		if (splitIndex < 0 || _points.size() <= splitIndex)
			return null;
		MultiLine firstMultiLine = new MultiLine(this.getAnnotatedShape()); 
		MultiLine secondMultiLine = new MultiLine(this.getAnnotatedShape());
		for (int i=0; i<=splitIndex; i++) {
			firstMultiLine.addAfterEnd(_points.get(i));
		}
		for (int i=splitIndex; i<_points.size(); i++) {
			firstMultiLine.addAfterEnd(_points.get(i));
		}
		MultiLine[] result = {firstMultiLine, secondMultiLine};
		return result;
	}

	public IPoint2D getCenterForCircle() {
		return _centerForCircle;
	}

	public void setCenterForCircle(IPoint2D forCircle) {
		_centerForCircle = forCircle;
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public void setup() {
		if (_annotatedShape != null)
			_annotatedShape.setup();
	}

	@Override
	public MultiLine calc() {
		for (IPoint2D point: _points) {
			_bBox.addPoint(point);
		}
		_dirty = false;
		return this;
	}

	@Override
	public MultiLine getCalcValue() {
		if (_dirty)
			calc();
		return this;
	}

	public BBox getBBox() {
		getCalcValue();
		return _bBox;
	}

	public CLine toCLine() {
		if (_points.size() == 2)
			return new CLine(_points.get(0),_points.get(1));
		if (_points.size() == 1)
			return new CLine(_points.get(0),_points.get(0));
		return null;
	}

	public LineType getLineType() {
		return _lineType;
	}
	
	public boolean isClosed() {
		return _points.size() > 1 && getStart().equals(getEnd()); 
	}

	@Override
	public MultiLine replacePointsInMap(
			Map<IPoint2D, IPoint2D> pointReplacementMap, AnnotatedShapeImplementation annotatedShape) {
		MultiLine replacemetMultiLine = new MultiLine(this.getAnnotatedShape());
		IPoint2D lastOldPoint = null;
		for (IPoint2D point: getPoints()) {
			//Annotate point
			IPoint2D newPoint = getPointWithDefault(pointReplacementMap, point);
			replacemetMultiLine.addAfterEnd(newPoint);
			//Annotate line
			if (lastOldPoint != null) {
				CLine oldLine = CLine.makeUnordered(lastOldPoint,point);
				oldLine.replacePointsInMap(pointReplacementMap,annotatedShape);
			}
			lastOldPoint = point;
		}
		Set<Object> annotationForOldMultiLine = null; 
		if (annotatedShape != null)
			annotationForOldMultiLine = annotatedShape.getAnnotationForShapes(this);
		if (annotationForOldMultiLine != null) {
			annotatedShape.putAllAnnotation(replacemetMultiLine, annotationForOldMultiLine); 
		}
		return replacemetMultiLine;
	}

	@Override
	public IPoint2D getCenter() {
		return _bBox.getCenter();
	}

	@Override
	public double getDiameter() {
		return getBBox().getDiameter();
	}

	public Boolean isClosedLineClockWise() {
		return _closedLineClockWise;
	}

	public void setClosedLineClockWise(Boolean lineClockWise) {
		_closedLineClockWise = lineClockWise;
	}
}
