package org.shapelogic.color;

import org.shapelogic.imageutil.SLImage;

/** Factory for different color classes.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorFactory {
	
	/** Private constructor to prevent instantiation of objects. */
	private ColorFactory() {
		
	}
	
	public static IColorRange makeColorRangeI(SLImage image) {
		if (image.isGray()) return new GrayRange();
		if (image.isRgb()) return new ColorRange();
		return null;
	}
	
	public static IColorAndVariance makeColorAndVarianceI(SLImage image) {
		if (image.isGray()) return new GrayAndVariance();
		return null;
	} 
	
	public static int[] makeColorCannels(SLImage image) {
		return new int[image.getNChannels()];
	}
	
	public static ColorChannelSplitter makeColorChannelSplitter(SLImage image) {
		if (image.isGray()) return ColorChannelSplitterGrayByte.getInstance();
		if (image.isRgb())  return ColorChannelSplitterRGB.getInstance();
		return null;
	}
}
