package org.shapelogic.streams;

import static org.shapelogic.util.Constants.LAST_UNKNOWN;


/** This is the abstract class to override to make a ListFilterStream.
 * 
 * The code here is the same as what would be in the BaseFilterStream.
 * 
 * @author Sami Badawi
 *
 * @param <E>
 */
abstract public class BaseListFilterStream<E> extends BaseListIndexedStream1<E,E> implements
		ListFilterStream<E> {
	
	public BaseListFilterStream(ListStream<E> inputStream) {
		_inputStream = inputStream;
	}
	
	@Override
	/** Maybe I could make this the predicate method, so if null is returned 
	 * then don't add anything.
	 * Or this could just be the identity.
	 */
	public E invoke(E input, int index) {
		return input;
	}
	
	public E next() {
		while (_inputStream.hasNext()) {
			E input = _inputStream.next();
			if (evaluate(input)){
				_list.add(input);
				return input;
			}
		}
		return null;
	}
	
	@Override
	public boolean hasNext() {
		return _inputStream.hasNext(); //XXX not right fix
	}
	
	@Override
	public E get(int index) {
		if ( _last != LAST_UNKNOWN && _last < index)
			return null;
		if (index >= _list.size()) {
			for (int i = _list.size(); i <= index; i++) {
				if (hasNext()) {
					E element = next();
				}
				else {
					_last = i - 1;
					_dirty = false;
					return null;
				}
			}
		}
		return _list.get(index);
	}
}
