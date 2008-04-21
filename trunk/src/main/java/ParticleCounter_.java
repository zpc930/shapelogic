import org.shapelogic.imageprocessing.ParticleCounter;
import org.shapelogic.imageutil.PlugInFilterAdapter;

/** Automatic particle counter for 24 bit RGB and 8 bit Gray.
 * <br />
 * Currently just a wrapper around segmenter.
 * 
 * Works with rectangular ROIs
 * 
 * @author Sami Badawi
 *
 */
public class ParticleCounter_ extends PlugInFilterAdapter {
	
	public ParticleCounter_() {
		super(new ParticleCounter());
	}
}

