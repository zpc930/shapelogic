package org.shapelogic.logic;

import junit.framework.TestCase;

/** Unit test for ParametricRuleTask.
 * 
 * @author Sami Badawi
 *
 */
public class ParametricRuleTaskTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/** String.replaceAll() is using regex, and it is always confusing.
	 * Remember to put the character in [] 
	 */
	public void testReplaceAll() {
		assertEquals("sami", "#".replaceAll("[#]", "sami") );
		assertEquals("sami badawi", "# badawi".replaceAll("[#]", "sami") );
	}
	
	/** Main use of the ParametricRuleTask.transformExpression(), simple replacement
	 */
	public void testTransformExpression() {
		String transformed = ParametricRuleTask.transformExpression("polygon", "#.getHorizontalLines().size()");
		assertEquals("polygon.getHorizontalLines().size()",transformed);
	}

	/** If the # sign is not used nothing should original expression should just be maintained.
	 */
	public void testTransformExpressionWithNoVariable() {
		String transformed = ParametricRuleTask.transformExpression(null, "polygon.getHorizontalLines().size()");
		assertEquals("polygon.getHorizontalLines().size()",transformed);
	}

}
