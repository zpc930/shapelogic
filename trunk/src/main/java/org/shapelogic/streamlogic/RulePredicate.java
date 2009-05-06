package org.shapelogic.streamlogic;

/** Simple data class for a rule that is a predicate with 4 inputs.<br />
 * 
 * How should this be represented in a text file?<br />
 * 
 * A: POINT_COUNT == 5 <br />
 * 
 * A POINT_COUNT == 5 <br />
 * 
 * A POINT_COUNT == 5; <br />
 * 
 * A: POINT_COUNT == 5; LINE_COUNT = 5 <br />
 * 
 * ! A POINT_COUNT == 5; LINE_COUNT = 5 <br />
 * 
 * A POINT_COUNT == 5; LINE_COUNT = 5 <br />
 * 
 * def A POINT_COUNT == 5; LINE_COUNT = 5 <br />
 * 
 * ========== def A 
 * POINT_COUNT == 5 LINE_COUNT == 5 <br />
 * HOLE_COUNT == 1 <br />
 * 
 * ===== def A
 * POINT_COUNT == 5 && LINE_COUNT == 5 <br />
 * || HOLE_COUNT == 1 <br />
 * 
 * @author Sami Badawi
 *
 */
public final class RulePredicate {
	
	public RulePredicate(String matchName, String streamName, String predicate, double value) {
		this.matchName = matchName;
		this.streamName = streamName;
		this.predicate = predicate;
		this.value = value;
	}
	
	public final String matchName; // Object Hypothesis 
	public final String streamName; 
	public final String predicate; 
	public final double value;
}
