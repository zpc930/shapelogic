package org.shapelogic.logic;

/** A calculation that lives in a context.
 *
 * If the calculation has already finished then just take the value
 * 
 * In order to create a context calculation override this class 
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

	/** This should overridden with the calculation that should be done.
	 * 
	 *  This should just return the cached value if the dirty flag is false
	 * 
	 * @param context
	 * @return result of calculation
	 * @throws Exception
	 */
	public abstract Object calculation(Task context) throws Exception;
}
