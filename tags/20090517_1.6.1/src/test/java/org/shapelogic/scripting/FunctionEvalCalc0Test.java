package org.shapelogic.scripting;

import junit.framework.TestCase;

/** Test FunctionEvalCalc0.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionEvalCalc0Test extends TestCase {
	
	/** Test calculation by function. */
	public void testGetValue2() {
		FunctionCalcInvoke<Integer> calc2 = new FunctionCalcInvoke<Integer>(
			"getValue2", "def getValue2 = {2};"
		); 
		assertEquals(new Integer(2), calc2.getValue());
	}

}
