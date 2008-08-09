package org.shapelogic.logic;

/** FilterCountTask is a new type of filter expression
 * The advantage of using this over the SimpleNumericTask is that you can do 
 * boolean expression of the filter expressions   
 * 
 * @author Sami Badawi
 *
 */
public class FilterCountTask extends SimpleNumericTask {

	public FilterCountTask(BaseTask parent, boolean createLocalContext,
			String variable,
			String expression, Number excectedValue) {
		super(parent, createLocalContext, makeFullExpression(variable,expression), excectedValue);
	}

	static public String makeFullExpression(String variable, String filterExpression) {
		String fullExpression = "";
		if (variable != null)
			fullExpression += variable + ".filter('" + filterExpression + "').size()";
		return fullExpression;
	}
}
