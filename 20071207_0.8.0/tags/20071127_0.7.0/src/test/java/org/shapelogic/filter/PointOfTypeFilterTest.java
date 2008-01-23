package org.shapelogic.filter;

import java.util.Collection;

import org.shapelogic.entities.NumericRule;
import org.shapelogic.imageprocessing.PointType;
import org.shapelogic.logic.BaseTask;
import org.shapelogic.logic.CommonLogicExpressions;
import org.shapelogic.logic.LogicState;
import org.shapelogic.logic.RootTask;
import org.shapelogic.polygon.AnnotatedShapeImplementation;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.util.Constants;

/** Test filter based on type of points
 * Create a rootTask and set the polygon in this since the filters need to run 
 * in a JEXL context
 * 
 * @author Sami Badawi
 *
 */
public class PointOfTypeFilterTest extends AbstractFilterTests {
	protected RootTask rootTask;
	
	public void setUp() {
		super.setUp();
		Polygon improved = polygon.improve();
		AnnotatedShapeImplementation annotation = polygon.getAnnotatedShape();
		annotation.putAnnotation(north, PointType.T_JUNCTION);
		rootTask = RootTask.getInstance();
		rootTask.setNamedValue(POLYGON, improved);
	}
	
	public void testParsing() {
		PointOfTypeFilter pointOfTypeFilter = new PointOfTypeFilter();
		pointOfTypeFilter.setConstraint(PointType.HARD_CORNER);
		assertEquals(PointType.HARD_CORNER,	pointOfTypeFilter.getConstraint());
		pointOfTypeFilter.setConstraint("SOFT_POINT");
		assertEquals(PointType.SOFT_POINT,	pointOfTypeFilter.getConstraint());
	}

	public void testUsingFilters() {
		Collection tJunction = polygon.filter(CommonLogicExpressions.T_JUNCTION_POINT_COUNT_EX);
		assertEquals(1,tJunction.size());

		String ruleName = "TestMatch";
		NumericRule nr = new NumericRule(ruleName, CommonLogicExpressions.T_JUNCTION_POINT_COUNT, 
				POLYGON, CommonLogicExpressions.T_JUNCTION_POINT_COUNT_EX, 0.,Constants.FILTER_COUNT_GREATER_TASK);
		BaseTask testTask = nr.makeTask(rootTask);
		Object matchResult = testTask.getCalcValue();
		assertEquals(1,matchResult);
		assertEquals(LogicState.SucceededDone, testTask.getState());
	} 
}
