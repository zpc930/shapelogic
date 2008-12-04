import org.shapelogic.imageprocessing.SegmentCounter;
import org.shapelogic.imageutil.PlugInFilterAdapter;

/** Segmentation for 24 bit RGB and 8 bit Gray 
 * 
 * Works with rectangular ROIs
 * 
 * @author Sami Badawi
 *
 */
public class SBSegment_ extends PlugInFilterAdapter {
	
	public SBSegment_() {
		super(new SegmentCounter(false));
	}
}

