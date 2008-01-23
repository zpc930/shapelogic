package org.shapelogic.streams;

/** NumberedStream is a Sequential stream where each element has an intrinsic number.
 * 
 * @author Sami Badawi
 *
 */
public interface InputsStream2 <In0,In1, E> {

	/** Closure to calculated 1 individual element based on index and input for 
	 * the same index.
	 * 
	 * Should later just call the invoke on the closure.
	 * 
	 * Maybe this should be moved up later.
	 * 
	 * @param index of element in list
	 */
	E invoke(In0 input0, In1 input1);
	
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