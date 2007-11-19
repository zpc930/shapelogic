package org.shapelogic.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shapelogic.entities.NumericRule;

/** Should create all 
 * 
 * @author Sami Badawi
 *
 */
public class LetterTaskFactory {

	public static final String POINT_COUNT = "pointCount";
	public static final String LINE_COUNT = "lineCount";
	public static final String HORIZONTAL_LINE_COUNT = "horizontalLineCount";
	public static final String VERTICAL_LINE_COUNT = "verticalLineCount";
	public static final String END_POINT_COUNT = "endPointCount";
	public static final String HARD_POINT_COUNT = "hardPointCount";
	public static final String SOFT_POINT_COUNT = "softPointCount";
	public static final String T_JUNCTION_POINT_COUNT = "tJunctionCount";
	public static final String T_JUNCTION_LEFT_POINT_COUNT = "tJunctionLeftCount";
	public static final String T_JUNCTION_RIGHT_POINT_COUNT = "tJunctionRightCount";
	public static final String HOLE_COUNT = "holeCount";
	public static final String END_POINT_BOTTOM_POINT_COUNT = "endpointBottomPointCount"; 
	public static final String END_POINT_TOP_POINT_COUNT =  "endpointTopPointCount";
	public static final String Y_JUNCTION_POINT_COUNT = "yJunctionCount";
	
	
	public static final String POINT_COUNT_EX = "getPoints().size()";
	public static final String LINE_COUNT_EX = "getLines().size()";
	public static final String HORIZONTAL_LINE_COUNT_EX = "getHorizontalLines().size()";
	public static final String VERTICAL_LINE_COUNT_EX = "getVerticalLines().size()";
	public static final String END_POINT_COUNT_EX = "getEndPointCount()";
	public static final String HARD_POINT_COUNT_EX = "filter('PointOfTypeFilter(PointType.HARD_CORNER)').size()";
	public static final String SOFT_POINT_COUNT_EX = "filter('PointOfTypeFilter(PointType.SOFT_POINT)').size()";
	public static final String T_JUNCTION_POINT_COUNT_EX = "filter('PointOfTypeFilter(PointType.T_JUNCTION)').size()";
	public static final String T_JUNCTION_LEFT_POINT_COUNT_EX = 
		"filter('PointOfTypeFilter(PointType.T_JUNCTION) && PointLeftOfFilter(0.5)').size()";
	public static final String T_JUNCTION_RIGHT_POINT_COUNT_EX = 
		"filter('PointOfTypeFilter(PointType.T_JUNCTION) && PointRightOfFilter(0.5)').size()";
	public static final String HOLE_COUNT_EX = "holeCount";
	public static final String END_POINT_BOTTOM_POINT_COUNT_EX = 
		"filter('PointOfTypeFilter(PointType.END_POINT) && PointBelowFilter(0.5)').size()";
	public static final String END_POINT_TOP_POINT_COUNT_EX = 
		"filter('PointOfTypeFilter(PointType.END_POINT) && PointAboveFilter(0.5)').size()";
	public static final String Y_JUNCTION_POINT_COUNT_EX = "filter('PointOfTypeFilter(PointType.Y_JUNCTION)').size()";

	public static final String POLYGON = "polygon";

	//Old attempt
	public static final String HORIZONTAL_LINE_COUNT_EX1 = "CLine.filterHorizontal(polygon.getLines()).size()";
	public static final String VERTICAL_LINE_COUNT_EX1 = "CLine.filterVertical(polygon.getLines()).size()";

	static public BaseTask createLetterATask(BaseTask parentTask) {
		AndTask letterATask = new AndTask(parentTask,true);
		String taskName = "A";
		String polygon = "polygon.";
		letterATask.setName(taskName);
		SimpleNumericTask pointCountTask = new SimpleNumericTask(letterATask, false,"polygon.getPoints().size()",5);
		pointCountTask.setName(POINT_COUNT);
		SimpleNumericTask lineCountTask = new SimpleNumericTask(letterATask, false,"polygon.getLines().size()",5);
		lineCountTask.setName(LINE_COUNT);
		SimpleNumericTask horizontalLineCountTask = new SimpleNumericTask(letterATask, false,polygon+HORIZONTAL_LINE_COUNT_EX,1);
		horizontalLineCountTask.setName(HORIZONTAL_LINE_COUNT);
		SimpleNumericTask verticalLineCountTask = new SimpleNumericTask(letterATask, false,polygon+VERTICAL_LINE_COUNT_EX,0);
		verticalLineCountTask.setName(VERTICAL_LINE_COUNT);
		SimpleNumericTask tJunctionPointCountTask = new SimpleNumericTask(letterATask, false,polygon+T_JUNCTION_POINT_COUNT_EX,2);
		tJunctionPointCountTask.setName(T_JUNCTION_POINT_COUNT);
		SimpleNumericTask tJunctionLeftPointCountTask = new SimpleNumericTask(letterATask, false,polygon+T_JUNCTION_LEFT_POINT_COUNT_EX,1);
		tJunctionLeftPointCountTask.setName(T_JUNCTION_LEFT_POINT_COUNT);
		SimpleNumericTask tJunctionRightPointCountTask = new SimpleNumericTask(letterATask, false,polygon+T_JUNCTION_RIGHT_POINT_COUNT_EX,1);
		tJunctionRightPointCountTask.setName(T_JUNCTION_RIGHT_POINT_COUNT);
		parentTask.setNamedTask(taskName,letterATask);
		return letterATask;
	}
	
	static public NumericRule[] getSimpleNumericRuleForAllStraightLetters(String polygon) {
		NumericRule[] numericRulesForAll = {
			new NumericRule("A", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("A", LINE_COUNT, polygon, LINE_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("A", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("A", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("A", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("E", POINT_COUNT, polygon, POINT_COUNT_EX, 6.,"SimpleNumericTask"),
			new NumericRule("E", LINE_COUNT, polygon, LINE_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("E", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 3.,"SimpleNumericTask"),
			new NumericRule("E", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("E", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,"SimpleNumericTask"),

			new NumericRule("F", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("F", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("F", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("F", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("F", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,"SimpleNumericTask"),

			new NumericRule("H", POINT_COUNT, polygon, POINT_COUNT_EX, 6.,"SimpleNumericTask"),
			new NumericRule("H", LINE_COUNT, polygon, LINE_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("H", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("H", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("H", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,"SimpleNumericTask"),

			new NumericRule("I", POINT_COUNT, polygon, POINT_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("I", LINE_COUNT, polygon, LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("I", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("I", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("I", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("K", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("K", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("K", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("K", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("K", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,"SimpleNumericTask"),

			new NumericRule("L", POINT_COUNT, polygon, POINT_COUNT_EX, 3.,"SimpleNumericTask"),
			new NumericRule("L", LINE_COUNT, polygon, LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("L", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("L", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("L", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),
//			new NumericRule("L", HARD_POINT_COUNT, polygon, HARD_POINT_COUNT_EX, 1.,"SimpleNumericTask"),

			new NumericRule("M", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("M", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("M", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("M", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("M", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("N", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("N", LINE_COUNT, polygon, LINE_COUNT_EX, 3.,"SimpleNumericTask"),
			new NumericRule("N", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("N", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("N", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("T", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("T", LINE_COUNT, polygon, LINE_COUNT_EX, 3.,"SimpleNumericTask"),
			new NumericRule("T", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("T", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("T", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,"SimpleNumericTask"),

			new NumericRule("V", POINT_COUNT, polygon, POINT_COUNT_EX, 3.,"SimpleNumericTask"),
			new NumericRule("V", LINE_COUNT, polygon, LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("V", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("V", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("V", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("W", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("W", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("W", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("W", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("W", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			//Same as W
			new NumericRule("X", POINT_COUNT, polygon, POINT_COUNT_EX, 5.,"SimpleNumericTask"),
			new NumericRule("X", LINE_COUNT, polygon, LINE_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("X", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("X", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("X", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,"SimpleNumericTask"),
			
			new NumericRule("Y", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("Y", LINE_COUNT, polygon, LINE_COUNT_EX, 3.,"SimpleNumericTask"),
			new NumericRule("Y", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("Y", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("Y", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,"SimpleNumericTask"),

			new NumericRule("Z", POINT_COUNT, polygon, POINT_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("Z", LINE_COUNT, polygon, LINE_COUNT_EX, 3.,"SimpleNumericTask"),
			new NumericRule("Z", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("Z", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("Z", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),
		};
		return numericRulesForAll;
	}
	
	/** Start by changing all the rule for straight capital letters to the new complex rules
	 * 
	 */
	static public NumericRule[] getSimpleNumericRuleForAllLetters(String polygon) {
		NumericRule[] numericRulesForAll = {
			new NumericRule("A", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("A", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("A", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("A", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,"FilterCountTask"),
			new NumericRule("A", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("A", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("A", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("B", HOLE_COUNT, polygon, HOLE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("B", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("B", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("B", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 0.,"FilterCountTask"),
//			new NumericRule("B", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("B", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("B", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 0.,"SimpleNumericTask"),

			new NumericRule("E", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("E", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("E", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("E", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("E", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 3.,"SimpleNumericTask"),
			new NumericRule("E", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("E", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,"SimpleNumericTask"),

			new NumericRule("F", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("F", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("F", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("F", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("F", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("F", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("F", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,"SimpleNumericTask"),

			new NumericRule("H", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("H", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("H", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("H", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,"FilterCountTask"),
			new NumericRule("H", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("H", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 4.,"SimpleNumericTask"),
			new NumericRule("H", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,"SimpleNumericTask"),

			new NumericRule("I", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("I", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("I", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("I", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("I", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("I", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("I", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("K", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
//			new NumericRule("K", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 2.,"FilterCountTask"),
//			new NumericRule("K", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("K", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,"FilterCountTask"),
			new NumericRule("K", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("K", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("K", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,"SimpleNumericTask"),

			new NumericRule("L", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("L", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("L", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("L", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("L", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("L", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("L", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("M", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("M", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("M", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("M", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,"FilterCountTask"),
			new NumericRule("M", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("M", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("M", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("N", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("N", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("N", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("N", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("N", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("N", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("N", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("P", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("P", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("P", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("P", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
//			new NumericRule("P", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("P", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("P", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 1.,"SimpleNumericTask"),

			new NumericRule("R", HOLE_COUNT, polygon, HOLE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("R", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("R", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("R", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,"FilterCountTask"),
//			new NumericRule("R", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
//			new NumericRule("R", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("R", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("T", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("T", T_JUNCTION_POINT_COUNT, polygon, T_JUNCTION_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("T", END_POINT_TOP_POINT_COUNT, polygon, END_POINT_TOP_POINT_COUNT_EX, 2.,"FilterCountTask"),
			new NumericRule("T", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("T", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("T", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("T", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,"SimpleNumericTask"),

			new NumericRule("V", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("V", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("V", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("V", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("V", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("V", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("V", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("W", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("W", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("W", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("W", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("W", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("W", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("W", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),

			new NumericRule("X", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("X", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("X", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("X", END_POINT_TOP_POINT_COUNT, polygon, END_POINT_TOP_POINT_COUNT_EX, 2.,"FilterCountTask"),
			new NumericRule("X", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("X", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("X", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("X", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 4.,"SimpleNumericTask"),
			
			new NumericRule("Y", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
//			new NumericRule("Y", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 1.,"FilterCountTask"),
//			new NumericRule("Y", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("Y", Y_JUNCTION_POINT_COUNT, polygon, Y_JUNCTION_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("Y", END_POINT_TOP_POINT_COUNT, polygon, END_POINT_TOP_POINT_COUNT_EX, 2.,"FilterCountTask"),
			new NumericRule("Y", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("Y", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("Y", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 1.,"SimpleNumericTask"),
			new NumericRule("Y", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 3.,"SimpleNumericTask"),

			new NumericRule("Z", HOLE_COUNT, polygon, HOLE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("Z", T_JUNCTION_LEFT_POINT_COUNT, polygon, T_JUNCTION_LEFT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("Z", T_JUNCTION_RIGHT_POINT_COUNT, polygon, T_JUNCTION_RIGHT_POINT_COUNT_EX, 0.,"FilterCountTask"),
			new NumericRule("Z", END_POINT_BOTTOM_POINT_COUNT, polygon, END_POINT_BOTTOM_POINT_COUNT_EX, 1.,"FilterCountTask"),
			new NumericRule("Z", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 2.,"SimpleNumericTask"),
			new NumericRule("Z", VERTICAL_LINE_COUNT, polygon, VERTICAL_LINE_COUNT_EX, 0.,"SimpleNumericTask"),
			new NumericRule("Z", END_POINT_COUNT, polygon, END_POINT_COUNT_EX, 2.,"SimpleNumericTask"),
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
			SimpleNumericTask task = 
				new SimpleNumericTask(letterATask, false, rule.getVariableAndExpression(),rule.getExpected());
			task.setName(rule.getName());
		}
		return letterATask;
	}
	
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
			SimpleNumericTask numericTask = 
				new SimpleNumericTask(currentAndTask, false, rule.getVariableAndExpression(),rule.getExpected());
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
