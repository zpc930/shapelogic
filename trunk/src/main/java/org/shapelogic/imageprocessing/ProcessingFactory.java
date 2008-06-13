package org.shapelogic.imageprocessing;

import org.shapelogic.imageutil.SLImage;

/** Factories that belongs under Image Processing, but do not need their own 
 * factory class.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ProcessingFactory {

	public static SBSimpleCompare compareFactory(SLImage ip) throws Exception
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
