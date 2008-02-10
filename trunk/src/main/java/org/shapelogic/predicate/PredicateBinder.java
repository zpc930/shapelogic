package org.shapelogic.predicate;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.CalcIndex0;

/** Transform predicates. 
 * <br />
 * E.g. binary predicates to unary predicates. <br />
 * 
 * @author Sami Badawi
 *
 */
public class PredicateBinder {
	
	/** Transform  binary predicates to unary predicates. 
	 * Bind the element 0 to the given bindObject */
	static public <In0,In1> Predicate<In1> bind0(final BinaryPredicate<In0,In1> binaryPredicate, 
			final In0 bindObject) {
		return new Predicate<In1>() {
			@Override
			public boolean evaluate(In1 input) {
				return binaryPredicate.evaluate(bindObject, input);
			}
		};
	}

	/** Transform  binary predicates to unary predicates. 
	 * Bind the element 1 to the given bindObject */
	static public <In0,In1> Predicate<In0> bind1(final BinaryPredicate<In0,In1> binaryPredicate, 
			final In1 bindObject) {
		return new Predicate<In0>() {
			@Override
			public boolean evaluate(In0 input) {
				return binaryPredicate.evaluate(input, bindObject);
			}
		};
	}

	/** Predicate and Calc has different signature this is an adapter method.
	 * <br />
	 * It creates a thin wrapper.
	 * 
	 * @param calc
	 * @return
	 */
	static public Predicate<Integer> calcIndex0ToPredicate(
			final CalcIndex0<Boolean> calc) 
	{
		return new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer input) {
				return calc.invoke(input);
			}
		};
	}

	/** Predicate and Calc has different signature this is an adapter method.
	 * <br />
	 * It creates a thin wrapper.
	 * 
	 * @param calc
	 * @return
	 */
	static public <In> Predicate<In> calc1ToPredicate(
			final Calc1<In,Boolean> calc) 
	{
		return new Predicate<In>() {
			@Override
			public boolean evaluate(In input) {
				return calc.invoke(input);
			}
		};
	}

	/** Combining a Stream represented as a CalcIndex0 with a binary Predicate. 
	 * 
	 * @param <In1>
	 * @param binaryPredicate
	 * @param calc
	 * @return
	 */
	static public <In0, In1, In2> Predicate<In2> bind0(
			final BinaryPredicate<In0, In1> binaryPredicate, 
			final Calc1<In2,In1> calc,
			final In0 bindObject) 
	{
		return new Predicate<In2>() {

			@Override
			public boolean evaluate(In2 input) {
				return binaryPredicate.evaluate(bindObject, calc.invoke(input));
			}
		};
	}
}
