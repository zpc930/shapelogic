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
	public BaseListFilterTransformerStream(NumberedStream<E> inputStream) {
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
	
	/** Ignore the index, and iterate over the input stream until a non null 
	 * value is found. 
	 */
	@Override
	public Entry<E, Out> invokeIndex(int index){
		while (_inputStream.hasNext()) {
			E input = _inputStream.next();
			Entry<E, Out> result = invoke(input);
			if (result != null)
				return result;
		}
		return null;
	}
}
