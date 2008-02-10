package org.shapelogic.streams;

import static org.shapelogic.util.Constants.LAST_UNKNOWN;

import java.util.AbstractMap;
import java.util.Map.Entry;

/** This is the abstract class to override to make a ListFilterStream.
 * 
 * The code here is the same as what would be in the BaseFilterStream.
 * 
 * This is a little problematic there are 2 calculation that need to be made.
 * So they cannot both have the same method name: invoke(input) 
 * <br />
 * 
 * You can either take one of them as a Transformer or as a Calc1 member. <br />
 * This one is implementing Transformer <br />
 * 
 * @author Sami Badawi
 *
 * @param <E>
 */
abstract public class BaseListFilterTransformerStream<E, Out> extends BaseListStream1<E,Entry<E,Out> > 
implements FilterTransformerStream<E, Out> {
	public BaseListFilterTransformerStream(ListStream<E> inputStream) {
		_inputStream = inputStream;
	}
	
	@Override
	/** Maybe I could make this the predicate method, so if null is returned 
	 * then don't add anything.
	 * Or this could just be the identity.
	 */
	public Entry<E, Out> invoke(E input) {
		 Out result = transform(input);
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