package org.shapelogic.predicate;

import org.shapelogic.util.Constants;

/** Standard binary equal predicate. 
 * <p/>
 * First try see if there is a normal equals match, then try to cast to number 
 * and does comparison with tolerance.
 * 
 * @author Sami Badawi
 *
 */
public class BinaryEqualPredicate implements BinaryPredicate {
	public static final String EQUALS = "==";  
	@Override
	public boolean evaluate(Object left, Object right) {
		if (left == right)
			return true;
		else if (left == null)
			return false;
		else if (left.equals(right))
			return true;
		else {
			if (left instanceof Number && right instanceof Number) {
				Number leftNumber = (Number) left;
				Number rightNumber = (Number) right;
				return Math.abs(leftNumber.doubleValue() - rightNumber.doubleValue()) < Constants.TOLERANCE;
			}
			else
				return false;
		}
	}

	@Override
	public String getName() {
		return EQUALS;
	}
}
