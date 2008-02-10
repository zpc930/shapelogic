package org.shapelogic.streams;

import static org.shapelogic.util.Constants.LAST_UNKNOWN;

import java.util.AbstractMap;
import java.util.Map.Entry;

import org.shapelogic.calculation.Calc1;

/** Does calculation on input and if it is different from null return Entry of input result.
 * <br />
 * <br />
 * 
 * @author Sami Badawi
 *
 * @param <E>
 */
public class ListFilterCalcStream1<E, Out> extends BaseListStream1<E,Entry<E,Out> > 
{
	protected final Calc1<E, Out> _calc1;
	
	public ListFilterCalcStream1(ListStream<E> inputStream, Calc1<E, Out> calc1) {
		_inputStream = inputStream;
		_calc1 = calc1;
	}
	
	public Calc1<E, Out> getCalc1() {
		return _calc1;
	}
	
	@Override
	/** Maybe I could make this the predicate method, so if null is returned 
	 * then don't add anything.
	 * Or this could just be the identity.
	 */
	public Entry<E, Out> invoke(E input) {
		 Out result = _calc1.invoke(input);
		 if (result == null)
			 return null;
		 Entry<E, Out> result2 = new AbstractMap.SimpleEntry<E,Out>(input,result);;
		return result2;
	}
	
	public Entry<E, Out> next() {
		while (_inputStream.hasNext()) {
			E input = _inputStream.next();
			Entry<E, Out> result = invoke(input);
			if (result != null)
				return result;
		}
		return null;
	}
	
	public boolean hasNext() {
		return _inputStream.hasNext(); //XXX not right fix
	}
	
	@Override
	public Entry<E, Out> get(int arg0) {
		if ( _last != LAST_UNKNOWN && _last < arg0)
			return null;
		if (arg0 >= _list.size()) {
			for (int i = _list.size(); i <= arg0; i++) {
				if (hasNext()) {
					Entry<E, Out> element = next();
					if (element != null)
						_list.add(element);
				}
				else {
					_last = i - 1;
					_dirty = false;
					return null;
				}
			}
		}
		return _list.get(arg0);
	}
}
