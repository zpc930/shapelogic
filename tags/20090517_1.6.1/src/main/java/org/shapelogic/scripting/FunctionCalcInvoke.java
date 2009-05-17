package org.shapelogic.scripting;

import org.shapelogic.calculation.CalcInvoke;

/** FunctionCalcInvoke based on an expression in a Scripting language using JSR 223.
 *  <br />
 * Requires Groovy to be installed. Need special installation of groovy-engine.jar 
 * that need to be downloade from Sun.<br />
 * 
 * Requires that the scripting language support the invocable interface.<br />
 * 
 * @author Sami Badawi
 *
 */
public class FunctionCalcInvoke<E> extends FunctionCalc0<E>
	implements CalcInvoke<E> 
{
	protected boolean _dirty = true;
	protected E _value = null;
	
	public FunctionCalcInvoke(String functionName, String expression, String language) {
		super(functionName, expression, language);
	}
	
	public FunctionCalcInvoke(String functionName, String expression){
		super(functionName, expression, DEFAULT_LANGUAGE);
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
