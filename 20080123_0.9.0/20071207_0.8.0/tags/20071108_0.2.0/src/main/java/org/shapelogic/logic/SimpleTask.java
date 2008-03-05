package org.shapelogic.logic;

import javax.persistence.Transient;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class SimpleTask extends BaseTask {

	protected String _expression;
	protected Object _excected;

	public SimpleTask(BaseTask parent, boolean createLocalContext, String expression, Object excectedValue)
	{
		super(parent, createLocalContext);
		_excected = excectedValue;
		_expression = expression;
	}
	
	/** Does the lazy calculation
	 * 
	 */
	/** Can be overridden
	 */
	public boolean match() {
		if (_excected == null && _calcValue == null)
			return true;
		else if (_calcValue == null)
			return false;
		return _excected.equals(_calcValue);
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
