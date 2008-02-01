package org.shapelogic.scripting;

import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;

import junit.framework.TestCase;

/** Test ScriptEngineFactory.
 * 
 * @author Sami Badawi
 *
 */
public class ScriptEngineFactoryTest extends TestCase {

	private static final String JAVASCRIPT = "javascript";
	private static final String GROOVY = "groovy";

	public void testThatDifferentEnginesoForSameLanguageCanBeCreated() throws Exception {
		ScriptEngine scriptEngine1 = ScriptEngineFactory.getNewScriptEngineByName(GROOVY);
		ScriptEngine scriptEngine2 = ScriptEngineFactory.getNewScriptEngineByName(GROOVY);
		assertNotSame(scriptEngine1, scriptEngine2);
		String NUMBER = "number";
		scriptEngine1.put(NUMBER, 1);
		assertEquals(1, scriptEngine1.get(NUMBER));
		scriptEngine2.put(NUMBER, 2);
		assertEquals(2, scriptEngine2.get(NUMBER));
		assertEquals(1, scriptEngine1.get(NUMBER));
	}
	
	public void testCallGroovyScripts() throws Exception {
		Object obj = ScriptEngineFactory.eval("println('Hello Scripting World!'); 'Hello Sami';",GROOVY);
		assertTrue(obj instanceof String);
		assertTrue(ScriptEngineFactory.eval("1;",GROOVY) instanceof Number);
		assertTrue(ScriptEngineFactory.eval("[1,2];",GROOVY) instanceof List);
	}

	public void testGroovyFunction() throws Exception {
		Object engine = ScriptEngineFactory.script("def clos = {it*2};",GROOVY);
	    Invocable inv = (Invocable) engine;
	    Object obj = inv.invokeFunction("clos", new Object[] { new Integer(1) });
		assertTrue(obj instanceof Number);
		assertEquals(obj,new Integer(2));
	}

	public void test2GroovyFunction() throws Exception {
		Object engine = ScriptEngineFactory.script("def multiply2 = {it*2};",GROOVY);
		ScriptEngineFactory.script("def multiply3 = {it*3};",GROOVY);
		System.out.println("engine: " + engine + ", class: "+ engine.getClass());
	    Invocable inv = (Invocable) engine;
	    Object result1 = inv.invokeFunction("multiply2", new Object[] { new Integer(1) });
		assertTrue(result1 instanceof Number);
		assertEquals(new Integer(2),result1);
	    Object result2 = inv.invokeFunction("multiply3", new Object[] { new Integer(1) });
		assertTrue(result2 instanceof Number);
		assertEquals(new Integer(3), result2);
	}

	public void testJavascriptFunction1() throws Exception {
		Object engine = ScriptEngineFactory.script(
				"function sami(it) { println('Hi ' + it)}; \n" +
				"var sam = sami; \n" +
				"println('Hello JavaScript ' + sam('Joe')); \n",
				JAVASCRIPT);
	    Invocable inv = (Invocable) engine;
	    Object obj = inv.invokeFunction("sam",null);
		assertNull(obj);
	}
	
	public void testJavascriptFunction() throws Exception {
		Object engine = ScriptEngineFactory.script(
				"function sami(it) { return it*2;}; \n" +
				"var sam = sami; \n" +
				"println('Hello JavaScript ' + sam(10)); \n",
				JAVASCRIPT);
	    Invocable inv = (Invocable) engine;
	    Object obj = inv.invokeFunction("sam", 1);
		assertTrue(obj instanceof Number);
		assertEquals(new Double(2), obj);
	}

	public void testJavascriptFunctionAsValue() throws Exception {
		Object function = ScriptEngineFactory.eval(
				"function sami(it) { return it*2;}; \n" +
				"var sam = sami; \n" +
				"sam; \n",
				JAVASCRIPT);
		
		System.out.println(function);
		assertNotNull(function);
	}
}
