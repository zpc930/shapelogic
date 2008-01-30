package org.shapelogic.streams;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/** Test BaseListStream0.
 * 
 * @author Sami Badawi
 *
 */
public class BaseListStream0Test extends TestCase {
	
	/** Create a Stream with base stored in a context.
	 * 
	 * @param base for multiplication table
	 */
	BaseListStream0<Integer> createStreamWithBase(final int base) {
		final Map context = new HashMap();
		context.put("base", base);
		BaseListStream0<Integer> inputNumberStream = 
			new BaseListStream0<Integer>(10) {
				{
					_contexts = new Map[] {context};
				}
				@Override
				public Integer invoke(int index) {
					return ((Integer)getInContext("base")) * index;
				}
		};
		return inputNumberStream;
	}
	
	void testBase(int base) {
		BaseListStream0<Integer> inputNumberStream = createStreamWithBase(base);
		assertEquals(new Integer(base),inputNumberStream.get(1));
	}
	
	/** To test that it is easy to take values out of context.
	 * 
	 */
	public void testMultiplicationTableWithDifferentBase() {
		testBase(2);
		testBase(3);
	}
	
}
