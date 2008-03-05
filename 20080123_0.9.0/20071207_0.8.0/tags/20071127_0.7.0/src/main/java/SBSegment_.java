import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import org.shapelogic.imageprocessing.SBSegmentation;
import org.shapelogic.imageprocessing.SBSimpleCompare;

/** Segmentation for 24 bit RGB and 8 bit Gray 
 * 
 * Works with rectangular ROIs
 * 
 * @author Sami Badawi
 *
 */
public class SBSegment_ implements PlugInFilter {
	boolean doAll = true;
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
			SBSegmentation segment = new SBSegmentation();
			segment.setImageProcessor(ip);
			segment.setPixelCompare(compare);
			segment.init();
			if (doAll)
				segment.segmentAll();
			else
				segment.segment(startX, startY);
			IJ.showMessage("About Inverter_...",segment.getStatus());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void showAbout() {
		IJ.showMessage("About SBSegment01_...",
			"Segments 24 bit RGB and 8 bit Gray\n" +
			"works with rectangular ROIs\n"
		);
	}
}

