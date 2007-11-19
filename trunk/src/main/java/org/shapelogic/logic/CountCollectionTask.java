package org.shapelogic.logic;

import java.util.Collection;

/** When an expression returns a collection this does a count on it.
 * 
 *  It will also work when the expression is returning null and the expected number is 0.
 * 
 * @author Sami Badawi
 *
 */
public class CountCollectionTask extends SimpleTask {
	public static final double TOLERANCE = 0.0001;
	public CountCollectionTask(BaseTask parent, boolean createLocalContext,
			String variable,
			String expression, Number excectedValue) {
		super(parent, createLocalContext, variable+"."+expression, excectedValue);
	}

	@Override
	public boolean match() {
		Number excectedValue = (Number) _expected;
		double excectedValueDouble = excectedValue.doubleValue();
		if (super.match())
			return true;
		else if (_calcValue == null)
			return Math.abs(excectedValueDouble) < TOLERANCE;
		Collection collection = (Collection) _calcValue;
		int expressionValue = collection.size();
		return Math.abs(excectedValueDouble - expressionValue) < TOLERANCE;
	}
}
