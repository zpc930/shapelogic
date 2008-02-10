package org.shapelogic.scripting;

import junit.framework.TestCase;

/** Test FunctionCalc0.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionCalc0Test extends TestCase {
	
	public void testCreationOfCalc1() {
		FunctionCalc0<Integer> functionTransform = 
			new FunctionCalc0<Integer>("return2_FUNCTION_","def return2_FUNCTION_ = {2};");
		assertNotNull(functionTransform);
		assertEquals(new Integer(2), functionTransform.invoke());
	}
}
