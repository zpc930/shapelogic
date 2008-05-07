package org.shapelogic.imageprocessing;

/** ParticleCounter count number of particles in a particle image.
 * <br />
 * 
 * There should be many different implementations of ParticleCounter.<br />
 * Maybe of the should be subclassed from BaseParticleCounter.<br />
 * This one just override a few default values.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ParticleCounter extends BaseParticleCounter
        implements IParticleCounter 
{
    public ParticleCounter() {
        super();
        _iterations = 3;
        _maxDistance = 70;
        _minPixelsInArea = 10;
    }
    
}
