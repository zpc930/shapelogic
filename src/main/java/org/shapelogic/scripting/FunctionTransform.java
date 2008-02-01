package org.shapelogic.scripting;

import javax.script.Invocable;
import javax.script.ScriptException;

import org.shapelogic.calculation.Transformer;

/** Transform based on an expression in a Scripting language using JSR 223.
 *  
 * Requires Groovy to be installed. Need special installation of groovy-engine.jar 
 * that need to be downloade from Sun.
 * 
 * Requires that the scripting language support the invocable interface.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionTransform<In,E> extends BaseScriptingFunction<In,E> 
	implements Transformer<In,E> 
{
	public static final String FUNCTION_NAME_SUFFIX = "_FUNCTION_";
	
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
	
	public E transform(In input) {
		Invocable inv = (Invocable)getScriptEngine();
		if (inv == null)
			return null;
	    Object obj = null;
		try {
			obj = inv.invokeFunction(_functionName, new Object[] { input });
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	    return (E) obj;
	}
	
}
