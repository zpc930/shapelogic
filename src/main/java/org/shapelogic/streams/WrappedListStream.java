package org.shapelogic.streams;

import java.util.List;

import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.calculation.RecursiveContext;

/** Wraps a stream around a list.<br />
 * 
 * @author Sami Badawi
 *
 */
public class WrappedListStream<E> extends BaseListCommonStream<E> {
	
	public WrappedListStream(List<E> inputList) {
		setList(inputList);
	}

	public WrappedListStream(List<E> inputList, String name, RecursiveContext recursiveContext) {
		this(inputList);
		QueryCalc.getInstance().put(name, inputList, recursiveContext);
	}

	@Override
	public E invokeIndex(int index) {
		if (index < _list.size())
			return _list.get(index);
		_last = _list.size() -1;
		return null;
	}

	@Override
	public boolean hasNext() {
		boolean result = _current < _list.size() -1;
		if (!result)
			_last = _list.size() -1;
		return result;
	}
}
