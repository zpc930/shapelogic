package org.shapelogic.streams;

/** FilterStream is the simplest filter Stream.
 * 
 * @author Sami Badawi
 * 
 * This is the same as the Predicate in Apache Commons is called.
 *
 * @param <E> Input and Output are the same
 */
public interface FilterStream<E> extends Stream<E> {
	boolean evaluate(E object); 
}
