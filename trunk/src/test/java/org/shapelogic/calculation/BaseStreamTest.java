package org.shapelogic.calculation;

import junit.framework.TestCase;

/** Test of BaseStream.
 * <br />
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 *
 * @author Sami Badawi
 *
 */
public class BaseStreamTest extends AbstractStreamTests {

	BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new BaseStream<Integer>() {

			@Override
			public Integer calcElement(int index) {
				return index;
			}

			@Override
			public boolean hasNext() {
				return getCurrentSize() <= stopNumber;
			}
			
			{
				_maxLast = stopNumber;
			}

		}; 
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
