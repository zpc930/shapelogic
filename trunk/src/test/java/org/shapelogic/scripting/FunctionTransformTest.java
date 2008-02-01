package org.shapelogic.scripting;

import junit.framework.TestCase;

/** Test FunctionTransform.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionTransformTest extends TestCase {
	
	public void testCreationOfTransformer() {
		FunctionTransform<Integer, Integer> functionTransform = 
			new FunctionTransform<Integer, Integer>("times2_FUNCTION_","def times2_FUNCTION_ = {it*2};");
		assertNotNull(functionTransform);
		assertEquals(new Integer(2), functionTransform.transform(1));
	}
}
