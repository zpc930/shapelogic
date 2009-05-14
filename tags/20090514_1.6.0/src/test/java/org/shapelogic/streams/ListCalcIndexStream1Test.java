package org.shapelogic.streams;

import org.shapelogic.calculation.CalcIndex1;
import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.util.Constants;

/** Test of ListCalcIndexStream1.
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
public class ListCalcIndexStream1Test extends AbstractListStreamTests {

	ListCalcIndexStream1<Integer, Integer> even;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;

		NaturalNumberStream natural = new NaturalNumberStream(3);
		CalcIndex1<Integer, Integer> times2 = new CalcIndex1<Integer, Integer>() {

			@Override
            public Integer invoke(Integer input, int index) {
				if (input == null)
					return null;
				return input + index;
            }
		};
		even =
			new ListCalcIndexStream1<Integer, Integer>(times2,(NumberedStream<Integer>)natural, Constants.LAST_UNKNOWN);
        even.setNullLegalValue(false);
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
	
	public void testWithNext() {
        assertEquals(Constants.LAST_UNKNOWN, even.getLast());
		assertEquals(new Integer(0), even.next());
		assertEquals(new Integer(2), even.next());
		assertEquals(new Integer(4), even.next());
		assertEquals(new Integer(6), even.next());
//        assertEquals(Constants.LAST_UNKNOWN, even.getLast());
//		assertFalse(even.hasNext());
        assertEquals(3, even.getLast());
		assertNull(even.next());
	}

	public void testWithGet() {
		assertEquals(new Integer(0), even.get(0));
		assertEquals(new Integer(2), even.get(1));
		assertEquals(new Integer(4), even.get(2));
		assertEquals(new Integer(6), even.get(3));
		assertNull(even.get(4));
	}


	public void testGetTwiceWithSameResult() {
		assertEquals(new Integer(0), even.get(0));
		assertEquals(new Integer(2), even.get(1));
		assertEquals(new Integer(4), even.get(2));
		assertEquals(new Integer(6), even.get(3));
		assertNull(even.get(4));
		assertEquals(new Integer(0), even.get(0));
		assertEquals(new Integer(2), even.get(1));
		assertEquals(new Integer(4), even.get(2));
		assertEquals(new Integer(6), even.get(3));
		assertNull(even.get(4));
	}

	public void testIterateTwiceWithSameResult() {
        assertEquals(Constants.LAST_UNKNOWN, even.getLast());
		assertEquals(new Integer(0), even.get(0));
		assertEquals(new Integer(2), even.get(1));
		assertEquals(new Integer(4), even.get(2));
		assertEquals(new Integer(6), even.get(3));
//        assertEquals(Constants.LAST_UNKNOWN, even.getLast());
		assertNull(even.get(4));
        assertEquals(3, even.getLast());
		assertEquals(new Integer(0), even.get(0));
		assertEquals(new Integer(2), even.get(1));
		assertEquals(new Integer(4), even.get(2));
		assertEquals(new Integer(6), even.get(3));
        assertEquals(3, even.getLast());
		assertNull(even.get(4));
        assertEquals(3, even.getLast());
	}
}
