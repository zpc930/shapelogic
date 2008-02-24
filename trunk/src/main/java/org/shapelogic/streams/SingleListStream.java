package org.shapelogic.streams;

import org.shapelogic.calculation.CalcInvoke;

/** Use this to treat a calculation as a stream.
 * 
 * @author Sami Badawi
 *
 * @param <T>
 */
abstract public class SingleListStream<T> extends BaseListCommonStream<T> 
implements CalcInvoke<T>, ListStream<T> 
{

	@Override
	public T invokeIndex(int index) {
		if (index != 0)
			return null;
		else {
			setup();
			_value = invoke();
			_dirty = false;
			return _value;
		}
	}
	
	public T getValue() {
		if (_value == null && _dirty)
			invokeIndex(0);
		return _value;
	}

}
