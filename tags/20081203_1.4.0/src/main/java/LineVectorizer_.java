import org.shapelogic.imageprocessing.LineVectorizer;
import org.shapelogic.imageutil.PlugInFilterAdapter;

/** Thin wrapper around DirectionBasedVectorizer
 * 
 * First approach to a vectorizer. This does not work as well as
 * MaxDistanceVectorizer. So it is recommended to use that instead for now.
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public class LineVectorizer_ extends PlugInFilterAdapter {
	
	public LineVectorizer_() {
		super(new LineVectorizer());
	}

}
