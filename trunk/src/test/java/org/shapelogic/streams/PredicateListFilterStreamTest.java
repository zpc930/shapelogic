package org.shapelogic.streams;

import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.predicate.AllPredicate;
import org.shapelogic.predicate.AnyPredicate;
import org.shapelogic.predicate.OnePredicate;
import org.shapelogic.predicate.Predicate;
import org.shapelogic.util.Constants;

import static org.shapelogic.predicate.TruePredicate.TRUE_PREDICATE;

import junit.framework.TestCase;

/** Test BaseListMuliFilterStream.
 * 
 * @author Sami Badawi
 *
 */
public class PredicateListFilterStreamTest extends TestCase {
	
	ListFilterStream<Integer> makeListFilterStream2(final Predicate<Integer>[] predicates,String type) {
		final Predicate<Integer> compositePredicate;
		if (predicates == null || predicates.length == 0) {
			compositePredicate = TRUE_PREDICATE;
		}
		else if (Constants.AND.equalsIgnoreCase(type)) {
			compositePredicate = new AllPredicate<Integer>(predicates);
		}
		else if (Constants.OR.equalsIgnoreCase(type)) {
			compositePredicate = new AnyPredicate<Integer>(predicates);
		}
		else if (Constants.XOR.equalsIgnoreCase(type)) {
			compositePredicate = new OnePredicate<Integer>(predicates);
		} 
		else //And is default 
		{
			compositePredicate = new AllPredicate<Integer>(predicates);
		} 
		BaseListFilterStream<Integer> andStream = 
			new BaseListFilterStream<Integer>(new NaturalNumberStream()) {

				@Override
				public boolean evaluate(Integer input) {
					return compositePredicate.evaluate(input);
				}
			
			};
		return andStream;
	}

	ListFilterStream<Integer> makeListFilterStream(final Predicate<Integer>[] predicates,String type) {
		final Predicate<Integer> compositePredicate;
		if (predicates == null || predicates.length == 0) {
			compositePredicate = TRUE_PREDICATE;
		}
		else if (Constants.AND.equalsIgnoreCase(type)) {
			compositePredicate = new AllPredicate<Integer>(predicates);
		}
		else if (Constants.OR.equalsIgnoreCase(type)) {
			compositePredicate = new AnyPredicate<Integer>(predicates);
		}
		else if (Constants.XOR.equalsIgnoreCase(type)) {
			compositePredicate = new OnePredicate<Integer>(predicates);
		} 
		else //And is default 
		{
			compositePredicate = new AllPredicate<Integer>(predicates);
		} 
		ListFilterStream<Integer> andStream = 
			new PredicateListFilterStream<Integer>(new NaturalNumberStream(), compositePredicate);
		return andStream;
	}

	public void testNoPreciates(){
		Predicate<Integer>[] predicates = null;
		ListFilterStream<Integer> andStream = 
			makeListFilterStream(predicates,"AND");
		assertEquals(new Integer(0),andStream.get(0));
	}
	
	public void testAndBaseListMuliFilterStreamUsingGet(){
		Predicate<Integer> even = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 2 == 0;
			}
		}; 
		final Predicate<Integer>[] predicates = new Predicate[]{ even };
		ListFilterStream<Integer> andStream = 
			makeListFilterStream(predicates,"AND");
		assertEquals(new Integer(0),andStream.get(0));
		assertEquals(new Integer(2),andStream.get(1));
	}
	
	public void testAndOn1BaseListMuliFilterStreamUsingNext(){
		Predicate<Integer> even = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 2 == 0;
			}
		}; 
		final Predicate<Integer>[] predicates = new Predicate[]{ even };
		ListFilterStream<Integer> andStream = 
			makeListFilterStream(predicates,"AND");
		assertEquals(new Integer(0),andStream.next());
		assertEquals(new Integer(2),andStream.next());
	}

	public void testAndOn2BaseListMuliFilterStreamUsingNext(){
		Predicate<Integer> even = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 2 == 0;
			}
		}; 
		Predicate<Integer> thirds = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 3 == 0;
			}
		}; 
		final Predicate<Integer>[] predicates = new Predicate[]{ even, thirds };
		ListFilterStream<Integer> andStream = 
			makeListFilterStream(predicates,"AND");
		assertEquals(new Integer(0),andStream.next());
		assertEquals(new Integer(6),andStream.next());
	}
	
	public void testThatAndIsDefaultOn2BaseListMuliFilterStreamUsingNext(){
		Predicate<Integer> even = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 2 == 0;
			}
		}; 
		Predicate<Integer> thirds = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 3 == 0;
			}
		}; 
		final Predicate<Integer>[] predicates = new Predicate[]{ even, thirds };
		ListFilterStream<Integer> andStream = 
			makeListFilterStream(predicates,"");
		assertEquals(new Integer(0),andStream.next());
		assertEquals(new Integer(6),andStream.next());
	}
	
	public void testOrOn2BaseListMuliFilterStreamUsingNext(){
		Predicate<Integer> even = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 2 == 0;
			}
		}; 
		Predicate<Integer> thirds = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 3 == 0;
			}
		}; 
		final Predicate<Integer>[] predicates = new Predicate[]{ even, thirds };
		ListFilterStream<Integer> andStream = 
			makeListFilterStream(predicates,"OR");
		assertEquals(new Integer(0),andStream.next());
		assertEquals(new Integer(2),andStream.next());
		assertEquals(new Integer(3),andStream.next());
		assertEquals(new Integer(4),andStream.next());
		assertEquals(new Integer(6),andStream.next());
	}

	public void testXOrOn2BaseListMuliFilterStreamUsingNext(){
		Predicate<Integer> even = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 2 == 0;
			}
		}; 
		Predicate<Integer> thirds = new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return input % 3 == 0;
			}
		}; 
		final Predicate<Integer>[] predicates = new Predicate[]{ even, thirds };
		ListFilterStream<Integer> andStream = 
			makeListFilterStream(predicates,"XOR");
		assertEquals(new Integer(2),andStream.next());
		assertEquals(new Integer(3),andStream.next());
		assertEquals(new Integer(4),andStream.next());
		assertEquals(new Integer(8),andStream.next());
	}
}
