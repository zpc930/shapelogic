package org.shapelogic.calculation;

/** Transformer method with 1 input and 1 output.<br /> 
 * 
 * Like the Apache Commons Transformer but using generic. 
 * 
 * @author Sami Badawi
 *
 * @param <In> Type of input
 * @param <Out> Type of output
 */
public interface Transformer<In,Out> {
	Out transform(In input);
}
