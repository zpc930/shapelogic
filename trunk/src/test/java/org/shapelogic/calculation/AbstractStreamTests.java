package org.shapelogic.calculation;

import junit.framework.TestCase;

public abstract class AbstractStreamTests extends TestCase {
	protected int fibonacciNumbersAtStart;
	protected int fibonacciNumbersAfterOneIteration = 1;
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	abstract BaseStream<Integer> countingBaseStreamFactory(final int stopNumber);
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	abstract BaseStream<Integer> fibonacciBaseStreamFactory();	
	
	public void testCount() {
		BaseStream stream = countingBaseStreamFactory(2);
		assertNotNull(stream);
		assertEquals(new Integer(0),stream.next());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(2),stream.next());
		assertTrue(!stream.hasNext());
	}
	
	public void testHasNext() {
		BaseStream stream = countingBaseStreamFactory(2);
		assertNotNull(stream);
		assertEquals(0,stream.getList().size());
		assertEquals(-1,stream.getCurrent());
		assertEquals(-1,stream.getCurrent());
		assertTrue(stream.hasNextBase());
		assertEquals(1,stream.getList().size());
		assertEquals(-1,stream.getCurrent());
		assertEquals(new Integer(0),stream.next());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(2),stream.next());
		assertTrue(!stream.hasNext());
	}

	public void testGet() {
		BaseStream stream = countingBaseStreamFactory(2);
		assertEquals(-1,stream.getCurrent());
		assertEquals(new Integer(1),stream.get(1));
	}

	public void testFailedGet() {
		BaseStream stream = countingBaseStreamFactory(2);
		assertEquals(-1,stream.getCurrent());
		assertEquals(null,stream.get(10));
		assertEquals(-1,stream.getCurrent());
	}
	
	public void testSum() {
		BaseStream<Integer> stream = countingBaseStreamFactory(2);
		int sum = 0;
		Iterable<Integer> iterator = stream;
		for (Integer e: stream) {
			sum += e;
		}
		assertEquals(3,sum);
	}

	public void testSumOfIterable() {
		BaseStream<Integer> stream = countingBaseStreamFactory(2);
		int sum = 0;
		Iterable<Integer> iterable = stream;
		for (Integer e: iterable) {
			sum += e;
		}
		assertEquals(3,sum);
	}

	public void testGetCurrent() {
		BaseStream<Integer> stream = countingBaseStreamFactory(10);
		assertEquals(-1,stream.getCurrent());
		assertEquals(new Integer(5),stream.get(5));
	}
	
	public void testFibonacci() {
		BaseStream<Integer> stream = fibonacciBaseStreamFactory();
		assertNotNull(stream);
		assertEquals(fibonacciNumbersAtStart,stream.getList().size());
		assertEquals(-1,stream.getCurrent());
		assertEquals(-1,stream.getCurrent());
		assertTrue(stream.hasNextBase());
		assertEquals(fibonacciNumbersAfterOneIteration,stream.getList().size());
		assertEquals(-1,stream.getCurrent());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(1),stream.next());
		assertNumberEquals(2,stream.next());
		assertNumberEquals(3,stream.next());
		assertNumberEquals(5,stream.next());
	}
	
	/** Maybe this should be moved to utility class */
	static public void assertNumberEquals(Number n1, Number n2 ) 
	{
		assertTrue(((Number)n2).doubleValue() == ((Number)n2).doubleValue());
	}  
}
