import org.shapelogic.imageprocessing.MaxDistanceVectorizer;
import org.shapelogic.imageutil.PlugInFilterAdapter;

/** Thin wrapper around MaxDistanceVectorizer
 * 
 * Third approach to a vectorizer. This work well.
 * 
 * @author Sami Badawi
 *
 */
public class MaxDistanceVectorizer_ extends PlugInFilterAdapter {
	public MaxDistanceVectorizer_() {
		super(new MaxDistanceVectorizer());
	}
}
