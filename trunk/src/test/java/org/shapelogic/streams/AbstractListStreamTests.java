package org.shapelogic.streams;

import org.shapelogic.calculation.RootMap;
import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.predicate.BinaryEqualPredicate;
import org.shapelogic.predicate.BinaryPredicate;
import org.shapelogic.streams.ListStream;

import junit.framework.TestCase;

/** Base class for other unit tests.
 * 
 * @author Sami Badawi
 *
 */
public abstract class AbstractListStreamTests extends TestCase {
	protected int fibonacciNumbersAtStart;
	protected int fibonacciNumbersAfterOneIteration = 1;
	protected boolean _disableTests = false;
	protected String _language;
	protected String _filterFunctionExpression;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	abstract ListStream<Integer> countingBaseStreamFactory(final int stopNumber);
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	abstract ListStream<Integer> fibonacciBaseStreamFactory();	
	
	public void testCount() {
		if (_disableTests) return;
		ListStream<Integer> stream = countingBaseStreamFactory(2);
		assertNotNull(stream);
		assertNumberEquals(0,stream.next());
		assertNumberEquals(1,stream.next());
		assertNumberEquals(2,stream.next());
		assertTrue(!stream.hasNext());
	}
	
	public void testHasNext() {
		if (_disableTests) return;
		ListStream<Integer> stream = countingBaseStreamFactory(2);
		assertNotNull(stream);
		assertNumberEquals(0,stream.getList().size());
		assertNumberEquals(-1,stream.getIndex());
		assertNumberEquals(-1,stream.getIndex());
		assertTrue(stream.hasNext());
		assertNumberEquals(1,stream.getList().size());
		assertNumberEquals(-1,stream.getIndex());
		assertNumberEquals(0,stream.next());
		assertNumberEquals(1,stream.next());
		assertNumberEquals(2,stream.next());
		assertTrue(!stream.hasNext());
	}

	public void testGet() {
		if (_disableTests) return;
		ListStream<Integer> stream = countingBaseStreamFactory(2);
		assertNumberEquals(-1,stream.getIndex());
		assertNumberEquals(1,stream.get(1));
	}

	public void testFailedGet() {
		if (_disableTests) return;
		ListStream<Integer> stream = countingBaseStreamFactory(2);
		assertNumberEquals(-1,stream.getIndex());
		assertEquals(null,stream.get(10));
		assertNumberEquals(-1,stream.getIndex());
	}
	
	public void testSum() {
		if (_disableTests) return;
		ListStream<Integer> stream = countingBaseStreamFactory(2);
		int sum = 0;
		for (Number e: stream) {
			sum += e.intValue();
		}
		assertEquals(3,sum);
	}

	public void testSumOfIterable() {
		if (_disableTests) return;
		ListStream<Integer> stream = countingBaseStreamFactory(2);
		int sum = 0;
		Iterable<Integer> iterable = stream;
		for (Number e: iterable) {
			sum += e.intValue();
		}
		assertEquals(3,sum);
	}

	public void testGetCurrent() {
		if (_disableTests) return;
		ListStream<Integer> stream = countingBaseStreamFactory(10);
		assertEquals(-1,stream.getIndex());
		assertNumberEquals(5,stream.get(5));
	}
	
	public void testFibonacci() {
		if (_disableTests) return;
		ListStream<Integer> stream = fibonacciBaseStreamFactory();
		assertNotNull(stream);
		assertEquals(fibonacciNumbersAtStart,stream.getList().size());
		assertEquals(-1,stream.getIndex());
		assertEquals(-1,stream.getIndex());
		assertTrue(stream.hasNext());
		assertEquals(fibonacciNumbersAfterOneIteration,stream.getList().size());
		assertEquals(-1,stream.getIndex());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(1),stream.next());
		Object val2 = stream.next();
		assertNotNull(val2);
		assertNumberEquals(2,(Number)val2);
		assertNumberEquals(3,stream.next());
		assertNumberEquals(5,stream.next());
	}
	
	/** Take a named input stream and make a filter based on a scripting function.
	 * <br />
	 * So this should only be run for the Scripting function based tests.
	 */
	public void testNamedFilterStream() {
		if (_disableTests) return;
		if (_filterFunctionExpression==null)
			return;
		NaturalNumberStream naturalNumbersTo3 = new NaturalNumberStream(3);
		String inputStreamName = "naturalNumbersTo3";
		RootMap.put(inputStreamName, naturalNumbersTo3);
		String ruleName = "EvenNumbers";
		BinaryPredicate<Integer, Integer> binaryPredicate = new BinaryEqualPredicate();
		Integer compareObject = 2;
		ListStream<Boolean> stream = StreamFactory.createListStream0(ruleName, 
				inputStreamName, _filterFunctionExpression, binaryPredicate, compareObject, _language);
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
//		assertFalse(stream.hasNext()); //XXX should work
//		assertNull(stream.next()); //XXX should work
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
	}
	
	/** Maybe this should be moved to utility class */
	static public void assertNumberEquals(Number n1, Number n2 ) 
	{
		assertTrue(((Number)n2).doubleValue() == ((Number)n2).doubleValue());
	}  
}
