package org.shapelogic.predicate;

/** One component predicates have to be true.
 * <br />
 * Same functionality as OnePredicate in Apache Commons. 
 * 
 * @author Sami Badawi
 *
 * @param <T>
 */
public class OnePredicate<T> extends CompositePredicate<T> {

	public OnePredicate(Predicate<T>[] predicates) {
		_predicates = predicates;
	}
	
	@Override
	public boolean evaluate(T input) {
		int success = 0; //success 
		for (Predicate<T> predicate : _predicates) {
			if (predicate.evaluate(input))
				success++;
		}
		return success == 1;
	}
}
