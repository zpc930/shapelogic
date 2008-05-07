package org.shapelogic.imageprocessing;

import java.util.List;

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
 * This is really just a slightly modified segmenter.
 * 
 * @author Sami Badawi
 *
 */
public class BaseParticleCounter extends BaseImageOperation 
        implements IParticleCounter 
{
	
	protected Boolean _particleImage;
	
	/** Modifying colors */
	protected boolean _modifying = true;
	protected SBSegmentation _segmentation;
	protected boolean _saveArea;
    protected IColorHypothesisFinder _colorHypothesisFinder;
    protected ColorHypothesis _colorHypothesis;
	protected int _backgroundArea;
	protected int _backgroundCount;
	protected Integer _particleCount;
	protected double _boundingBoxArea;
    protected int _maxIterations = 2;
    protected double _maxDistance = 50.;
    protected int _minPixelsInArea = 5;
    
	public BaseParticleCounter()
	{
		super(DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING);
		_saveArea = true;
		_modifying = false;
	}

    @Override
	public void run() {
		try {
            init();
            //find the color hypothesis
            _colorHypothesis = _colorHypothesisFinder.findBestColorHypothesis();
            //segment all the background colors, count how maybe connected regions this has
			_segmentation.segmentAll(_colorHypothesis.getBackground().getMeanColor());
            //count how many components and how much area the background takes up
            countBackground();
            //segment all the remaining
            if (_particleImage != null && _particleImage) {
                _segmentation.setMaxDistance(1000);//Everything get lumped together
    			_segmentation.segmentAll();
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
			SBSimpleCompare compare = SBSimpleCompare.factory(getImage());
			compare.setModifying(_modifying);
			_segmentation = new SBSegmentation();
			_segmentation.setSLImage(getImage());
			_segmentation.setPixelCompare(compare);
			if (_saveArea)
				_segmentation.setSegmentAreaFactory(SBSimpleCompare.segmentAreaFactory(getImage()));
			_segmentation.init();
            _colorHypothesisFinder = new DistanceBasedColorHypothesisFinder(_arg, _image, _maxDistance);
            _colorHypothesisFinder.setMaxIterations(_maxIterations);
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
    public int getMaxIterations() {
        return _maxIterations;
    }

    @Override
    public void setMaxIterations(int maxIterations) {
        _maxIterations = maxIterations;
    }
}
