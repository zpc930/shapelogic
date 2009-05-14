package org.shapelogic.scripting;

import junit.framework.TestCase;

/** Test FunctionCalcInvoke.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionCalcInvokeTest extends TestCase {
	
	/** Test calculation by function. */
	public void testGetValue2() {
		FunctionEvalCalcInvoke<Integer> calc2 = new FunctionEvalCalcInvoke<Integer>("2"); 
		assertEquals(new Integer(2), calc2.getValue());
		assertEquals(new Integer(2), calc2.invoke());
	}

}
