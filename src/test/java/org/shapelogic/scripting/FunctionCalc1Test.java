package org.shapelogic.scripting;

import junit.framework.TestCase;

/** Test FunctionCalc1.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionCalc1Test extends TestCase {
	
	public void testCreationOfTransformer() {
		FunctionCalc1<Integer, Integer> functionTransform = 
			new FunctionCalc1<Integer, Integer>("times2_FUNCTION_","def times2_FUNCTION_ = {it*2};");
		assertNotNull(functionTransform);
		assertEquals(new Integer(2), functionTransform.invoke(1));
	}
}
