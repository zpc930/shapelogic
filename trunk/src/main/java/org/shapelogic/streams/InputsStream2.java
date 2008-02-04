package org.shapelogic.streams;

import org.shapelogic.calculation.Calc2;

/** NumberedStream is a Sequential stream where each element has an intrinsic number.
 * 
 * @author Sami Badawi
 *
 */
public interface InputsStream2 <In0,In1, E> extends Calc2<In0,In1, E> {

	/* Last accessed calculated element. 
	 * This should cause a lazy calculation for a calculation. 
	 * Possibly also for this first element.
	 * */
//	E getValue();
	
	/** Stream that this stream is connected to. 
	 * What if the Range is of different type?
	 * I think that maybe I can relax this later.
	 * */
	Stream<In0> getInputStream0();
	Stream<In1> getInputStream1();
}