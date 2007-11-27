package org.shapelogic.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shapelogic.entities.NumericRule;

import static org.shapelogic.util.Constants.SIMPLE_NUMERIC_TASK;
import static org.shapelogic.util.Constants.COUNT_COLLECTION_TASK;
import static org.shapelogic.util.Constants.FILTER_COUNT_TASK;

import static org.shapelogic.util.Constants.NUMERIC_GREATER_TASK;
import static org.shapelogic.util.Constants.COUNT_COLLECTION_GREATER_TASK;
import static org.shapelogic.util.Constants.FILTER_COUNT_GREATER_TASK;

import static org.shapelogic.util.Constants.BOOLEAN_TASK;

import static org.shapelogic.logic.CommonLogicExpressions.*;

/** Should create all 
 * 
 * @author Sami Badawi
 *
 */
public class LetterTaskFactory {

	public static final String POLYGON = "polygon";

	//Old attempt
	public static final String HORIZONTAL_LINE_COUNT_EX1 = "CLine.filterHorizontal(polygon.getLines()).size()";
	public static final String VERTICAL_LINE_COUNT_EX1 = "CLine.filterVertical(polygon.getLines()).size()";

	static public BaseTask createLetterATask(BaseTask parentTask) {
		AndTask letterATask = new AndTask(parentTask,true);
		String taskName = "A";
		String polygon = "polygon";
		String polygonDot = "polygon.";
		letterATask.setName(taskName);
		SimpleNumericTask pointCountTask = new SimpleNumericTask(letterATask, false,"polygon.getPoints().size()",5);
		pointCountTask.setName(POINT_COUNT);
		SimpleNumericTask lineCountTask = new SimpleNumericTask(letterATask, false,"polygon.getLines().size()",5);
		lineCountTask.setName(LINE_COUNT);
		SimpleNumericTask horizontalLineCountTask = new SimpleNumericTask(letterATask, false,polygonDot+HORIZONTAL_LINE_COUNT_EX,1);
		horizontalLineCountTask.setName(HORIZONTAL_LINE_COUNT);
		SimpleNumericTask verticalLineCountTask = new SimpleNumericTask(letterATask, false,polygonDot+VERTICAL_LINE_COUNT_EX,0);
		verticalLineCountTask.setName(VERTICAL_LINE_COUNT);
		SimpleNumericTask tJunctionPointCountTask = new FilterCountTask(letterATask, false,polygon,T_JUNCTION_POINT_COUNT_EX,2);
		tJunctionPointCountTask.setName(T_JUNCTION_POINT_COUNT);
		SimpleNumericTask tJunctionLeftPointCountTask = new FilterCountTask(letterATask, false,polygon,T_JUNCTION_LEFT_POINT_COUNT_EX,1);
		tJunctionLeftPointCountTask.setName(T_JUNCTION_LEFT_POINT_COUNT);
		SimpleNumericTask tJunctionRightPointCountTask = new FilterCountTask(letterATask, false,polygon,T_JUNCTION_RIGHT_POINT_COUNT_EX,1);
		tJunctionRightPointCountTask.setName(T_JUNCTION_RIGHT_POINT_COUNT);
		parentTask.setNamedTask(taskName,letterATask);
		return letterATask;
	}
	
	/** Generate all rule that are needed for matching all the straight capital letter 
	 * This is used in the DirectionBasedVectorizer
	 * 
	 * @param polygon is a String that contain the context variable that the rules will be applied to
	 */
	static public NumericRule[] getSimpleNumericRuleForAllStraightLetters(String polygon) {
		NumericRule[] numericRulesForAll = {
			new NumericRule("A", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,COUNT_COLLECTION_TASK),
			new NumericRule("A", LINE_COUNT, polygon, LINE_COUNT_EX, 5.,SIMPLE_NUMERIC_TASK),
			new NumericRule("A", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("A", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("A", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),

			new NumericRule("E", POINT_COUNT, polygon, POINT_COUNT_EX, 6.,COUNT_COLLECTION_TASK),
			new NumericRule("E", LINE_COUNT, polygon, LINE_COUNT_EX, 5.,SIMPLE_NUMERIC_TASK),
			new NumericRule("E", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("E", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("E", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),

			new NumericRule("F", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,COUNT_COLLECTION_TASK),
			new NumericRule("F", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("F", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("F", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("F", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),

			new NumericRule("H", POINT_COUNT, polygon, POINT_COUNT_EX, 6.,COUNT_COLLECTION_TASK),
			new NumericRule("H", LINE_COUNT, polygon, LINE_COUNT_EX, 5.,SIMPLE_NUMERIC_TASK),
			new NumericRule("H", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("H", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("H", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),

			new NumericRule("I", POINT_COUNT, polygon, POINT_COUNT_EX, 2.,COUNT_COLLECTION_TASK),
			new NumericRule("I", LINE_COUNT, polygon, LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("I", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("I", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("I", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),

			new NumericRule("K", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,COUNT_COLLECTION_TASK),
			new NumericRule("K", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("K", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("K", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("K", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),

			new NumericRule("L", POINT_COUNT, polygon, POINT_COUNT_EX, 3.,COUNT_COLLECTION_TASK),
			new NumericRule("L", LINE_COUNT, polygon, LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("L", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("L", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("L", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
//			new NumericRule("L", HARD_POINT_COUNT, polygon, HARD_POINT_COUNT_EX, 1.,"SimpleNumericTask"),

			new NumericRule("M", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,COUNT_COLLECTION_TASK),
			new NumericRule("M", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("M", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("M", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("M", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),

			new NumericRule("N", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,COUNT_COLLECTION_TASK),
			new NumericRule("N", LINE_COUNT, polygon, LINE_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("N", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("N", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("N", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),

			new NumericRule("T", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,COUNT_COLLECTION_TASK),
			new NumericRule("T", LINE_COUNT, polygon, LINE_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("T", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("T", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("T", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),

			new NumericRule("V", POINT_COUNT, polygon, POINT_COUNT_EX, 3.,COUNT_COLLECTION_TASK),
			new NumericRule("V", LINE_COUNT, polygon, LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("V", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("V", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("V", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),

			new NumericRule("W", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,COUNT_COLLECTION_TASK),
			new NumericRule("W", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("W", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("W", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("W", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),

			//Same as W
			new NumericRule("X", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,COUNT_COLLECTION_TASK),
			new NumericRule("X", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("X", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("X", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("X", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			
			new NumericRule("Y", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,COUNT_COLLECTION_TASK),
			new NumericRule("Y", LINE_COUNT, polygon, LINE_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Y", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Y", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Y", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),

			new NumericRule("Z", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,COUNT_COLLECTION_TASK),
			new NumericRule("Z", LINE_COUNT, polygon, LINE_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Z", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Z", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Z", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
		};
		return numericRulesForAll;
	}
	
	/** Generate all rule that are needed for matching all the capital letter 
	 * using new complex filter rules
	 * This is used in the MaxDistanceVectorizer
	 * 
	 * @param polygon is a String that contain the context variable that the rules will be applied to
	 */
	static public NumericRule[] getSimpleNumericRuleForAllLetters(String polygon) {
		NumericRule[] numericRulesForAll = {
			new NumericRule("A", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,COUNT_COLLECTION_TASK),
			new NumericRule("A", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1., SIMPLE_NUMERIC_TASK),
			new NumericRule("A", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("A", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("A", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			new NumericRule("A", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("A", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("A", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("A", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("B", HOLE_COUNT, polygon, HOLE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("B", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("B", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("B", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
//			new NumericRule("B", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("B", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,NUMERIC_GREATER_TASK),
			new NumericRule("B", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("B", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),

			new NumericRule("C", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("C", T_JUNCTION_POINT_COUNT, polygon, T_JUNCTION_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("C", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("C", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("C", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("C", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("C", INFLECTION_POINT_COUNT, polygon, INFLECTION_POINT_COUNT_EX, 0.,COUNT_COLLECTION_TASK),
			new NumericRule("C", CURVE_ARCH_COUNT, polygon, CURVE_ARCH_COUNT_EX, 1.,NUMERIC_GREATER_TASK),
			new NumericRule("C", HARD_POINT_COUNT, polygon, HARD_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("C", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),

			new NumericRule("D", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("D", T_JUNCTION_POINT_COUNT, polygon, T_JUNCTION_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("D", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
//			new NumericRule("D", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("D", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("D", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("D", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("D", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("D", HARD_POINT_COUNT, polygon, HARD_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),

			new NumericRule("E", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("E", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("E", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("E", END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT, polygon, END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("E", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,NUMERIC_GREATER_TASK),
			new NumericRule("E", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,NUMERIC_GREATER_TASK),
			new NumericRule("E", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("E", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("E", POINT_COUNT, polygon, POINT_COUNT_EX2, 5.,NUMERIC_GREATER_TASK),

			new NumericRule("F", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("F", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("F", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("F", END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT, polygon, END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("F", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("F", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("F", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("F", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("G", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("G", T_JUNCTION_POINT_COUNT, polygon, T_JUNCTION_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("G", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("G", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("G", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("G", INFLECTION_POINT_COUNT, polygon, INFLECTION_POINT_COUNT_EX, 0.,COUNT_COLLECTION_TASK),
			new NumericRule("G", CURVE_ARCH_COUNT, polygon, CURVE_ARCH_COUNT_EX, 2.,NUMERIC_GREATER_TASK),
			new NumericRule("G", HARD_POINT_COUNT, polygon, HARD_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("G", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("G", ASPECT_RATIO, polygon, ASPECT_RATIO_EX, 0.5, NUMERIC_GREATER_TASK),

			new NumericRule("H", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("H", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("H", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("H", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			new NumericRule("H", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("H", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("H", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("H", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("I", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("I", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("I", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("I", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("I", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("I", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("I", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("I", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 0.,COUNT_COLLECTION_TASK),
			new NumericRule("I", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("J", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("J", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("J", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("J", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
//			new NumericRule("J", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("J", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("J", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("J", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
//			new NumericRule("J", CURVE_ARCH_COUNT, polygon, CURVE_ARCH_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("J", HARD_POINT_COUNT, polygon, HARD_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("J", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),

			new NumericRule("K", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
//			new NumericRule("K", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 2.,"FilterCountTask"),
//			new NumericRule("K", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("K", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			new NumericRule("K", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("K", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("K", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("K", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("L", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("L", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("L", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("L", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("L", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("L", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("L", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("L", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("L", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("M", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("M", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("M", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("M", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			new NumericRule("M", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("M", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("M", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("M", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("M", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("N", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("N", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("N", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("N", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("N", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("N", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("N", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
//			new NumericRule("N", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("N", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("O", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("O", T_JUNCTION_POINT_COUNT, polygon, T_JUNCTION_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
//			new NumericRule("O", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
//			new NumericRule("O", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("O", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("O", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("O", CURVE_ARCH_COUNT, polygon, CURVE_ARCH_COUNT_EX, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("O", HARD_POINT_COUNT, polygon, HARD_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("O", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),

			new NumericRule("P", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("P", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("P", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("P", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
//			new NumericRule("P", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("P", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("P", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("P", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),

			new NumericRule("Q", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
//			new NumericRule("Q", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_GREATER_TASK),
//			new NumericRule("Q", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("Q", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Q", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_GREATER_TASK),
			new NumericRule("Q", CURVE_ARCH_COUNT, polygon, CURVE_ARCH_COUNT_EX, 0.,NUMERIC_GREATER_TASK),
//			new NumericRule("Q", HARD_POINT_COUNT, polygon, HARD_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("Q", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("Q", END_POINT_BOTTOM_RIGHT_HALF_POINT_COUNT, polygon, END_POINT_BOTTOM_RIGHT_HALF_POINT_COUNT_EX, 0., FILTER_COUNT_GREATER_TASK),
			
			new NumericRule("R", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("R", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_GREATER_TASK),
			new NumericRule("R", U_JUNCTION_POINT_COUNT, polygon, U_JUNCTION_POINT_COUNT_EX2, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("R", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			//This is because the way that the top point is found as the first scan line point to top line will not always be vertical
			new NumericRule("R", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("R", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("R", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),

			new NumericRule("S", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("S", T_JUNCTION_POINT_COUNT, polygon, T_JUNCTION_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("S", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("S", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("S", END_POINT_TOP_RIGHT_HALF_POINT_COUNT, polygon, END_POINT_TOP_RIGHT_HALF_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("S", END_POINT_BOTTOM_LEFT_HALF_POINT_COUNT, polygon, END_POINT_BOTTOM_LEFT_HALF_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("S", INFLECTION_POINT_COUNT, polygon, INFLECTION_POINT_COUNT_EX, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("S", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),
			new NumericRule("S", ASPECT_RATIO, polygon, ASPECT_RATIO_EX, 0.5, NUMERIC_GREATER_TASK),
			
			new NumericRule("T", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("T", T_JUNCTION_POINT_COUNT, polygon, T_JUNCTION_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("T", END_POINT_TOP_POINT_COUNT, polygon, END_POINT_TOP_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			new NumericRule("T", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("T", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("T", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("T", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("T", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("U", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("U", T_JUNCTION_POINT_COUNT, polygon, T_JUNCTION_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("U", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("U", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("U", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("U", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("U", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("U", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,NUMERIC_GREATER_TASK),

			new NumericRule("V", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("V", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("V", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("V", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("V", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("V", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("V", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("V", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("V", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("V", INFLECTION_POINT_COUNT, polygon, INFLECTION_POINT_COUNT_EX, 0.,COUNT_COLLECTION_TASK),

			new NumericRule("W", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("W", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("W", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("W", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("W", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("W", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("W", MULTI_LINE_COUNT, polygon, MULTI_LINE_COUNT_EX, 1.,COUNT_COLLECTION_TASK),
			new NumericRule("W", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("W", INFLECTION_POINT_COUNT, polygon, INFLECTION_POINT_COUNT_EX, 1.,COUNT_COLLECTION_GREATER_TASK),

			new NumericRule("X", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("X", END_POINT_TOP_POINT_COUNT, polygon, END_POINT_TOP_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			new NumericRule("X", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			new NumericRule("X", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("X", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("X", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,SIMPLE_NUMERIC_TASK),
			new NumericRule("X", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),
			
			new NumericRule("Y", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Y", Y_JUNCTION_POINT_COUNT, polygon, Y_JUNCTION_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("Y", END_POINT_TOP_POINT_COUNT, polygon, END_POINT_TOP_POINT_COUNT_EX, 2.,FILTER_COUNT_TASK),
			new NumericRule("Y", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("Y", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Y", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Y", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Y", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),

			new NumericRule("Z", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Z", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("Z", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,FILTER_COUNT_TASK),
			new NumericRule("Z", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("Z", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Z", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,SIMPLE_NUMERIC_TASK),
			new NumericRule("Z", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,SIMPLE_NUMERIC_TASK),
//			new NumericRule("Z", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, polygon, END_POINT_TOP_LEFT_THIRD_POINT_COUNT_EX, 1.,FILTER_COUNT_TASK),
			new NumericRule("Z", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,COUNT_COLLECTION_TASK),
			new NumericRule("Z", SOFT_POINT_COUNT, polygon, SOFT_POINT_COUNT_EX2, 0.,SIMPLE_NUMERIC_TASK),
		};
		return numericRulesForAll;
	}
	
	static public BaseTask createLetterATaskFromRule(BaseTask parentTask) {
		AndTask letterATask = new AndTask(parentTask,true);
		String ohName = "A";
		letterATask.setName(ohName);
		NumericRule[] numericRulesForA = getSimpleNumericRuleForAllStraightLetters(POLYGON);
		for (NumericRule rule: numericRulesForA) {
			if (!ohName.equalsIgnoreCase(rule.getParentOH()))
				continue;
			BaseTask task = rule.makeTask(letterATask);
			task.setName(rule.getName());
		}
		return letterATask;
	}
	
	/** Create a letter task from all NumericRules of the underlying letters 
	 * 
	 * @param parentTask To insert this task into, will often be the RootTask
	 * @param allNumericRules all the Numeric Rules that are used
	 * @param letterSelector if you only want to match against on letter for debugging problems
	 * @return
	 */
	static public BaseTask createLetterTasksFromRule(BaseTask parentTask, 
			List<NumericRule> allNumericRules, String letterSelector) {
		XOrTask letterTask = new XOrTask(parentTask,false); 
		String topLetterTaskName = "Letter";
		letterTask.setName(topLetterTaskName);
		parentTask.setNamedTask(letterTask.getName(), letterTask);

		Map<String, AndTask> nameToTaskMap = new HashMap<String, AndTask>(); 
		for (NumericRule rule: allNumericRules) {
			String ohName = rule.getParentOH();
			if (letterSelector != null && !letterSelector.equalsIgnoreCase(ohName))
				continue;
			AndTask currentAndTask = nameToTaskMap.get(ohName);
			if (currentAndTask == null) {
				currentAndTask = new AndTask(letterTask,true);
				currentAndTask.setName(ohName);
				nameToTaskMap.put(ohName, currentAndTask);
			}
			BaseTask numericTask = rule.makeTask(currentAndTask);
			numericTask.setName(rule.getName());
		}
		for (String letterOh: nameToTaskMap.keySet()) {
			AndTask currentLetterTask = nameToTaskMap.get(letterOh);
			// Also set in parent task so you can run a single letter match independent of letter match 
			parentTask.setNamedTask(currentLetterTask.getName(), currentLetterTask); 
		}
		return letterTask;
	}
	
}
