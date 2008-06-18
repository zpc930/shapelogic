package org.shapelogic.streams;

import org.shapelogic.calculation.RootMap;

/** Get a Named Stream out of the context.
 * 
 * <p>The idea is that there should be a stream already and this will create a 
 * thin wrapper around it, so now there will be an extra stream, this serves no 
 * purpose.</p>
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public class NamedNumberedStream<E> implements NumberedStream<E> {
	protected String _key;
	protected NumberedStream<E> _inputStream; 
	
	@Deprecated
	public NamedNumberedStream(String key) {
		_key = key;
		_inputStream = (NumberedStream) RootMap.get(_key);
	}
	
	static public NumberedStream getInstance(String key) {
		Object obj = RootMap.get(key);
		if (obj instanceof NumberedStream)
			return (NumberedStream) obj;
		return null;
	}

	@Override
	public E get(int input) {
		return _inputStream.get(input);
	}

	@Override
	public int getIndex() {
		return _inputStream.getIndex();
	}

	@Override
	public int getLast() {
		return _inputStream.getLast();
	}

	@Override
	public int getMaxLast() {
		return _inputStream.getMaxLast();
	}

	@Override
	public void setMaxLast(int maxLast) {
		_inputStream.setMaxLast(maxLast);
	}

	@Override
	public boolean isDirty() {
		return _inputStream.isDirty();
	}

	@Override
	public void setup() {
		_inputStream.setup();
	}

	@Override
	public E getValue() {
		return _inputStream.getValue();
	}

	@Override
	public boolean hasNext() {
		return _inputStream.hasNext();
	}

	@Override
	public E next() {
		return _inputStream.next();
	}

	@Override
	public void remove() {
		_inputStream.remove();
	}
	
}
