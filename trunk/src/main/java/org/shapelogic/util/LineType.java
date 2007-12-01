package org.shapelogic.util;


/** Enum for with types for Lines. 
 * 
 * This could change quite a bit.
 * 
 * @author Sami Badawi
 */
public enum LineType implements OHInterface {
	/* Before a type is determined */
	UNKNOWN,
	
	/** Not completely straight */
	STRAIGHT, //only a start and an end point

	/** only on one side of a line between the start and end point */
	CURVE_ARCH,

	/** On both sides of a line between the start and end point 
	 * this is the rest category for lines */
	WAVE,

	/** Means that this is an arch and that it is concave compared to 1 or both 
	 * neighbor points, meaning they are on the same side of the line as the 
	 * arch */
	CONCAVE_ARCH,
	
	/** This line contains an inflection point, meaning the end points have 
	 * different signed direction changes */
	INFLECTION_POINT,
	
	/** For multi lines, if 2 lines are added together */
	STRAIGHT_MULTI_LINE, 
	
	/** Multi lines or polygon, is a circle */
	WHOLE_CIRCLE,
	;

	@Override
	public String getOhName() {
		return this.name();
	}
}
