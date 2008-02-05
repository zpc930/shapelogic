package org.shapelogic.predicate;

/** Predicate with 2 arguments.
 * <p/>
 * BinaryPredicate is close to the Apache Commons BinaryPredicateBinaryFunction.<p/>
 * 
 * But defined independently since Commons Functor lib is still a sand box lib.
 * 
 * @author Sami Badawi
 *
 */
public interface BinaryPredicate<In0,In1> {
	
	/** Checks if a binary predicate relation holds. */
	boolean evaluate(In0 left, In1 right);
	
	/** Used in factory. */
	String getName();
}
