package org.shapelogic.predicate;

import org.shapelogic.calculation.Transformer;

/** OnePredicateSelector is an XOR or One Predicate where you can also see 
 * which predicate returned true.
 * <br />
 * 
 * @author Sami Badawi
 *
 * @param <T>
 */
public class OnePredicateSelector<T> extends OnePredicate<T> implements Transformer<T, String> {
	final protected NamedPredicate<T>[] _namedPredicates; //Same a _predicates
	
	public OnePredicateSelector(NamedPredicate<T>[] predicates) {
		super(predicates);
		_namedPredicates = predicates;
	}
	
	@Override
	public String transform(T input) {
		int success = 0; 
		String ohName = null;
		for (NamedPredicate<T> predicate : _namedPredicates) {
			if (predicate.evaluate(input)) {
				success++;
				if (1 < success)
					break;
				ohName = predicate.getOhName();
			}
		}
		if (1 < success)
			return null;
		return ohName;
	}
}
