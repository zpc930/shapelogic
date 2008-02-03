package org.shapelogic.predicate;

import org.shapelogic.util.OHInterface;

/** NamedPredicate is a predicate with and OH name, Object Hypothesis name.
 * <br />
 * This is used for XOR or OnePredicate. You want to know what predicate that 
 * was satisfied.
 * 
 * @author Sami Badawi
 *
 * @param <E>
 */
public interface NamedPredicate<T> extends Predicate<T>, OHInterface {

}
