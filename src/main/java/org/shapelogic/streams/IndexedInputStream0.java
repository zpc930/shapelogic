package org.shapelogic.streams;

/** Universal calculation and logic unit. 
 * 
 * This is going to replace the current Calc, so this should probably just be 
 * renamed to Calc or LazyCalc.
 * 
 * Should I assume that null means absent of value?
 * That seems a little harsh. 
 * 
 * @author Sami Badawi
 *
 */
public interface IndexedInputStream0 <E> {
	
	/** Closure to calculated 1 individual element based on index and input for 
	 * the same index.
	 * 
	 * Should later just call the invoke on the closure.
	 * 
	 * Maybe this should be moved up later.
	 * 
	 * @param index of element in list
	 */
	E invoke(int index);
}