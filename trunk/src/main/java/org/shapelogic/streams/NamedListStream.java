package org.shapelogic.streams;

import java.util.Iterator;
import java.util.List;

import org.shapelogic.calculation.RootMap;
import org.shapelogic.util.Constants;

/** Get a Named Stream out of the context.
 * 
 * @author Sami Badawi
 *
 */
public class NamedListStream<E> implements ListStream<E> {
	protected String _key;
	protected ListStream<E> _inputStream; 
	
	public NamedListStream(String key, int maxLast) {
		_key = key;
		_inputStream = (ListStream) RootMap.get(_key);
	}

	public NamedListStream(String key) {
		this(key,Constants.LAST_UNKNOWN);
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

	@Override
	public List<E> getList() {
		return _inputStream.getList();
	}

	@Override
	public void setList(List<E> list) {
		_inputStream.setList(list);
	}

	@Override
	public Iterator<E> iterator() {
		return _inputStream.iterator();
	}
	
}
