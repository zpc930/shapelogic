package org.shapelogic.calculation;

import java.util.Map;

/** A very general interface for doing any kind of queries to lazy calculations and streams.
 * 
 * @author Sami Badawi
 * <br />
 * Generic will mainly not be used. The can be expanded with wild cards, but ignore for now.
 * @param <K> key
 * @param <V> value
 */
public class QueryCalc<K,V> implements IQueryCalc<K,V> {
	private static QueryCalc _instance = new QueryCalc();
	
	/** It is a get that will do the lazy calculation.
	 * 
	 * @param key
	 * @param map sequence of maps to do lookup in starting from the last
	 * @return the value of the first lookup found from the back
	 */
	@Override
	public V get(K key, Map<K,?> ... maps) {
		for (int i = maps.length-1; 0 <= i; i--) {
			Map<K,?> map = maps[i];
			Object result = map.get(key);
			if (result instanceof Calc) {
				if (result instanceof LazyCalc) {
					if (!((LazyCalc)result).isDirty())
						return (V)((Calc)result).getValue();
					
				}
				if (result instanceof CalcInContext) {
					((CalcInContext)result).calc(maps);
				}
				return (V)((Calc)result).getValue();
			}
			if (result != null)
				return (V)result;
		}
		return null;
	}
	
	@Override
	public V get(K key, InContexts<K> inContexts) {
		return (V) get(key, inContexts.getContexts());
	}
	
	static public QueryCalc getInstance() {
		return _instance;
	} 
}
