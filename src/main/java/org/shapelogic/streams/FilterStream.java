package org.shapelogic.streams;

import org.shapelogic.predicate.Predicate;

/** FilterStream is the simplest filter Stream.
 * 
 * @author Sami Badawi
 * 
 * This is the same as the Predicate in Apache Commons is called.
 *
 * @param <E> Input and Output are the same
 */
public interface FilterStream<E> extends Stream<E>, Predicate<E> {
}
