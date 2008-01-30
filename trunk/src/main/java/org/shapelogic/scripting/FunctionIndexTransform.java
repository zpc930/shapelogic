package org.shapelogic.scripting;

import javax.script.Invocable;
import javax.script.ScriptException;

import org.shapelogic.calculation.IndexTransform;

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
public class FunctionIndexTransform<In,E> extends BaseScriptingFunction<In,E> 
	implements IndexTransform<In,E> 
{
	public static final String FUNCTION_NAME_SUFFIX = "_FUNCTION_";
	
	public FunctionIndexTransform(String name, String expression, String language) {
		_name = name;
		_functionName = name + FUNCTION_NAME_SUFFIX;
		if (language != null)
			_language = language;
		_expression = expression;
	}
	
	public FunctionIndexTransform(String name, String expression){
		this(name,expression,DEFAULT_LANGUAGE);
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
	
}
