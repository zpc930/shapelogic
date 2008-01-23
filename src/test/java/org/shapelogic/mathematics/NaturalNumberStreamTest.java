package org.shapelogic.mathematics;

import org.shapelogic.mathematics.NaturalNumberStream;

import junit.framework.TestCase;

/** Test NaturalNumberStream.
 * 
 * @author Sami Badawi
 *
 */
public class NaturalNumberStreamTest extends TestCase {

	/** Problem 1.
	 * Add all the natural numbers below 1000 that are multiples of 3 or 5.
	 * 
	 */
	public void testNaturalNumberBricStop() {
		NaturalNumberStream naturalNumberStream = new NaturalNumberStream(2);
		assertNotNull(naturalNumberStream);
		assertEquals(new Integer(0),naturalNumberStream.next());
		assertEquals(new Integer(1),naturalNumberStream.next());
		assertEquals(new Integer(2),naturalNumberStream.next());
		assertNull(naturalNumberStream.next());
	}

	public void testNaturalNumberBricStartStop() {
		NaturalNumberStream naturalNumberStream = new NaturalNumberStream(1,2);
		assertNotNull(naturalNumberStream);
		assertEquals(new Integer(1),naturalNumberStream.next());
		assertEquals(new Integer(2),naturalNumberStream.next());
		assertNull(naturalNumberStream.next());
	}

}
