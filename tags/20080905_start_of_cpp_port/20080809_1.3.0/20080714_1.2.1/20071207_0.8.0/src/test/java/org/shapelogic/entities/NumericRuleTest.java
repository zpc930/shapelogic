package org.shapelogic.entities;

import org.shapelogic.logic.SimpleTask;

import junit.framework.TestCase;

/** This test NumericRule independent of Hibernate.
 * 
 * @author Sami Badawi
 *
 */
public class NumericRuleTest extends TestCase 
{
	
	public void testSimpleNumericTask() {
		String OHName = "Sami";
		NumericRule message = new NumericRule(OHName,"number", "person", "number",1d, "SimpleNumericTask");
		NumericRule message2 = new NumericRule(OHName,"age", "person", "age",45d, "SimpleNumericTask");
		
		assertEquals("person.number",message.getVariableAndExpression());
		assertEquals("person.age",message2.getVariableAndExpression());
	}

	public void testParametricRuleTask() {
		String OHName = "Sami";
		NumericRule message = new NumericRule(OHName,"number", "person", "#.number","==",1d);
		NumericRule message2 = new NumericRule(OHName,"age", "person", "#.age","==",45d);
		
		SimpleTask taskNumber = (SimpleTask) message.makeTask(null);
		assertEquals("person.number", taskNumber.getExpression() );
		SimpleTask taskAge = (SimpleTask) message2.makeTask(null);
		assertEquals("person.age",taskAge.getExpression());
	}

}
