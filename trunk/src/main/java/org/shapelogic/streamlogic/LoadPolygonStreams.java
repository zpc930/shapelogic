package org.shapelogic.streamlogic;

import static org.shapelogic.logic.CommonLogicExpressions.*;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.streams.ListCalcStream1;
import org.shapelogic.streams.ListStream;
import org.shapelogic.streams.NamedNumberedStreamLazySetup;
import org.shapelogic.streams.NumberedStream;
import org.shapelogic.streams.StreamFactory;

/** Create letter streams.
 * 
 * Based on LetterTaskFactory.<br /> 
 * 
 * Should create all the rules used for letter matching.<br />
 * 
 * Contains 2 sets of rules for letter matching<br />
 * 
 * 1: A simple set that only works for straight letters using simple properties<br />
 * 2: A more complex set of rules works for straight and curved letters <br />
 * This uses annotations <br />
 * 
 * @author Sami Badawi
 *
 */
public class LoadPolygonStreams {

	/** Helper method to create one rule in one letter. 
	 * 
	 * @param letter to define rule for
	 * @param streamName what the required stream for this rule is called in RootMap
	 * @param value constraint value
	 * @param letterFilter if only one rule should be generated this should be set to a letter 
	 */
	public static void rule(String letter, String streamName, int value, String letterFilter) {
		if (letterFilter != null && !letterFilter.equalsIgnoreCase(letter))
			return;
		StreamFactory.addToAndListStream0(letter, streamName, "==",	value);
	}
	
	/** Load all the required streams for the letter matcher to work.
	 * <br />
	 * In order for this to work the polygons have to be defined first.
	 */
	public static void loadStreamsRequiredForLetterMatch(NumberedStream<Polygon> polygons) {
		
		loadPointCountStream(polygons);
		
		loadLineCountStream(polygons);
		
		loadHorizontalLineCountStream(polygons);
		
		loadVerticalLineCountStream(polygons);
		
		loadEndPointCountStream(polygons);
	}

	public static void loadStreamsRequiredForLetterMatch() {
		//In order for this to work the polygons have to be defined first
		NumberedStream<Polygon> polygons = new NamedNumberedStreamLazySetup<Polygon>(StreamNames.POLYGONS);
		loadStreamsRequiredForLetterMatch(polygons);
	}
	
	private static void loadEndPointCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> endPointCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return input.getEndPointCount();
			}
		};
		ListStream<Integer> endPointCountStream = 
			new ListCalcStream1<Polygon, Integer>(endPointCountCalc1,polygons); 
		RootMap.put(END_POINT_COUNT,endPointCountStream);
	}

	private static void loadVerticalLineCountStream(
			NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> verticalLineCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return input.getVerticalLines().size();
			}
		};
		ListStream<Integer> verticalLineCountStream = 
			new ListCalcStream1<Polygon, Integer>(verticalLineCountCalc1,polygons); 
		RootMap.put(VERTICAL_LINE_COUNT,verticalLineCountStream);
	}

	private static void loadHorizontalLineCountStream(
			NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> horizontalLineCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return input.getHorizontalLines().size();
			}
		};
		ListStream<Integer> horizontalLineCountStream = 
			new ListCalcStream1<Polygon, Integer>(horizontalLineCountCalc1,polygons); 
		RootMap.put(HORIZONTAL_LINE_COUNT,horizontalLineCountStream);
	}

	private static void loadLineCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> lineCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return input.getLines().size();
			}
		};
		ListStream<Integer> lineCountStream = 
			new ListCalcStream1<Polygon, Integer>(lineCountCalc1,polygons); 
		RootMap.put(LINE_COUNT,lineCountStream);
	}

	private static void loadPointCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> pointCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return input.getPoints().size();
			}
		};
		ListStream<Integer> pointCountStream = 
			new ListCalcStream1<Polygon, Integer>(pointCountCalc1,polygons); 
		RootMap.put(POINT_COUNT,pointCountStream);
	}
	
}
