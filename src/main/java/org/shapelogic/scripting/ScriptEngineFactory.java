package org.shapelogic.scripting;

import java.util.Map;
import java.util.TreeMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/** ScriptEngineFactory get ScriptEngine by name.
 * <br />
 * Create script and evaluate.
 * 
 * @author Sami Badawi
 *
 */
public class ScriptEngineFactory {
	public static final boolean GET_ENGINE_BY_EXTENSION = false;
	
    static private ScriptEngineManager _scriptEngineManager;

    static private Map<String, ScriptEngine> _scriptEngineMap = 
    	new TreeMap<String,ScriptEngine>();
    
    static private ScriptEngineManager getScriptEngineManager() {
    	if (_scriptEngineManager == null)
    		_scriptEngineManager = new ScriptEngineManager();
    	return _scriptEngineManager;
    }
    
    /** I should also create one for extension */
    static public ScriptEngine getScriptEngineByName(String language) {
    	ScriptEngine scriptEngine = _scriptEngineMap.get(language);
    	if (scriptEngine == null) {
        	scriptEngine = getNewScriptEngineByName(language);
        	_scriptEngineMap.put(language, scriptEngine);
    	}
    	return scriptEngine;
    }

    /** I should also create one for extension */
    static public ScriptEngine getNewScriptEngineByName(String language) {
    	ScriptEngine scriptEngine = getScriptEngineManager().getEngineByName(language);
        if (scriptEngine == null) {
            throw new IllegalArgumentException(
                "No script engine on classpath to handle language: " + language
            );
        }
    	return scriptEngine;
    }
    
    /** I should also create one for extension */
    static public ScriptEngine getScriptEngineByExtension(String extension) {
    	ScriptEngine scriptEngine = _scriptEngineMap.get(extension);
    	if (scriptEngine == null) {
        	scriptEngine = getScriptEngineManager().getEngineByExtension(extension);
            if (scriptEngine == null) {
                throw new IllegalArgumentException(
                    "No script engine on classpath to handle extension: " + extension
                );
            }
        	_scriptEngineMap.put(extension, scriptEngine);
    	}
    	return scriptEngine;
    }

    static public Object eval(String script, String language) throws Exception
    {
    	ScriptEngine scriptEngine = getScriptEngineByName(language);
    	if (scriptEngine != null)
    		return scriptEngine.eval(script);
    	return null;
    }

    static public ScriptEngine script(String script, String language) throws Exception
    {
    	ScriptEngine scriptEngine = getScriptEngineByName(language);
    	if (scriptEngine != null)
    		scriptEngine.eval(script);
    	return scriptEngine;
    }

    static public void put(String key, Object value, String language) throws Exception
    {
    	ScriptEngine scriptEngine = getScriptEngineByName(language);
    	if (scriptEngine != null)
    		scriptEngine.put(key, value);
    }
}
