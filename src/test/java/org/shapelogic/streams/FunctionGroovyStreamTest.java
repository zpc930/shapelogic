package org.shapelogic.streams;

import org.shapelogic.streams.StreamFactory;
import org.shapelogic.streams.ListStream;

/** Test of FunctionStream.
 * 
 * Requires Groovy to be installed. Need special installation.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionGroovyStreamTest extends AbstractScriptingListStreamTests {

	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;
		_language = "groovy";
		_filterFunctionExpressionEven = "def EvenNumbers_FUNCTION_ = {it*2 % 6};";
		_filterFunctionExpressionThird = "def ThirdNumbers_FUNCTION_ = {it*8};";
	}

	ListStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		ListStream<Integer> stream = StreamFactory.createListStream( "identity", "def identity_FUNCTION_ = { a, it -> it };", null, new Integer(stopNumber));
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	ListStream<Integer> fibonacciBaseStreamFactory() {
		ListStream<Integer> stream = StreamFactory.createListStream("fibo","def fibo_FUNCTION_ = { a, it -> fibo.get(it-2) + fibo.get(it-1) };",null,null,1,1);
		return stream;
	}
	
}
