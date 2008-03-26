import org.shapelogic.imageprocessing.ParticleCounter;

/** Automatic particle counter for 24 bit RGB and 8 bit Gray.
 * <br />
 * Currently just a wrapper around segmenter.
 * 
 * Works with rectangular ROIs
 * 
 * @author Sami Badawi
 *
 */
public class ParticleCounter_ extends ParticleCounter {
	
	public boolean isGuiEnabled() {
		return true;
	}

}

