package org.shapelogic.calculation;


/** Interface for calculations with a dirty and setup concept.
 * 
 * @author Sami Badawi
 *
 */
public interface LazyCalc<T> extends Calc<T> {

	/** When dirty is false that means that the calculated value can be used
	 */
	boolean isDirty();
	
	/** Currently not super well define used for 2 purposes: reset and init.
	 * TODO: This should be separated better.
	 */
	void setup();
}
