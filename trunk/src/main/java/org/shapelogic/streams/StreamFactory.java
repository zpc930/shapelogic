package org.shapelogic.streams;

import java.util.Iterator;

import org.shapelogic.calculation.CalcIndex1;
import org.shapelogic.predicate.BinaryPredicate;
import org.shapelogic.predicate.Predicate;
import org.shapelogic.scripting.FunctionCalcIndex0;
import org.shapelogic.scripting.FunctionCalcIndex1;
import org.shapelogic.util.Constants;

/** StreamFactory is a factory for Streams.
 * 
 * @author Sami Badawi
 *
 */
public class StreamFactory {
	static public <In, E> ListStream<E> createListStream(CalcIndex1<In, E> transformer){
		return new TransformerListStream<In, E>(transformer);
	}

	static public <E> ListStream<E> createListStream(Iterator<E> iterator, int maxLast){
		ListStream<E> result = new TransformerListStream<Object, E>(createCalcIndex1(iterator));
		result.setMaxLast(maxLast);
		return result;
	}
	
	static public <E> ListStream<E> createListStream(Iterator<E> iterator){
		return new TransformerListStream<Object, E>(createCalcIndex1(iterator));
	}
	
	static public <E> CalcIndex1<Object, E>  createCalcIndex1(final Iterator<E> iterator) {
		CalcIndex1<Object, E> result = new CalcIndex1<Object, E>() 
		{
			@Override
			public E invoke(Object input, int index) {
				if (iterator.hasNext())
					return iterator.next();
				return null;
			}
		};
		return result;
	}

	/** Create stream based on a function.
	 * 
	 * @param <In> is not used now maybe I should change the type
	 * @param <E> Element type
	 * @param name of the stream
	 * @param expression in Scripting language of values stream
	 * @param language Scripting language by name, Groovy is the default
	 * @param stopNumber length of this stream
	 * @param startList elements to add to stream at the beginning
	 * @return
	 */
	static public <In,E> ListStream<E> createListStream(String name, String expression , String language, Integer stopNumber, E ... startList){
		String functionName = name + Constants.FUNCTION_NAME_SUFFIX;
		FunctionCalcIndex1<In, E> transformer = new FunctionCalcIndex1<In, E>(functionName, expression, language); 
		ListStream<E> result = new TransformerListStream<In, E>(transformer);
		if (stopNumber != null)
			result.setMaxLast(stopNumber);
		for (E el: startList)
			result.getList().add(el);
		transformer.put(name, result);
		return result;
	}
	
	/** Create stream based on a function.
	 * 
	 * I need to handle something like this:
	 * 
	 * A, "polygons", "def tempA2 = {it.points.length}", "==", 5
	 * 
	 * What should the output type of this be?
	 * NumberedStream[Boolean] <br />
	 * CaclIndex[Boolean] <br />
	 * Predicate[Integer] <br />
	 * <br />
	 * @param <In> is not used now maybe I should change the type
	 * @param <E> Element type
	 * @param name of the created stream
	 * @param input name of the input stream
	 * @param expression in Scripting language of values stream
	 * @param binaryPredicate by name
	 * @param compareObject
	 * @param language Scripting language by name, Groovy is the default
	 * @return
	 */
	static public <In0, In1> ListStream<Boolean> createListStream0(String name, 
			String inputName, String expression, 
			BinaryPredicate<In0, In1> binaryPredicate, In1 compareObject, String language)
	{
		String functionName = name + Constants.FUNCTION_NAME_SUFFIX;
		//I need to insert the transformer in the stream otherwise I have a 
		//problem with an extra stream and what kind of caching I should use
		//would it be better to have a way to turn an expression into a Calc and 
		//I should have a way to combine Calcs.
		FunctionCalcIndex0<In0> transformer = 
			new FunctionCalcIndex0<In0>(functionName, expression, language);
//		transformer
//		ListStream<E> result = new TransformerListStream<In, E>(transformer);
//		if (stopNumber != null)
//			result.setMaxLast(stopNumber);
//		for (E el: startList)
//			result.getList().add(el);
//		transformer.put(name, result);
//		return result;
		return null;
	}
	
}
