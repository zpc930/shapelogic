package org.shapelogic.logic;

import org.shapelogic.util.Constants;

/** Does numerical comparison of an expected value and a found value.
 * 
 * @author Sami Badawi
 *
 */
public class SimpleNumericTask extends SimpleTask {
	public SimpleNumericTask(BaseTask parent, boolean createLocalContext,
			String expression, Number excectedValue) {
		super(parent, createLocalContext, expression, excectedValue);
	}

	@Override
	public boolean match() {
		if (super.match())
			return true;
		else if (_calcValue == null)
			return false;
		Number excectedValue = (Number) _expected;
		Number expressionValue = (Number) _calcValue;
		return Math.abs(excectedValue.doubleValue() - expressionValue.doubleValue()) < Constants.TOLERANCE;
	}
}
