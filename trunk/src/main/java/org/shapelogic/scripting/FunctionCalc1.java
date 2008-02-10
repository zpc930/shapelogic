package org.shapelogic.scripting;

import javax.script.Invocable;
import javax.script.ScriptException;

import org.shapelogic.calculation.Calc1;

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
public class FunctionCalc1<In,E> extends BaseScriptingFunction
	implements Calc1<In,E> 
{
	public FunctionCalc1(String functionName, String expression, String language) {
		_functionName = functionName;
		if (language != null)
			_language = language;
		_expression = expression;
	}
	
	public FunctionCalc1(String functionName, String expression){
		this(functionName,expression,DEFAULT_LANGUAGE);
	}
	
	@Override
	public E invoke(In input) {
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
