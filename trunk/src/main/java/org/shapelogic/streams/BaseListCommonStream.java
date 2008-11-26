package org.shapelogic.streams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.shapelogic.calculation.ContextGettable;
import org.shapelogic.calculation.IQueryCalc;
import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.calculation.RecursiveContext;
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
abstract public class BaseListCommonStream<E> 
implements ListStream<E>, StreamProperties, ContextGettable {
	protected List<E> _list = new ArrayList<E>();
	
	/** The last value that was calculated and looked at
	 * So if you want to see that again use this for a lookup
	 */
	protected int _current = -1;
	
	/** Last value that can be calculated.
	 * When _maxLast is set then _last should first be set to the same.
	 * If lower limit is found move down.
	 */
	protected int _last = LAST_UNKNOWN;
	
	/** Highest value that last can take.
	 */
	protected int _maxLast = LAST_UNKNOWN;
	
	/** Does not always exist. */
	protected String _name;
	
	protected boolean _dirty = true;
	
	protected E _value = null;
	
	protected Map _context;

	protected IQueryCalc _query;

	protected RecursiveContext _parentContext;

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
		if (element != null) {
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
			else if ((_last != LAST_UNKNOWN && _last <= _current )) {
				return false;
			} 
			else {
				calcAddNext();
			}
			return _current < _list.size() - 1;
		}
		else 
			return (_maxLast != LAST_UNKNOWN && _current < _maxLast);
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
	public E get(int arg0) {
		if ( _last != LAST_UNKNOWN && _last < arg0)
			return null;
		if (isRandomAccess()) {
			E result = _list.get(arg0);
			if (result == null) {
				int index = _list.size();
				result = invokeIndex(index);
				if (result != null && isCached()) 
					_list.set(arg0,result);
			}
			return result;
		}
		if (arg0 >= _list.size()) {
			for (int i = _list.size(); i <= arg0; i++) {
				E element = invokeIndex(i);
				if (element != null)
					_list.add(element);
				else {
					_last = i - 1;
					_dirty = false;
					return null;
				}
			}
		}
		return _list.get(arg0);
	}
	
	@Override
	public int getLast() {
		return _last;
	}

	@Override
	public int getMaxLast() {
		return _maxLast;
	}

	/** Set a max value for last possible element. This also set last. */
	public void setMaxLast(int maxLast) {
		_maxLast = maxLast;
		if (maxLast < _last || _last == Constants.LAST_UNKNOWN)
			_last = maxLast;
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
	
	@Override
    public boolean isNullLegalValue() {
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
	public int getIndex() {
		return _current;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setup() {
	}

	@Override
	public Map getContext() {
		return _context;
	}

	@Override
	public RecursiveContext getParentContext() {
		return _parentContext;
	}
	
	@Override
	public E getValue() {
		return _value;
	}

	@Override
	public boolean isDeterministic() {
		return true;
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	/** XXX not sure if I can do this
	 */
	public void remove() {
	}

	@Override
	/** XXX Should maybe be changed to create new independent iterators on each call.
	 * All referring back to this, but with a different index  
	 */
	public Iterator<E> iterator() {
		return this; //XXX works
		//return _list.iterator();//XXX lazy init does not work
	}

	public Object getInContext(Object key){
		if (_query == null)
			_query = new QueryCalc();
		return _query.get(key, this);
	}
}
