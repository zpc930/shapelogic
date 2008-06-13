package org.shapelogic.imageprocessing;

import org.shapelogic.color.ColorFactory;
import org.shapelogic.imageutil.BaseImageOperation;

import static org.shapelogic.imageutil.ImageJConstants.*;

/** Segmentation for 24 bit RGB and 8 bit Gray.
 * <br /> 
 * No longer used as base class for particle counter.<br /> 
 * Might be renamed.<br />
 * 
 * Works with rectangular ROIs
 * 
 * @author Sami Badawi
 *
 */
public class SegmentCounter extends BaseImageOperation {
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
	
	public void run() {
//		if (!(ip instanceof ByteProcessor || ip instanceof ColorProcessor))
//			return;
		try {
			int startX = getImage().getWidth()/2;
			int startY = getImage().getHeight()/2;
			SBSimpleCompare compare = ColorFactory.factory(getImage());
			compare.grabColorFromPixel(startX, startY);
			compare.setModifying(_modifying);
			_segmentation = new SBSegmentation();
			_segmentation.setSLImage(getImage());
			_segmentation.setPixelCompare(compare);
			if (_saveArea)
				_segmentation.setSegmentAreaFactory(ColorFactory.segmentAreaFactory(getImage()));
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

	public SBSegmentation getSegmentation() {
		return _segmentation;
	}

}


