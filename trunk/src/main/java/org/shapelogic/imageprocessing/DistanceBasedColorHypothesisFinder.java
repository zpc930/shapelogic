package org.shapelogic.imageprocessing;

import org.shapelogic.color.ColorChannelSplitter;
import org.shapelogic.color.ColorDistance1;
import org.shapelogic.color.ColorFactory;
import org.shapelogic.color.ColorHypothesis;
import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.color.IColorDistance;
import org.shapelogic.color.IColorHypothesisFinder;
import org.shapelogic.color.IColorRange;
import org.shapelogic.color.SimpleColorHypothesis;
import org.shapelogic.imageutil.BaseImageOperation;
import org.shapelogic.imageutil.PixelAreaHandler;
import org.shapelogic.imageutil.PixelHandler;
import org.shapelogic.imageutil.SLBufferedImage;
import org.shapelogic.imageutil.SLImage;

/** Find a color hypothesis based on just distance of colors. <br />
 * 
 * Should this be a BaseImageOperation?<br />
 * 
 * What should go on in run and what in findBestColorHypothesis()?<br /> 
 * 
 * findBestColorHypothesis() should be the workhorse.<br />
 * 
 * What should I do about color and gray, should there be a separate class for each?<br />
 * 
 * I would think that maybe 
 * 
 * @author Sami Badawi
 *
 */
public class DistanceBasedColorHypothesisFinder extends BaseImageOperation
implements IColorHypothesisFinder, PixelHandler {
	protected ColorHypothesis _colorHypothesis = new SimpleColorHypothesis();
	protected IColorRange _currentColorRange;
	protected double _tolerance;
	protected IColorDistance _distance;
	protected int[] _colorCannels;
	protected PixelAreaHandler _pixelAreaHandler;
	protected ColorChannelSplitter _colorChannelSplitter; 
	
	public DistanceBasedColorHypothesisFinder(String arg, SLImage image, double tolerance) {
		_tolerance = tolerance;
		if (image != null)
			init(arg,image);
	}
	
	protected void init(String arg, SLImage image) {
		_arg = arg;
		_image = image;
		_colorHypothesis.setGlobalTolerance(_tolerance);
		_distance = new ColorDistance1();
		_colorCannels = ColorFactory.makeColorCannels(_image);
		if (_image != null)
			_pixelAreaHandler = new PixelAreaHandler(_image);
		_colorChannelSplitter = ColorFactory.makeColorChannelSplitter(image);
	}
	
	public DistanceBasedColorHypothesisFinder(String arg, String filePath, double tolerance) {
		this(arg, new SLBufferedImage(filePath), tolerance);
	}
	
	public DistanceBasedColorHypothesisFinder(String arg, String dir, 
			String fileName, String fileFormat, double tolerance) {
		this(arg, new SLBufferedImage(dir,fileName,fileFormat), tolerance);
	}
	
	public DistanceBasedColorHypothesisFinder() {
		this(30.);
	}
	
	public DistanceBasedColorHypothesisFinder(double tolerance) {
		this(null, (SLImage)null, tolerance);
	}
	
	@Override
	public ColorHypothesis findBestColorHypothesis(
			ColorHypothesis lastColorHypothesis) {
		if (_pixelAreaHandler == null) 
			return null;
		_pixelAreaHandler.handleAllPixels(this);
		return _colorHypothesis;
	}
	
	@Override
	public int setup(String arg, SLImage image){
		if (_pixelAreaHandler == null && image != null)
			init(arg,image);
		return super.setup(arg, image);
	}
	

	@Override
	public void run() {
		_colorHypothesis = findBestColorHypothesis(null);
		if (_colorHypothesis == null) {
			showMessage("Error in " + getClass().getSimpleName(), "Image not set.");
			return;
		}
		String message = "Numbers of color found: " + _colorHypothesis.getColors().size();
		showMessage(getClass().getSimpleName(), message);
	}

	@Override
	public void putPixel(int x, int y, int color) {
		if (_currentColorRange == null || !_currentColorRange.colorInRange(color)) {
			_currentColorRange = null;
			for (IColorAndVariance colorI : _colorHypothesis.getColors()) {
				_colorChannelSplitter.split(color,_colorCannels);
				//XXX cast to range and use center
				if (_distance.distance(_colorCannels,colorI.getColorChannels()) <= _tolerance) {
					_currentColorRange = (IColorRange) colorI;
					break;
				}
			}
			if (_currentColorRange == null) {
				_currentColorRange = ColorFactory.makeColorRangeI(_image);
			}
		}
		_currentColorRange.putPixel(x, y, color);
	}
	
}
