package org.shapelogic.predicate;

/** Predicate that always return true.
 * <br />
 * Same name as Apache Commons.
 * 
 * @author sbadawi
 *
 */
public class TruePredicate implements Predicate {
	public static TruePredicate TRUE_PREDICATE = new TruePredicate(); 
	
	@Override
	public boolean evaluate(Object input) {
		return true;
	}

}
