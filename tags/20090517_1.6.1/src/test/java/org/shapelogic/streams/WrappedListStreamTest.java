package org.shapelogic.streams;

import java.util.ArrayList;
import java.util.List;

import org.shapelogic.util.Constants;

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

	public void testLastModifiedWithHasNext() {
		assertEquals(Constants.LAST_UNKNOWN, wrappedListStream.getLast());
		wrappedListStream.hasNext();
		assertEquals(listLength-1, wrappedListStream.getLast());
	}
	
	public void testLastModifiedWithGet() {
		assertEquals(Constants.LAST_UNKNOWN, wrappedListStream.getLast());
		wrappedListStream.get(0);
		assertEquals(listLength-1, wrappedListStream.getLast());
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
		for (int i=0;wrappedListStream.getLast() != Constants.LAST_UNKNOWN ||
		i <= wrappedListStream.getLast();i++) {
			assertTrue(wrappedListStream.hasNext());
			assertEquals(new Integer(i), wrappedListStream.get(i));
		}
		assertEquals(-1,wrappedListStream.getIndex());
		assertNull(wrappedListStream.get(listLength)); //XXX should throw an exception
	}
}
