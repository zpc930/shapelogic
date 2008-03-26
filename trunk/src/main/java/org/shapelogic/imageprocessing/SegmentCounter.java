package org.shapelogic.imageprocessing;
import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import org.shapelogic.imageprocessing.SBSegmentation;
import org.shapelogic.imageprocessing.SBSimpleCompare;

/** Base class for segmentation and particle counter for 24 bit RGB and 8 bit Gray.
 * <br /> 
 * Might be split up into seperate class for segmentation and particle counter.<br />
 * 
 * Works with rectangular ROIs
 * 
 * @author Sami Badawi
 *
 */
public class SegmentCounter implements PlugInFilter {
	protected boolean _doAll = true;
	
	/** Modifying colors */
	protected boolean _modifying = true;
	protected SBSegmentation _segmentation;
	
	public int setup(String arg, ImagePlus imp) {
		if (arg.equals("about"))
			{showAbout(); return DONE;}
		return DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING;
	}

	public void run(ImageProcessor ip) {
		if (!(ip instanceof ByteProcessor || ip instanceof ColorProcessor))
			return;
		try {
			int startX = ip.getWidth()/2;
			int startY = ip.getHeight()/2;
			SBSimpleCompare compare = SBSimpleCompare.factory(ip);
			compare.grabColorFromPixel(startX, startY);
			compare.setModifying(_modifying);
			_segmentation = new SBSegmentation();
			_segmentation.setImageProcessor(ip);
			_segmentation.setPixelCompare(compare);
			_segmentation.setSegmentAreaFactory(SBSimpleCompare.segmentAreaFactory(ip));
			_segmentation.init();
			if (_doAll)
				_segmentation.segmentAll();
			else
				_segmentation.segment(startX, startY);
			showMessage("About Inverter_...",_segmentation.getStatus());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void showAbout() {
		showMessage("About SBSegment01_...",
			"Segments 24 bit RGB and 8 bit Gray\n" +
			"works with rectangular ROIs\n"
		);
	}

	public void showMessage(String title, String text) {
		if (isGuiEnabled())
			IJ.showMessage(title,text);
		else {
			System.out.println(title);
			System.out.println(text);
		}
	}
	
	public boolean isGuiEnabled() {
		return false;
	}

	public SBSegmentation getSegmentation() {
		return _segmentation;
	}

}


