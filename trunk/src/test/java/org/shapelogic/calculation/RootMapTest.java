package org.shapelogic.calculation;

import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.streams.BaseListFilterStream;
import org.shapelogic.streams.BaseListStream0;
import org.shapelogic.streams.ListStream;
import org.shapelogic.streams.NumberedStream;

import junit.framework.TestCase;

/** Test RootMap.
 * 
 * @author Sami Badawi
 *
 */
public class RootMapTest extends TestCase {
	
	public void test1() {
		RootMap.put("number", 1);
		assertEquals(new Integer(1), RootMap.get("number"));
	}
	
	/** So remember to reset rootMap between tests.
	 */
	public void testThatValuseArePreservedBetweenTests() {
		assertEquals(new Integer(1), RootMap.get("number"));
	}
	
	public void testThatSetAndGetStream() {
		RootMap.put("naturalNumbers", new NaturalNumberStream(3));
		NumberedStream<Integer> naturalNumbers = (NumberedStream<Integer>) RootMap.get("naturalNumbers");
		assertEquals(new Integer(0),naturalNumbers.next());
	}
	
	public void testThatYouCanOverride() {
		RootMap.put("naturalNumbers", new NaturalNumberStream(3));
		NumberedStream<Integer> naturalNumbers = (NumberedStream<Integer>) RootMap.get("naturalNumbers");
		assertEquals(new Integer(0),naturalNumbers.next());
	}
	
	/** Test that there is a simple way to set this up.
	 */
	public void testEvenFilter() {
		RootMap.put("naturalNumbers", new NaturalNumberStream(5));
		ListStream<Integer> naturalNumbers = (ListStream<Integer>) RootMap.get("naturalNumbers");

		BaseListFilterStream<Integer> evenNumbers = new BaseListFilterStream<Integer>(naturalNumbers) {
			@Override
			public boolean evaluate(Integer object) {
				return object % 2 == 0;
			}
		};
	
		assertEquals(new Integer(0),evenNumbers.next());
		assertEquals(new Integer(2),evenNumbers.next());
		assertEquals(new Integer(4),evenNumbers.next());
		assertNull(evenNumbers.next());
		assertEquals(new Integer(0),evenNumbers.get(0));
		assertEquals(new Integer(2),evenNumbers.get(1));
		assertEquals(new Integer(4),evenNumbers.get(2));
	}

	public void testMultipleInputsByNameInSurrondingClass() {
		RootMap.put("naturalNumbers1", new NaturalNumberStream(5));
		RootMap.put("naturalNumbers2", new NaturalNumberStream(6,12));
		final ListStream<Integer> naturalNumbers1 = (ListStream<Integer>) RootMap.get("naturalNumbers1");
		final ListStream<Integer> naturalNumbers2 = (ListStream<Integer>) RootMap.get("naturalNumbers2");
		ListStream<Integer> sum = new BaseListStream0<Integer>(6) {
			@Override
			public Integer invoke(int index) {
				
				return naturalNumbers1.get(index) + naturalNumbers2.get(index);
			}
		}; 
		assertEquals(new Integer(6),sum.get(0));
		assertEquals(new Integer(8),sum.get(1));
	}

	/** This will probably be the preferred method of access. */
	public void testMultipleInputsByNameInClass() {
		RootMap.put("naturalNumbers1", new NaturalNumberStream(5));
		RootMap.put("naturalNumbers2", new NaturalNumberStream(6,12));
		ListStream<Integer> sum = new BaseListStream0<Integer>(6) {
			final ListStream<Integer> naturalNumbers1 = (ListStream<Integer>) RootMap.get("naturalNumbers1");
			final ListStream<Integer> naturalNumbers2 = (ListStream<Integer>) RootMap.get("naturalNumbers2");
			@Override
			public Integer invoke(int index) {
				
				return naturalNumbers1.get(index) + naturalNumbers2.get(index);
			}
		}; 
		assertEquals(new Integer(6),sum.get(0));
		assertEquals(new Integer(8),sum.get(1));
	}

	public void testMultipleInputsByNameInMethod() {
		RootMap.put("naturalNumbers1", new NaturalNumberStream(5));
		RootMap.put("naturalNumbers2", new NaturalNumberStream(6,12));
		ListStream<Integer> sum = new BaseListStream0<Integer>(6) {
			@Override
			public Integer invoke(int index) {
				ListStream<Integer> naturalNumbers1 = (ListStream<Integer>) RootMap.get("naturalNumbers1");
				ListStream<Integer> naturalNumbers2 = (ListStream<Integer>) RootMap.get("naturalNumbers2");
				return naturalNumbers1.get(index) + naturalNumbers2.get(index);
			}
		}; 
		assertEquals(new Integer(6),sum.get(0));
		assertEquals(new Integer(8),sum.get(1));
	}

	public void testMultipleInputsByNameInGroovy() {
		RootMap.put("naturalNumbers1", new NaturalNumberStream(5));
		RootMap.put("naturalNumbers2", new NaturalNumberStream(6,12));
		ListStream<Integer> sum = new BaseListStream0<Integer>(6) {
			@Override
			public Integer invoke(int index) {
				ListStream<Integer> naturalNumbers1 = (ListStream<Integer>) RootMap.get("naturalNumbers1");
				ListStream<Integer> naturalNumbers2 = (ListStream<Integer>) RootMap.get("naturalNumbers2");
				return naturalNumbers1.get(index) + naturalNumbers2.get(index);
			}
		}; 
		assertEquals(new Integer(6),sum.get(0));
		assertEquals(new Integer(8),sum.get(1));
	}

}
