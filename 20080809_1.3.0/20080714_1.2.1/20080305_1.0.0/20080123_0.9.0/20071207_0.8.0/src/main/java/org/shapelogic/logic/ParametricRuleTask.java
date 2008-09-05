package org.shapelogic.logic;

import org.shapelogic.predicate.BinaryPredicate;
import org.shapelogic.predicate.BinaryPredicateFactory;

/** More general task that can create all the other tasks, by taking 2 parameters.
 * <p>
 * 1: A variable that the expression should work on<br/>
 * 2: An expression that can contain the #, which will be replaced with the variable <br/>
 * 
 * <p>
 * The constructor takes a <code>BinaryPredicate</code> as an argument.
 * 
 * @author Sami Badawi
 *
 */
public class ParametricRuleTask extends SimpleTask {
	protected BinaryPredicate _binaryPredicate; 
	public ParametricRuleTask(BaseTask parent, boolean createLocalContext,
			String variable, String expression, Object expectedValue, String binaryPredicateName) {
		super(parent, createLocalContext, transformExpression(variable,expression), expectedValue);
		_binaryPredicate = BinaryPredicateFactory.getInstance(binaryPredicateName);
	}

	@Override
	public boolean match() {
		return _binaryPredicate.evaluate(_calcValue, _expected);
	}
	
	/** Transform an expression and a variable to an expression where the 
	 * variable has been substituted for #.
	 * 
	 * E.g.  "polygon", "#.getHorizontalLines().size()" -> "polygon.getHorizontalLines().size()" 
	 * 
	 * @param variable in a JEXL context
	 * @param expression where # is a place holder for the variable
	 * @return the transformed expression 
	 */
	static public String transformExpression(String variable, String expression) {
		String transformedExpression = expression;
		return transformedExpression.replaceAll("[#]", variable);
	}
}
