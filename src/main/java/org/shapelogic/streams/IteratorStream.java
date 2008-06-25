package org.shapelogic.streams;

import static org.shapelogic.util.Constants.LAST_UNKNOWN;

import java.util.Iterator;

/** Stream based on Iterator that can be set.<br />
 * 
 * @author Sami Badawi
 *
 */
public class IteratorStream<E> extends BaseListCommonStream<E> {
	
	protected Iterator<E> _iterator;
	
	public IteratorStream(Iterator<E> iterator, int maxLast) {
		_iterator = iterator;
		setMaxLast(maxLast);
	}

	public IteratorStream(Iterator<E> iterator) {
		_iterator = iterator;
	}

	@Override
	public E invokeIndex(int index) {
		return _iterator.next();
	}

	@Override
	public boolean hasNext() {
		if ((_last != LAST_UNKNOWN && _last <= _current )) {
			return false;
		}
		return _iterator.hasNext();
	}
	
	public Iterator<E> getIterator() {
		return _iterator;
	}

	public void setIterator(Iterator<E> iterator) {
		_iterator = iterator;
	}

}
