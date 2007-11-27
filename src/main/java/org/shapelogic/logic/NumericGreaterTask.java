package org.shapelogic.logic;

/** Does numerical comparison of an expected value and a found value
 * Checks that calculated value is greater than the expected value
 *  
 * @author Sami Badawi
 *
 */
public class NumericGreaterTask extends SimpleTask {
	public static final double TOLERANCE = 0.0001;
	public NumericGreaterTask(BaseTask parent, boolean createLocalContext,
			String expression, Number excectedValue) {
		super(parent, createLocalContext, expression, excectedValue);
	}

	@Override
	public boolean match() {
		if (_calcValue == null)
			return false;
		Number excectedValue = (Number) _expected;
		Number expressionValue = (Number) _calcValue;
		return excectedValue.doubleValue() < expressionValue.doubleValue();
	}
}
