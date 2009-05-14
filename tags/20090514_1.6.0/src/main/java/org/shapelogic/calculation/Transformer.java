package org.shapelogic.calculation;

/** Transformer method with 1 input and 1 output.<br /> 
 * 
 * Like the Apache Commons Transformer but using generic.<br /> 
 * 
 * Deprecated instead use Calc1.<br />
 * 
 * One use of this if a class need to have 2 differently named calculations. 
 * 
 * @author Sami Badawi
 *
 * @param <In> Type of input
 * @param <Out> Type of output
 */
@Deprecated
public interface Transformer<In,Out> {
	Out transform(In input);
}
