package org.shapelogic.calculation;

/** Calc2 is basically a Closure with 1 input, and one index.
 * <br />
 * Used in a lazy calculation of a value, and lazy stream with 2 input, and one index.
 * <br />
 * The goal is to make lazy calculation and lazy streams as interchangeable as possible.
 * <br />
 * Using the same names as Neal Gafter's Closure prototype. 
 * Might be changed to be a sub interface of them later.
 * <br />
 * 
 * @author Sami Badawi
 *
 * @param <Out>
 */
public interface CalcIndex2<In0,In1,Out> {
	Out invoke(In0 input0, In1 input1, int index);
}
