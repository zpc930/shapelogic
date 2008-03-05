package org.shapelogic.logic;

/** This only checks that something exists or can be found by a calculation 
 * 
 * @author Sami Badawi
 *
 */
public class ExistTasks extends AndTask {
	String[] _nameInContext;
	/** Test is something exist in another context */
	public ExistTasks(BaseTask parent, String ... nameInContext) {
		super(parent,false);
		_nameInContext = nameInContext;
		for (String fieldName: _nameInContext) {
			new ExistTask(this, fieldName);
		}
	}
}
