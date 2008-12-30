package org.shapelogic.streams;

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
 * I think that there could be a _nextElement that is calculated when hasNext()
 * is called. When next is called check if this exist and return it. After that
 * set it to null.
 *
 * @author Sami Badawi
 *
 */
abstract public class BaseCommonStream<E> 
implements NumberedStream<E>, StreamProperties, ContextGettable, RecursiveContext
{
	

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

    protected boolean _nullLegalValue = true;

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

	/** Try to calculate one more, independent of the _current.
	 */
	abstract protected boolean calcAddNext();

    protected void setLastFromInput() {

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
	public boolean isNullLegalValue() {
        return _nullLegalValue;
    }

	public void setNullLegalValue(boolean nullLegalValue) {
        _nullLegalValue = nullLegalValue;
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

	public Object getInContext(Object key){
		if (_query == null)
			_query = new QueryCalc();
		return _query.get(key, this);
	}
}
