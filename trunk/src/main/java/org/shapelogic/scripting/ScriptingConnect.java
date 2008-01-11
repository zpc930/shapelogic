package org.shapelogic.scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/** ScriptingConnect
 * 
 * This is testing JavaScript and Groovy
 * 
 * There is currently not a Maven 2 file for the groovy-engine.
 * This has to be downloaded from Sun.
 * 
 * This also requires that this is run with JDK 1.6
 * 
 * @author Sami Badawi
 *
 */
public class ScriptingConnect {
	public static final boolean GET_ENGINE_BY_EXTENSION = false;
	
    private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

    public Object evalScript(String script, String language) throws Exception
    {
        ScriptEngine scriptEngine = null; 
        if (GET_ENGINE_BY_EXTENSION)
        	scriptEngine = scriptEngineManager.getEngineByExtension(language);
        else
        	scriptEngine = scriptEngineManager.getEngineByName(language);
        if (scriptEngine == null) {
            throw new IllegalArgumentException(
                "No script engine on classpath to handle file: "
            );
        }
//        scriptEngine.put("name", "Sami");
        return scriptEngine.eval(script);
    }

    public ScriptEngine getEngine(String script, String language) throws Exception
    {
        ScriptEngine scriptEngine = null; 
        if (GET_ENGINE_BY_EXTENSION)
        	scriptEngine = scriptEngineManager.getEngineByExtension(language);
        else
        	scriptEngine = scriptEngineManager.getEngineByName(language);
        if (scriptEngine == null) {
            throw new IllegalArgumentException(
                "No script engine on classpath to handle file: "
            );
        }
//        scriptEngine.put("name", "Sami");
        scriptEngine.eval(script);
        return scriptEngine;
    }
}
