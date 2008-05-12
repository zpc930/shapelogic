package org.shapelogic.imageprocessing;

import org.shapelogic.util.Constants;

/** Enum with types for annotating pixels.  
 * 
 * This is classification of all points on the foreground.
 * 
 * How should these be ordered?
 * I think maybe in priority sequence.
 * And in numeric order too.
 * 
 * What about the numbers?
 * The last bit is a used bit
 * 
 * I do not think that an enum is ordered in Java 6
 * 
 * What version of the color should I set?
 * If I use the foreground as a guide then 
 * Let a 1 mean unused and set the unused
 * 
 * @author Sami Badawi
 *
 */
public enum PixelType implements Comparable<PixelType>{

	BACKGROUND_POINT((byte)0), //Background point, , cross index of 0

	PIXEL_LINE_END((byte)237), // Marked with E in diagrams. Normal point, 1 neighbors, cross index of 2
	  
	PIXEL_SINGLE_POINT((byte)239), //Single point, , cross index of 0

	PIXEL_SOLID((byte)241), //Inner point, 8 neighbors or 7 where the last on is an even number.

	PIXEL_EXTRA_NEIGHBOR((byte)199), //More neighbors, more than 2 neighbors, cross index of 4

	PIXEL_ON_LINE((byte)201), // Marked with P in diagrams. Normal point, 2 neighbors, cross index of 4
	  
	PIXEL_BORDER((byte)247), //Edge of solid, cross index of 2
	  
	PIXEL_JUNCTION((byte)249), //Junction point, more than cross index of 4
	   
	PIXEL_L_CORNER((byte)251), //L corner, 2 neighbors with modulo distance either 2 or 6, cross index of 4
	
	PIXEL_V_CORNER((byte)253), //A corner, 2 neighbors, cross index of 2, should always be next to a junction
	  
	PIXEL_FOREGROUND_UNKNOWN((byte)255); //Before it is calculated
	
	public final byte color; //This is the unused
	static private final byte UNUSED_BIT = 1; //
	static private final byte IGNORE_UNUSED_BIT_MASK = -2; //
	
	PixelType(byte color) {
		this.color = color;
	}
	
	static public PixelType getPixelType(byte input) {
		if (input == 0)
			return BACKGROUND_POINT;
		else
			input |= UNUSED_BIT;
		for (PixelType pixelType: PixelType.values()){
			if (pixelType.color == input) {
				return pixelType;
			}
		}
		return PIXEL_FOREGROUND_UNKNOWN;
	}
	
	public byte getColorUsed() {
		int result = color & IGNORE_UNUSED_BIT_MASK;
		return (byte) result;
	}

	public boolean equalsIgnore(byte input) {
		return (color == input) || 
		((color & IGNORE_UNUSED_BIT_MASK) == (input & IGNORE_UNUSED_BIT_MASK));
	}
	
	/** Change a byte to unused version. 
	 * 
	 * That is change the last bit to be 1, except for background that only have one form */
	public static byte toUnused(byte input) {
		if (input == 0)
			return input;
		return (byte) (input | UNUSED_BIT);
	}

	/** Change a byte to unused version. 
	 * 
	 * That is change the last bit to be 1, except for background that only have one form */
	public static byte toUnused(PixelType input) {
		return toUnused(input.color);
	}

	/** Change a byte to used version. 
	 * 
	 * That is change the last bit to be 0 */
	public static byte toUsed(byte input) {
		return (byte) (input & IGNORE_UNUSED_BIT_MASK);
	} 

	public static byte toUsed(PixelType input) {
		return toUsed(input.color);
	}

	public static boolean isUsed(byte input) {
		return (input & UNUSED_BIT) == 0;
	} 

	public static boolean isUnused(byte input) {
		return (input & UNUSED_BIT) != 0;
	} 
	
	public int nonNegativeId() {
		return color & Constants.BYTE_MASK;
	}
}
