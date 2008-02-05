package org.shapelogic.calculation;

/** Transform that know what index in a sequence it is transforming.<br /> 
 * 
 * So it will take the same index from both the input and the output.<br /> 
 * It will do outSeq.get(index) = transform(inSeq.get(index),index).<br /> 
 * 
 * Deprecated instead use Calc1.<br />
 * 
 * @author Sami Badawi
 *
 * @param <In> Type of input
 * @param <Out> Type of output
 */
@Deprecated
public interface IndexTransform<In,Out> {
	Out transform(In input, int index);
}
