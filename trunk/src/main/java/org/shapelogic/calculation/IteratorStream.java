package org.shapelogic.calculation;

import java.util.Iterator;

/** Creates a stream based on an iterator.
 * 
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 * @param <E> Element of the iterator and stream.
 *
 */
@Deprecated
public class IteratorStream<E> extends BaseStream<E> {
	/** To prevent runaway calculations. */
	public static final int DEFAULT_STOP = 100;
	protected Iterator<E> _iterator;
	
	public IteratorStream(Iterator<E> iterator, int maxLast) {
		_iterator = iterator;
		_maxLast = maxLast;
	}
	
	public IteratorStream(Iterator<E> iterator) {
		this(iterator, LAST_UNKNOWN);
	}
	
	/** This would only work for an element that is one higher than the 
	 * current last element.
	 * I should put a check in for this.
	 */
	@Override
	public E calcElement(int index) {
		if (_iterator.hasNext())
			return _iterator.next();
		return null;
	}

}
