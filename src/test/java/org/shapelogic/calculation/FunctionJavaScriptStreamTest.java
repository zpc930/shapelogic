package org.shapelogic.calculation;


/** Test of FunctionStream.<br />
 * 
 * JavaScript is the only scripting language that is pre installed in Java 6. <br />  
 * <br />
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public class FunctionJavaScriptStreamTest extends AbstractStreamTests {

	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;
	}

	BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new FunctionStream<Integer>("identity","javascript",stopNumber, "function identity_FUNCTION_(it) { return it };"); 
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	BaseStream<Integer> fibonacciBaseStreamFactory() {
		BaseStream<Integer> stream = new FunctionStream<Integer>(
			"fibo","javascript",null,"function fibo_FUNCTION_(it) { return parseInt(fibo.get(it-2) ) + parseInt(fibo.get(it-1))};",1,1); 
		return stream;
	}
	
}
