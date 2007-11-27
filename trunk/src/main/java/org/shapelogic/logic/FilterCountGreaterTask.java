package org.shapelogic.logic;

/** FilterCountTask is a new type of filter expression
 * The advantage of using this over the SimpleNumericTask is that you can do 
 * boolean expression of the filter expressions   
 * 
 * @author Sami Badawi
 *
 */
public class FilterCountGreaterTask extends NumericGreaterTask {

	public FilterCountGreaterTask(BaseTask parent, boolean createLocalContext,
			String variable,
			String expression, Number excectedValue) {
		super(parent, createLocalContext, FilterCountTask.makeFullExpression(variable,expression), excectedValue);
	}
}
