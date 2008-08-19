package org.shapelogic.calculation;

import java.util.Map;

import org.shapelogic.streams.Stream;

/** A very general interface for doing any kind of queries to lazy calculations and streams.
 * <br />
 * Generic will mainly not be used. The can be expanded with wild cards, but ignore for now.
 * 
 * @author Sami Badawi
 * 
 * @param <K> key
 * @param <V> value
 */
public class QueryCalc<K,V> implements IQueryCalc<K,V> {
	private static QueryCalc _instance = new QueryCalc();
	
	static public QueryCalc getInstance() {
		return _instance;
	}

	/** It is a get that will do the lazy calculation.
	 * <br />
	 * This should not be used to get a Stream since this is treated as a 
	 * CalcValue and the value is returned instead of the stream.
	 * 
	 * @param key
	 * @param map sequence of maps to do lookup in starting from the last
	 * @return the value of the first lookup found from the back
	 */
	@Override
	public V get(K key, Map<K,?> ... maps) {
		for (int i = maps.length-1; 0 <= i; i--) {
			final Map map = maps[i];
			Object result = map.get(key);
			if (result instanceof CalcValue && (!(result instanceof Stream))) {
				if (result instanceof LazyCalc) {
					if (!((LazyCalc)result).isDirty())
						return (V)((CalcValue)result).getValue();
					
				}
				if (result instanceof CalcInContext) {
					RecursiveContext rc = new RecursiveContext() {
						@Override
						public Map getContext() {
							return map;
						}
						@Override
						public RecursiveContext getParentContext() {
							return null;
						}
					}; 
					result = ((CalcInContext)result).calc(rc);
				}
				else
					result = ((CalcValue)result).getValue();
			}
			if (result != null) {
				map.put(key, result);
				return (V)result;
			}
		}
		return null;
	}
	
	@Override
	public V get(K key, RecursiveContext<K> recursiveContexts) {
		do {
			Map<K,?> map = recursiveContexts.getContext();
			Object result = map.get(key);
			if (result instanceof CalcValue && (!(result instanceof Stream))) {
				if (result instanceof LazyCalc) {
					if (!((LazyCalc)result).isDirty())
						return (V)((CalcValue)result).getValue();
					
				}
				if (result instanceof CalcInContext) {
					return (V)((CalcInContext)result).calc(recursiveContexts);
				}
				return (V)((CalcValue)result).getValue();
			}
			if (result != null)
				return (V)result;
			recursiveContexts = recursiveContexts.getParentContext();
		}
		while (recursiveContexts != null);
		return null;
	}
	
	/** Put in the first non null context in a RecursiveContext. */
	@Override
	public void put(K key, V value, RecursiveContext<K> recursiveContext) {
		do {
			Map map = recursiveContext.getContext();
			if (map == null)
				recursiveContext = recursiveContext.getParentContext();
			else {
				map.put(key, value);
			}
		}
		while (recursiveContext != null);
	} 
}
