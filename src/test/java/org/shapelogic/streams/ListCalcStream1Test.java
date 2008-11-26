package org.shapelogic.streams;

import junit.framework.TestCase;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.util.Constants;

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
public class ListCalcStream1Test extends TestCase {
	ListCalcStream1<Integer, Integer> even;
	
	@Override
	public void setUp(){
		NaturalNumberStream natural = new NaturalNumberStream(3);
		Calc1<Integer, Integer> times2 = new Calc1<Integer, Integer>() {
			@Override
			public Integer invoke(Integer input) {
				if (input == null)
					return null;
				return input * 2;
			}
		};
		even = 
			new ListCalcStream1<Integer, Integer>(times2,(NumberedStream<Integer>)natural);
        even.setNullLegalValue(false);
	}
	
	public void testWithNext() {
        assertEquals(Constants.LAST_UNKNOWN, even.getLast());
		assertEquals(new Integer(0), even.next());
		assertEquals(new Integer(2), even.next());
		assertEquals(new Integer(4), even.next());
		assertEquals(new Integer(6), even.next());
        assertEquals(Constants.LAST_UNKNOWN, even.getLast());
		assertFalse(even.hasNext());
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
        assertEquals(Constants.LAST_UNKNOWN, even.getLast());
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
