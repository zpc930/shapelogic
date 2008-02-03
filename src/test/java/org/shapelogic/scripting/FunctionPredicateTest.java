package org.shapelogic.scripting;

import org.shapelogic.predicate.Predicate;
import org.shapelogic.util.Constants;

import junit.framework.TestCase;

/** Test FunctionPredicate.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionPredicateTest extends TestCase {

	public void testGroovyFunctionEven() throws Exception {
		Predicate<Integer> evenPredicate = 
			new FunctionPredicate<Integer>("even", "def even = {it%2 == 0};",Constants.GROOVY);
		assertTrue(evenPredicate.evaluate(0));
		assertFalse(evenPredicate.evaluate(1));
		assertTrue(evenPredicate.evaluate(2));
		assertFalse(evenPredicate.evaluate(3));
	}
}
