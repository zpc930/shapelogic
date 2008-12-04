package org.shapelogic.calculation;

import java.util.List;

/** CalcList is basically a Closure with input as a List.
 * <br />
 * Used in a lazy calculation of a value, and lazy stream with a List input.
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
public interface CalcList<In,Out> {
	Out invoke(List<In> input);
}
