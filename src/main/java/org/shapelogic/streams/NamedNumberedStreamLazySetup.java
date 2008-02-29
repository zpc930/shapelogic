package org.shapelogic.streams;

import org.shapelogic.calculation.RootMap;
import org.shapelogic.calculation.SetupFlagged;
import org.shapelogic.util.Constants;

/** Get a Named Stream out of the context.
 * <br />
 * With lazy setup, so the input stream is only found when you start to use the
 * values in the stream.
 * 
 * @author Sami Badawi
 *
 */
public class NamedNumberedStreamLazySetup<E> implements NumberedStream<E>, SetupFlagged {
	protected String _key;
	protected NumberedStream<E> _inputStream; 
	protected boolean _setup = false;
	
	public NamedNumberedStreamLazySetup(String key, int maxLast) {
		_key = key;
	}

	public NamedNumberedStreamLazySetup(String key) {
		this(key,Constants.LAST_UNKNOWN);
	}

	@Override
	public E get(int input) {
		if (!isSetup())
			setup();
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
		_inputStream = (NumberedStream) RootMap.get(_key);
		_inputStream.setup();
		_setup = true;
	}

	@Override
	public E getValue() {
		if (!isSetup())
			setup();
		return _inputStream.getValue();
	}

	@Override
	public boolean hasNext() {
		if (!isSetup())
			setup();
		return _inputStream.hasNext();
	}

	@Override
	public E next() {
		if (!isSetup())
			setup();
		return _inputStream.next();
	}

	@Override
	public void remove() {
		_inputStream.remove();
	}

	@Override
	public boolean isSetup() {
		return _setup;
	}
	
}
