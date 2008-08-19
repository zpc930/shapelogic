package org.shapelogic.calculation;

import java.util.Map;

/** BaseCalcInContext normal base class for CalcInContext. <br />
 * 
 * @author Sami Badawi
 *
 * @param <K> Input type for calculation
 * @param <V> Output type for calculation
 */
abstract public class BaseCalcInContext<K,V> implements CalcInContext<K,V> {
	protected boolean _dirty = true;
	//It seems a little redundant to have the key both in the map and here
	protected K _key; 
	protected V _value;
	protected QueryCalc<K,V> _query = QueryCalc.getInstance();
	protected int _topLevelUsed = 0;
	
	@Override
	abstract public V invoke(RecursiveContext<K> contextArray);
	
	public BaseCalcInContext(K key) {
		_key = key;
	}
	
	@Override
	public V calc(RecursiveContext<K> recursiveContext) {
		_value = invoke(recursiveContext);
		Map map = recursiveContext.getContext();
		map.put(_key, _value);
		_dirty = false;
		return _value;
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public void setup() {
	}

	@Override
	public V getValue() {
		return _value;
	}
}
