package org.shapelogic.streams;

import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.streams.BaseListFilterStream;

import junit.framework.TestCase;

/** Test for filtering on Natural numbers.
 * 
 * @author Sami Badawi
 *
 */
public class BaseListFilterStreamTest extends TestCase {
	
	BaseListFilterStream<Integer> createEvenNaturalNumberStream() {
		final NaturalNumberStream naturalNumberStream = new NaturalNumberStream(2);
		BaseListFilterStream<Integer> evenFilter = new BaseListFilterStream<Integer>(naturalNumberStream) {
			@Override
			public boolean evaluate(Integer object) {
				return object % 2 == 0;
			}
		};
		return evenFilter;
	}

	public void testEvenNaturalNumberStream() {
		BaseListFilterStream<Integer> evenFilter = createEvenNaturalNumberStream();
		assertEquals(new Integer(0),evenFilter.next());
		assertEquals(new Integer(2),evenFilter.next());
		assertNull(evenFilter.next());
	}

	public void testEvenNaturalNumberStreamUsingGet() {
		BaseListFilterStream<Integer> evenFilter = createEvenNaturalNumberStream();
		assertEquals(new Integer(0),evenFilter.get(0));
		assertEquals(new Integer(2),evenFilter.get(1));
		assertNull(evenFilter.next());
	}
}
