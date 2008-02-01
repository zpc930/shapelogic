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
	
	protected ScriptEngine _scriptEngine;
	
	/** Lazy init for a ScriptEngine */
	public ScriptEngine getScriptEngine() {
		if (_scriptEngine == null) {
			_scriptEngine = ScriptEngineFactory.getScriptEngineByName(_language);
			ScriptEngine engine;
			try {
				ScriptEngineFactory.script(_expression, _language);
				_scriptEngine.put(_name, this);
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
