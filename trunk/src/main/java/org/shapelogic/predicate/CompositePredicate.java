package org.shapelogic.predicate;

/** All component predicates have to be true.
 * <br />
 * Same functionality as AllPredicate in Apache Commons. 
 * 
 * @author Sami Badawi
 *
 * @param <T>
 */
public abstract class CompositePredicate<T> implements Predicate<T> {
	protected Predicate<T>[] _predicates;
	                    
	public Predicate<T>[] getPredicates(){
		return _predicates;
	}

	@Override
	public abstract boolean evaluate(T input);
}
