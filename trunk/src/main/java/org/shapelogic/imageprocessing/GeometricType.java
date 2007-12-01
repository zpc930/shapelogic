package org.shapelogic.imageprocessing;

import org.shapelogic.util.OHInterface;

/** Not sure if I should just reuse PixelType. 
 * 
 * @author Sami Badawi
 *
 */
public enum GeometricType implements OHInterface 
{
// Points have number between 0 and 1000
	BACKGROUND_POINT(0), //Background point, , cross index of 0

	T_JUNCTION(13),

	Y_JUNCTION(15),
	
	PIXEL_LINE_END(237), // Marked with E in diagrams. Normal point, 1 neighbors, cross index of 2
	  
	PIXEL_SINGLE_POINT(239), //Single point, , cross index of 0

	PIXEL_SOLID(241), //Inner point, 8 neighbors or 7 where the last on is an even number.

	PIXEL_EXTRA_NEIGHBOR(199), //More neighbors, more than 2 neighbors, cross index of 4

	PIXEL_ON_LINE(201), // Marked with P in diagrams. Normal point, 2 neighbors, cross index of 4
	  
	PIXEL_BORDER(247), //Edge of solid, cross index of 2
	  
	PIXEL_JUNCTION(249), //Junction point, more than cross index of 4
	   
	PIXEL_L_CORNER(251), //L corner, 2 neighbors with modulo distance either 2 or 6, cross index of 4
	
	PIXEL_V_CORNER(253), //A corner, 2 neighbors, cross index of 2, should always be next to a junction
	  
	PIXEL_FOREGROUND_UNKNOWN(255), //Before it is calculated

// Annotations for lines
	NORMAL_LINE(1001),
	
	CIRCLE(1003),

	ARCH(1005),
	
	SPIRAL(1007),
	
	S_SHAPE(1009),
	
	STRAIGHT(1011),
	
	WEAVE(1013),
	;
	
	public final int id; //This is the unused

	GeometricType(int id) {
		this.id = id;
	}

	@Override
	public String getOhName() {
		return this.name();
	}
	
	
}
