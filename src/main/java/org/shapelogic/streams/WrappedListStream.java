package org.shapelogic.streams;

import java.util.List;

import org.shapelogic.calculation.RootMap;

/** Wraps a stream around a list.<br />
 * 
 * @author Sami Badawi
 *
 */
public class WrappedListStream<E> extends BaseListCommonStream<E> {
	
	public WrappedListStream(List<E> inputList) {
		setList(inputList);
	}

	public WrappedListStream(List<E> inputList, String name) {
		this(inputList);
		RootMap.put(name, this);
	}

	@Override
	public E invokeIndex(int index) {
		return _list.get(index);
	}

	@Override
	public boolean hasNext() {
		return _current < _list.size() -1;
	}
}
