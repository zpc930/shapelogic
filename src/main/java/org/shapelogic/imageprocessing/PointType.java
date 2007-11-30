package org.shapelogic.imageprocessing;

import org.shapelogic.logic.OHInterface;

/** Enum for with types for Points.
 *   
 * This could change quite a bit
 * 
 * @author Sami Badawi
 */
public enum PointType implements OHInterface {
	/** Before a type is determined */
	UNKNOWN,
	
	/** If the point has a sharp angle for now 30 degrees is set as the limit */
	HARD_CORNER,

	/** If it is not a hard point */
	SOFT_POINT,
	
	/** 3 or more lines meets, U is for unknown */
	U_JUNCTION,
	
	/** 3 lines meet 2 are collinear and the last is somewhat orthogonal */
	T_JUNCTION,

	/** 3 lines meet less than 180 degrees between all of them */
	ARROW_JUNCTION,

	/** 3 lines meet, not a T junction */
	Y_JUNCTION,
	
	/** point is an end point, 
	 * maybe later there should be a distinction between end points and have 
	 * nothing else close by and end points that have close neighbors */
	END_POINT,
	;

	@Override
	public String getOhName() {
		return this.name();
	}
}
