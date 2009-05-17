package org.shapelogic.streams;

import org.shapelogic.calculation.Calc1;

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
public interface InputStream1 <In, E> extends Calc1<In, E> {
	
	/* Last accessed calculated element. 
	 * This should cause a lazy calculation for a calculation. 
	 * Possibly also for this first element.
	 * */
//	E getValue();
	
	/** Stream that this stream is connected to. 
	 * What if the Range is of different type?
	 * I think that maybe I can relax this later.
	 * */
	Stream<In> getInputStream();
	
	In getInput(int index);
	
}