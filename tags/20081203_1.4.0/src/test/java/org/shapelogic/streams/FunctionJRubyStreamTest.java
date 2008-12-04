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
public class FunctionJRubyStreamTest extends AbstractScriptingListStreamTests {

	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;
		_disableTests = true; //XXX JRuby works fine in Eclipse but not under Maven 2, fix and enable again
		_language = "jruby";
		_filterFunctionExpressionEven = "def EvenNumbers_FUNCTION_(it) return it*2 end";
		_filterFunctionExpressionThird = "def ThirdNumbers_FUNCTION_(it) it*8 end";
		_filterFunctionExpressionEvenNoPartName = "def XOrRule_naturalNumbersTo3_FUNCTION_(it) return it*2 end";
	}

	ListStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		ListStream<Integer> stream = StreamFactory.createListStream( "identity", 
				"def identity_FUNCTION_(a,it) it end", _language, new Integer(stopNumber));
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	ListStream<Integer> fibonacciBaseStreamFactory() {
		ListStream<Integer> stream = StreamFactory.createListStream("fibo",
				"def fibo_FUNCTION_(a,it) return $fibo.get(it-2) + $fibo.get(it-1) end",_language,null,1,1);
		return stream;
	}
	
}
