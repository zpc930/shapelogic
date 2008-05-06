package org.shapelogic.imageprocessing;

import java.util.List;

import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.color.IColorHypothesisFinder;
import org.shapelogic.imageutil.BaseImageOperation;

import static org.shapelogic.imageutil.ImageJConstants.*;

/** ParticleCounter count number of particles in a particle image.
 * <br />
 * 
 * This is really just a slightly modified segmenter.
 * 
 * @author Sami Badawi
 *
 */
public class ParticleCounter extends BaseImageOperation 
        implements IParticleCounter 
{
	
	Boolean _particleImage;
	protected boolean _doAll = true;
	
	/** Modifying colors */
	protected boolean _modifying = true;
	protected SBSegmentation _segmentation;
	protected String _pluginName = "Segmenter";
	protected boolean _saveArea;
    protected IColorHypothesisFinder _colorHypothesisFinder;
    protected double _maxDistance = 30.;
	
	public ParticleCounter()
	{
		super(DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING);
		_saveArea = true;
		_modifying = false;
		_pluginName = "Particle Counter";
	}

    @Override
	public void run() {
		try {
			int startX = getImage().getWidth()/2;
			int startY = getImage().getHeight()/2;
			SBSimpleCompare compare = SBSimpleCompare.factory(getImage());
			compare.grabColorFromPixel(startX, startY);
			compare.setModifying(_modifying);
			_segmentation = new SBSegmentation();
			_segmentation.setSLImage(getImage());
			_segmentation.setPixelCompare(compare);
            compare.setMaxDistance((int)_maxDistance);
            _segmentation.setMaxDistance((int)_maxDistance);
			if (_saveArea)
				_segmentation.setSegmentAreaFactory(SBSimpleCompare.segmentAreaFactory(getImage()));
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

    @Override
    public double getMaxDistance() {
        return _maxDistance;
    }
    
    @Override
    public void setMaxDistance(double maxDistance) {
        _maxDistance = maxDistance;
    }
}
