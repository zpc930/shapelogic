package org.shapelogic.streams;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/** Test WrappedListStream.<br />
 * 
 * @author Sami Badawi
 *
 */
public class WrappedListStreamTest extends TestCase {
	final int listLength = 3;
	WrappedListStream<Integer> wrappedListStream;
	
	@Override
	public void setUp() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i=0;i<listLength;i++) {
			list.add(i);
		}
		wrappedListStream = new WrappedListStream<Integer>(list);
		
	}

	public void testIterator() {
		assertNotNull(wrappedListStream);
		for (int i=0;i<listLength;i++) {
			assertTrue(wrappedListStream.hasNext());
			assertEquals(new Integer(i), wrappedListStream.next());
		}
		assertFalse(wrappedListStream.hasNext());
	}

	public void testGet() {
		assertNotNull(wrappedListStream);
		for (int i=0;i<listLength;i++) {
			assertTrue(wrappedListStream.hasNext());
			assertEquals(new Integer(i), wrappedListStream.get(i));
		}
		assertEquals(-1,wrappedListStream.getIndex());
		try {
			assertNull(wrappedListStream.get(listLength)); //XXX should throw an exception
			fail();
		} catch (RuntimeException e) {
		}
	}
}
