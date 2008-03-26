package org.shapelogic.imageprocessing;

/** ParticleCounter count number of particles in a particle image.
 * <br />
 * 
 * This is really just a slightly modified segmenter.
 * 
 * @author Sami Badawi
 *
 */
public class ParticleCounter extends SegmentCounter {
	
	public ParticleCounter()
	{
		super();
		_modifying = false;
	}

}
