package org.shapelogic.streams;

import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.streams.BaseListStream2;

import junit.framework.TestCase;

/** Test BaseListStream2.
 * 
 * @author Sami Badawi
 *
 */
public class BaseListStream2Test extends TestCase {
	BaseListStream2<Integer, Integer, Integer> _baseListStream2;
	NaturalNumberStream naturalNumberStream1;  
	NaturalNumberStream naturalNumberStream2;  

	public void init() {
		_baseListStream2 = new BaseListStream2<Integer, Integer, Integer>(
				naturalNumberStream1,naturalNumberStream2) {
					@Override
					public Integer invoke(Integer input0, Integer input1,
							int index) {
						return input0 + input1;
					}
		};
	}
	
	public void testCartesian(){
		naturalNumberStream1 = new NaturalNumberStream(0,1);
		naturalNumberStream2 = new NaturalNumberStream(100,101);
		init();
		assertEquals(new Integer(100),_baseListStream2.next());
		assertEquals(new Integer(101),_baseListStream2.next());
		assertEquals(new Integer(101),_baseListStream2.next());
		assertTrue(_baseListStream2.hasNext());
		assertEquals(new Integer(102),_baseListStream2.next());
		assertFalse(_baseListStream2.hasNext());
	}

	public void testCartesianOfSame(){
		naturalNumberStream1 = new NaturalNumberStream(0,1);
		naturalNumberStream2 = naturalNumberStream1;
		init();
		assertEquals(new Integer(0),_baseListStream2.next());
		assertEquals(new Integer(1),_baseListStream2.next());
		assertEquals(new Integer(1),_baseListStream2.next());
		assertTrue(_baseListStream2.hasNext());
		assertEquals(new Integer(2),_baseListStream2.next());
		assertFalse(_baseListStream2.hasNext());
	}
}
