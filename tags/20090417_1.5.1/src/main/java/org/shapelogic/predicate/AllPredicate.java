package org.shapelogic.predicate;

/** All component predicates have to be true.
 * <br />
 * Same functionality as AllPredicate in Apache Commons. 
 * 
 * @author Sami Badawi
 *
 * @param <T>
 */
public class AllPredicate<T> extends CompositePredicate<T> {
	
	public AllPredicate(Predicate<T>[] predicates) {
		super(predicates);
	}
	
	@Override
	public boolean evaluate(T input) {
		for (Predicate<T> predicate : _predicates) {
			if (!predicate.evaluate(input))
				return false;
		}
		return true;
	}
}
