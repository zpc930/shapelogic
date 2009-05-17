package org.shapelogic.streams;

import org.shapelogic.predicate.Predicate;

/** For ListFilterStream that just takes an input predicate. <br />
 * 
 * @author Sami Badawi
 *
 * @param <E>
 */
public class PredicateListFilterStream<E> extends BaseListFilterStream<E> 
{
	final protected Predicate<E> _predicate;
	
	public PredicateListFilterStream(NumberedStream<E> inputStream, Predicate<E> predicate) {
		super(inputStream);
		_predicate = predicate;
	}
	
	/** Evaluate the component predicates. */
	@Override
	public boolean evaluate(E input) {
		return _predicate.evaluate(input);
	}
	
	public Predicate<E> getPredicate() {
		return _predicate;
	}
	
}
