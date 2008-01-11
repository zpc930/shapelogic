package org.shapelogic.calculation;

import org.apache.commons.collections.Transformer;

import junit.framework.TestCase;

/** Test of TransformerStream.
 * 
 * This is a lazy stream that will calculate a value based on 
 * 
 * <ul>
 * <li>the input index of the element</li> 
 * <li>a transform function from the index</li>
 * <li>the other values in the stream</li> 
 * </ul>
 * 
 * @author Sami Badawi
 *
 */
public class TransformerStreamTest extends TestCase {

	private BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new TransformerStream<Integer>() {

			@Override
			public boolean hasNext() {
				return getCalcIndex() <= stopNumber;
			}
			
			{
				_maxLast = stopNumber;
			}
			
			{
				_transformer = new Transformer() {

					@Override
					public Object transform(Object arg0) {
						return arg0;
					}
					
				};
			}

		}; 
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	private BaseStream<Integer> fibonacciBaseStreamFactory() {
		BaseStream<Integer> stream = new TransformerStream<Integer>() {
			{
				final BaseStream parent = this;
				//Add 2 first values
				add(1);
				add(1);
				_transformer = new Transformer() {

					@Override
					public Object transform(Object arg0) {
						int index = (Integer)arg0;
						return ((Number)parent.get(index-2)).intValue() + ((Number)parent.get(index-1)).intValue();
					}
				};
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
