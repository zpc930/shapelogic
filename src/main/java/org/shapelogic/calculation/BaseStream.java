package org.shapelogic.calculation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/** Implementation of Stream. 
 * 
 * How should a step work?
 * 
 * Normally call the hasNext, in order for this to work it would have to do the 
 * calculation.
 * 
 * So _current should be the last element that has been returned.
 * 
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
abstract public class BaseStream<E> implements Stream<E> {
	public final static int LAST_UNKNOWN = -2;
	protected ArrayList<E> _list = new ArrayList<E>();
	
	/** The last value that was calculated and looked at
	 * So if you want to see that again use this for a lookup
	 */
	protected int _current = -1;
	
	/** Last value that can be calculated
	 * 
	 */
	protected int _last = LAST_UNKNOWN;
	
	/** Calculate the next value
	 * 
	 * @return
	 */
	@Override
	abstract public E calcNext();
	
	@Override
	public boolean hasNext() {
		return hasNextBase();
	}
	
	/** Try to calculate one more
	 * 
	 * @return
	 */
	public boolean calcAddNext() {
		if (LAST_UNKNOWN != _last) return false;
		E element = calcNext();
		//XXX this should not always be the case
		if (element != null) {
			_list.add(element);
			return true;
		}
		else {
			_last = _current;
			return false;
		}
	}
	
	public boolean hasNextBase() {
		if (_current < _list.size() - 1) 
			return true;
		else {
			calcAddNext();
		}
		return _current < _list.size() - 1;
	}

	@Override
	public E next() {
		if (hasNextBase()) {
			_current++;
			return _list.get(_current);
		}
		return null;
	}
	
	@Override
	public E get(int arg0) {
		if (arg0 >= _list.size()) {
			while (hasNext() && calcAddNext()) {
				if (arg0 < _list.size()) {
					_current = arg0;
					return _list.get(arg0);
				}
			}
		}
		return null;
	}

	@Override
	public int getCalcIndex() {
		return _list.size();
	}

	public List<E> getList() { 
		return _list;
	}
	
	public int getCurrent() {
		return _current;
	}

	@Override
	public void remove() {
//		_list.remove();
	}

	@Override
	public boolean add(E arg0) {
		return _list.add(arg0);
	}

	@Override
	public void add(int arg0, E arg1) {
		_list.add(arg0, arg1);
	}

	@Override
	public void clear() {
		_list.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return _list.contains(arg0);
	}

	/** Not sure what to do about this
	 * This could 
	 * 1: Cause the calculation to block
	 * 2: Do the index in what is there yet
	 * 
	 */
	@Override
	public int indexOf(Object arg0) {
		// TODO Auto-generated method stub
		return _list.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	/** I could also generate a new iterator from this
	 */
	@Override
	public Iterator iterator() {
		return this;
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return _list.lastIndexOf(arg0);
	}

	@Override
	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E remove(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return _list.size();
	}

	@Override
	public List subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		return _list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return _list.toArray(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return _list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return _list.addAll(index, c);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return _list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return _list.removeAll(c);
	}

	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return _list.set(index, element);
	}
}
