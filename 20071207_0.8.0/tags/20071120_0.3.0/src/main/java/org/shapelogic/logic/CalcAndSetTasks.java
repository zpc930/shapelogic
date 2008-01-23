package org.shapelogic.logic;

/** This task is inserting values into the context tree  
 * 
 * @author Sami Badawi
 *
 */
public class CalcAndSetTasks extends AndTask {
	Object[] _nameInContext;
	/** Test is something exist in another context */
	public CalcAndSetTasks(BaseTask parent, Object ... nameInContext) {
		super(parent,false);
		_nameInContext = nameInContext;
		for (int i = _nameInContext.length/2 - 1; i >= 0; i--) {
			String fieldName = _nameInContext[2 * i].toString();
			Object expression = _nameInContext[2 * i + 1];
			new CalcAndSetTask(this, fieldName, expression);
		}
	}
}
