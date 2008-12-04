import org.shapelogic.imageprocessing.DistanceBasedColorHypothesisFinder;
import org.shapelogic.imageutil.PlugInFilterAdapter;

/** ColorHypothesisFinder_ finds color hypothesis.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorHypothesisFinder_  extends PlugInFilterAdapter {
	public ColorHypothesisFinder_() {
		super(new DistanceBasedColorHypothesisFinder());
	}
}
