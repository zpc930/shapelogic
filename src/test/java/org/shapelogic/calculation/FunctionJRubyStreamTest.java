package org.shapelogic.calculation;


/** Test of FunctionStream.
 * 
 * Requires JRuby to be installed. Need special installation.<br />
 * 
 * <br />
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
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
