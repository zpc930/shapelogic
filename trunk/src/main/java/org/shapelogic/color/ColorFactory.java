package org.shapelogic.color;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

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
	
	public static IColorDistance makeColorDistance(SLImage image) {
		if (image.isGray()) return new ColorDistance1();
		if (image.isRgb())  return  new ColorDistance1RGB();
		return null;
	}

	public static IColorDistanceWithImage makeColorDistanceWithImage(SLImage image) {
		IColorDistanceWithImage result;
		if (image.isGray()) {
			result = new ColorDistanceWithImage1();
		}
		else if (image.isRgb()) { 
			result = new ColorDistanceWithImage1RGB();
		}
		else
			return null;
		result.setImage(image);
		return result;
	}

	public static ValueAreaFactory segmentAreaFactory(ImageProcessor ip) throws Exception
	{
		ValueAreaFactory result = null;
		if (ip instanceof ByteProcessor) {
			result = new GrayAreaFactory();
		}
		else if (ip instanceof ColorProcessor) {
			result = new ColorAreaFactory();
		}
		return result;
	}

	public static ValueAreaFactory segmentAreaFactory(SLImage image) throws Exception
	{
		ValueAreaFactory result = null;
		int channels = image.getNChannels(); 
		if (image.isGray()) {
			result = new GrayAreaFactory();
		}
		else if (image.isRgb()) {
			result = new ColorAreaFactory();
		}
		else {
			System.out.println("Error: could not create PixelAreaFactory. image.getNChannels()=" + image.getNChannels());
		}
		return result;
	}
}
