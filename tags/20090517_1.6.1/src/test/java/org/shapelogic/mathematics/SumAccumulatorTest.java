package org.shapelogic.mathematics;


import junit.framework.TestCase;

/** Test AddAccumulator. 
 * 
 * @author Sami Badawi
 *
 */
public class SumAccumulatorTest extends TestCase {
	public void test(){
		NaturalNumberStream naturalNumberStream = new NaturalNumberStream(2);
		SumAccumulator sum = new SumAccumulator(naturalNumberStream);
		assertEquals(new Long(3), sum.getValue());
	}
}
