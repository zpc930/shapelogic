package org.shapelogic.calculation;

/** Top interface for calculations.
 * 
 * @author Sami Badawi
 *
 */
public interface CalcValue <T> {
	/** Starts a lazy calculation.
	 * If dirty do calc() else return cached value.
	 * 
	 *  This should maybe be moved up in the hierarchy
	 */
	T getValue();
}
