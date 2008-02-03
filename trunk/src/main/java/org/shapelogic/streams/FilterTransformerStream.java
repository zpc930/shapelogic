package org.shapelogic.streams;

import org.shapelogic.calculation.Transformer;
import java.util.Map.Entry;

/** FilterStream is the simplest filter Stream.
 * 
 * @author Sami Badawi
 * 
 * This is the same as the Predicate in Apache Commons is called.
 *
 * @param <E> Input and Output are the same
 */
public interface FilterTransformerStream<E,Out> extends Stream<Entry<E, Out> >, Transformer<E, Out> {
}
