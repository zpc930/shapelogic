package org.shapelogic.predicate;

import junit.framework.TestCase;

/** Test PredicateBinder.
 * 
 * @author Sami Badawi
 *
 */
public class PredicateBinderTest extends TestCase {
	
	public void testBindBinaryEqual() {
		Predicate equal2 = PredicateBinder.bind0(new BinaryEqualPredicate(), new Integer(2));
		assertTrue(equal2.evaluate(2));
	}

	/** 
	 * 
	 */
	public void testBindBinaryGreater() {
		Predicate greater2 = PredicateBinder.bind1(new BinaryGreaterPredicate(), new Integer(2));
		assertTrue(greater2.evaluate(3));
		assertFalse(greater2.evaluate(2));
		assertFalse(greater2.evaluate(1));
	}

	public void testBindBinarySmaller() {
		Predicate samller2 = PredicateBinder.bind1(new BinarySmallerPredicate(), new Integer(2));
		assertFalse(samller2.evaluate(3));
		assertFalse(samller2.evaluate(2));
		assertTrue(samller2.evaluate(1));
	}
}
