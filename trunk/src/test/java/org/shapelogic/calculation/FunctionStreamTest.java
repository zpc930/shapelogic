package org.shapelogic.calculation;

import junit.framework.TestCase;

/** Test of FunctionStream.
 * 
 * Requires Groovy to be installed. Need special installation.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionStreamTest extends TestCase {

	private BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new FunctionStream<Integer>("identity",stopNumber, "def identity_FUNCTION_ = { it };"); 
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	private BaseStream<Integer> fibonacciBaseStreamFactory() {
		BaseStream<Integer> stream = new FunctionStream<Integer>(
				"fibo","def fibo_FUNCTION_ = { fibo.get(it-2) + fibo.get(it-1) };",1,1) {
		}; 
		return stream;
	}
	
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
		assertEquals(2,stream.getList().size());
		assertEquals(-1,stream.getCurrent());
		assertEquals(-1,stream.getCurrent());
		assertTrue(stream.hasNextBase());
		assertEquals(2,stream.getList().size());
		assertEquals(-1,stream.getCurrent());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(2),stream.next());
		assertEquals(new Integer(3),stream.next());
		assertEquals(new Integer(5),stream.next());
	}
}
