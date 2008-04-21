package org.shapelogic.imageprocessing;

import java.util.List;

/** ParticleCounter count number of particles in a particle image.
 * <br />
 * 
 * This is really just a slightly modified segmenter.
 * 
 * @author Sami Badawi
 *
 */
public class ParticleCounter extends SegmentCounter {
	
	Boolean _particleImage;
	
	public ParticleCounter()
	{
		super(true);
		_modifying = false;
		_pluginName = "Particle Counter";
	}

	public boolean isParticleImage() {
		if (_particleImage == null) {
			_segmentation.getSegmentAreaFactory().sort();
			List<? extends PixelArea> store = _segmentation.getSegmentAreaFactory().getStore();
			int biggestArea = store.get(store.size()-1).getArea();
			double biggestAreaPercentage = biggestArea * 100 / getSegmentation().getSLImage().getPixelCount();
			_particleImage = biggestAreaPercentage > 50;
		}
		return _particleImage;
	}

	public int getParticleCount() {
		return _segmentation.getSegmentAreaFactory().getStore().size() - 1;
	}

	public String getStatus() {
		String status = super.getStatus();
		String negation = "";
		if (!isParticleImage())
			negation = "not ";
		status += "\nImage is " + negation + "a particle image.";
		if (isParticleImage()) {
			status += "\nParticle count: " + getParticleCount();
		}
		return status;
	}
}
