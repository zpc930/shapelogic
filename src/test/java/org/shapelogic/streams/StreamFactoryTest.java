package org.shapelogic.streams;

import org.shapelogic.calculation.RootMap;
import org.shapelogic.mathematics.NaturalNumberStream;

import junit.framework.TestCase;

public class StreamFactoryTest extends TestCase {
	final static String naturalNumberStreamTo3String = "naturalNumberStreamTo3";
	final static String interval1To2 = "interval1To2";
	NumberedStream<Integer> naturalNumberStreamTo3;
	
	
	@Override
	public void setUp() {
		RootMap.clear();
		naturalNumberStreamTo3 = new NaturalNumberStream(3);
		RootMap.put(naturalNumberStreamTo3String, naturalNumberStreamTo3);
	}
	
	public void testAddToAndListStream0Arg0() {
		StreamFactory.addToAndListStream0(interval1To2, 
				naturalNumberStreamTo3String,  
				">", 
				new Integer(0));
		NumberedStream<Integer> stream = naturalNumberStreamTo3;
		assertNotNull(stream);
		assertTrue(stream.hasNext());
		assertEquals(new Integer(0),stream.next());
		assertEquals(new Integer(1),stream.next());
		assertEquals(new Integer(2),stream.next());
		assertEquals(new Integer(3),stream.next());
		assertFalse(stream.hasNext());
		assertEquals(null,stream.next());
	}
	
	public void testAddToAndListStream0Arg1() {
		StreamFactory.addToAndListStream0(interval1To2, 
				naturalNumberStreamTo3String,  
				">", 
				new Integer(0));
		NumberedStream<Integer> stream = (NumberedStream<Integer>) RootMap.get(interval1To2);
		assertNotNull(stream);
		assertTrue(stream.hasNext());
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
//		assertFalse(stream.hasNext()); //XXX enable this
//		assertEquals(Boolean.TRUE,stream.next()); //XXX enable this
	}

	public void testAddToAndListStream0Arg2() {
		StreamFactory.addToAndListStream0(interval1To2, 
				naturalNumberStreamTo3String,  
				">", 
				new Integer(0));
		StreamFactory.addToAndListStream0(interval1To2, 
				naturalNumberStreamTo3String,  
				"<", 
				new Integer(3));
		NumberedStream<Integer> stream = (NumberedStream<Integer>) RootMap.get(interval1To2);
		assertNotNull(stream);
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
		
	}
}
