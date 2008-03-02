package org.shapelogic.imageprocessing;

import org.shapelogic.logic.LetterTaskFactory;

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
		_matchingOH = LetterTaskFactory.matchPolygonToLetterUsingTask(
				getPolygon(), _cleanedupPolygon, _rulesArrayForLetterMatching);
		if (_matchingOH == null) {
			System.out.println("\n\nLetter matched failed for this:\n" + _cleanedupPolygon);
		}
		showMessage("Letter match result: " + _matchingOH);
	}
}
