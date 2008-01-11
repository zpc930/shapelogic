package org.shapelogic.scripting;

import java.util.List;

import javax.script.Invocable;

import junit.framework.TestCase;

/** Test ScriptingConnect
 * 
 * This is testing JavaScript and Groovy
 * 
 * There is currently not a Maven 2 file for the groovy-engine.
 * This has to be downloaded from Sun.
 * 
 * This also requires that this is run with JDK 1.6
 * 
 * @author Sami Badawi
 *
 */
public class ScriptingConnectTest extends TestCase {

	public void testCallGroovyScripts() throws Exception {
		ScriptingConnect scriptingConnect = new ScriptingConnect();
		assertNotNull(scriptingConnect);
		Object obj = scriptingConnect.evalScript("println('Hello Scripting World!'); 'Hello Sami';","groovy");
		assertTrue(obj instanceof String);
		assertTrue(scriptingConnect.evalScript("1;","groovy") instanceof Number);
		assertTrue(scriptingConnect.evalScript("[1,2];","groovy") instanceof List);
	}

	public void testGroovyFunction() throws Exception {
		ScriptingConnect scriptingConnect = new ScriptingConnect();
		assertNotNull(scriptingConnect);
		Object engine = scriptingConnect.getEngine("def clos = {it*2};","groovy");
//		Object engine = ScriptingConnect.getEngine("def clos = { println('Hello Groovy!');}","groovy");
	    Invocable inv = (Invocable) engine;
	    Object obj = inv.invokeFunction("clos", new Object[] { new Integer(1) });
		assertTrue(obj instanceof Number);
		assertEquals(obj,new Integer(2));
	}

	public void testJavascriptFunction1() throws Exception {
		ScriptingConnect scriptingConnect = new ScriptingConnect();
		assertNotNull(scriptingConnect);
		Object engine = scriptingConnect.getEngine(
				"function sami(it) { println('Hi ' + it)}; \n" +
				"var sam = sami; \n" +
				"println('Hello JavaScript ' + sam('Joe')); \n",
				"javascript");
	    Invocable inv = (Invocable) engine;
	    Object obj = inv.invokeFunction("sam",null);
		assertNull(obj);
	}
	
	public void testJavascriptFunction() throws Exception {
		ScriptingConnect scriptingConnect = new ScriptingConnect();
		assertNotNull(scriptingConnect);
		Object engine = scriptingConnect.getEngine(
				"function sami(it) { return it*2;}; \n" +
				"var sam = sami; \n" +
				"println('Hello JavaScript ' + sam(10)); \n",
				"javascript");
	    Invocable inv = (Invocable) engine;
	    Object obj = inv.invokeFunction("sam", new Object[] { new Integer(1) });
		assertTrue(obj instanceof Number);
		assertEquals(new Double(2), obj);
	}

	public void testJavascriptFunctionAsValue() throws Exception {
		ScriptingConnect scriptingConnect = new ScriptingConnect();
		assertNotNull(scriptingConnect);
		Object function = scriptingConnect.evalScript(
				"function sami(it) { return it*2;}; \n" +
				"var sam = sami; \n" +
				"sam; \n",
				"javascript");
		
		System.out.println(function);
	}
}
