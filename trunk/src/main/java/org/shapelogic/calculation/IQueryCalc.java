package org.shapelogic.calculation;

import java.util.Map;

/** A very general interface for doing any kind of queries to lazy calculations and streams.
 * <br />
 * Generic will mainly not be used. The can be expanded with wild cards, but ignore for now.
 * 
 * @author Sami Badawi 
 * 
 * @param <K> key
 * @param <V> value
 */
public interface IQueryCalc<K,V> {
	
	/** It is a get that will do the lazy calculation.
	 * 
	 * @param key
	 * @param map sequence of maps to do lookup in starting from the last
	 * @return the value of the first lookup found from the back
	 */
	V get(K key, Map<K,?> ... maps);

	/** It is a get that will do the lazy calculation.
	 * 
	 * @param key
	 * @param inContexts class that contains the contexts that this is to be done in.
	 * @return
	 */
	V get(K key, RecursiveContext<K> inContexts);
}
