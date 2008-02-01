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
public class FunctionTransform<In,E> extends BaseScriptingFunction
	implements Transformer<In,E> 
{
	public FunctionTransform(String functionName, String expression, String language) {
		_functionName = functionName;
		if (language != null)
			_language = language;
		_expression = expression;
	}
	
	public FunctionTransform(String functionName, String expression){
		this(functionName,expression,DEFAULT_LANGUAGE);
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
