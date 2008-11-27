package org.shapelogic.streams;

import java.util.Iterator;


/** Test of IteratorStream.
 * 
 * @author Sami Badawi
 *
 */
public class IteratorStreamTest extends AbstractListStreamTests {
	
	/** Simple counting iterator. */
	Iterator<Integer> countingIteratorFactory() {
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
	Iterator<Integer> fibonacciIteratorFactory() {
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

	ListStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		ListStream<Integer> stream = new IteratorStream<Integer>(countingIteratorFactory(),stopNumber);
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	ListStream<Integer> fibonacciBaseStreamFactory() {
		ListStream<Integer> stream = new IteratorStream<Integer>(fibonacciIteratorFactory());
		return stream;
	}
	
}
