package org.shapelogic.calculation;

import java.util.HashMap;
import java.util.Map;

import org.shapelogic.scripting.ScriptEngineCache;

/** This is the RootContext where all the certain objects are stored. <br />
 * 
 * There will be a lot of calculations and streams here. <br />
 * 
 * Currently implemented as a kind of a singleton. <br />
 * This might be changed if more contexts need to be active at the same time.<br />
 * 
 * This survives between tests so reset if testing.
 * 
 * @author Sami Badawi
 *
 */
public class RootMap {
	private static final boolean NEW_MAP_ON_CLEAR = false;
	static Map _map = mapFactory();
	static Map[] _maps = {_map};
	
	static public Map getMap() {
		return _map;
	}
	
	public static Map mapFactory() {
		return ScriptEngineCache.getScriptEngineManager().getBindings();
//		return new HashMap();
	}

	static public Map[] getMaps(){
		return _maps;
	}
	
	static public void put(Object key, Object value) {
		_map.put(key, value);
	}
	
	static public void putNoOverwrite(Object key, Object value) {
		if (!_map.containsValue(key))
			_map.put(key, value);
	}
	
	static public Object get(Object key) {
		return _map.get(key);
	}
	
	/** Maybe making a new map would be better. */
	static public void clear() {
		if (NEW_MAP_ON_CLEAR) {
			_map = new HashMap();
			_maps[0] = _map;
		}
		else
			_map.clear();
	}
	
	static public boolean containsValue(Object key){
		return _map.containsValue(key);
	}
}
