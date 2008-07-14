package org.shapelogic.polygon;

import java.util.Map;
import java.util.Set;

import org.shapelogic.util.PointType;

/** Not this functionality is in ChainCodeHandler
 * 
 *  But this is interesting on its own, 
 *  now there are no annotation going on, I need to move this in too 
 * 
 * @author Sami Badawi
 *
 */
public class PolygonAnnotator implements ObjectAnnotator<Polygon> {
	
	private Polygon _polygon;
	private boolean _dirty = true;
	private boolean _annotationsFound = false;

	public PolygonAnnotator() {
		this(null);
	}

	public PolygonAnnotator(Polygon calcValue) {
		_polygon = calcValue;
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
		findJunctions();
		_dirty = false;
		return _polygon;
	}


	@Override
	public Polygon getCalcValue() {
		if (isDirty())
			calc();
		return _polygon;
	}

	public void findJunctions() {
		Map<IPoint2D,Integer> pointsCountMap = _polygon.getPointsCountMap();
		AnnotatedShape annotation = _polygon.getAnnotatedShape();
		Integer ONE = 1;
		int endPointCount = 0;
		int junctionCount = 0;
		for (IPoint2D point: pointsCountMap.keySet()) {
			if (ONE.equals(pointsCountMap.get(point) )) {
				endPointCount++;
				annotation.putAnnotation(point, PointType.END_POINT );
			}
			else if (2 < pointsCountMap.get(point)) {
				junctionCount++;
				annotation.putAnnotation(point, PointType.U_JUNCTION );
				//Find specific junction type
				if (3 == pointsCountMap.get(point)) {
					Set<CLine> lines = _polygon.getPointsToLineMap().get(point);
					CLine[] lineArray = new CLine[3];  
					lineArray = lines.toArray(lineArray);
					double sumOfDifferenceInAngle = 0;
					double maxOfDifferenceInAngle = 0;
					PointType junctionType = PointType.UNKNOWN;
					for (int i=0;i<3;i++) {
						for (int j=0; j<3; j++) {
							if (i==j)
								continue;
							CLine line1 = lineArray[i].lineStartingAtPoint(point);
							CLine line2 = lineArray[j].lineStartingAtPoint(point);
							double angle1 = line1.angle();
							double angle2 = line2.angle();
							double differenceInAngle = Math.abs(Calculator2D.angleBetweenLines(angle1, angle2));
							sumOfDifferenceInAngle += differenceInAngle;
							if (maxOfDifferenceInAngle < differenceInAngle)
								maxOfDifferenceInAngle = differenceInAngle;
						}
					}
					if (Math.PI * 0.8 < maxOfDifferenceInAngle)
						junctionType = PointType.T_JUNCTION;
					else if (sumOfDifferenceInAngle < Math.PI * 1.5)
						junctionType = PointType.ARROW_JUNCTION;
					else 
						junctionType = PointType.Y_JUNCTION;
					if (junctionType != PointType.UNKNOWN)
						annotation.putAnnotation(point, junctionType );
				}
			}
		}
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
