package org.shapelogic.predicate;

import org.shapelogic.scripting.FunctionPredicate;
import org.shapelogic.util.Constants;

import junit.framework.TestCase;

/** Test OnePredicateSelector.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class OnePredicateSelectorTest extends TestCase {
	NamedPredicate<Integer> even;
	NamedPredicate<Integer> thirds;
	NamedPredicate<Integer>[] predicates;
	OnePredicateSelector<Integer> xorEvenThirds;
	Predicate<Integer> evenScript;
	
	public void setUpJava() {
		even = new NamedPredicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 2 == 0;
			}
			@Override
			public String getOhName() {
				return "even";
			}
		};
		
		thirds = new NamedPredicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 3 == 0;
			}
			@Override
			public String getOhName() {
				return "thirds";
			}
		};
		
		predicates = new NamedPredicate[] {even, thirds};
		xorEvenThirds = new OnePredicateSelector<Integer>(predicates);
	}
	
	public void setUpGroovy() {
		evenScript = new FunctionPredicate<Integer>("even", "def even = {it%2 == 0};",Constants.GROOVY);
		even = new NamedPredicateDecorator<Integer>(evenScript, "even"); 
		
		thirds = new NamedPredicateDecorator<Integer>(new FunctionPredicate<Integer>("thirds", "def thirds = {it%3 == 0};",Constants.GROOVY), "thirds");
		
		predicates = new NamedPredicate[] {even, thirds};
		xorEvenThirds = new OnePredicateSelector<Integer>(predicates);
	}
	
	public void testJava() {
		setUpJava();
		common();
	}

	public void testGroovy() {
		setUpGroovy();
		common();
	}

	public void common() {
		assertTrue(even.evaluate(0));
		assertFalse(even.evaluate(1));
		assertNull(xorEvenThirds.invoke(0));
		assertNull(xorEvenThirds.invoke(1));
		assertEquals("even",xorEvenThirds.invoke(2));
		assertEquals("thirds",xorEvenThirds.invoke(3));
		assertEquals("even",xorEvenThirds.invoke(4));
		assertNull(xorEvenThirds.invoke(5));
		assertNull(xorEvenThirds.invoke(6));
	}
}
