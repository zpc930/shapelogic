package org.shapelogic.streams;

import java.util.List;
import org.shapelogic.util.Constants;


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
abstract public class BaseListIndexedStream1<In,E> extends BaseListCommonStream<E> 
implements IndexedInputStream1<In, E> {
	
	/** Does not always exist. */
	protected NumberedStream<In> _inputStream;
	
	public BaseListIndexedStream1(NumberedStream<In> inputStream, int maxLast){
		_inputStream = inputStream;
		setMaxLast(maxLast);
	}
	
	public BaseListIndexedStream1(NumberedStream<In> inputStream){
		_inputStream = inputStream;
        if (_inputStream != null) {
            setMaxLast(_inputStream.getMaxLast());
        }
	}
	
	public BaseListIndexedStream1(){
	}

	@Override
	public E invokeIndex(int index){
        In inputObj = getInput(index);
		return invoke(inputObj, index);
	}
	
	public List<E> getList() { 
		return _list;
	}

	@Override
	public void setList(List<E> list) {
		_list = list;
	}
	
	@Override
	public NumberedStream<In> getInputStream() {
		return _inputStream;
	}
	
	@Override
	public In getInput(int index) {
		if (getInputStream() == null)
			return null;
		In inputObj = getInputStream().get(index);
        setLastFromInput();
        return inputObj;
	}

    protected void setLastFromInput() {
		if (getInputStream() == null)
			return;
        int inputLast = getInputStream().getLast();
        if (inputLast != Constants.LAST_UNKNOWN) {
            if (_last == Constants.LAST_UNKNOWN)
                _last = inputLast;
            else
                _last = Math.min(_last, inputLast);
        }
    }
}
