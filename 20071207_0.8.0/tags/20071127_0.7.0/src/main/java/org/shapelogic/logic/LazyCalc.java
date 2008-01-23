package org.shapelogic.logic;

/**   
 * 
 * @author Sami Badawi
 *
 */
public interface LazyCalc<T> extends Calc<T> {

	boolean isDirty();
	void setup();
}
