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
	
	public ListFilterCalcStream1(NumberedStream<E> inputStream, Calc1<E, Out> calc1) {
		_inputStream = inputStream;
		_calc1 = calc1;
	}
	
	public Calc1<E, Out> getCalc1() {
		return _calc1;
	}
	
	/** Maybe I could make this the predicate method, so if null is returned 
	 * then don't add anything.
	 * Or this could just be the identity.
	 */
	@Override
	public Entry<E, Out> invoke(E input) {
		 Out result = _calc1.invoke(input);
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
