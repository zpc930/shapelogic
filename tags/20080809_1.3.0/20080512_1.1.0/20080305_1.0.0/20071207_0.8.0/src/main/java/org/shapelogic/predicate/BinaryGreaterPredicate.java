package org.shapelogic.predicate;

/** Standard binary greater predicate. 
 * <p/>
 * First try see if there is a normal equals match, then try to cast to number 
 * and does comparison.
 * 
 * @author Sami Badawi
 *
 */
public class BinaryGreaterPredicate implements BinaryPredicate {
	public static final String GREATER = ">";
	
	@Override
	public boolean evaluate(Object left, Object right) {
		if (left == null || right == null)
			return false;
		else {
			if (left instanceof Number && right instanceof Number) {
				Number leftNumber = (Number) left;
				Number rightNumber = (Number) right;
				return leftNumber.doubleValue() > rightNumber.doubleValue();
			}
			else
				//XXX Expand to comparable values 
				return false;
		}
	}

	@Override
	public String getName() {
		return GREATER;
	}
}
