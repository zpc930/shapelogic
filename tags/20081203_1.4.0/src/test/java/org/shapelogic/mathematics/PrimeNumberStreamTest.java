package org.shapelogic.mathematics;

import junit.framework.TestCase;

/** Test PrimeNumberStream.
 * 
 * @author Sami Badawi
 *
 */
public class PrimeNumberStreamTest extends TestCase {
	
	public void testPrimes() {
		PrimeNumberStream primeNumberStream = new PrimeNumberStream();
		assertEquals(new Integer(2), primeNumberStream.next());
		assertEquals(new Integer(3), primeNumberStream.next());
		assertEquals(new Integer(5), primeNumberStream.next());
		assertEquals(new Integer(7), primeNumberStream.next());
		assertEquals(new Integer(11), primeNumberStream.next());
		assertEquals(new Integer(13), primeNumberStream.next());
		assertEquals(new Integer(17), primeNumberStream.next());
		assertEquals(new Integer(19), primeNumberStream.next());
		assertEquals(new Integer(23), primeNumberStream.next());
	}
	
	public void testPrimesWithStop() {
		PrimeNumberStream primeNumberStream = new PrimeNumberStream(3);
		assertEquals(new Integer(2), primeNumberStream.next());
		assertEquals(new Integer(3), primeNumberStream.next());
		assertEquals(new Integer(5), primeNumberStream.next());
		assertTrue(primeNumberStream.hasNext());
		assertEquals(new Integer(7), primeNumberStream.next());
		assertFalse(primeNumberStream.hasNext());
	}
}
