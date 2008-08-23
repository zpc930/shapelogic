package org.shapelogic.imageprocessing;

import java.util.HashMap;
import java.util.Map;

import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.streamlogic.LoadLetterStreams;
import org.shapelogic.streamlogic.StreamNames;
import org.shapelogic.streams.NumberedStream;
import org.shapelogic.streams.StreamFactory;

/** Same vectorizer as MaxDistanceVectorizer, but logic implemented with streams.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class StreamVectorizer extends BaseMaxDistanceVectorizer implements RecursiveContext {
	protected Map _context = new HashMap();
	protected LoadLetterStreams loadLetterStreams;

	/** This does really not belong in a vectorizer. */
	@Override
	protected void matchLines() {
//		_matchingOH = LetterTaskFactory.matchPolygonToLetterUsingTask(
//				getPolygon(), _cleanedupPolygon, _rulesArrayForLetterMatching);
		NumberedStream<String> letters = StreamFactory.findNumberedStream(StreamNames.LETTERS, this);
		String message = "";
		for (int i = 0; hasNext(); i++)
		{
			String currentMatch = letters.next();
			if (i != 0)
				message += "; ";
			message += currentMatch;
			if (currentMatch == null || "".equals(currentMatch))
				System.out.println("\n\nMatch failed for this:\n" + _cleanedupPolygon);
		}
		_matchingOH = message;
		if (_matchingOH == null) {
			System.out.println("\n\nLetter matched failed for this:\n" + _cleanedupPolygon);
		}
		showMessage("","Letter match result: " + _matchingOH);
	}
	
	/** Use this to setup all the needed streams.
	 */
	@Override
	public void init() {
//		RootMap.clear();
		_context.clear();
		super.init();
//		NumberedStream<Polygon> polygons = new NamedNumberedStreamLazySetup<Polygon>(StreamNames.POLYGONS);
		_context.put(StreamNames.POLYGONS, getStream());
		loadLetterStreams = new LoadLetterStreams(this);
		matchSetup();
	}
	
	/** In order to match a different alphabet override this. 
	 */
	public void matchSetup() {
		loadLetterStreams.loadLetterStream(null);
		
	}
	
	@Override
	public void run() {
		init();
//		next();
		matchLines();
	}

	@Override
	public Map getContext() {
		return _context;
	}

	@Override
	public RecursiveContext getParentContext() {
		return null;
	}
}
