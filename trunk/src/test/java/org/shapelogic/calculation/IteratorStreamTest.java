package org.shapelogic.calculation;

import java.util.Iterator;

import junit.framework.TestCase;

/** Test of IteratorStream.
 * 
 * @author Sami Badawi
 *
 */
public class IteratorStreamTest extends TestCase {
	
	/** Simple counting iterator. */
	private Iterator<Integer> countingIteratorFactory() {
		return new Iterator<Integer>() {
			int i = -1;

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Integer next() {
				i++;
				return i;
			}

			@Override
			public void remove() {
			}
			
		};
	}

	/** Keep a state in the fibonacci iterator, being the 2 last fibonacci numbers. 
	 */
	private Iterator<Integer> fibonacciIteratorFactory() {
		return new Iterator<Integer>() {
			int i = -1;
			int fibonacci;
			int even = 1;
			int odd = 0;

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Integer next() {
				i++;
				fibonacci = even + odd;
				if ((i & 1) != 0)
					odd = fibonacci;
				else
					even = fibonacci;
				return fibonacci;
			}

			@Override
			public void remove() {
			}
			
		};
	}

	private BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new IteratorStream<Integer>(countingIteratorFactory(), stopNumber);
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	private BaseStream<Integer> fibonacciBaseStreamFactory() {
		BaseStream<Integer> stream = new BaseStream<Integer>() {

			@Override
			public Integer calcElement(int index) {
				if (getCalcIndex() < 2) return 1;
				return get(getCalcIndex()-2) + get(getCalcIndex()-1);
			}
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
		assertEquals(0,stream.getList().size());
		assertEquals(-1,stream.getCurrent());
		assertEquals(-1,stream.getCurrent());
		assertTrue(stream.hasNextBase());
		assertEquals(1,stream.getList().size());
		assertEquals(-1,stream.getCurrent());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(2),stream.next());
		assertEquals(new Integer(3),stream.next());
		assertEquals(new Integer(5),stream.next());
	}
}
