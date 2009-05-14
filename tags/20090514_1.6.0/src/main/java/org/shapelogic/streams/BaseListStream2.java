package org.shapelogic.streams;

import org.shapelogic.calculation.CartesianIndex;
import org.shapelogic.calculation.CartesianIndex2;
import org.shapelogic.util.Constants;

import static org.shapelogic.util.Constants.LAST_UNKNOWN;

/** Implementation of ListStream. <br />
 * 
 * Close to<br /> 
 * 1: a lazy stream.<br />
 * 2: a UNIX pipe.<br />
 * <br />
 * This is close to org.apache.commons.collections.list.LazyList,
 * but that takes a factory to calculate next element, and that works badly here.
 * 
 * How should a step work?<br />
 * Normally call the hasNext, in order for this to work it would have to do the 
 * calculation.<br />
 * <br />
 * So _current should be the last element that has been returned.<br />
 * <br />
 * So when you call hasNext it will add that element to the list.
 * When you call next it will bump the counter up one element if goes too high 
 * then do the calculation.
 * 
 * At the end it will get to a point where the calculation fails. 
 * At that point last will be set to the current element.
 * 
 * I will ignore synchronization to begin with.
 * 
 * @author Sami Badawi
 *
 */
abstract public class BaseListStream2<In0,In1,E> extends BaseListCommonStream<E> 
implements IndexedInputStream2<In0, In1, E> 
{
	final protected NumberedStream<In0> _inputStream0;
	final protected NumberedStream<In1> _inputStream1;
	final protected CartesianIndex _cartesianIndex;
	
	public BaseListStream2(NumberedStream<In0> inputStream0, NumberedStream<In1> inputStream1, int maxLast){
		_inputStream0 = inputStream0;
		_inputStream1 = inputStream1;
		setMaxLast(maxLast);
		_cartesianIndex = new CartesianIndex2(inputStream0,inputStream1);
		setMaxLast(_cartesianIndex.getLast());
	}
	
	public BaseListStream2(NumberedStream<In0> inputStream0, NumberedStream<In1> inputStream1){
		this(inputStream0,inputStream1,Constants.LAST_UNKNOWN);
	}
	
	/** Calculate the next value
	 * 
	 * @return
	 */
	@Override
	abstract public E invoke(In0 input0, In1 input1, int index);
	
	@Override
	public E invokeIndex(int index){
		return invoke(getInput0(index),getInput1(index),index);
	}
	
	
	/** Try to calculate one more, independent of the _current.
	 */
	public boolean calcAddNext() {
		if (LAST_UNKNOWN != _last && _last <= _list.size()-1) return false;
		int index = _list.size();
		int[] indexes = _cartesianIndex.get(index);
		E element = invoke(getInput0(indexes[0]),getInput1(indexes[1]),index);
		//XXX this should not always be the case
		if (element != null || isNullLegalValue()) {
			_list.add(element);
			return true;
		}
		else {
			_last = _list.size()-1;
			_dirty = false;
			return false;
		}
	}
	
	@Override
	public In0 getInput0(int index) {
		if (getInputStream0() == null)
			return null;
		return getInputStream0().get(index);
	}
	
	@Override
	public In1 getInput1(int index) {
		if (getInputStream1() == null)
			return null;
		return getInputStream1().get(index);
	}
	
	@Override
	public NumberedStream<In0> getInputStream0() {
		return _inputStream0;
	}

	@Override
	public NumberedStream<In1> getInputStream1() {
		return _inputStream1;
	}
}
