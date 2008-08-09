package org.shapelogic.logic;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.context.HashMapContext;

import junit.framework.TestCase;

/** Test problems with JexlContext:
 * call static method on class
 * 
 * @author Sami Badawi
 *
 */
public class JexlContextTest extends TestCase {
	JexlContext context = null;
	
	@Override
	public void setUp() {
		context = new HashMapContext();
	}

	/** So the solution is that you have to associate a the class name with the class first 
	 * */
	public void testStaticMethodCallFromClass() throws Exception {
		Object expected = Boolean.valueOf(true);
		Class klass = java.lang.Boolean.class;
		String expression = "Boolean.valueOf(true)";
		context.getVars().put("Boolean", klass);
		Expression e = ExpressionFactory.createExpression( expression );
		Object calcValue = e.evaluate(context);

		assertEquals(expected, calcValue);
	}

	/** So the solution is that you have to associate a the class name with the class first 
	 * */
	public void testStaticMethodCallFromClassUsingSetClassInContext() throws Exception {
		setClassInContext(java.lang.Boolean.class, context);
		Object expected = Boolean.valueOf(true);
		String expression = "Boolean.valueOf(true)";
		Expression e = ExpressionFactory.createExpression( expression );
		Object calcValue = e.evaluate(context);

		assertEquals(expected, calcValue);
	}

	/** Notice that the result get turned into a Long 
	 * */
	public void testMultipleStatements() throws Exception {
		Integer expectedInt = 3;
		Long expected = 3L;
		context.getVars().put("a", 1);
		context.getVars().put("b", 2);
		String expression = "a + b";
		Expression e = ExpressionFactory.createExpression( expression );
		Object calcValue = e.evaluate(context);
		assertEquals(expected,calcValue);
		assertFalse(expectedInt.equals(calcValue)); //Notice that this is not equal
	}
	
	public void testCollectionSize() throws Exception {
		Object expected = 2;
		String[] array1 = {"a","b"};
		context.getVars().put("array1", array1);
		String expression = "size(array1)";
		Expression e = ExpressionFactory.createExpression( expression );
		Object calcValue = e.evaluate(context);
		assertEquals(expected,calcValue);
	}
	
	public void testMapAccess() throws Exception {
		String expected = "Sami";
		Map<String,String> map = new TreeMap<String, String>();
		map.put("name", expected);
		context.getVars().put("key", "name");
		context.getVars().put("map", map);
		String expression = "map['name']";
		Expression e = ExpressionFactory.createExpression( expression );
		Object calcValue = e.evaluate(context);
		assertEquals(expected,calcValue);
		
		String expression2 = "map[key]";
		Expression e2 = ExpressionFactory.createExpression( expression2 );
		Object calcValue2 = e.evaluate(context);
		assertEquals(expected,calcValue2);
	}
	
//Helper methods should be moved
	
	private void setClassInContext(Class klass, JexlContext context) {
		String className = klass.getName();
		String classNameBase = className;
		int lastIndex = className.lastIndexOf('.');
		classNameBase = className.substring(lastIndex+1); 
		context.getVars().put(classNameBase, klass);
	}
	
	private void setClassInContext(Collection<Class> classes, JexlContext context) {
		for (Class klass: classes) {
			setClassInContext(klass, context);
		}
	}
	
	private void setClassInContext(Class[] classes, JexlContext context) {
		for (Class klass: classes) {
			setClassInContext(klass,context);
		}
	}
}
