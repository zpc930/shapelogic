package org.shapelogic.calculation;


/** Test of FunctionStream.
 * 
 * Requires Groovy to be installed. Need special installation.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionJRubyStreamTest extends AbstractStreamTests {

	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;
		_disableTests = true; //XXX JRuby works fine in Eclipse but not under Maven 2, fix and enable again
	}

	BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new FunctionStream<Integer>("identity","jruby",stopNumber, "def identity_FUNCTION_(it) it end"); 
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	BaseStream<Integer> fibonacciBaseStreamFactory() {
		BaseStream<Integer> stream = new FunctionStream<Integer>(
			"fibo","jruby",null,"def fibo_FUNCTION_(it) return $fibo.get(it-2) + $fibo.get(it-1) end",1,1) {
		}; 
		return stream;
	}
	
}
