package org.shapelogic.streams;

import java.util.Iterator;

import org.shapelogic.calculation.IndexTransform;
import org.shapelogic.scripting.FunctionIndexTransform;
import org.shapelogic.util.Constants;

/** StreamFactory is a factory for Streams.
 * 
 * @author Sami Badawi
 *
 */
public class StreamFactory {
	static public <In, E> ListStream<E> createListStream(IndexTransform<In, E> transformer){
		return new TransformerListStream<In, E>(transformer);
	}

	static public <E> ListStream<E> createListStream(Iterator<E> iterator, int maxLast){
		ListStream<E> result = new TransformerListStream<Object, E>(createIndexTransform(iterator));
		result.setMaxLast(maxLast);
		return result;
	}
	
	static public <E> ListStream<E> createListStream(Iterator<E> iterator){
		return new TransformerListStream<Object, E>(createIndexTransform(iterator));
	}
	
	static public <E> IndexTransform<Object, E>  createIndexTransform(final Iterator<E> iterator) {
		IndexTransform<Object, E> result = new IndexTransform<Object, E>() 
		{
			@Override
			public E transform(Object input, int index) {
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
		FunctionIndexTransform<In, E> transformer = new FunctionIndexTransform<In, E>(functionName, expression, language); 
		ListStream<E> result = new TransformerListStream<In, E>(transformer);
		if (stopNumber != null)
			result.setMaxLast(stopNumber);
		for (E el: startList)
			result.getList().add(el);
		transformer.put(name, result);
		return result;
	}
}
