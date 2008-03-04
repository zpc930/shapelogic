package org.shapelogic.streamlogic;

import static org.shapelogic.logic.CommonLogicExpressions.*;

import java.util.List;
import java.util.Set;

import org.hsqldb.lib.Collection;
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
	public static void loadStreamsRequiredForStraightLetterMatch(NumberedStream<Polygon> polygons) {
		
		loadPointCountStream(polygons);
		
		loadLineCountStream(polygons);
		
		loadHorizontalLineCountStream(polygons);
		
		loadVerticalLineCountStream(polygons);
		
		loadEndPointCountStream(polygons);
	}

	/** Load all the required streams for the letter matcher to work.
	 * <br />
	 * In order for this to work the polygons have to be defined first.
	 */
	public static void loadStreamsRequiredForLetterMatch(NumberedStream<Polygon> polygons) {
		
		loadStreamsRequiredForStraightLetterMatch(polygons);
		
		loadHoleCountStream(polygons);
		
		loadSoftPointCountStream(polygons);
		
		loadHardPointCountStream(polygons);
		
		loadUJunctionPointCountStream(polygons);
		
		loadTJunctionPointCountStream(polygons);

		loadCurveArchCountStream(polygons);
		
		multiLineCountStream(polygons);
		
		aspectRatioStream(polygons);
		
		loadInflectionPointCountStream(polygons);
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
	
	private static void loadHoleCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> holeCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return input.getHoleCount();
			}
		};
		ListStream<Integer> holeCountStream = 
			new ListCalcStream1<Polygon, Integer>(holeCountCalc1,polygons); 
		RootMap.put(HOLE_COUNT,holeCountStream);
	}
	
	private static void loadSoftPointCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> softPointCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return size(input.getAnnotatedShape().getShapesForAnnotation("PointType.SOFT_POINT"));
			}
		};
		ListStream<Integer> softPointCountStream = 
			new ListCalcStream1<Polygon, Integer>(softPointCountCalc1,polygons); 
		RootMap.put(SOFT_POINT_COUNT,softPointCountStream);
	}
	
	private static void loadHardPointCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> hardPointCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return size(input.getAnnotatedShape().getShapesForAnnotation("PointType.HARD_CORNER"));
			}
		};
		ListStream<Integer> hardPointCountStream = 
			new ListCalcStream1<Polygon, Integer>(hardPointCountCalc1,polygons); 
		RootMap.put(HARD_POINT_COUNT,hardPointCountStream);
	}
	
	private static void loadUJunctionPointCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> uJunctionPointCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return size(input.getAnnotatedShape().getShapesForAnnotation("PointType.U_JUNCTION"));
			}
		};
		ListStream<Integer> uJunctionPointCountStream = 
			new ListCalcStream1<Polygon, Integer>(uJunctionPointCountCalc1,polygons); 
		RootMap.put(U_JUNCTION_POINT_COUNT,uJunctionPointCountStream);
	}
	
	private static void loadTJunctionPointCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> tJunctionPointCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return size(input.getAnnotatedShape().getShapesForAnnotation("PointType.T_JUNCTION"));
			}
		};
		ListStream<Integer> tJunctionPointCountStream = 
			new ListCalcStream1<Polygon, Integer>(tJunctionPointCountCalc1,polygons); 
		RootMap.put(T_JUNCTION_POINT_COUNT,tJunctionPointCountStream);
	}
	
	private static void loadCurveArchCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> curveArchCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return size(input.getAnnotatedShape().getShapesForAnnotation("LineType.CURVE_ARCH"));
			}
		};
		ListStream<Integer> curveArchCountStream = 
			new ListCalcStream1<Polygon, Integer>(curveArchCountCalc1,polygons); 
		RootMap.put(CURVE_ARCH_COUNT,curveArchCountStream);
	}
	
	private static void multiLineCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> curveArchCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return size(input.getMultiLines());
			}
		};
		ListStream<Integer> curveArchCountStream = 
			new ListCalcStream1<Polygon, Integer>(curveArchCountCalc1,polygons); 
		RootMap.put(MULTI_LINE_COUNT,curveArchCountStream);
	}
	
	private static void aspectRatioStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Number> aspectRatioCalc1 = new Calc1<Polygon, Number>() {
			@Override
			public Number invoke(Polygon input) {
				return input.getBBox().getAspectRatio();
			}
		};
		ListStream<Number> aspectRatioStream = 
			new ListCalcStream1<Polygon, Number>(aspectRatioCalc1,polygons); 
		RootMap.put(ASPECT_RATIO,aspectRatioStream);
	}
	
	private static void loadInflectionPointCountStream(NumberedStream<Polygon> polygons) {
		Calc1<Polygon, Integer> inflectionPointCountCalc1 = new Calc1<Polygon, Integer>() {
			@Override
			public Integer invoke(Polygon input) {
				return size(input.getAnnotatedShape().getShapesForAnnotation("LineType.INFLECTION_POINT"));
			}
		};
		ListStream<Integer> inflectionPointCountStream = 
			new ListCalcStream1<Polygon, Integer>(inflectionPointCountCalc1,polygons); 
		RootMap.put(INFLECTION_POINT_COUNT,inflectionPointCountStream);
	}
	
	static public int size(Collection collection) {
		if (collection == null)
			return 0;
		return collection.size();
	}

	static public int size(List collection) {
		if (collection == null)
			return 0;
		return collection.size();
	}

	static public int size(Set set) {
		if (set == null)
			return 0;
		return set.size();
	}
}
