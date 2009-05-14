package org.shapelogic.imageprocessing;

import org.shapelogic.imageutil.SLImage;

/** Factories that belongs under Image Processing, but do not need their own 
 * factory class.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ProcessingFactory {

	public static SBSimpleCompare compareFactory(SLImage image) throws Exception
	{
		SBSimpleCompare result = null;
		if (image.isGray()) {
			if (image.isGray8())
				result = new SBByteCompare();
			else if (image.isGray16())
				result = new SBShortCompare();
			else
				result = new SBByteCompare();
		}
		else if (image.isRgb()) {
			result = new SBColorCompare();
		}
		else {
			System.out.println("Error: could not create SBSimpleCompare. ip.getNChannels()=" + image.getNChannels());
		}
			
		if (result != null) {
			result.init(image);
		}
		return result;
	}

}
