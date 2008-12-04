import org.shapelogic.imageprocessing.StreamVectorizer;
import org.shapelogic.imageutil.PlugInFilterAdapter;

/** Thin wrapper around StreamVectorizer.
 * <br />
 * Third approach to a vectorizer. This work well.
 * 
 * @author Sami Badawi
 *
 */
public class StreamVectorizer_ extends PlugInFilterAdapter {
	public StreamVectorizer_() {
		super(new StreamVectorizer());
	}
}
