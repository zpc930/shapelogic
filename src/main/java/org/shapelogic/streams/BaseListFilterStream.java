package org.shapelogic.streams;


/** This is the abstract class to override to make a ListFilterStream.
 * 
 * The code here is the same as what would be in the BaseFilterStream.
 * 
 * @author Sami Badawi
 *
 * @param <E>
 */
abstract public class BaseListFilterStream<E> extends BaseListStream1<E,E> implements
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
			if (evaluate(input))
				return input;
		}
		return null;
	}
	
	public boolean hasNext() {
		return _inputStream.hasNext(); //XXX not right fix
	}
}
