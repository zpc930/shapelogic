package org.shapelogic.logic;

/**   
 * 
 * @author Sami Badawi
 *
 */
public abstract class ContextCalculation {
	public String name = null;
	public Object value = null;

	public ContextCalculation(String nameIn, Task task) {
		name = nameIn;
		task.setContextCalculation(name, this);
	}

	public ContextCalculation(String nameIn) {
		name = nameIn;
	}

	public ContextCalculation() {
	}

	/** I think that this should first try to find the value in the context.
	 * 
	 * 
	 */
	public Object getValue(Task task) {

		if (value != null)
			return value;
		value = task.getNamedValue(name);
		try {
			value = calculation(task);
			task.setNamedValue(name,value);
			return value;
		} catch (Exception e) {
			e.printStackTrace(); //XXX do something else
			return null;
		}
	}

	public abstract Object calculation(Task context) throws Exception;
}
