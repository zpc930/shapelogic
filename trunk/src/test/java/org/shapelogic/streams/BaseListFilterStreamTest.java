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
	public void test() {
		final NaturalNumberStream naturalNumberStream = new NaturalNumberStream(2);
		BaseListFilterStream<Integer> evenFilter = new BaseListFilterStream<Integer>(naturalNumberStream) {
			@Override
			public boolean evaluate(Integer object) {
				return object % 2 == 0;
			}
		};
		assertNotNull(naturalNumberStream);
		assertEquals(new Integer(0),evenFilter.next());
		assertEquals(new Integer(2),evenFilter.next());
		assertNull(naturalNumberStream.next());
	}
}
