package org.shapelogic.predicate;

/** Predicate with 1 arguments.
 * <p/>
 * Predicate is close to the Apache Commons Predicate.<p/>
 * 
 * But defined independently since there is to little to justify coupling 
 * ShapeLogic to Apache Commons. <br /> 
 * But can be replaced with Apache Commons Predicate later if needed.
 * 
 * @author Sami Badawi
 *
 */
public interface Predicate<E> {
	
	/** Checks if a binary predicate relation holds. */
	boolean evaluate(E input);
	
}
