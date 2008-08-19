package org.shapelogic.calculation;

import java.util.HashMap;
import java.util.Map;

//import org.shapelogic.scripting.ScriptEngineCache;

/** This is the RootContext where all the certain objects are stored. <br />
 * 
 * There will be a lot of calculations and streams here. <br />
 * 
 * Singleton. <br />
 * This might be changed if more contexts need to be active at the same time.<br />
 * 
 * This survives between tests so reset if testing.<br />
 * 
 * @author Sami Badawi
 *
 */
public class RootMap implements RecursiveContext {
	private static final boolean NEW_MAP_ON_CLEAR = false;
	private Map _map;
	private static RootMap INSTANCE; 
	
	private RootMap() {
		_map = mapFactory();
	}
	
	static public RootMap getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RootMap();
		}
		return INSTANCE;
	}
	
	public static Map mapFactory() {
//		return ScriptEngineCache.getScriptEngineManager().getBindings();
		return new HashMap();
	}

	static public void put(Object key, Object value) {
		getInstance().getContext().put(key, value);
	}
	
	static public void putNoOverwrite(Object key, Object value) {
		if (!getInstance().getContext().containsValue(key))
			getInstance().getContext().put(key, value);
	}
	
	static public Object get(Object key) {
		return getInstance().getContext().get(key);
	}
	
	/** Maybe making a new map would be better. */
	static public void clear() {
		getInstance();
		if (NEW_MAP_ON_CLEAR) {
			INSTANCE._map = mapFactory();
		}
		else
			INSTANCE._map.clear();
	}
	
	static public boolean containsValue(Object key){
		return getInstance().getContext().containsValue(key);
	}

	@Override
	public Map getContext() {
		return _map;
	}

	@Override
	public RecursiveContext getParentContext() {
		return null;
	}	
}
