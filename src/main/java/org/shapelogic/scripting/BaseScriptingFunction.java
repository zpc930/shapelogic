package org.shapelogic.scripting;

import javax.script.ScriptEngine;

/** BaseScriptingFunction base of any scripting functions.
 * 
 * @author Sami Badawi
 *
 */
public abstract class BaseScriptingFunction {

	public static final String DEFAULT_LANGUAGE = "groovy";
	protected String _functionName;
	protected String _expression;
	protected String _language = DEFAULT_LANGUAGE;
	
	protected ScriptEngine _scriptEngine;
	
	/** Lazy init for a ScriptEngine */
	public ScriptEngine getScriptEngine() {
		if (_scriptEngine == null) {
			_scriptEngine = ScriptEngineCache.getScriptEngineByName(_language);
			try {
				ScriptEngineCache.script(_expression, _language);
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
