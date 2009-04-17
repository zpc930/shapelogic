package org.shapelogic.predicate;

/** NamedPredicate is a predicate with and OH name, Object Hypothesis name.
 * <br />
 * This is used for XOR or OnePredicate. You want to know what predicate that 
 * was satisfied.
 * 
 * @author Sami Badawi
 *
 * @param <E>
 */
public class NamedPredicateDecorator<T> implements NamedPredicate<T>{
	
	protected final Predicate<T> _predicate;
	protected final String _ohName;
	public NamedPredicateDecorator(Predicate<T> predicate, String ohName) {
		_predicate = predicate;
		_ohName = ohName;
	}
	
	@Override
	public boolean evaluate(T input) {
		return _predicate.evaluate(input);
	}

	@Override
	public String getOhName() {
		return _ohName;
	}

}
