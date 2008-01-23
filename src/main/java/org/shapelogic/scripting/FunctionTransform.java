package org.shapelogic.scripting;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.shapelogic.calculation.IndexTransform;
import org.shapelogic.scripting.ScriptingConnect;

/** IndexTransform based on an expression in a Scripting language using JSR 223.
 *  
 * Requires Groovy to be installed. Need special installation of groovy-engine.jar 
 * that need to be downloade from Sun.
 * 
 * Requires that the scripting language support the invocable interface.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionTransform<In,E> implements IndexTransform<In,E> {
	public static final String FUNCTION_NAME_SUFFIX = "_FUNCTION_";
	public static final String DEFAULT_LANGUAGE = "groovy";
	
	protected ScriptingConnect _scriptingConnect;
	protected ScriptEngine _scriptEngine;
	protected ScriptEngineManager _scriptEngineManager;
	protected String _name;
	protected String _expression;
	protected String _functionName;
	protected String _language = DEFAULT_LANGUAGE;
	
	public FunctionTransform(String name, String expression, String language) {
		_name = name;
		_functionName = name + FUNCTION_NAME_SUFFIX;
		if (language != null)
			_language = language;
		_expression = expression;
	}
	
	public FunctionTransform(String name, String expression){
		this(name,expression,DEFAULT_LANGUAGE);
	}
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

	@Override
	public E transform(In input, int index) {
		Invocable inv = (Invocable)getScriptEngine();
		if (inv == null)
			return null;
	    Object obj = null;
		try {
			obj = inv.invokeFunction(_functionName, new Object[] { input, new Integer(index) });
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	    return (E) obj;
	}
	
	public void put(String key, Object value) {
		getScriptEngine().put(key,value);
	}
}
