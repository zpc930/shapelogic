package org.shapelogic.calculation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.shapelogic.predicate.Predicate;

import junit.framework.TestCase;

/** Test CollectionCalc.
 * 
 * @author Sami Badawi
 *
 */
public class CollectionCalcTest extends TestCase {
	
	Collection<Integer> numbers; 

	Predicate<Integer> even = new Predicate<Integer>() {
		@Override
		public boolean evaluate(Integer input) {
			return input % 2 == 0;
		}
	}; 
	
	Calc1<Integer,Boolean> evenCalc = new Calc1<Integer,Boolean>() {
		@Override
		public Boolean invoke(Integer input) {
			return input % 2 == 0;
		}
	}; 
	
	@Override
	public void setUp() {
		numbers = new ArrayList<Integer>();
		for (int i = 0; i<5; i++) {
			numbers.add(i);
		}
	}
	
	public void testFilter() {
		List<Integer> result = (List)CollectionCalc.filter(numbers, even);
		assertEquals(new Integer(0),result.get(0));
		assertEquals(new Integer(2),result.get(1));
		assertEquals(new Integer(4),result.get(2));
		assertEquals(3,result.size());
	}

	public void testFilterCalc() {
		List<Integer> result = (List)CollectionCalc.filter(numbers, evenCalc);
		assertEquals(new Integer(0),result.get(0));
		assertEquals(new Integer(2),result.get(1));
		assertEquals(new Integer(4),result.get(2));
		assertEquals(3,result.size());
	}
}
