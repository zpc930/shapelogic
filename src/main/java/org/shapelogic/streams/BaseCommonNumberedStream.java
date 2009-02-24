package org.shapelogic.streams;

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
 * I think that there could be a _nextElement that is calculated when hasNext()
 * is called. When next is called check if this exist and return it. After that
 * set it to null.
 *
 * @author Sami Badawi
 *
 */
abstract public class BaseCommonNumberedStream<E> extends BaseCommonStream<E>
{

    E _nextElement;
    boolean _nextIsNull = false;

    public BaseCommonNumberedStream()
    {
        _current = -1;
    }

	/** Calculate the value at an index.
	 * <br />
	 * So it gets the needed input value and call the appropriate invoke function.<br /> 
	 * 
	 * Can this be used for a filter call?<br />
	 * The index does not make sense for a filter since you do not know where 
	 * the input is coming from. So maybe just ignore it.<br /> 
	 * This is a little messy but less messy that what is there now.
	 */
	abstract public E invokeIndex(int index);
	
	@Override
	public boolean hasNext() {
		return hasNextBase();
	}
	
	/** Try to calculate one more, independent of the _current.
	 */
	protected boolean calcAddNext() {
		int index = _current + 1;
		if (LAST_UNKNOWN != _last && _last < index) return false;
		E element = invokeIndex(index);
		//XXX this should not always be the case
		if ((element != null) || isNullLegalValue()) {
            _nextIsNull = (element == null);
            _nextElement = element;
			return true;
		}
		else {
			_last = _current;
			_dirty = false;
            _nextIsNull = false;
			return false;
		}
	}
	
	public boolean hasNextBase() {
        if (_nextElement != null || _nextIsNull)
            return true;
        return calcAddNext();
	}

	/** Get next element and advance _current. */
	@Override
	public E next() {
        if (hasNext()) {
            E tempNextElement = _nextElement;
            _nextElement = null;
            _current++;
            return tempNextElement;
        }
        return null;
	}

    protected void setLast(int index) {
        if (_last == LAST_UNKNOWN)
            _last = index - 1;
        else
            _last = Math.min(index - 1,_last);
    }

	/** Get next element without advancing _current.  */
	@Override
	public E get(int inputIndex) {
        E result = null;
		if ( _last != LAST_UNKNOWN && _last < inputIndex) 
			return null;
        result = invokeIndex(inputIndex);
        if (result == null && !isNullLegalValue() &&
                _last != LAST_UNKNOWN && inputIndex < _last )
            setLast(inputIndex);
        return result;
	}
	
	@Override
	public boolean isRandomAccess() {
		return true;
	}

	/** If there is a list that contains all the results.
     *
     * I would think that this should always happen for a ListStream,
     * but it could happen for other streams as well.
     */
	@Override
	public boolean isCached() {
		return false;
	}
	
}
