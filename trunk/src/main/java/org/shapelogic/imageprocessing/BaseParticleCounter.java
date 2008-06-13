package org.shapelogic.imageprocessing;

import java.awt.Rectangle;
import java.util.List;

import org.shapelogic.color.ColorFactory;
import org.shapelogic.color.ColorHypothesis;
import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.color.IColorHypothesisFinder;
import org.shapelogic.imageutil.BaseImageOperation;

import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.polygon.BBox;
import static org.shapelogic.imageutil.ImageJConstants.*;

/** ParticleCounter count number of particles in a particle image.
 * <br />
 * 
 * This is using the SBSegmentation that is also used by Segmenter.<br />
 * 
 * In this base implementation:<br />
 * Find a color hypothesis.<br />
 * Set everything in the background color into one segment.<br />
 * Segment the rest into normal segments regardless of color.<br />
 * 
 * If this is overridden then there can be more segments in the background color.<br />
 * 
 * How do I know what is background colored?<br />
 * 
 * @author Sami Badawi
 *
 */
public class BaseParticleCounter extends BaseImageOperation 
        implements IParticleCounter 
{
	//These defaults are not fine tuned yet
    final static protected int ITERATIONS_DEFAULTS = 2;
    final static protected double MAX_DISTANCE_DEFAULTS = 50.;
    final static protected int MIN_PIXELS_IN_AREA_DEFAULTS = 5;
	
	protected Boolean _particleImage;
	
	/** Modifying colors */
	protected boolean _modifying = false;
	protected SBSegmentation _segmentation;

	/** Create a IColorAndVariance, area for each particle. */
	protected boolean _saveArea = true;
    protected IColorHypothesisFinder _colorHypothesisFinder;
    protected ColorHypothesis _colorHypothesis;
	protected int _backgroundArea;
	protected int _backgroundCount;
	protected Integer _particleCount;
	protected double _boundingBoxArea;
	
    protected int _iterations = ITERATIONS_DEFAULTS;
    protected double _maxDistance = MAX_DISTANCE_DEFAULTS;
    protected int _minPixelsInArea = MIN_PIXELS_IN_AREA_DEFAULTS;
    
	public BaseParticleCounter()
	{
		super(DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING);
	}

    @Override
	public void run() {
		try {
            init();
            //find the color hypothesis
            _colorHypothesis = _colorHypothesisFinder.findBestColorHypothesis();
            //segment all the background colors, count how maybe connected regions this has
            if (_colorHypothesis.getBackground() == null) {
                _particleImage = Boolean.FALSE;
            }
            else {
                _segmentation.segmentAll(_colorHypothesis.getBackground().getMeanColor());
                //count how many components and how much area the background takes up
                countBackground();
                //segment all the remaining
                if (_particleImage != null && _particleImage) {
                    _segmentation.setMaxDistance(1000000000);//Everything get lumped together
                    _segmentation.segmentAll();
                }
            }
			showMessage(getClass().getSimpleName(), getStatus());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
    /** Setup all the needed factory methods based on what type the image has.
     * 
     * @throws java.lang.Exception
     */
    protected void init() throws Exception {
			SBSimpleCompare compare = ColorFactory.factory(getImage());
			compare.setModifying(_modifying);
			_segmentation = new SBSegmentation();
			_segmentation.setSLImage(getImage());
			_segmentation.setPixelCompare(compare);
			if (_saveArea)
				_segmentation.setSegmentAreaFactory(ColorFactory.segmentAreaFactory(getImage()));
			_segmentation.init();
            _colorHypothesisFinder = new DistanceBasedColorHypothesisFinder(_arg, _image, _maxDistance);
            _colorHypothesisFinder.setIterations(_iterations);
    }
	
    @Override
	public String getStatus() {
		String status = _segmentation.getStatus();
		String negation = "";
		if (!isParticleImage())
			negation = "not ";
		status += "\nImage is " + negation + "a particle image.";
		if (isParticleImage()) {
			status += "\nParticle count: " + getParticleCount();
            status += "\nBackground color is: " + _colorHypothesis.getBackground().toString();
            status += "\nNumber of colors is: " + _colorHypothesis.getColors().size();
		}
		return status;
	}

    @Override
	public SBSegmentation getSegmentation() {
		return _segmentation;
	}

    /** This is not fine tuned. */
    @Override
	public boolean isParticleImage() {
		if (_particleImage == null) {
        double totalImageArea = getImageArea();
        double biggestAreaPercentage = _backgroundArea * 100 / totalImageArea;
        double boundingBoxPercentage = _boundingBoxArea * 100 / totalImageArea;
        _particleImage =  50 < biggestAreaPercentage && 
                90 < boundingBoxPercentage;
		}
		return _particleImage;
	}

    /** Count background pixels.<br/>
     *  
     *  Should be called when only background have been segmented.<br/>
     *  Not sure that this really makes sense, or I can assume that there is always 1 background.<br/>
     *  
     * */
    protected boolean countBackground() {
        boolean result = false;
    	_backgroundArea = 0;
		List<IColorAndVariance> store = _segmentation.getSegmentAreaFactory().getStore();
        BBox aggregatedBoundingBox = new BBox();
        for (IColorAndVariance area: store) {
            _backgroundArea += area.getArea();
            PixelArea pixelArea = area.getPixelArea();
            if (pixelArea != null) {
                aggregatedBoundingBox.add(pixelArea.getBoundingBox());
            }
        }
        _backgroundCount = _segmentation.getSegmentAreaFactory().areasGreaterThan(_minPixelsInArea);
        _boundingBoxArea = 
                (aggregatedBoundingBox.getDiagonalVector().getX() +1) * 
                (aggregatedBoundingBox.getDiagonalVector().getY() + 1);
        result = isParticleImage();
        return result;
    }
    
    public double getImageArea() {
        Rectangle rectangle = getImage().getRoi();
        if (rectangle != null)
            return rectangle.getWidth() * rectangle.getHeight();
        else
            return getImage().getPixelCount();
    }
    
    @Override
	public int getParticleCount() {
        if (_particleCount == null) {
            _segmentation.getSegmentAreaFactory().sort();
            List<IColorAndVariance> store = 
                    _segmentation.getSegmentAreaFactory().getStore();
            int totalCount = _segmentation.getSegmentAreaFactory().areasGreaterThan(_minPixelsInArea);
            _particleCount = totalCount - _backgroundCount;
        }
        return _particleCount;
	}

    @Override
    public IColorHypothesisFinder getColorHypothesisFinder() {
        return _colorHypothesisFinder;
    }

    @Override
    public void setColorHypothesisFinder(IColorHypothesisFinder colorHypothesisFinder) {
        _colorHypothesisFinder = colorHypothesisFinder;
    }
    
    @Override
    public double getMaxDistance() {
        return _maxDistance;
    }
    
    @Override
    public void setMaxDistance(double maxDistance) {
        _maxDistance = maxDistance;
    }

    @Override
    public int getMinPixelsInArea() {
        return _minPixelsInArea;
    }

    @Override
    public void setMinPixelsInArea(int minPixelsInArea) {
        _minPixelsInArea = minPixelsInArea;
    }

    @Override
    public int getIterations() {
        return _iterations;
    }

    @Override
    public void setIterations(int iterations) {
        _iterations = iterations;
    }
}
