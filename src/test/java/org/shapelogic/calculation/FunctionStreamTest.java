package org.shapelogic.calculation;

/** Test of FunctionStream.
 * 
 * Requires Groovy to be installed. Need special installation.
 * 
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public class FunctionStreamTest extends AbstractStreamTests {

	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;
	}

	BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new FunctionStream<Integer>("identity",stopNumber, "def identity_FUNCTION_ = { it };"); 
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	BaseStream<Integer> fibonacciBaseStreamFactory() {
		BaseStream<Integer> stream = new FunctionStream<Integer>(
				"fibo","def fibo_FUNCTION_ = { fibo.get(it-2) + fibo.get(it-1) };",1,1); 
		return stream;
	}
	
}
