package org.shapelogic.filter;

import org.shapelogic.entities.NumericRule;
import org.shapelogic.logic.BaseTask;
import org.shapelogic.logic.CommonLogicExpressions;
import org.shapelogic.logic.LogicState;
import org.shapelogic.logic.RootTask;
import org.shapelogic.polygon.AnnotatedShapeImplementation;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.util.Constants;
import org.shapelogic.util.PointType;

/** Test filter based direct boolean expressions evaluated in a JEXL context   
 * 
 * @author Sami Badawi
 *
 */
public class BooleanTaskTest extends AbstractFilterTests {
	protected RootTask rootTask;
	
	public void setUp() {
		super.setUp();
		Polygon improved = polygon.improve();
		AnnotatedShapeImplementation annotation = polygon.getAnnotatedShape();
		annotation.putAnnotation(north, PointType.T_JUNCTION);
		rootTask = RootTask.getInstance();
		rootTask.setNamedValue(POLYGON, improved);
	}
	
	public void testBooleanExpression() {
		String ruleName = "TestMatch";
		NumericRule nr = new NumericRule(ruleName, CommonLogicExpressions.POINT_COUNT, 
				null, POLYGON+"."+CommonLogicExpressions.POINT_COUNT_EX + ".size()==5", 0.,Constants.BOOLEAN_TASK);
		BaseTask testTask = nr.makeTask(rootTask);
		Object matchResult = testTask.getValue();
		assertEquals(true,matchResult);
		assertEquals(LogicState.SucceededDone, testTask.getState());
	} 

	/** The other version of size is not a method, but a function 
	 * It is more robust in case the expression returns a null
	 */
	public void testBooleanExpressionUsingOtherVersionOfSize() {
		String ruleName = "TestMatch";
		NumericRule nr = new NumericRule(ruleName, CommonLogicExpressions.POINT_COUNT, 
				null, "size("+POLYGON+"."+CommonLogicExpressions.POINT_COUNT_EX + ")==5", 0.,Constants.BOOLEAN_TASK);
		BaseTask testTask = nr.makeTask(rootTask);
		Object matchResult = testTask.getValue();
		assertEquals(true,matchResult);
		assertEquals(LogicState.SucceededDone, testTask.getState());
	} 
}
