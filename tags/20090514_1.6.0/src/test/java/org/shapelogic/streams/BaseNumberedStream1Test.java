package org.shapelogic.streams;

import org.shapelogic.mathematics.NaturalNumberStream;

import junit.framework.TestCase;

/** Test BaseListStream1.
 * 
 * @author Sami Badawi
 *
 */
public class BaseNumberedStream1Test extends TestCase {
	BaseNumberedStream1<Integer,Integer> evenNumbers;
	
	@Override
	public void setUp() {
		NaturalNumberStream naturalNumbers = new NaturalNumberStream();
		evenNumbers = new BaseNumberedStream1<Integer,Integer>(naturalNumbers,2){

			@Override
			public Integer invoke(Integer input) {
				return input * 2;
			}
		};
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
