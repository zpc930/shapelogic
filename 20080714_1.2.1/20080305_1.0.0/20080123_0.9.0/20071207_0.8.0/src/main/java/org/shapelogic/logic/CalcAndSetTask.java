package org.shapelogic.logic;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;

/**   
 * 
 * @author Sami Badawi
 * 
 * This only checks that something exists or can be found by a calculation 
 * */
public class CalcAndSetTask<T> extends BaseTask<T> {
	protected String _nameInContext;
	protected Object _expression;
	/** Test is something exist in another context */
	public CalcAndSetTask(BaseTask parent, String nameInContext, Object expression) {
		super(parent, false);
		_nameInContext = nameInContext;
		_expression = expression;
	}
	
	@Override
	public boolean match() {
		_calcValue = (T) findNamedValue(_nameInContext);
		if (_calcValue != null)
			return true;
		if (_expression instanceof String ) {
			Expression e;
			try {
				e = ExpressionFactory.createExpression( (String)_expression );
				_calcValue = (T) e.evaluate(getContext());
			} catch (Exception e1) {
				e1.printStackTrace();
				_calcValue = (T)_expression; 
			}
		}
		else
			_calcValue = (T)_expression;
		setNamedValue(_nameInContext, _calcValue);
		return _calcValue != null;
	}
}
