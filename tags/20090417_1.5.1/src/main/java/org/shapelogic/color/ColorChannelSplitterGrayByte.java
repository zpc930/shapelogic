package org.shapelogic.color;

/** Split a int into 1 colors.<br /> 
 * 
 * @author Sami Badawi
 *
 */
public class ColorChannelSplitterGrayByte implements ColorChannelSplitter {
	public static ColorChannelSplitter INSTANCE = new ColorChannelSplitterGrayByte(); 
	
	private ColorChannelSplitterGrayByte(){
	}
	
	@Override
	public int[] split(int colorIn, int[] iArray) {
		if (iArray == null)
			iArray = new int[1];
		iArray[0] = colorIn;
		return iArray;
	}
	
	public static ColorChannelSplitter getInstance(){
		return INSTANCE;
	}
}
