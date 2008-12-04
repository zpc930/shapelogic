package org.shapelogic.streams;

import org.shapelogic.mathematics.NaturalNumberStream;

import junit.framework.TestCase;

/** Test AddListStream.
 * 
 * @author Sami Badawi
 *
 */
public class AddListStreamTest extends TestCase {
	
	public void testAddListStream0() {
		AddListStream addListStream = new AddListStream();
		
		assertEquals(new Integer(0),addListStream.get(0));
		assertEquals(new Integer(0),addListStream.get(1));
	}
	
	public void testAddListStream1() {
		AddListStream addListStream = new AddListStream();
		addListStream.getInputStream().add(new NaturalNumberStream());
		
		assertEquals(new Integer(0),addListStream.get(0));
		assertEquals(new Integer(1),addListStream.get(1));
	}
	
	public void testAddListStream2() {
		AddListStream addListStream = new AddListStream();
		addListStream.getInputStream().add(new NaturalNumberStream());
		addListStream.getInputStream().add(new NaturalNumberStream());
		
		assertEquals(new Integer(0),addListStream.get(0));
		assertEquals(new Integer(2),addListStream.get(1));
	}
}
