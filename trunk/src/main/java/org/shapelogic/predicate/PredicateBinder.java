package org.shapelogic.predicate;

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
}
