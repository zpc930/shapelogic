package org.shapelogic.scripting;

import javax.script.ScriptException;

import org.shapelogic.calculation.CalcInvoke;

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
public class FunctionEvalCalcInvoke<E> extends BaseScriptingFunction
	implements CalcInvoke<E> 
{
	protected boolean _dirty = true;
	protected E _value = null;
	
	public FunctionEvalCalcInvoke(String expression, String language) {
		_functionName = "";
		if (language != null)
			_language = language;
		_expression = expression;
	}
	
	public FunctionEvalCalcInvoke(String expression){
		this(expression,DEFAULT_LANGUAGE);
	}
	
	@Override
	public E invoke() {
	    Object obj = null;
		try {
			obj = getScriptEngine().eval(_expression );
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	    return (E) obj;
	}
	
	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public void setup() {
	}

	@Override
	public E getValue() {
		if (_dirty) {
			_value = invoke();
			_dirty = false;
		}
		return _value;
	}
	
}
