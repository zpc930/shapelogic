package org.shapelogic.color;

import static org.shapelogic.color.ColorUtil.*;

/** Split a int into 3 colors.<br /> 
 * 
 * @author Sami Badawi
 *
 */
public class ColorChannelSplitterRGB implements ColorChannelSplitter {
	public static ColorChannelSplitter INSTANCE = new ColorChannelSplitterRGB(); 

	@Override
	public int[] split(int colorIn, int[] iArray) {
		if (iArray == null)
			iArray = new int[3];
		iArray[RED_POS] = (colorIn&RED_MASK)>>RED_OFFSET; //red
		iArray[GREEN_POS] = (colorIn&GREEN_MASK)>>GREEN_OFFSET; //green
		iArray[BLUE_POS] = colorIn&BLUE_MASK; //blue
		return iArray;
	}

	static public ColorChannelSplitter getInstance(){
		return INSTANCE;
	}
}
