package org.shapelogic.calculation;

import java.util.Iterator;

/** Accumulator. 
 * 
 * Used to calculate say sum or a stream or just an iterator.
 * 
 * @author Sami Badawi
 *
 * @param <In>
 * @param <Out>
 */
public interface Accumulator<In,Out> extends LazyCalc<Out> , Iterator<Out> {
	Iterator<In> getInput();
	/**
	 * return element + out; 
	 */
	Out accumulate(In element,Out out);
	
//	void accumulate();// Maybe use this instead

	Out getPreviousValue();

}
