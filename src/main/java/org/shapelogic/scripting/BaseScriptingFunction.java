package org.shapelogic.scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/** BaseScriptingFunction base of any scripting functions.
 * 
 * @author Sami Badawi
 *
 */
public abstract class BaseScriptingFunction<In,Out> {

	public static final String DEFAULT_LANGUAGE = "groovy";
	public static final String FUNCTION_NAME_SUFFIX = "_FUNCTION_";
	
	protected String _name;
	protected String _functionName;
	protected String _expression;
	protected String _language = DEFAULT_LANGUAGE;
	
	protected ScriptingConnect _scriptingConnect;
	protected ScriptEngine _scriptEngine;
	protected ScriptEngineManager _scriptEngineManager;
	
	/** Lazy init for ScriptingCoonect */
	public ScriptingConnect getScriptingConnect() {
		if (_scriptingConnect == null)
			_scriptingConnect = new ScriptingConnect();
		return _scriptingConnect;
	} 
	
	/** Lazy init for a ScriptEngine */
	public ScriptEngine getScriptEngine() {
		if (_scriptEngine == null) {
			_scriptingConnect = new ScriptingConnect();
			ScriptEngine engine;
			try {
				engine = _scriptingConnect.getEngine(_expression, _language);
		        engine.put(_name, this);
				_scriptEngine = engine; 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return _scriptEngine;
	}

	public void put(String key, Object value) {
		getScriptEngine().put(key,value);
	}
}
