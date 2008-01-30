package org.shapelogic.calculation;

/** ContextGettable has contexts and ability to do a get in them. <br />
 * 
 * Will often use QueryCalc but this is not used for anything else.
 * 
 * @author Sami Badawi
 *
 * @param <K> Key type
 * @param <V> Value type of result
 */
public interface ContextGettable<K,V> extends InContexts<K> {
	V getInContext(K key);
}
