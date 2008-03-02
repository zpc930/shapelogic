package org.shapelogic.util;

/** This just contains a lot of constants.
 * This should help prevent cyclic dependencies.
 * 
 * @author Sami Badawi
 *
 */
public class Constants {
	public static final double PRECISION = 0.001;
	public static final int BYTE_MASK = 255;
	public static final byte DIRECTION_NOT_USED = -1;
	public static final int DIRECTIONS_AROUND_POINT = 8;
	public static final int BEFORE_START_INDEX = -1; 
	public static final int START_INDEX = 0; 
	public static final Integer ZERO = new Integer(0);
	/** What you need to add to the x coordinate to get to the indexed point */
	public static int[] CYCLE_POINTS_X = {1,  1, 0,  -1, -1, -1, 0, 1};
	/** What you need to add to the y coordinate to get to the indexed point */
	public static int[] CYCLE_POINTS_Y = {0, 1, 1, 1,  0,  -1, -1, -1};

	//Equality test tasks
	public static final String SIMPLE_NUMERIC_TASK = "SimpleNumericTask";
	public static final String COUNT_COLLECTION_TASK = "CountCollectionTask";
	public static final String FILTER_COUNT_TASK = "FilterCountTask";
	
	//Boolean test tasks
	public static final String BOOLEAN_TASK = "BooleanTask";

	//Greater test tasks
	public static final String NUMERIC_GREATER_TASK = "NumericGreaterTask";
	public static final String COUNT_COLLECTION_GREATER_TASK = "CountCollectionGreaterTask";
	public static final String FILTER_COUNT_GREATER_TASK = "FilterCountGreaterTask";

	//A more general task that can create all the other tasks, by taking 2 parameters
	public static final String PARAMETRIC_RULE_TASK = "ParametricRuleTask";
	public static final double TOLERANCE = 0.0001;

	//Missing
	public final static int LAST_UNKNOWN = -2;
	public final static String NO_OH = "";
	
	//Names of Boolean operations
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String XOR = "XOR";
	
	/** Suffix to function name based on stream name. */
	public static final String FUNCTION_NAME_SUFFIX = "_FUNCTION_";
	
	/** Names of scripting languages in JSR 223. */
	public static final String GROOVY = "groovy";
	public static final String JAVASCRIPT = "javascript";
	
	/** Names used for polygons in matches. Might be moved again. */
	public static final String POLYGON = "polygon";
	public static final String RAW_POLYGON = "rawPolygon";
}