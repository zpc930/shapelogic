package org.shapelogic.imageprocessing;

import org.shapelogic.imageprocessing.SBSegmentation;
import org.shapelogic.imageprocessing.SBSimpleCompare;
import org.shapelogic.imageutil.BaseSLImageFilter;
import org.shapelogic.imageutil.SLImage;

import static org.shapelogic.imageutil.ImageJConstants.*;

/** Base class for segmentation and particle counter for 24 bit RGB and 8 bit Gray.
 * <br /> 
 * Might be split up into seperate class for segmentation and particle counter.<br />
 * 
 * Works with rectangular ROIs
 * 
 * @author Sami Badawi
 *
 */
public class SegmentCounter extends BaseSLImageFilter {
	protected boolean _doAll = true;
	
	/** Modifying colors */
	protected boolean _modifying = true;
	protected SBSegmentation _segmentation;
	protected String _pluginName = "Segmenter";
	protected boolean _saveArea;
	
	public SegmentCounter(boolean saveArea) {
		super(DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING);
		_saveArea = saveArea;
	}
	
	public int setup(String arg, SLImage image) {
		if (arg.equals("about"))
			{showAbout(); return DONE;}
		return super.setup(arg,image);
	}

	public void run() {
//		if (!(ip instanceof ByteProcessor || ip instanceof ColorProcessor))
//			return;
		try {
			int startX = getSLImage().getWidth()/2;
			int startY = getSLImage().getHeight()/2;
			SBSimpleCompare compare = SBSimpleCompare.factory(getSLImage());
			compare.grabColorFromPixel(startX, startY);
			compare.setModifying(_modifying);
			_segmentation = new SBSegmentation();
			_segmentation.setSLImage(getSLImage());
			_segmentation.setPixelCompare(compare);
			if (_saveArea)
				_segmentation.setSegmentAreaFactory(SBSimpleCompare.segmentAreaFactory(getSLImage()));
			_segmentation.init();
			if (_doAll)
				_segmentation.segmentAll();
			else
				_segmentation.segment(startX, startY);
			showMessage(_pluginName, getStatus());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getStatus() {
		return _segmentation.getStatus();
	}

	void showAbout() {
		showMessage(_pluginName,
			"Segments 24 bit RGB and 8 bit Gray\n" +
			"works with rectangular ROIs\n"
		);
	}

	public SBSegmentation getSegmentation() {
		return _segmentation;
	}

}


