package org.shapelogic.logic;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;

/** Give an expression this is evaluated in the current context
 * If it returns true the task passes else it fails
 * 
 * @author Sami Badawi
 *
 */
public class BooleanTask extends BaseTask {

	protected String _expression;

	public BooleanTask(BaseTask parent, boolean createLocalContext, String variable, String expression)
	{
		super(parent, createLocalContext);
		_expression = ParametricRuleTask.transformExpression(variable,expression);
	}
	
	/** Does the lazy calculation
	 * 
	 */
	/** Can be overridden
	 */
	public boolean match() {
		if (_value == null)
			return false;
		return Boolean.TRUE.equals(_value);
	}

	public void setup() {
		Expression e;
		try {
			e = ExpressionFactory.createExpression( _expression );
			_value = e.evaluate(getContext());
		} catch (Exception e1) {
			System.out.println("Error expression could not be evaluated: " +_expression);
			e1.printStackTrace();
		}
	}
}
