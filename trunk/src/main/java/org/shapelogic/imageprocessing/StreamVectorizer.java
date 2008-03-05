package org.shapelogic.imageprocessing;

import ij.process.ImageProcessor;

import org.shapelogic.calculation.RootMap;
import org.shapelogic.streamlogic.LoadLetterStreams;
import org.shapelogic.streamlogic.StreamNames;
import org.shapelogic.streams.NamedNumberedStream;
import org.shapelogic.streams.NumberedStream;

/** Same vectorizer as MaxDistanceVectorizer, but logic implemented with streams.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class StreamVectorizer extends BaseMaxDistanceVectorizer {

	/** This does really not belong in a vectorizer. */
	@Override
	protected void matchLines() {
//		_matchingOH = LetterTaskFactory.matchPolygonToLetterUsingTask(
//				getPolygon(), _cleanedupPolygon, _rulesArrayForLetterMatching);
		NumberedStream<String> letters = new NamedNumberedStream<String>(StreamNames.LETTERS);
		String message = "";
		message += letters.next();
		while (hasNext()) {
			message += "; " + letters.next() ;
		}
		_matchingOH = message;
		if (_matchingOH == null) {
			System.out.println("\n\nLetter matched failed for this:\n" + _cleanedupPolygon);
		}
		showMessage("Letter match result: " + _matchingOH);
		if (1 < _nextCount)
			System.out.println("\n _nextCount = " + _nextCount); 
	}
	
	/** Use this to setup all the needed streams.
	 */
	@Override
	public void init(ImageProcessor ip) {
		RootMap.clear();
		super.init(ip);
//		NumberedStream<Polygon> polygons = new NamedNumberedStreamLazySetup<Polygon>(StreamNames.POLYGONS);
		RootMap.put(StreamNames.POLYGONS, getStream());
		matchSetup(ip);
	}
	
	/** In order to match a different alphabet override this. 
	 */
	public void matchSetup(ImageProcessor ip) {
		LoadLetterStreams.loadLetterStream(null);
		
	}
	
	@Override
	public void run(ImageProcessor ip) {
		init(ip);
//		next();
		matchLines();
	}
}
