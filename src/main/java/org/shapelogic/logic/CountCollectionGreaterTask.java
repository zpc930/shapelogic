package org.shapelogic.logic;

import java.util.Collection;

/** When an expression returns a collection this does a count on it.
 * 
 *  It will also work when the expression is returning null and the expected number is 0.
 * 
 * @author Sami Badawi
 *
 */
public class CountCollectionGreaterTask extends SimpleTask {
	public static final double TOLERANCE = 0.0001;
	public CountCollectionGreaterTask(BaseTask parent, boolean createLocalContext,
			String variable,
			String expression, Number excectedValue) {
		super(parent, createLocalContext, variable+"."+expression, excectedValue);
	}

	@Override
	public boolean match() {
		Number excectedValue = (Number) _expected;
		double excectedValueDouble = excectedValue.doubleValue();
		int expressionValue = 0;
		if (_calcValue != null) {
			Collection collection = (Collection) _calcValue;
			expressionValue = collection.size();
		}
		return excectedValueDouble < expressionValue;
	}
}
