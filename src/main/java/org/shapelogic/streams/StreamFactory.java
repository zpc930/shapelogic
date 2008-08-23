package org.shapelogic.streams;

import java.util.Iterator;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.CalcIndex1;
import org.shapelogic.calculation.IQueryCalc;
import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.predicate.BinaryPredicate;
import org.shapelogic.predicate.BinaryPredicateFactory;
import org.shapelogic.scripting.FunctionCalc1;
import org.shapelogic.scripting.FunctionCalcIndex1;
import org.shapelogic.util.Constants;

/** StreamFactory is a factory for Streams.
 * 
 * @author Sami Badawi
 *
 */
public class StreamFactory {
	private static IQueryCalc queryCalc = QueryCalc.getInstance();
	RecursiveContext recursiveContext;
	
	public StreamFactory(RecursiveContext recursiveContext) {
		this.recursiveContext = recursiveContext;
	}
	
	static public <In, E> ListStream<E> createListStream(final CalcIndex1<In, E> transformer){
		return new ListCalcIndexStream1<In, E>(transformer);
	}

	static public <E> ListStream<E> createListStream(final Iterator<E> iterator, int maxLast){
		ListStream<E> result = new ListCalcIndexStream1<Object, E>(createCalcIndex1(iterator));
		result.setMaxLast(maxLast);
		return result;
	}
	
	static public <E> ListStream<E> createListStream(final Iterator<E> iterator){
		return new ListCalcIndexStream1<Object, E>(createCalcIndex1(iterator));
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
	static public <In,E> ListStream<E> createListStream(String name, String expression , 
		String language, Integer stopNumber, E ... startList)
	{
		String functionName = name + Constants.FUNCTION_NAME_SUFFIX;
		FunctionCalcIndex1<In, E> functionCalcIndex1 = 
			new FunctionCalcIndex1<In, E>(functionName, expression, language); 
		ListStream<E> result = new ListCalcIndexStream1<In, E>(functionCalcIndex1);
		if (stopNumber != null)
			result.setMaxLast(stopNumber);
		for (E el: startList)
			result.getList().add(el);
		functionCalcIndex1.put(name, result);
		return result;
	}
	
	/** Create stream based on a function.
	 * <br />
	 * I need to handle something like this:<br />
	 * <br />
	 * A, "polygons", "def tempA2 = {it.points.length}", "==", 5<br />
	 * <br />
	 * What should the output type of this be?<br />
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
	public <In0, In1, In2> ListStream<Boolean> createListStream0(String name, 
			String inputName, String expression, 
			final BinaryPredicate<In1, In2> binaryPredicate, 
			final In2 compareObject, String language)
	{
		String functionName = name + Constants.FUNCTION_NAME_SUFFIX;
		//I need to insert the transformer in the stream otherwise I have a 
		//problem with an extra stream and what kind of caching I should use
		//would it be better to have a way to turn an expression into a Calc and 
		//I should have a way to combine Calcs.
		final NumberedStream<In0> input = findNumberedStream(inputName);
		final Calc1<In0, Boolean> calcBoolean = makeFunctionBooleanCalc1(expression,
				binaryPredicate, compareObject, language, functionName);
		final ListCalcStream1<In0,Boolean> calcStream1 = 
			new ListCalcStream1<In0,Boolean>(calcBoolean,input);
		queryCalc.put(name, calcStream1, recursiveContext);
		return calcStream1;
	}

	/** Create stream based on a function.
	 * <br />
	 * I need to handle something like this:<br />
	 * <br />
	 * A, "polygons", "def tempA2 = {it.points.length}", "==", 5<br />
	 * <br />
	 * What should the output type of this be?<br />
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
	public <In0, In1, In2> ListStream<Boolean> createListStream0(String name, 
			String inputName, String expression, 
			final String binaryPredicateString, final In2 compareObject, String language)
	{
		String functionName = name + Constants.FUNCTION_NAME_SUFFIX;
		//I need to insert the transformer in the stream otherwise I have a 
		//problem with an extra stream and what kind of caching I should use
		//would it be better to have a way to turn an expression into a Calc and 
		//I should have a way to combine Calcs.
		final NumberedStream<In0> input = findNumberedStream(inputName);
		final Calc1<In0, Boolean> calcBoolean = makeFunctionBooleanCalc1(expression,
				binaryPredicateString, compareObject, language, functionName);
		final ListCalcStream1<In0,Boolean> calcStream1 = 
			new ListCalcStream1<In0,Boolean>(calcBoolean,input);
		return calcStream1;
	}

	public static <In0, In1, In2> Calc1<In0, Boolean> makeFunctionBooleanCalc1(
			String expression, final BinaryPredicate<In1, In2> binaryPredicate,
			final In2 compareObject, String language, String functionName) 
	{
		final FunctionCalc1<In0,In1> calc1 = 
			new FunctionCalc1<In0,In1>(functionName, expression, language);
		final Calc1<In0,Boolean> calcBoolean = new Calc1<In0,Boolean>() {
			@Override
			public Boolean invoke(In0 input) {
				return binaryPredicate.evaluate(calc1.invoke(input), compareObject);
			}
		};
		return calcBoolean;
	}
	
	public static <In1, In2> Calc1<In1, Boolean> makeBooleanCalc1(
			final BinaryPredicate<In1, In2> binaryPredicate,
			final In2 compareObject) 
	{
		final Calc1<In1,Boolean> calcBoolean = new Calc1<In1,Boolean>() {
			@Override
			public Boolean invoke(In1 input) {
				return binaryPredicate.evaluate(input, compareObject);
			}
		};
		return calcBoolean;
	}
	
	public static < In0, In1, In2> Calc1<In0, Boolean> makeFunctionBooleanCalc1(
			String expression, final String binaryPredicateString,
			final In2 compareObject, String language, String functionName) 
	{
		final BinaryPredicate<In1, In2> binaryPredicate = 
			BinaryPredicateFactory.getInstance(binaryPredicateString);
		if (binaryPredicate == null)
			return null;
		final Calc1<In0,Boolean> calcBoolean = makeFunctionBooleanCalc1(
			expression, binaryPredicate, compareObject, language, functionName);
		return calcBoolean;
	}
	
	/** Factory for finding NumberedStream in RootMap.
	 * 
	 * @param <E>
	 * @param name of the stream
	 * @return
	 */
	static public <E> NumberedStream<E> findNumberedStream(String name, RecursiveContext recursiveContext) {
		Object obj = queryCalc.get(name, recursiveContext);
		if (obj == null)
			throw new RuntimeException("NumberedStream not found in RootMap for name: " 
					+ name + ", nothing found.");
		if (obj instanceof NumberedStream)
			return (NumberedStream<E>) obj;
		throw new RuntimeException("NumberedStream not found in RootMap for name: " + name + 
				", type: " + obj.getClass().getSimpleName());
	}

	public <E> NumberedStream<E> findNumberedStream(String name) {
		return findNumberedStream(name,recursiveContext);
	}
	
	public <In0, In2> ListStream<Boolean> createListStream0(String name, 
			String inputName,  
			final BinaryPredicate<In0, In2> binaryPredicate, 
			final In2 compareObject)
	{
		final NumberedStream<In0> input = findNumberedStream(inputName);
		final Calc1<In0, Boolean> calcBoolean = makeBooleanCalc1(binaryPredicate, compareObject);
		final ListCalcStream1<In0,Boolean> calcStream1 = 
			new ListCalcStream1<In0,Boolean>(calcBoolean,input);
		if (name != null)
			queryCalc.put(name, calcStream1, recursiveContext);
		return calcStream1;
	}

	public <In0, In1, In2> ListStream<Boolean> addToAndListStream0(String andName, 
			String partName, String inputName, String expression, 
			final BinaryPredicate<In1, In2> binaryPredicate, 
			final In2 compareObject, String language)
	{
		Object obj = queryCalc.get(andName,recursiveContext);
		AndListStream andListStream = null;
		if (partName == null) {
			partName = andName + "_" + inputName;
		}
		if (obj == null) {
			andListStream = new AndListStream();
			queryCalc.put(andName, andListStream, recursiveContext);
		}
		else if (!(obj instanceof AndListStream)) 
			throw new RuntimeException("Wrong type of object: " + obj);
		else
			andListStream = (AndListStream) obj;
		ListStream<Boolean> component = createListStream0(partName, inputName,
				expression, binaryPredicate, compareObject, language);
		andListStream.getInputStream().add(component);
		return andListStream;
	}

	public <In0, In1, In2> ListStream<Boolean> addAndListStream0(String andName, 
			String partName, String inputName, String expression, 
			final String binaryPredicateString, final In2 compareObject, String language)
	{
		final BinaryPredicate<In1, In2> binaryPredicate = 
			BinaryPredicateFactory.getInstance(binaryPredicateString);
		return addToAndListStream0(andName, partName, inputName,
				expression, binaryPredicate, compareObject, language);
	}
	
	public <In0, In1, In2> ListStream<Boolean> addToAndListStream0(String andName, 
			String partName, String inputName,  
			final BinaryPredicate<In1, In2> binaryPredicate, 
			final In2 compareObject)
	{
		Object obj = queryCalc.get(andName,recursiveContext);
		AndListStream andListStream = null;
//		if (partName == null) {
//			partName = andName + "_" + inputName;
//		}
		if (obj == null) {
			andListStream = new AndListStream();
			queryCalc.put(andName, andListStream, recursiveContext);
		}
		else if (!(obj instanceof AndListStream)) 
			throw new RuntimeException("Wrong type of object: " + obj);
		else
			andListStream = (AndListStream) obj;
		ListStream<Boolean> component = createListStream0(partName, inputName,
				binaryPredicate, compareObject);
		andListStream.getInputStream().add(component);
		return andListStream;
	}

	public <In0, In1, In2> ListStream<Boolean> addToAndListStream0(String andName, 
			String partName, String inputName,  
			final String binaryPredicateString, 
			final In2 compareObject)
	{
		final BinaryPredicate<In1, In2> binaryPredicate = 
			BinaryPredicateFactory.getInstance(binaryPredicateString);
		return addToAndListStream0(andName, partName, inputName,  
				binaryPredicate, compareObject);
	}

	public <In0, In1, In2> ListStream<Boolean> addToAndListStream0(String andName, 
			String inputName,  
			final String binaryPredicateString, 
			final In2 compareObject)
	{
		final BinaryPredicate<In1, In2> binaryPredicate = 
			BinaryPredicateFactory.getInstance(binaryPredicateString);
		return addToAndListStream0(andName, null, inputName,  
				binaryPredicate, compareObject);
	}
}
