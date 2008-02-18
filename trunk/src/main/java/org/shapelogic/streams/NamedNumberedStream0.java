package org.shapelogic.streams;

import org.shapelogic.calculation.RootMap;
import org.shapelogic.util.Constants;

/** Get a Named Stream out of the context.
 * 
 * @author Sami Badawi
 *
 */
public class NamedNumberedStream0<E> extends BaseListStream0<E> {
	protected String _key;
	protected NumberedStream<E> _inputStream; 
	
	public NamedNumberedStream0(String key, int maxLast) {
		super(maxLast);
		_key = key;
		_inputStream = (NumberedStream) RootMap.get(_key);
	}

	public NamedNumberedStream0(String key) {
		this(key,Constants.LAST_UNKNOWN);
	}
	
	@Override
	public E invoke(int index) {
		return _inputStream.get(index) ;
	}

}
