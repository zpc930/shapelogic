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

import static org.shapelogic.logic.CommonLogicExpressions.*;

/** Should create all the rules used for letter matching.
 * 
 * Contains 2 sets of rules for letter matching
 * 
 * 1: A simple set that only works for straight letters using simple properties
 * 2: A more complex set of rules works for straight and curved letters 
 * This uses annotations 
 * 
 * @author Sami Badawi
 *
 */
public class LetterTaskFactory {

	public static final String POLYGON = "polygon";

	/** Transform base expression to expression with size applied. 
	 * <p/>
	 * E.g: getPoints() -> size(#.getPoints())
	 */
	public static String size(String expression) {
		return VAR_SIZE_START + expression + VAR_SIZE_END;
	}
	
	/** Transform base expression to expression filter with size applied. 
	 * <p/>
	 * E.g: PointLeftOfFilter(0.5) -> #.filter('PointLeftOfFilter(0.5)').size()
	 */
	public static String filter(String expression) {
		return FILTER_START + expression + FILTER_END;
	}
	
	/** Generate all rule that are needed for matching all the capital letter 
	 * using new complex filter rules.
	 * <p/>
	 * This is used in the MaxDistanceVectorizer
	 * <p/>
	 * This uses the new uniform syntax that is creating ParametricRuleTask for all task.
	 * 
	 * @param polygon is a String that contain the context variable that the rules will be applied to
	 */
	static public NumericRule[] getSimpleNumericRuleForAllLetters(String polygon) {
		NumericRule[] numericRulesForAll = {
			new NumericRule("A", POINT_COUNT, polygon, VAR_SIZE_START + POINT_COUNT_EX + VAR_SIZE_END,"==", 5.),
			new NumericRule("A", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX,"==", 1.),
			new NumericRule("A", T_JUNCTION_LEFT_POINT_COUNT, polygon, FILTER_START +  T_JUNCTION_LEFT_POINT_COUNT_EX + FILTER_END,"==", 1.),
			new NumericRule("A", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX),"==", 1.),
			new NumericRule("A", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 2.),
			new NumericRule("A", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("A", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("A", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("A", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("B", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX,"==", 2.),
			new NumericRule("B", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX),">", 0.),
			new NumericRule("B", U_JUNCTION_POINT_COUNT, polygon, VAR + U_JUNCTION_POINT_COUNT_EX2, "==", 2.),
			new NumericRule("B", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 0.),
//			new NumericRule("B", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("B", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, ">",1.),
			new NumericRule("B", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 0.),
			new NumericRule("B", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),

			new NumericRule("C", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX,"==", 0.),
			new NumericRule("C", T_JUNCTION_POINT_COUNT, polygon, filter(T_JUNCTION_POINT_COUNT_EX), "==",0.),
			new NumericRule("C", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
			new NumericRule("C", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("C", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("C", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("C", INFLECTION_POINT_COUNT, polygon, size(INFLECTION_POINT_COUNT_EX), "==", 0.),
			new NumericRule("C", CURVE_ARCH_COUNT, polygon, VAR + CURVE_ARCH_COUNT_EX, ">",1.),
			new NumericRule("C", HARD_POINT_COUNT, polygon, filter(HARD_POINT_COUNT_EX), "==", 0.),
			new NumericRule("C", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),

			new NumericRule("D", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 1.),
			new NumericRule("D", T_JUNCTION_POINT_COUNT, polygon, filter(T_JUNCTION_POINT_COUNT_EX), "==", 0.),
			new NumericRule("D", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 0.),
//			new NumericRule("D", HORIZONTAL_LINE_COUNT, polygon, HORIZONTAL_LINE_COUNT_EX, 1.,SIMPLE_NUMERIC_TASK),
			new NumericRule("D", VERTICAL_LINE_COUNT, polygon, size(VERTICAL_LINE_COUNT_EX), ">", 0.),
			new NumericRule("D", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 0.),
			new NumericRule("D", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("D", SOFT_POINT_COUNT, polygon, size(SOFT_POINT_COUNT_EX2), ">", 0.),
			new NumericRule("D", HARD_POINT_COUNT, polygon, size(HARD_POINT_COUNT_EX2), ">", 0.),

			new NumericRule("E", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX,"==", 0.),
			new NumericRule("E", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 1.),
			new NumericRule("E", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("E", END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT_EX), "==", 1.),
			new NumericRule("E", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, ">", 1.),
			new NumericRule("E", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, ">", 1.),
			new NumericRule("E", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 3.),
			new NumericRule("E", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),
			new NumericRule("E", POINT_COUNT, polygon, VAR + POINT_COUNT_EX2, ">", 5.),

			new NumericRule("F", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX,"==", 0.),
			new NumericRule("F", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 1.),
			new NumericRule("F", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("F", END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT_EX), "==", 1.),
			new NumericRule("F", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("F", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("F", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 3.),
			new NumericRule("F", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("G", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("G", T_JUNCTION_POINT_COUNT, polygon, filter(T_JUNCTION_POINT_COUNT_EX), "==", 0.),
			new NumericRule("G", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
			new NumericRule("G", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("G", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("G", INFLECTION_POINT_COUNT, polygon, size(INFLECTION_POINT_COUNT_EX), "==", 0.),
			new NumericRule("G", CURVE_ARCH_COUNT, polygon, VAR + CURVE_ARCH_COUNT_EX, ">", 2.),
			new NumericRule("G", HARD_POINT_COUNT, polygon, VAR + HARD_POINT_COUNT_EX2, ">", 0.),
			new NumericRule("G", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),
			new NumericRule("G", ASPECT_RATIO, polygon, VAR + ASPECT_RATIO_EX, ">", 0.5),

			new NumericRule("H", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("H", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 1.),
			new NumericRule("H", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 1.),
			new NumericRule("H", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 2.),
			new NumericRule("H", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("H", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 4.),
			new NumericRule("H", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 4.),
			new NumericRule("H", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("I", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("I", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("I", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("I", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
			new NumericRule("I", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("I", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("I", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("I", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 0.),
			new NumericRule("I", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("J", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("J", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("J", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("J", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
//			new NumericRule("J", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("J", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("J", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("J", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
//			new NumericRule("J", CURVE_ARCH_COUNT, polygon, VAR + CURVE_ARCH_COUNT_EX2, "==", 0.),
			new NumericRule("J", HARD_POINT_COUNT, polygon, filter(HARD_POINT_COUNT_EX), "==", 0.),
			new NumericRule("J", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),

			new NumericRule("K", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
//			new NumericRule("K", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 2.),
//			new NumericRule("K", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("K", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 2.),
			new NumericRule("K", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("K", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("K", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 4.),
			new NumericRule("K", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("L", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("L", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("L", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("L", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
			new NumericRule("L", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("L", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("L", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("L", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("L", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("M", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("M", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("M", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("M", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 2.),
			new NumericRule("M", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("M", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("M", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("M", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("M", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("N", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("N", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("N", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("N", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
			new NumericRule("N", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("N", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("N", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
//			new NumericRule("N", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("N", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("O", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 1.),
			new NumericRule("O", T_JUNCTION_POINT_COUNT, polygon, filter(T_JUNCTION_POINT_COUNT_EX), "==", 0.),
//			new NumericRule("O", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
//			new NumericRule("O", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("O", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 0.),
			new NumericRule("O", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("O", CURVE_ARCH_COUNT, polygon, VAR + CURVE_ARCH_COUNT_EX, ">", 0.),
			new NumericRule("O", HARD_POINT_COUNT, polygon, filter(HARD_POINT_COUNT_EX), "==", 0.),
			new NumericRule("O", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),

			new NumericRule("P", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 1.),
			new NumericRule("P", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 1.),
			new NumericRule("P", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("P", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
//			new NumericRule("P", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("P", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("P", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 1.),
			new NumericRule("P", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),

			new NumericRule("Q", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 1.),
//			new NumericRule("Q", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), ">", 0.),
//			new NumericRule("Q", T_JUNCTION_LEFT_POINT_COUNT, polygon, VAR + T_JUNCTION_LEFT_POINT_COUNT_EX, "==", 0.),
			new NumericRule("Q", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 1.),
			new NumericRule("Q", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), ">", 1.),
			new NumericRule("Q", CURVE_ARCH_COUNT, polygon, VAR + CURVE_ARCH_COUNT_EX, ">", 0.),
//			new NumericRule("Q", HARD_POINT_COUNT, polygon, filter(HARD_POINT_COUNT_EX), "==", 0.),
			new NumericRule("Q", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),
			new NumericRule("Q", END_POINT_BOTTOM_RIGHT_HALF_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_RIGHT_HALF_POINT_COUNT_EX), ">", 0.),
			
			new NumericRule("R", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 1.),
			new NumericRule("R", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), ">", 0.),
			new NumericRule("R", U_JUNCTION_POINT_COUNT, polygon, VAR + U_JUNCTION_POINT_COUNT_EX2, "==", 2.),
			new NumericRule("R", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 2.),
			//This is because the way that the top point is found as the first scan line point to top line will not always be vertical
			new NumericRule("R", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, ">", 0.),
			new NumericRule("R", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("R", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),

			new NumericRule("S", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("S", T_JUNCTION_POINT_COUNT, polygon, filter(T_JUNCTION_POINT_COUNT_EX), "==", 0.),
			new NumericRule("S", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("S", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("S", END_POINT_TOP_RIGHT_HALF_POINT_COUNT, polygon, filter(END_POINT_TOP_RIGHT_HALF_POINT_COUNT_EX), "==", 1.),
			new NumericRule("S", END_POINT_BOTTOM_LEFT_HALF_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_LEFT_HALF_POINT_COUNT_EX), "==", 1.),
			new NumericRule("S", INFLECTION_POINT_COUNT, polygon, size(INFLECTION_POINT_COUNT_EX), ">", 0.),
			new NumericRule("S", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),
			new NumericRule("S", ASPECT_RATIO, polygon, VAR + ASPECT_RATIO_EX, ">", 0.5),
			
			new NumericRule("T", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("T", T_JUNCTION_POINT_COUNT, polygon, filter(T_JUNCTION_POINT_COUNT_EX), "==", 1.),
			new NumericRule("T", END_POINT_TOP_POINT_COUNT, polygon, filter(END_POINT_TOP_POINT_COUNT_EX), "==", 2.),
			new NumericRule("T", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
			new NumericRule("T", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("T", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("T", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 3.),
			new NumericRule("T", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("U", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("U", T_JUNCTION_POINT_COUNT, polygon, filter(T_JUNCTION_POINT_COUNT_EX), "==", 0.),
			new NumericRule("U", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 0.),
			new NumericRule("U", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("U", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("U", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("U", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("U", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, ">", 0.),

			new NumericRule("V", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("V", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("V", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("V", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 0.),
			new NumericRule("V", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("V", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("V", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("V", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("V", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),
			new NumericRule("V", INFLECTION_POINT_COUNT, polygon, size(INFLECTION_POINT_COUNT_EX), "==", 0.),

			new NumericRule("W", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("W", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("W", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 0.),
			new NumericRule("W", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("W", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("W", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
			new NumericRule("W", MULTI_LINE_COUNT, polygon, size(MULTI_LINE_COUNT_EX), "==", 1.),
			new NumericRule("W", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),
			new NumericRule("W", INFLECTION_POINT_COUNT, polygon, size(INFLECTION_POINT_COUNT_EX), ">", 1.),

			new NumericRule("X", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("X", END_POINT_TOP_POINT_COUNT, polygon, filter(END_POINT_TOP_POINT_COUNT_EX), "==", 2.),
			new NumericRule("X", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 2.),
			new NumericRule("X", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("X", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("X", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 4.),
			new NumericRule("X", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),
			
			new NumericRule("Y", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("Y", Y_JUNCTION_POINT_COUNT, polygon, filter(Y_JUNCTION_POINT_COUNT_EX), "==", 1.),
			new NumericRule("Y", END_POINT_TOP_POINT_COUNT, polygon, filter(END_POINT_TOP_POINT_COUNT_EX), "==", 2.),
			new NumericRule("Y", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
			new NumericRule("Y", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("Y", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 1.),
			new NumericRule("Y", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 3.),
			new NumericRule("Y", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),

			new NumericRule("Z", HOLE_COUNT, polygon, VAR + HOLE_COUNT_EX, "==", 0.),
			new NumericRule("Z", T_JUNCTION_LEFT_POINT_COUNT, polygon, filter(T_JUNCTION_LEFT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("Z", T_JUNCTION_RIGHT_POINT_COUNT, polygon, filter(T_JUNCTION_RIGHT_POINT_COUNT_EX), "==", 0.),
			new NumericRule("Z", END_POINT_BOTTOM_POINT_COUNT, polygon, filter(END_POINT_BOTTOM_POINT_COUNT_EX), "==", 1.),
			new NumericRule("Z", HORIZONTAL_LINE_COUNT, polygon, VAR + HORIZONTAL_LINE_COUNT_EX, "==", 2.),
			new NumericRule("Z", VERTICAL_LINE_COUNT, polygon, VAR + VERTICAL_LINE_COUNT_EX, "==", 0.),
			new NumericRule("Z", END_POINT_COUNT, polygon, VAR + END_POINT_COUNT_EX, "==", 2.),
//			new NumericRule("Z", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, polygon, filter(END_POINT_TOP_LEFT_THIRD_POINT_COUNT_EX), "==", 1.),
			new NumericRule("Z", POINT_COUNT, polygon, size(POINT_COUNT_EX), "==", 4.),
			new NumericRule("Z", SOFT_POINT_COUNT, polygon, VAR + SOFT_POINT_COUNT_EX2, "==", 0.),
		};
		return numericRulesForAll;
	}
	
	/** Create a letter task from all NumericRules of the underlying letters.
	 * 
	 * @param parentTask To insert this task into, will often be the RootTask
	 * @param allNumericRules all the Numeric Rules that are used
	 * @param letterSelector if you only want to match against on letter for debugging problems
	 * @return task representing a lowest level rule
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
