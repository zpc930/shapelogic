package org.shapelogic.streams;

import java.util.ArrayList;
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
abstract public class BaseListStreamList<In,E> extends BaseListCommonStream<E> 
implements InputStreamList<In, E> {
	
	public BaseListStreamList(List<NumberedStream<In> > inputStream, int maxLast){
		_inputStream = inputStream;
		setMaxLast(maxLast);
	}
	
	public BaseListStreamList(List<NumberedStream<In> > inputStream){
		this(inputStream, Constants.LAST_UNKNOWN);
	}
	
	public BaseListStreamList(){
		this(new ArrayList<NumberedStream<In> >());
	}

	/** Does not always exist. */
	protected List<NumberedStream<In> > _inputStream;
	
	@Override
	public E invokeIndex(int index){
		return invoke(getInput(index));
	}
	
	public List<E> getList() { 
		return _list;
	}

	@Override
	public void setList(List<E> list) {
		_list = list;
	}
	
	@Override
	public List<NumberedStream<In> > getInputStream() {
		return _inputStream;
	}
	
	@Override
	public List<In> getInput(int index) {
		if (getInputStream() == null)
			return null;
        setLastFromInput();
		List<In> result = new ArrayList<In>(getDimension());
		boolean notNull = false;
		for (int i = 0; i < getDimension(); i++) {
			NumberedStream<In> currentInputStream = _inputStream.get(i);
			In element = currentInputStream.get(index);
			if (element != null)
				notNull = true; 
			result.add(element);
		}
		if (!notNull)
			return null;
		return result;
	}

	public int getDimension() {
		return _inputStream.size();
	}

	@Override
	public boolean hasNext() {
        setLastFromInput();
        return _last == Constants.LAST_UNKNOWN || _current < _last;
	}

    protected void setLastFromInput() {
		if (getInputStream() == null)
			return;
		for (int i = 0; i < getDimension(); i++) {
			NumberedStream<In> currentInputStream = _inputStream.get(i);
            int inputLast = currentInputStream.getLast();
            if (inputLast != Constants.LAST_UNKNOWN) {
                if (_last == Constants.LAST_UNKNOWN)
                    _last = inputLast;
                else
                    _last = Math.min(_last, inputLast);
            }
        }
    }
}
