package org.shapelogic.calculation;

import java.util.Iterator;

/** Creates a stream based on an iterator.
 * 
 * @author Sami Badawi
 *
 * @param <E> Element of the iterator and stream.
 */
public class IteratorStream<E> extends BaseStream<E> {
	public static final int NO_STOP = -2;
	
	/** To prevent runaway calculations. */
	public static final int DEFAULT_STOP = 100;
	protected Iterator<E> _iterator;
	protected int _stop = NO_STOP;
	
	public IteratorStream(Iterator<E> iterator, int stop) {
		_iterator = iterator;
		_stop = stop;
	}
	
	public IteratorStream(Iterator<E> iterator) {
		this(iterator, NO_STOP);
	}
	
	/** This would only work for an element that is one higher than the 
	 * current last element.
	 * I should put a check in for this.
	 */
	@Override
	public E calcElement(int index) {
		if (_stop != NO_STOP && _stop < _list.size()-1)
			return null;
		if (_iterator.hasNext())
			return _iterator.next();
		return null;
	}
	
	@Override
	public boolean hasNext() {
		return !(_stop != NO_STOP && _stop < _list.size()); 
	}
}
