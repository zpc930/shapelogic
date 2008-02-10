package org.shapelogic.streams;

import java.util.Map.Entry;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.mathematics.NaturalNumberStream;

import junit.framework.TestCase;

/** Test ListFilterCalcStream1.<br />
 * 
 * Does calculation on input and if it is different from null return Entry of input result.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ListFilterCalcStream1Test extends TestCase {
	NumberedStream<Entry<Integer,Integer> > evenNumbers;
	
	@Override
	public void setUp() {
		NaturalNumberStream naturalNumbers = new NaturalNumberStream(5);
		Calc1<Integer,Integer> calcEven = 
			new Calc1<Integer,Integer>() {
			@Override
			public Integer invoke(Integer input) {
				if (input % 2 == 0)
					return input;
				return null;
			}
		};
		evenNumbers = new ListFilterCalcStream1<Integer,Integer>(
				naturalNumbers,calcEven);
	} 
	
	public void testWithNext(){
		assertEquals(new Integer(0),evenNumbers.next().getKey());
		assertEquals(new Integer(2),evenNumbers.next().getKey());
		assertEquals(new Integer(4),evenNumbers.next().getKey());
		assertNull(evenNumbers.next());
	}

	public void testWithGet(){
		assertEquals(new Integer(0),evenNumbers.get(0).getKey());
		assertEquals(new Integer(2),evenNumbers.get(1).getKey());
		assertEquals(new Integer(4),evenNumbers.get(2).getKey());
//		assertNull(evenNumbers.get(3));//XXX gives exception
	}

}
