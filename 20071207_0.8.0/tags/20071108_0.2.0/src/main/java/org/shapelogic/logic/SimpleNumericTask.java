package org.shapelogic.logic;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class SimpleNumericTask extends SimpleTask {
	public static final double TOLERANCE = 0.0001;
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
		Number excectedValue = (Number) _excected;
		Number expressionValue = (Number) _calcValue;
		return Math.abs(excectedValue.doubleValue() - expressionValue.doubleValue()) < TOLERANCE;
	}
}
