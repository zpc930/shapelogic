package org.shapelogic.predicate;

/** All component predicates have to be true.
 * <br />
 * Same functionality as AnyPredicate in Apache Commons. 
 * 
 * @author Sami Badawi
 *
 * @param <T>
 */
public class AnyPredicate<T> extends CompositePredicate<T> {
	                    
	public AnyPredicate(Predicate<T>[] predicates) {
		super(predicates);
	}
	
	@Override
	public boolean evaluate(T input) {
		for (Predicate<T> predicate : _predicates) {
			if (predicate.evaluate(input))
				return true;
		}
		return false;
	}
}
