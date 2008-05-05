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
	
	Boolean _particleImage;
	
	/** Modifying colors */
	protected boolean _modifying = true;
	protected SBSegmentation _segmentation;
	protected boolean _saveArea;
    protected IColorHypothesisFinder _colorHypothesisFinder;
    protected ColorHypothesis _colorHypothesis;
	
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
			_segmentation.segmentAll();
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
            _colorHypothesisFinder = new DistanceBasedColorHypothesisFinder(_arg, _image, 30);
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
			_segmentation.getSegmentAreaFactory().sort();
			List<IColorAndVariance> store = _segmentation.getSegmentAreaFactory().getStore();
			int biggestArea = store.get(store.size()-1).getArea();
			double biggestAreaPercentage = biggestArea * 100 / getSegmentation().getSLImage().getPixelCount();
			_particleImage = biggestAreaPercentage > 50;
		}
		return _particleImage;
	}

    protected boolean countBackground() {
        boolean result = false;
        int backgroundArea = 0;
		List<IColorAndVariance> store = _segmentation.getSegmentAreaFactory().getStore();
        BBox aggregatedBoundingBox = new BBox();
        for (IColorAndVariance area: store) {
            backgroundArea += area.getArea();
            PixelArea pixelArea = area.getPixelArea();
            if (pixelArea != null) {
                aggregatedBoundingBox.add(pixelArea.getBoundingBox());
            }
        }
        int totalImageArea = getSegmentation().getSLImage().getPixelCount();
        double biggestAreaPercentage = backgroundArea * 100 / totalImageArea;
        double boundingBoxPercentage = 
                aggregatedBoundingBox.getDiagonalVector().getX() * 
                aggregatedBoundingBox.getDiagonalVector().getY() * 
                100 / totalImageArea;
        _particleImage =  50 <biggestAreaPercentage && 90 < boundingBoxPercentage;
        _particleImage = result;
        return result;
    }
    
    @Override
	public int getParticleCount() {
		return _segmentation.getSegmentAreaFactory().getStore().size() - 1;
	}

    @Override
    public IColorHypothesisFinder getColorHypothesisFinder() {
        return _colorHypothesisFinder;
    }

    @Override
    public void setColorHypothesisFinder(IColorHypothesisFinder colorHypothesisFinder) {
        _colorHypothesisFinder = colorHypothesisFinder;
    }
}
