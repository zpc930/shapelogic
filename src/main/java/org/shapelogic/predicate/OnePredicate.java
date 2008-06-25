package org.shapelogic.predicate;

/** One component predicates have to be true.
 * <br />
 * Same functionality as OnePredicate in Apache Commons.<br /> 
 * 
 * This is an Exclusive OR operation.<br />
 * 
 * @author Sami Badawi
 *
 * @param <T>
 */
public class OnePredicate<T> extends CompositePredicate<T> {

	public OnePredicate(Predicate<T>[] predicates) {
		super(predicates);
	}
	
	@Override
	public boolean evaluate(T input) {
		int success = 0; //success 
		for (Predicate<T> predicate : _predicates) {
			if (predicate.evaluate(input)) {
				success++;
				if (1 < success)
					break;
			}
		}
		return success == 1;
	}
}
