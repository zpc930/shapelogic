package org.shapelogic.streamlogic;

import static org.shapelogic.logic.CommonLogicExpressions.ASPECT_RATIO;
import static org.shapelogic.logic.CommonLogicExpressions.CURVE_ARCH_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_BOTTOM_LEFT_HALF_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_BOTTOM_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_BOTTOM_RIGHT_HALF_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_TOP_LEFT_THIRD_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_TOP_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_TOP_RIGHT_HALF_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.END_POINT_TOP_RIGHT_THIRD_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.HARD_CORNER_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.HOLE_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.HORIZONTAL_LINE_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.INFLECTION_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.LINE_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.MULTI_LINE_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.SOFT_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.STRAIGHT_LINE_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.T_JUNCTION_LEFT_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.T_JUNCTION_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.T_JUNCTION_RIGHT_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.U_JUNCTION_POINT_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.VERTICAL_LINE_COUNT;
import static org.shapelogic.logic.CommonLogicExpressions.Y_JUNCTION_POINT_COUNT;

import java.util.ArrayList;
import java.util.List;

import org.shapelogic.calculation.IQueryCalc;
import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.streams.StreamFactory;
import org.shapelogic.streams.XOrListStream;

/** Create letter streams.
 * 
 * Based on LetterTaskFactory.<br /> 
 * 
 * Should create all the rules used for letter matching.<br />
 * 
 * Contains rules for more complex set of rules works for straight and curved letters <br />
 * This uses annotations <br />
 * 
 * @author Sami Badawi
 *
 */
public class LoadLetterStreams {
	
	private static IQueryCalc queryCalc = QueryCalc.getInstance();
	RecursiveContext recursiveContext = RootMap.getInstance();
	StreamFactory streamFactory;
	public LoadPolygonStreams loadPolygonStreams;
	
	public LoadLetterStreams(RecursiveContext recursiveContext) {
		this.recursiveContext = recursiveContext;
		streamFactory = new StreamFactory(recursiveContext);
		loadPolygonStreams = new LoadPolygonStreams(recursiveContext);
	}
	
	final static public String[] lettersArray = 
	{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
		"T","U","V","W","X","Y","Z"};

	/** Helper method to create one rule in one letter. 
	 * 
	 * @param letter to define rule for
	 * @param streamName what the required stream for this rule is called in RootMap
	 * @param value constraint value
	 * @param letterFilter if only one rule should be generated this should be set to a letter 
	 */
	public void rule(String letter, String streamName, int value, String letterFilter) {
		if (letterFilter != null && !letterFilter.equalsIgnoreCase(letter))
			return;
		streamFactory.addToAndListStream0(letter, streamName, "==",	value);
	}
	
	/** Helper method to create one rule in one letter. 
	 * 
	 * @param letter to define rule for
	 * @param streamName what the required stream for this rule is called in RootMap
	 * @param value constraint value
	 * @param letterFilter if only one rule should be generated this should be set to a letter 
	 */
	public void rule(String letter, String streamName, String predicate, double value, String letterFilter) {
		if (letterFilter != null && !letterFilter.equalsIgnoreCase(letter))
			return;
		streamFactory.addToAndListStream0(letter, streamName, predicate, value);
	}

	public void rule(String letter, String streamName, String predicate, double value) {
		streamFactory.addToAndListStream0(letter, streamName, predicate, value);
	}
	
	public void makeXOrStream(String streamName, String[] symbolStreamArray) {
		List<String> symbols = new ArrayList<String>();
		for (String symbol: symbolStreamArray)
			symbols.add(symbol);
		XOrListStream letterMatchStream = new XOrListStream( symbols, recursiveContext);
		queryCalc.put(streamName, letterMatchStream, recursiveContext);
	}

	/** Setup all the stream for a  letter match.<br />
	 * 
	 * Requirements streams: polygons.
	 *  */
	public void loadLetterStream(String letterFilter) {
		loadPolygonStreams.loadStreamsRequiredForLetterMatch();
		letterFilter = null;
		makeAllLetterStream(letterFilter);
    	makeXOrStream(StreamNames.LETTERS, lettersArray);
	}
	
	/** Rules for matching  letters, using only very simple properties.
	 * 
	 * @param letterFilter
	 */
	public void makeStraightLetterStream(String letterFilter) {
		rule("A", POINT_COUNT, 5, letterFilter);
		rule("A", LINE_COUNT, 5, letterFilter);
		rule("A", HORIZONTAL_LINE_COUNT, 1, letterFilter);
		rule("A", VERTICAL_LINE_COUNT, 0, letterFilter);
		rule("A", END_POINT_COUNT, 2, letterFilter);
		
		rule("E", POINT_COUNT, 6, letterFilter);
		rule("E", LINE_COUNT, 5, letterFilter);
		rule("E", HORIZONTAL_LINE_COUNT, 3, letterFilter);
		rule("E", VERTICAL_LINE_COUNT, 2, letterFilter);
		rule("E", END_POINT_COUNT, 3, letterFilter);

		rule("F", POINT_COUNT, 5, letterFilter);
		rule("F", LINE_COUNT, 4, letterFilter);
		rule("F", HORIZONTAL_LINE_COUNT, 2, letterFilter);
		rule("F", VERTICAL_LINE_COUNT, 2, letterFilter);
		rule("F", END_POINT_COUNT, 3, letterFilter);

		rule("H", POINT_COUNT, 6, letterFilter);
		rule("H", LINE_COUNT, 5, letterFilter);
		rule("H", HORIZONTAL_LINE_COUNT, 1, letterFilter);
		rule("H", VERTICAL_LINE_COUNT, 4, letterFilter);
		rule("H", END_POINT_COUNT, 4, letterFilter);

		rule("I", POINT_COUNT, 2, letterFilter);
		rule("I", LINE_COUNT, 1, letterFilter);
		rule("I", HORIZONTAL_LINE_COUNT, 0, letterFilter);
		rule("I", VERTICAL_LINE_COUNT, 1, letterFilter);
		rule("I", END_POINT_COUNT, 2, letterFilter);

		rule("K", POINT_COUNT, 5, letterFilter);
		rule("K", LINE_COUNT, 4, letterFilter);
		rule("K", HORIZONTAL_LINE_COUNT, 0, letterFilter);
		rule("K", VERTICAL_LINE_COUNT, 2, letterFilter);
		rule("K", END_POINT_COUNT, 4, letterFilter);

		rule("L", POINT_COUNT, 3, letterFilter);
		rule("L", LINE_COUNT, 2, letterFilter);
		rule("L", HORIZONTAL_LINE_COUNT, 1, letterFilter);
		rule("L", VERTICAL_LINE_COUNT, 1, letterFilter);
		rule("L", END_POINT_COUNT, 2, letterFilter);

		rule("M", POINT_COUNT, 5, letterFilter);
		rule("M", LINE_COUNT, 4, letterFilter);
		rule("M", HORIZONTAL_LINE_COUNT, 0, letterFilter);
		rule("M", VERTICAL_LINE_COUNT, 2, letterFilter);
		rule("M", END_POINT_COUNT, 2, letterFilter);

		rule("N", POINT_COUNT, 4, letterFilter);
		rule("N", LINE_COUNT, 3, letterFilter);
		rule("N", HORIZONTAL_LINE_COUNT, 0, letterFilter);
		rule("N", VERTICAL_LINE_COUNT, 2, letterFilter);
		rule("N", END_POINT_COUNT, 2, letterFilter);

		rule("T", POINT_COUNT, 4, letterFilter);
		rule("T", LINE_COUNT, 3, letterFilter);
		rule("T", HORIZONTAL_LINE_COUNT, 2, letterFilter);
		rule("T", VERTICAL_LINE_COUNT, 1, letterFilter);
		rule("T", END_POINT_COUNT, 3, letterFilter);

		rule("V", POINT_COUNT, 3, letterFilter);
		rule("V", LINE_COUNT, 2, letterFilter);
		rule("V", HORIZONTAL_LINE_COUNT, 0, letterFilter);
		rule("V", VERTICAL_LINE_COUNT, 0, letterFilter);
		rule("V", END_POINT_COUNT, 2, letterFilter);

		rule("W", POINT_COUNT, 5, letterFilter);
		rule("W", LINE_COUNT, 4, letterFilter);
		rule("W", HORIZONTAL_LINE_COUNT, 0, letterFilter);
		rule("W", VERTICAL_LINE_COUNT, 0, letterFilter);
		rule("W", END_POINT_COUNT, 2, letterFilter);

		//Same as W
		rule("X", POINT_COUNT, 5, letterFilter);
		rule("X", LINE_COUNT, 4, letterFilter);
		rule("X", HORIZONTAL_LINE_COUNT, 0, letterFilter);
		rule("X", VERTICAL_LINE_COUNT, 0, letterFilter);
		rule("X", END_POINT_COUNT, 4, letterFilter);
		
		rule("Y", POINT_COUNT, 4, letterFilter);
		rule("Y", LINE_COUNT, 3, letterFilter);
		rule("Y", HORIZONTAL_LINE_COUNT, 0, letterFilter);
		rule("Y", VERTICAL_LINE_COUNT, 1, letterFilter);
		rule("Y", END_POINT_COUNT, 3, letterFilter);
		
		rule("Z", POINT_COUNT, 4, letterFilter);
		rule("Z", LINE_COUNT, 3, letterFilter);
		rule("Z", HORIZONTAL_LINE_COUNT, 2, letterFilter);
		rule("Z", VERTICAL_LINE_COUNT, 0, letterFilter);
		rule("Z", END_POINT_COUNT, 2, letterFilter);
		
	}
	
	public void makeAllLetterStream(String letterFilter) {
		//To test if rules with old syntax still works
		rule("A", POINT_COUNT, "==", 5., letterFilter);
		rule("A", HOLE_COUNT, "==", 1., letterFilter);
		rule("A", T_JUNCTION_LEFT_POINT_COUNT,"==", 1., letterFilter);
		rule("A", T_JUNCTION_RIGHT_POINT_COUNT,"==", 1., letterFilter);
		rule("A", END_POINT_BOTTOM_POINT_COUNT, "==", 2., letterFilter);
		rule("A", HORIZONTAL_LINE_COUNT, "==", 1., letterFilter);
		rule("A", VERTICAL_LINE_COUNT, "==", 0., letterFilter);
		rule("A", END_POINT_COUNT, "==", 2., letterFilter);
		rule("A", SOFT_POINT_COUNT, "==", 0., letterFilter);
		
		rule("B", HOLE_COUNT, "==", 2, letterFilter); //try Boolean Task
		rule("B", T_JUNCTION_LEFT_POINT_COUNT,">", 0., letterFilter);
		rule("B", U_JUNCTION_POINT_COUNT, "==", 2., letterFilter);
		rule("B", END_POINT_BOTTOM_POINT_COUNT, "==", 0., letterFilter);
		rule("B", END_POINT_COUNT, "==", 0., letterFilter);
		rule("B", SOFT_POINT_COUNT, ">", 0., letterFilter);
		rule("B", STRAIGHT_LINE_COUNT, ">", 0., letterFilter);

		rule("C", HOLE_COUNT, "==", 0., letterFilter);
		rule("C", T_JUNCTION_POINT_COUNT, "==",0., letterFilter);
		rule("C", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("C", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("C", VERTICAL_LINE_COUNT, "==", 0., letterFilter);
		rule("C", END_POINT_COUNT, "==", 2., letterFilter);
		rule("C", INFLECTION_POINT_COUNT, "==", 0., letterFilter);
		rule("C", CURVE_ARCH_COUNT, ">",1., letterFilter);
		rule("C", HARD_CORNER_COUNT, "==", 0., letterFilter);
		rule("C", SOFT_POINT_COUNT, ">", 0., letterFilter);

		rule("D", HOLE_COUNT, "==", 1., letterFilter);
		rule("D", T_JUNCTION_POINT_COUNT, "==", 0., letterFilter);
		rule("D", END_POINT_BOTTOM_POINT_COUNT, "==", 0., letterFilter);
		rule("D", VERTICAL_LINE_COUNT, ">", 0., letterFilter);
		rule("D", END_POINT_COUNT, "==", 0., letterFilter);
		rule("D", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("D", SOFT_POINT_COUNT, ">", 0., letterFilter);
		rule("D", HARD_CORNER_COUNT, ">", 0., letterFilter);

		rule("E", HOLE_COUNT, "==", 0., letterFilter);
		rule("E", T_JUNCTION_LEFT_POINT_COUNT, "==", 1., letterFilter);
		rule("E", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("E", END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("E", HORIZONTAL_LINE_COUNT, ">", 1., letterFilter);
		rule("E", VERTICAL_LINE_COUNT, ">", 1., letterFilter);
		rule("E", END_POINT_COUNT, "==", 3., letterFilter);
		rule("E", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("E", POINT_COUNT, ">", 5., letterFilter);

		rule("F", HOLE_COUNT, "==", 0., letterFilter);
		rule("F", T_JUNCTION_LEFT_POINT_COUNT, "==", 1., letterFilter);
		rule("F", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("F", END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("F", HORIZONTAL_LINE_COUNT, ">", 1., letterFilter);
		rule("F", VERTICAL_LINE_COUNT, ">", 1., letterFilter);
		rule("F", END_POINT_COUNT, "==", 3., letterFilter);
		rule("F", STRAIGHT_LINE_COUNT, ">", 2., letterFilter);

		rule("G", HOLE_COUNT, "==", 0., letterFilter);
		rule("G", T_JUNCTION_POINT_COUNT, "==", 0., letterFilter);
		rule("G", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("G", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("G", END_POINT_COUNT, "==", 2., letterFilter);
		rule("G", INFLECTION_POINT_COUNT, "==", 0., letterFilter);
		rule("G", CURVE_ARCH_COUNT, ">", 2., letterFilter);
		rule("G", HARD_CORNER_COUNT, ">", 0., letterFilter);
		rule("G", SOFT_POINT_COUNT, ">", 0., letterFilter);
		rule("G", ASPECT_RATIO, ">", 0.5, letterFilter);
		rule("G", STRAIGHT_LINE_COUNT, ">", 0., letterFilter);

		rule("H", HOLE_COUNT, "==", 0., letterFilter);
		rule("H", T_JUNCTION_LEFT_POINT_COUNT, "==", 1., letterFilter);
		rule("H", T_JUNCTION_RIGHT_POINT_COUNT, "==", 1., letterFilter);
		rule("H", END_POINT_BOTTOM_POINT_COUNT, "==", 2., letterFilter);
		rule("H", HORIZONTAL_LINE_COUNT, "==", 1., letterFilter);
		rule("H", VERTICAL_LINE_COUNT, "==", 4., letterFilter);
		rule("H", END_POINT_COUNT, "==", 4., letterFilter);
		rule("H", SOFT_POINT_COUNT, "==", 0., letterFilter);

		rule("I", HOLE_COUNT, "==", 0., letterFilter);
		rule("I", T_JUNCTION_LEFT_POINT_COUNT, "==", 0., letterFilter);
		rule("I", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("I", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("I", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("I", VERTICAL_LINE_COUNT, "==", 1., letterFilter);
		rule("I", END_POINT_COUNT, "==", 2., letterFilter);
		rule("I", MULTI_LINE_COUNT, "==", 0., letterFilter);
		rule("I", SOFT_POINT_COUNT, "==", 0., letterFilter);

		rule("J", HOLE_COUNT, "==", 0., letterFilter);
		rule("J", T_JUNCTION_LEFT_POINT_COUNT, "==", 0., letterFilter);
		rule("J", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("J", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("J", VERTICAL_LINE_COUNT, "==", 1., letterFilter);
		rule("J", END_POINT_COUNT, "==", 2., letterFilter);
		rule("J", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("J", HARD_CORNER_COUNT, "==", 0., letterFilter);
		rule("J", SOFT_POINT_COUNT, ">", 0., letterFilter);

		rule("K", HOLE_COUNT, "==", 0., letterFilter);
		rule("K", END_POINT_BOTTOM_POINT_COUNT, "==", 2., letterFilter);
		rule("K", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("K", VERTICAL_LINE_COUNT, "==", 2., letterFilter);
		rule("K", END_POINT_COUNT, "==", 4., letterFilter);
		rule("K", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("K", STRAIGHT_LINE_COUNT, ">", 2., letterFilter);

		rule("L", HOLE_COUNT, "==", 0., letterFilter);
		rule("L", T_JUNCTION_LEFT_POINT_COUNT, "==", 0., letterFilter);
		rule("L", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("L", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("L", HORIZONTAL_LINE_COUNT, "==", 1., letterFilter);
		rule("L", VERTICAL_LINE_COUNT, "==", 1., letterFilter);
		rule("L", END_POINT_COUNT, "==", 2., letterFilter);
		rule("L", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("L", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("L", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("L", END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT, "==", 1., letterFilter);

		rule("M", HOLE_COUNT, "==", 0., letterFilter);
		rule("M", T_JUNCTION_LEFT_POINT_COUNT, "==", 0., letterFilter);
		rule("M", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("M", END_POINT_BOTTOM_POINT_COUNT, "==", 2., letterFilter);
		rule("M", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("M", VERTICAL_LINE_COUNT, "==", 2., letterFilter);
		rule("M", END_POINT_COUNT, "==", 2., letterFilter);
		rule("M", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("M", SOFT_POINT_COUNT, "==", 0., letterFilter);

		rule("N", HOLE_COUNT, "==", 0., letterFilter);
		rule("N", T_JUNCTION_LEFT_POINT_COUNT, "==", 0., letterFilter);
		rule("N", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("N", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("N", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("N", VERTICAL_LINE_COUNT, "==", 2., letterFilter);
		rule("N", END_POINT_COUNT, "==", 2., letterFilter);
		rule("N", SOFT_POINT_COUNT, "==", 0., letterFilter);

		rule("O", HOLE_COUNT, "==", 1., letterFilter);
		rule("O", T_JUNCTION_POINT_COUNT, "==", 0., letterFilter);
		rule("O", END_POINT_COUNT, "==", 0., letterFilter);
		rule("O", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("O", CURVE_ARCH_COUNT, ">", 0., letterFilter);
		rule("O", HARD_CORNER_COUNT, "==", 0., letterFilter);
		rule("O", SOFT_POINT_COUNT, ">", 0., letterFilter);

		rule("P", HOLE_COUNT, "==", 1., letterFilter);
		rule("P", T_JUNCTION_LEFT_POINT_COUNT, "==", 1., letterFilter);
		rule("P", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("P", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("P", VERTICAL_LINE_COUNT, ">", 1., letterFilter);
		rule("P", END_POINT_COUNT, "==", 1., letterFilter);
		rule("P", SOFT_POINT_COUNT, ">", 0., letterFilter);

		rule("Q", HOLE_COUNT, "==", 1., letterFilter);
		rule("Q", END_POINT_COUNT, "==", 1., letterFilter);
		rule("Q", MULTI_LINE_COUNT, ">", 1., letterFilter);
		rule("Q", CURVE_ARCH_COUNT, ">", 0., letterFilter);
		rule("Q", SOFT_POINT_COUNT, ">", 0., letterFilter);
		rule("Q", END_POINT_BOTTOM_RIGHT_HALF_POINT_COUNT, ">", 0., letterFilter);
		
		rule("R", HOLE_COUNT, "==", 1., letterFilter);
		rule("R", T_JUNCTION_LEFT_POINT_COUNT, ">", 0., letterFilter);
		rule("R", U_JUNCTION_POINT_COUNT, "==", 2., letterFilter);
		rule("R", END_POINT_BOTTOM_POINT_COUNT, "==", 2., letterFilter);
		//This is because the way that the top point is found as the first scan line point to top line will not always be vertical
		rule("R", VERTICAL_LINE_COUNT, ">", 0., letterFilter);
		rule("R", END_POINT_COUNT, "==", 2., letterFilter);
		rule("R", SOFT_POINT_COUNT, ">", 0., letterFilter);

		rule("S", HOLE_COUNT, "==", 0., letterFilter);
		rule("S", T_JUNCTION_POINT_COUNT, "==", 0., letterFilter);
		rule("S", END_POINT_COUNT, "==", 2., letterFilter);
		rule("S", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("S", END_POINT_TOP_RIGHT_HALF_POINT_COUNT, "==", 1., letterFilter);
		rule("S", END_POINT_BOTTOM_LEFT_HALF_POINT_COUNT, "==", 1., letterFilter);
		rule("S", INFLECTION_POINT_COUNT, ">", 0., letterFilter);
		rule("S", SOFT_POINT_COUNT, ">", 0., letterFilter);
		rule("S", ASPECT_RATIO, ">", 0.5, letterFilter);
		
		rule("T", HOLE_COUNT, "==", 0., letterFilter);
		rule("T", T_JUNCTION_POINT_COUNT, "==", 1., letterFilter);
		rule("T", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("T", END_POINT_TOP_RIGHT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("T", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("T", HORIZONTAL_LINE_COUNT, ">", 0., letterFilter);
		rule("T", END_POINT_COUNT, "==", 3., letterFilter);
		rule("T", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("T", STRAIGHT_LINE_COUNT, ">", 1., letterFilter);

		rule("U", HOLE_COUNT, "==", 0., letterFilter);
		rule("U", T_JUNCTION_POINT_COUNT, "==", 0., letterFilter);
		rule("U", END_POINT_BOTTOM_POINT_COUNT, "==", 0., letterFilter);
		rule("U", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("U", END_POINT_TOP_RIGHT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("U", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("U", VERTICAL_LINE_COUNT, "==", 2., letterFilter);
		rule("U", END_POINT_COUNT, "==", 2., letterFilter);
		rule("U", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("U", SOFT_POINT_COUNT, ">", 0., letterFilter);

		rule("V", HOLE_COUNT, "==", 0., letterFilter);
		rule("V", T_JUNCTION_LEFT_POINT_COUNT, "==", 0., letterFilter);
		rule("V", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("V", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("V", END_POINT_TOP_RIGHT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("V", END_POINT_BOTTOM_POINT_COUNT, "==", 0., letterFilter);
		rule("V", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("V", VERTICAL_LINE_COUNT, "==", 0., letterFilter);
		rule("V", END_POINT_COUNT, "==", 2., letterFilter);
		rule("V", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("V", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("V", INFLECTION_POINT_COUNT, "==", 0., letterFilter);
		rule("V", POINT_COUNT,"<", 5., letterFilter);

		rule("W", HOLE_COUNT, "==", 0., letterFilter);
		rule("W", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("W", END_POINT_BOTTOM_POINT_COUNT, "==", 0., letterFilter);
		rule("W", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("W", END_POINT_TOP_RIGHT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("W", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("W", VERTICAL_LINE_COUNT, "==", 0., letterFilter);
		rule("W", END_POINT_COUNT, "==", 2., letterFilter);
		rule("W", MULTI_LINE_COUNT, "==", 1., letterFilter);
		rule("W", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("W", INFLECTION_POINT_COUNT, ">", 1., letterFilter);
		rule("W", POINT_COUNT,">", 4., letterFilter);

		rule("X", HOLE_COUNT, "==", 0., letterFilter);
		rule("X", END_POINT_TOP_POINT_COUNT, "==", 2., letterFilter);
		rule("X", END_POINT_BOTTOM_POINT_COUNT, "==", 2., letterFilter);
		rule("X", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("X", VERTICAL_LINE_COUNT, "==", 0., letterFilter);
		rule("X", END_POINT_COUNT, "==", 4., letterFilter);
		rule("X", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("X", POINT_COUNT,">", 4., letterFilter);
		
		rule("Y", HOLE_COUNT, "==", 0., letterFilter);
		rule("Y", Y_JUNCTION_POINT_COUNT, "==", 1., letterFilter);
		rule("Y", END_POINT_TOP_POINT_COUNT, "==", 2., letterFilter);
		rule("Y", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("Y", HORIZONTAL_LINE_COUNT, "==", 0., letterFilter);
		rule("Y", VERTICAL_LINE_COUNT, "==", 1., letterFilter);
		rule("Y", END_POINT_COUNT, "==", 3., letterFilter);
		rule("Y", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("Y", POINT_COUNT,">", 3., letterFilter);

		rule("Z", HOLE_COUNT, "==", 0., letterFilter);
		rule("Z", T_JUNCTION_LEFT_POINT_COUNT, "==", 0., letterFilter);
		rule("Z", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0., letterFilter);
		rule("Z", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("Z", END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT, "==", 1., letterFilter);
		rule("Z", END_POINT_BOTTOM_POINT_COUNT, "==", 1., letterFilter);
		rule("Z", HORIZONTAL_LINE_COUNT, "==", 2., letterFilter);
		rule("Z", VERTICAL_LINE_COUNT, "==", 0., letterFilter);
		rule("Z", END_POINT_COUNT, "==", 2., letterFilter);
		rule("Z", POINT_COUNT, "==", 4., letterFilter);
		rule("Z", SOFT_POINT_COUNT, "==", 0., letterFilter);
		rule("Z", POINT_COUNT,">", 3., letterFilter);
	}

}
