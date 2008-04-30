package org.shapelogic.imageprocessing;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
	private ColorHypothesis _colorHypothesis; // = new SimpleColorHypothesis();
	private ColorHypothesis _lastColorHypothesis;
	private IColorRange _currentColorRange;
	private double _maxDistance;
	private IColorDistance _distance;
	private int[] _colorCannels;
	private PixelAreaHandler _pixelAreaHandler;
	private ColorChannelSplitter _colorChannelSplitter; 
	private int _maxIterations = 1;
    private int _iteration;
    
	public DistanceBasedColorHypothesisFinder(String arg, SLImage image, double maxDistance) {
		_maxDistance = maxDistance;
		if (image != null)
			init(arg,image);
	}
	
	private void init(String arg, SLImage image) {
		_arg = arg;
		_image = image;
		_distance = new ColorDistance1();
		_colorCannels = ColorFactory.makeColorCannels(_image);
		if (_image != null)
			_pixelAreaHandler = new PixelAreaHandler(_image);
		_colorChannelSplitter = ColorFactory.makeColorChannelSplitter(image);
	}
	
	public DistanceBasedColorHypothesisFinder(String arg, String filePath, double maxDistance) {
		this(arg, new SLBufferedImage(filePath), maxDistance);
	}
	
	public DistanceBasedColorHypothesisFinder(String arg, String dir, 
			String fileName, String fileFormat, double maxDistance) {
		this(arg, new SLBufferedImage(dir,fileName,fileFormat), maxDistance);
	}
	
	public DistanceBasedColorHypothesisFinder() {
		this(30.);
	}
	
	public DistanceBasedColorHypothesisFinder(double maxDistance) {
		this(null, (SLImage)null, maxDistance);
	}
    
    @Override
	public ColorHypothesis findBestColorHypothesis() {
        for (_iteration = 0; _iteration < _maxIterations; _iteration++) {
            _colorHypothesis = colorHypothesisIteration(_colorHypothesis);
        }
        findBestBackground(_colorHypothesis);
        return _colorHypothesis;
    }
	
	@Override
	public ColorHypothesis colorHypothesisIteration(
			ColorHypothesis lastColorHypothesis) {
        _lastColorHypothesis = lastColorHypothesis;
        _colorHypothesis = new SimpleColorHypothesis();
		_colorHypothesis.setMaxDistance(_maxDistance);
		if (_pixelAreaHandler == null) 
			return null;
		_pixelAreaHandler.handleAllPixels(this);
                List<IColorAndVariance> colors = (List<IColorAndVariance>) _colorHypothesis.getColors();
                
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
		_colorHypothesis = findBestColorHypothesis();
		if (_colorHypothesis == null) {
			showMessage("Error in " + getClass().getSimpleName(), "Image not set.");
			return;
		}
		String message = "Numbers of color found: " + _colorHypothesis.getColors().size();
		showMessage(getClass().getSimpleName(), message);
	}

	@Override
	public void putPixel(int x, int y, int color) {
		_colorChannelSplitter.split(color,_colorCannels);
		if (_currentColorRange == null || !_currentColorRange.colorInRange(color)) {
			_currentColorRange = null;
            int newColorCenter = color;
			for (IColorAndVariance colorI : _colorHypothesis.getColors()) {
				//XXX cast to range and use center
				if (_distance.distance(_colorCannels,colorI.getColorChannels()) <= _maxDistance) {
					_currentColorRange = (IColorRange) colorI;
					break;
				}
			}
            if (_currentColorRange == null && _lastColorHypothesis != null) {
                for (IColorAndVariance colorJ : _lastColorHypothesis.getColors()) {
                    if (_distance.distance(_colorCannels,colorJ.getColorChannels()) <= _maxDistance) {
                        newColorCenter = ((IColorRange) colorJ).getMeanColor();
                        break;
                    }
                }
            }
			if (_currentColorRange == null) {
				_currentColorRange = ColorFactory.makeColorRangeI(_image);
				_currentColorRange.setColorCenter(newColorCenter);
                _currentColorRange.setMaxDistance(_maxDistance);
				_colorHypothesis.addColor(_currentColorRange);
			}
		}
		_currentColorRange.putPixel(x, y, color);
	}
	
    public void setMaxDistance(double maxDistance){
        _maxDistance = maxDistance;
    }
    
    @Override
    public ColorHypothesis getColorHypothesis() {
        return _colorHypothesis;
    }

    @Override
    public int getMaxIterations() {
        return _maxIterations;
    }

    @Override
    public void setMaxIterations(int maxIterations) {
        _maxIterations = maxIterations;
    }

    @Override
    public boolean verifyColor(IColorRange colorRange) {
        return true;
    }

    @Override
    public int getIteration() {
        return _iteration;
    }

    @Override
    public IColorRange findBestBackground(ColorHypothesis colorHypothesis) {
        List<IColorAndVariance> colorList = null;
        Collection<IColorAndVariance> colorObj = colorHypothesis.getColors();
        if (colorObj instanceof List) {
            colorList = (List<IColorAndVariance>) colorObj;
            Collections.sort(colorList,AreaComparator.INSTANCE);
            IColorAndVariance biggestColor = colorList.get(colorList.size()-1);
            if (_image.getPixelCount() < biggestColor.getArea()*2){
                colorHypothesis.setBackground(biggestColor);
                return (IColorRange) biggestColor;
            }
        }
        return null;
    }

}
