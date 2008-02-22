package org.shapelogic.scripting;

import javax.script.Invocable;
import javax.script.ScriptException;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.predicate.BinaryPredicate;
import org.shapelogic.predicate.BinaryPredicateFactory;
import org.shapelogic.predicate.Predicate;

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
public class FunctionPredicate<In> extends BaseScriptingFunction
	implements Predicate<In>, Calc1<In,Object> 
{
	public FunctionPredicate(String functionName, String expression, String language) {
		_functionName = functionName;
		if (language != null)
			_language = language;
		_expression = expression;
	}
	
	public FunctionPredicate(String name, String expression){
		this(name,expression,DEFAULT_LANGUAGE);
	}
	
	public Object invoke(In input) {
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
	    return obj;
	}

	@Override
	public boolean evaluate(In input) {
		Object result = invoke(input);
		if (result == null) return false;
		if (result instanceof Boolean) return ((Boolean)result).booleanValue();
		if (result instanceof Number) return ((Number)result).doubleValue() == 0.0;
		return false;
	}

	public static <In0, In1, In2> Predicate<In0> makeFunctionPredicate(
			String expression, final BinaryPredicate<In1, In2> binaryPredicate,
			final In2 compareObject, String language, String functionName) 
	{
		final FunctionCalc1<In0,In1> calc1 = 
			new FunctionCalc1<In0,In1>(functionName, expression, language);
		final Predicate<In0> calcBoolean = new Predicate<In0>() {
			@Override
			public boolean evaluate(In0 input) {
				return binaryPredicate.evaluate(calc1.invoke(input), compareObject);
			}
		};
		return calcBoolean;
	}
	
	public static <In0, In1, In2> Predicate<In0> makeFunctionPredicate(
			String expression, final String binaryPredicateString,
			final In2 compareObject, String language, String functionName) 
	{
		final BinaryPredicate<In1, In2> binaryPredicate = 
			BinaryPredicateFactory.getInstance(binaryPredicateString);
		if (binaryPredicate == null)
			return null;
		final Predicate<In0> calcBoolean = makeFunctionPredicate(
			expression, binaryPredicate, compareObject, language, functionName);
		return calcBoolean;
	}
}
