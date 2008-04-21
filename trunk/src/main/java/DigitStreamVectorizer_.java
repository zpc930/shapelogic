import static org.shapelogic.logic.CommonLogicExpressions.*;
import static org.shapelogic.streamlogic.LoadLetterStreams.rule;

import org.shapelogic.imageprocessing.StreamVectorizer;
import org.shapelogic.imageutil.PlugInFilterAdapter;
import org.shapelogic.streamlogic.LoadLetterStreams;
import org.shapelogic.streamlogic.LoadPolygonStreams;

/** Class running StreamVectorizer and matching polygons to digits.<br />
 * <p>
 * The main purpose of this is to demonstrate how users of ShapeLogic 
 * relatively easily can define their own match.
 * This class did not need to be in the ShapeLogic jar file. 
 * </p>
 * @author Sami Badawi
 *
 */
public class DigitStreamVectorizer_ extends PlugInFilterAdapter {
	
	public DigitStreamVectorizer_() {
		super(new StreamVectorizer()  {
			@Override
			public void matchSetup() {
				loadDigitStream();
			}
		});
	}
	
	public static void loadDigitStream() {
		LoadPolygonStreams.loadStreamsRequiredForLetterMatch();
		makeDigitStream();
		String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; 
    	LoadLetterStreams.makeLetterXOrStream(digits);
	}

	public static void makeDigitStream() {

		rule("0", HOLE_COUNT, "==", 1.);
		rule("0", T_JUNCTION_POINT_COUNT, "==", 0.);
		rule("0", END_POINT_COUNT, "==", 0.);
		rule("0", MULTI_LINE_COUNT, "==", 1.);
		rule("0", CURVE_ARCH_COUNT, ">", 0.);
		rule("0", HARD_CORNER_COUNT, "==", 0.);
		rule("0", SOFT_POINT_COUNT, ">", 0.);

		rule("1", HOLE_COUNT, "==", 0.);
		rule("1", T_JUNCTION_LEFT_POINT_COUNT, "==", 0.);
		rule("1", T_JUNCTION_RIGHT_POINT_COUNT, "==", 0.);
		rule("1", END_POINT_BOTTOM_POINT_COUNT, "==", 1.);
		rule("1", HORIZONTAL_LINE_COUNT, "==", 0.);
		rule("1", VERTICAL_LINE_COUNT, "==", 1.);
		rule("1", END_POINT_COUNT, "==", 2.);
		rule("1", MULTI_LINE_COUNT, "==", 0.);
		rule("1", SOFT_POINT_COUNT, "==", 0.);
		rule("1", ASPECT_RATIO, "<", 0.4);
		
		rule("2", HOLE_COUNT, "==", 0.);
		rule("2", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1.);
		rule("2", END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT, "==", 1.);
		rule("2", END_POINT_COUNT, ">", 1.);
		rule("2", END_POINT_COUNT, "<", 4.);
		rule("2", POINT_COUNT,">", 3.);
		rule("2", ASPECT_RATIO, ">", 0.5);
		rule("2", ASPECT_RATIO, "<", 0.9);
		
		rule("3", HOLE_COUNT, "==", 0.);
		rule("3", T_JUNCTION_LEFT_POINT_COUNT, "==", 0.);
		rule("3", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1.);
		rule("3", END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT, "==", 1.);
		rule("3", END_POINT_COUNT, ">", 1.);
		rule("3", END_POINT_COUNT, "<", 4.);
		rule("3", SOFT_POINT_COUNT, ">", 0.);
		rule("3", POINT_COUNT, ">", 5.);
		rule("3", ASPECT_RATIO, ">", 0.5);
		rule("3", ASPECT_RATIO, "<", 0.9);

		rule("4", HOLE_COUNT, "==", 1.);
		rule("4", END_POINT_BOTTOM_RIGHT_THIRD_POINT_COUNT, "==", 1.);
		rule("4", T_JUNCTION_RIGHT_POINT_COUNT, ">", 0.);
		rule("4", T_JUNCTION_RIGHT_POINT_COUNT, "<", 4.);
		rule("4", ASPECT_RATIO, ">", 0.5);
		rule("4", ASPECT_RATIO, "<", 0.9);
		rule("4", SOFT_POINT_COUNT, "==", 0.);
		rule("4", HARD_CORNER_COUNT, ">", 1.);
		
		rule("5", HOLE_COUNT, "==", 0.);
		rule("5", END_POINT_TOP_RIGHT_THIRD_POINT_COUNT, "==", 1.);
		rule("5", END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT, "==", 1.);
		rule("5", END_POINT_COUNT, ">", 1.);
		rule("5", END_POINT_COUNT, "<", 4.);
		rule("5", POINT_COUNT,">", 3.);
		rule("5", ASPECT_RATIO, ">", 0.5);
		rule("5", ASPECT_RATIO, "<", 0.9);
		rule("5", HARD_CORNER_COUNT, ">", 0.);
		rule("5", SOFT_POINT_COUNT, ">", 0.);
		
		rule("6", HOLE_COUNT, "==", 1.);
		rule("6", END_POINT_TOP_RIGHT_THIRD_POINT_COUNT, "==", 1.);
		rule("6", T_JUNCTION_LEFT_POINT_COUNT, ">", 0.);
		rule("6", T_JUNCTION_LEFT_POINT_COUNT, "<", 4.);
		rule("6", ASPECT_RATIO, ">", 0.5);
		rule("6", ASPECT_RATIO, "<", 0.9);
		rule("6", SOFT_POINT_COUNT, ">", 0.);
		rule("6", HARD_CORNER_COUNT, "<", 2.);
		
		rule("7", HOLE_COUNT, "==", 0.);
		rule("7", END_POINT_TOP_LEFT_THIRD_POINT_COUNT, "==", 1.);
		rule("7", END_POINT_BOTTOM_CENTER_THIRD_POINT_COUNT, "==", 1.);
		rule("7", HORIZONTAL_LINE_COUNT, "==", 1.);
		rule("7", END_POINT_COUNT, ">", 1.);
		rule("7", END_POINT_COUNT, "<", 4.);
		rule("7", POINT_COUNT,">", 2.);
		rule("7", ASPECT_RATIO, ">", 0.5);
		rule("7", ASPECT_RATIO, "<", 0.9);
		rule("7", HARD_CORNER_COUNT, "==", 1.);
		
		rule("8", HOLE_COUNT, "==", 2.);
		rule("8", END_POINT_COUNT, "==", 0.);
		rule("8", U_JUNCTION_POINT_COUNT, ">", 0.);
		rule("8", ASPECT_RATIO, ">", 0.5);
		rule("8", ASPECT_RATIO, "<", 0.9);
		rule("8", SOFT_POINT_COUNT, ">", 0.);
		rule("8", HARD_CORNER_COUNT, "<", 2.);
		
		rule("9", HOLE_COUNT, "==", 1.);
		rule("9", END_POINT_BOTTOM_LEFT_THIRD_POINT_COUNT, "==", 1.);
		rule("9", T_JUNCTION_RIGHT_POINT_COUNT, ">", 0.);
		rule("9", T_JUNCTION_RIGHT_POINT_COUNT, "<", 4.);
		rule("9", ASPECT_RATIO, ">", 0.5);
		rule("9", ASPECT_RATIO, "<", 0.9);
		rule("9", SOFT_POINT_COUNT, ">", 1.);
		rule("9", HARD_CORNER_COUNT, "<", 2.);
	}
}