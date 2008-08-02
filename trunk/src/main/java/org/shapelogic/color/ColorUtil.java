package org.shapelogic.color;

/** Contains all the color utility methods that are missing in ImageJ.<br /> 
 * 
 * @author Sami Badawi
 *
 */
public class ColorUtil {

	public static final int BLUE_MASK = 0xff;
	public static final int GREEN_MASK = 0xff00;
	public static final int RED_MASK = 0xff0000;

	//Saving in this sequence gives the option of saving alpha too
	public static final int RED_POS = 0;
	public static final int GREEN_POS = 1;
	public static final int BLUE_POS = 2;

	public static final int GREEN_OFFSET = 8;
	public static final int RED_OFFSET = 16;

	/** Split color coded as int into 3 int. */
	static public int[] splitColor(int colorIn) {
		int[] iArray = new int[3];
		iArray[RED_POS] = (colorIn&RED_MASK)>>RED_OFFSET; //red
		iArray[GREEN_POS] = (colorIn&GREEN_MASK)>>GREEN_OFFSET; //green
		iArray[BLUE_POS] = colorIn&BLUE_MASK; //blue
		return iArray;
	}

	/** Split color coded as int into 3 int. */
	static public int[] splitColor(int colorIn, int[] iArray) {
		if (iArray == null)
			iArray = new int[3];
		iArray[RED_POS] = (colorIn&RED_MASK)>>RED_OFFSET; //red
		iArray[GREEN_POS] = (colorIn&GREEN_MASK)>>GREEN_OFFSET; //green
		iArray[BLUE_POS] = colorIn&BLUE_MASK; //blue
		return iArray;
	}

	/** Split red from int. */
	static public int splitRed(int colorIn) {
		return (colorIn&RED_MASK)>>RED_OFFSET; //red
	}

	/** Split green from int. */
	static public int splitGreen(int colorIn) {
		return (colorIn&GREEN_MASK)>>GREEN_OFFSET; //green
	}

	/** Split blue from int. */
	static public int splitBlue(int colorIn) {
		return colorIn&BLUE_MASK; //blue
	}
	
	static public int packColors(int red, int green, int blue) {
		int result = (red << RED_OFFSET) + (green << GREEN_OFFSET) + blue;
        return result;
	}

	static public int packColors(int[] colors) {
		return (colors[RED_POS] << RED_OFFSET) + (colors[GREEN_POS] << GREEN_OFFSET) + colors[BLUE_POS];
	}
    
    static public int grayToRGB(int gray) {
        return packColors(gray, gray, gray);
    }
    
    /** Change an RGB color to a gray value.
     * 
     * Based on the perceived contribution to the brightness. 
     *  */
    static public int rgbToGray(int colorIn) {
    	int red = (colorIn&RED_MASK)>>RED_OFFSET; //red
		int green = (colorIn&GREEN_MASK)>>GREEN_OFFSET; //green
		int blue = colorIn&BLUE_MASK; //blue
		double brightness = 0.3*red + 0.59*green + 0.11*blue;
    	return (int)brightness;
    }
}
