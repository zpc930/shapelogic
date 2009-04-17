package org.shapelogic.streams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
abstract public class BaseListCommonStream<E> extends BaseCommonStream<E>
implements ListStream<E> {
	protected List<E> _list = new ArrayList<E>();
	
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
		if (LAST_UNKNOWN != _last && _last <= _list.size()-1) return false;
		int index = _list.size();
		E element = invokeIndex(index);
		//XXX this should not always be the case
		if ((element != null) || isNullLegalValue()) {
			_list.add(element);
			return true;
		}
		else {
			_last = _list.size()-1;
			_dirty = false;
			return false;
		}
	}
	
	public boolean hasNextBase() {
		if (isCached()) { 
			if (_current < _list.size() - 1) 
				return true;
            setLastFromInput();
			if ((_last != LAST_UNKNOWN && _last <= _current )) {
				return false;
			} 
			else {
				calcAddNext();
			}
			return _current < _list.size() - 1;
		}
		else {
            setLastFromInput();
            return _last == Constants.LAST_UNKNOWN || _current < _last;
        }
	}

    protected void setLastFromInput() {

    }

	/** Get next element and advance _current. */
	@Override
	public E next() {
		if (isCached()) { 
			if (hasNextBase()) {
				_current++;
				return _list.get(_current);
			}
			return null;
		}
		else {
			if (hasNextBase()) {
				_current++;
				E element = invokeIndex(_current);
				return element;
			}
			return null;
		}
	}
	
	/** Get next element without advancing _current.  */
	@Override
	public E get(int inputIndex) {
		if ( _last != LAST_UNKNOWN && _last < inputIndex)
			return null;
		if (isRandomAccess()) {
			E result = _list.get(inputIndex);
			if (result == null) {
				result = invokeIndex(inputIndex);
				if (result != null) {
//                      if (isCached())
					_list.set(inputIndex,result);
                }
                else if (!isNullLegalValue() && _last != LAST_UNKNOWN && inputIndex < _last ) {
                    if (_last == LAST_UNKNOWN)
                        _last = inputIndex - 1;
                    else
                        _last = Math.min(inputIndex - 1,_last);
                }
			}
			return result;
		}
		if (inputIndex >= _list.size()) {
			for (int i = _list.size(); i <= inputIndex; i++) {
				E element = invokeIndex(i);
				if (element != null)
					_list.add(element);
				else {
                    if (!isNullLegalValue()) {
                        if (_last == LAST_UNKNOWN)
                            _last = i - 1;
                        else
                            _last = Math.min(i - 1,_last);
                        _dirty = false;
                    }
                    else if (_last == LAST_UNKNOWN || inputIndex <= _last)
    					_list.add(element);
					return null;
				}
			}
		}
		return _list.get(inputIndex);
	}
	
	@Override
	public boolean isRandomAccess() {
		return false;
	}

	/** If there is a list that contains all the results.
     *
     * I would think that this should always happen for a ListStream,
     * but it could happen for other streams as well.
     */
	@Override
	public boolean isCached() {
		return true;
	}
	
	public int getCurrentSize() {
		return _list.size();
	}

	public List<E> getList() { 
		return _list;
	}

	@Override
	public void setList(List<E> list) {
		_list = list;
	}
	
	@Override
	/** XXX Should maybe be changed to create new independent iterators on each call.
	 * All referring back to this, but with a different index  
	 */
	public Iterator<E> iterator() {
		return this; //XXX works
		//return _list.iterator();//XXX lazy init does not work
	}
}
