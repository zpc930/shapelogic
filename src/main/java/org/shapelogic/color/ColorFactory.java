package org.shapelogic.color;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import org.shapelogic.imageprocessing.SBByteCompare;
import org.shapelogic.imageprocessing.SBColorCompare;
import org.shapelogic.imageprocessing.SBSimpleCompare;
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

	public static SBSimpleCompare factory(SLImage ip) throws Exception
	{
		SBSimpleCompare result = null;
		int channels = ip.getNChannels();
		if (ip.isGray()) {
			result = new SBByteCompare();
		}
		else if (ip.isRgb()) {
			result = new SBColorCompare();
		}
		else {
			System.out.println("Error: could not create SBSimpleCompare. ip.getNChannels()=" + ip.getNChannels());
		}
			
		if (result != null) {
			result.init(ip);
		}
		return result;
	}
}
