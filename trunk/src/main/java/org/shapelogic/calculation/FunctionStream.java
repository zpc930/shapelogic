package org.shapelogic.calculation;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.shapelogic.scripting.ScriptingConnect;

/** Make a stream that is using a external function from a Scripting language 
 * to do a calculation.
 * 
 * Requires Groovy to be installed. Need special installation of groovy-engine.jar 
 * that need to be downloade from Sun.
 * 
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public class FunctionStream<E> extends BaseStream<E> {
	public static final String functionNameSuffix = "_FUNCTION_";
	
	protected ScriptingConnect _scriptingConnect;
	protected Invocable _scriptEngine;
	protected ScriptEngineManager _scriptEngineManager;
	protected String _name;
	protected String _expression;
	protected String _functionName;
	protected String _language = "groovy";
	
	public FunctionStream(String name, String language, Integer stopNumber, String expression, E ... startList){
		_name = name;
		_functionName = name + functionNameSuffix;
		if (language != null)
			_language = language;
		if (stopNumber != null)
			_maxLast = stopNumber;
		_expression = expression;
		for (E element: startList) 
			_list.add(element);
	}
	
	public FunctionStream(String name, Integer stopNumber, String expression, E ... startList){
		this(name, null, stopNumber, expression, startList);
	}
	
	public FunctionStream(String name, String expression, E ... startList){
		this(name,null,expression,startList);
	}
	
	public ScriptingConnect getScriptingConnect() {
		if (_scriptingConnect == null)
			_scriptingConnect = new ScriptingConnect();
		return _scriptingConnect;
	} 
	
	public Invocable getScriptEngine() {
		if (_scriptEngine == null) {
			_scriptingConnect = new ScriptingConnect();
			ScriptEngine engine;
			try {
				engine = _scriptingConnect.getEngine(_expression, _language);
		        engine.put(_name, this);
				_scriptEngine = (Invocable) engine; 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return _scriptEngine;
	}

	@Override
	public E calcElement(int index) {
		Invocable inv = getScriptEngine();
		if (inv == null)
			return null;
	    Object obj = null;
		try {
			obj = inv.invokeFunction(_functionName, new Object[] { new Integer(index) });
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return (E) obj;
	}

	@Override
	public boolean hasNext() {
		return _maxLast != LAST_UNKNOWN && getCurrentSize() <= _maxLast;
	}
	
}
