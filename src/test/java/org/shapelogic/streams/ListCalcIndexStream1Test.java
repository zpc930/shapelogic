package org.shapelogic.streams;

import org.shapelogic.calculation.CalcIndex1;

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
 * XXX This should be called ListCalcIndexStream1Test or something close to that.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ListCalcIndexStream1Test extends AbstractListStreamTests {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;
	}

	ListCalcIndexStream1<Object,Integer> countingBaseStreamFactory(final int stopNumber) {
		ListCalcIndexStream1<Object, Integer> stream = new ListCalcIndexStream1<Object,Integer>() {

			{
				_maxLast = stopNumber;
				_last = stopNumber;
			}
			
			{
				_calc = new CalcIndex1<Object,Integer>() {

					@Override
					public Integer invoke(Object arg0, int index) {
						return index;
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
	ListCalcIndexStream1<Object,Integer> fibonacciBaseStreamFactory() {
		ListCalcIndexStream1<Object,Integer> stream = new ListCalcIndexStream1<Object,Integer>() {
			{
				final ListCalcIndexStream1<Object,Integer> parent = this;
				//Add 2 first values
				_list.add(1);
				_list.add(1);
				_calc = new CalcIndex1<Object,Integer>() {

					@Override
					public Integer invoke(Object obj, int arg0) {
						int index = (Integer)arg0;
						return ((Number)parent.get(index-2)).intValue() + ((Number)parent.get(index-1)).intValue();
					}

				};
			}
		};
		return stream;
	}
	
}
