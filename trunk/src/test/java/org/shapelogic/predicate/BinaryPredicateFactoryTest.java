package org.shapelogic.predicate;

import junit.framework.TestCase;

/** Unit tests for BinaryPredicateFactory.
 * 
 * @author Sami Badawi
 *
 */
public class BinaryPredicateFactoryTest extends TestCase {

	/** Test for BinaryEqualPredicate. */
	public void testGetInstanceEuqals() {
		Object obj = BinaryPredicateFactory.getInstance("==");
		assertEquals(obj.getClass(),BinaryEqualPredicate.class);
	}

	/** Test for BinaryGreaterPredicate. */
	public void testGetInstanceGreater() {
		Object obj = BinaryPredicateFactory.getInstance(">");
		assertEquals(obj.getClass(),BinaryGreaterPredicate.class);
	}
}
