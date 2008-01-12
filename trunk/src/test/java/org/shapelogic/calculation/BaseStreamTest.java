package org.shapelogic.calculation;

import junit.framework.TestCase;

/** Test of BaseStream.
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
				return getCalcIndex() <= stopNumber;
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
				if (getCalcIndex() < 2) return 1;
				return get(getCalcIndex()-2) + get(getCalcIndex()-1);
			}
		}; 
		return stream;
	}
	
}
