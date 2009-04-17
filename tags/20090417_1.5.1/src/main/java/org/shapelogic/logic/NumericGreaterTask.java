package org.shapelogic.logic;

/** Does numerical comparison of an expected value and a found value
 * Checks that calculated value is greater than the expected value
 * 
 * There are several version doing related tasks:
 * This is the simple version that does not do a size() operation for you
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
		if (_value == null)
			return false;
		Number excectedValue = (Number) _expected;
		Number expressionValue = (Number) _value;
		return excectedValue.doubleValue() < expressionValue.doubleValue();
	}
}
