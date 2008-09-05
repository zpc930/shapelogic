package org.shapelogic.logic;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;

/** Base class for tasks where you have an expression to be evaluated in 
 * local context and an expected value
 * 
 * @author Sami Badawi
 *
 */
public class SimpleTask extends BaseTask {

	protected String _expression;
	protected Object _expected;

	public SimpleTask(BaseTask parent, boolean createLocalContext, String expression, Object expectedValue)
	{
		super(parent, createLocalContext);
		_expected = expectedValue; //expected
		_expression = expression;
	}
	
	/** Does the lazy calculation
	 * 
	 */
	/** Can be overridden
	 */
	public boolean match() {
		if (_expected == null && _calcValue == null)
			return true;
		else if (_calcValue == null)
			return false;
		return _expected.equals(_calcValue);
	}

	public void setup() {
		Expression e;
		try {
			e = ExpressionFactory.createExpression( _expression );
			_calcValue = e.evaluate(getContext());
		} catch (Exception e1) {
			System.out.println("Error expression could not be evaluated: " +_expression);
			e1.printStackTrace();
		}
	}
}
