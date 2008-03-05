package org.shapelogic.logic;

/**   
 * 
 * @author Sami Badawi
 *
 */
public interface Calc <T> {
	T calc(); //For now there is just one eager executer
	
	/** If dirty do calc() else return cached value.
	 * 
	 *  This should maybe be moved up in the hierarchy
	 */
	T getCalcValue();
}
