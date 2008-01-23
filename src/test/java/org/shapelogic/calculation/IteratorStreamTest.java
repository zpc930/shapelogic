package org.shapelogic.calculation;

import java.util.Iterator;

/** Test of IteratorStream.
 * 
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public class IteratorStreamTest extends AbstractStreamTests {
	
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

	BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new IteratorStream<Integer>(countingIteratorFactory(), stopNumber);
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	BaseStream<Integer> fibonacciBaseStreamFactory() {
		BaseStream<Integer> stream = new BaseStream<Integer>() {

			@Override
			public Integer calcElement(int index) {
				if (getCurrentSize() < 2) return 1;
				return get(getCurrentSize()-2) + get(getCurrentSize()-1);
			}
		}; 
		return stream;
	}
	
}
