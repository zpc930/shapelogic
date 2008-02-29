package org.shapelogic.imageprocessing;

import java.util.Arrays;
import java.util.List;

import org.shapelogic.entities.NumericRule;
import org.shapelogic.logic.BaseTask;
import org.shapelogic.logic.LetterTaskFactory;
import org.shapelogic.logic.RootTask;

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
		RootTask rootTask = RootTask.getInstance();
		rootTask.setNamedValue(RAW_POLYGON, getPolygon());
		rootTask.setNamedValue(POLYGON, _cleanedupPolygon);
		List<NumericRule> rulesList = Arrays.asList(_rulesArrayForLetterMatching);
		BaseTask letterTask = LetterTaskFactory.createLetterTasksFromRule(rootTask, rulesList, null);
		_matchingOH = letterTask.invoke();
		if (_matchingOH == null) {
			System.out.println("\n\nLetter matched failed for this:\n" + _cleanedupPolygon);
		}
		showMessage("Letter match result: " + _matchingOH);
	}
}
