package org.shapelogic.streams;

import junit.framework.TestCase;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.SimpleRecursiveContext;
import org.shapelogic.mathematics.NaturalNumberStream;

/** Test of CalcNumberedStream1. <br />
 * <br />
 * Based on NamedListCalcStream1Test. <br />
 * <br />
 * This is a lazy stream that will calculate a value based on 
 * 
 * <ul>
 * <li>the input index of the element</li> 
 * <li>a transform function from the index</li>
 * <li>the other values in the stream</li> 
 * </ul>
 * 
 * 
 * @author Sami Badawi
 *
 */
public class CalcNumberedStream1Test extends TestCase {
	CalcNumberedStream1<Integer, Integer> even;
	SimpleRecursiveContext _recursiveContext;
	NumberedStream<Integer> evenNumbers;

	@Override
	public void setUp() {
		NaturalNumberStream naturalNumbers = new NaturalNumberStream();
		Calc1<Integer, Integer> times2 = new Calc1<Integer, Integer>() {
			@Override
			public Integer invoke(Integer input) {
				if (input == null)
					return null;
				return input * 2;
			}
		};
		evenNumbers = new CalcNumberedStream1<Integer, Integer>(times2, naturalNumbers, 2);
	}

	public void testWithNext(){
		assertEquals(new Integer(0),evenNumbers.next());
		assertEquals(new Integer(2),evenNumbers.next());
		assertEquals(new Integer(4),evenNumbers.next());
		assertNull(evenNumbers.next());
	}

	public void testWithGet(){
		assertEquals(new Integer(0),evenNumbers.get(0));
		assertEquals(new Integer(2),evenNumbers.get(1));
		assertEquals(new Integer(4),evenNumbers.get(2));
		assertNull(evenNumbers.get(3));
	}
}
