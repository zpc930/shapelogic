package org.shapelogic.streams;

import org.shapelogic.calculation.Transformer;
import java.util.Map.Entry;

/** FilterStream is the simplest filter Stream.
 * <br />
 * 
 * This is a little problematic there are 2 calculation that need to be made.
 * So they cannot both have the same method name: invoke(input) 
 * <br />
 * 
 * You can either take one of them as a Transformer or as a Calc1 member. <br />
 * This one is implementing Transformer <br />
 * 
 * @author Sami Badawi
 * 
 * @param <E> Input and Output are the same
 */
public interface FilterTransformerStream<E,Out> extends Stream<Entry<E, Out> >, Transformer<E, Out> {
}
