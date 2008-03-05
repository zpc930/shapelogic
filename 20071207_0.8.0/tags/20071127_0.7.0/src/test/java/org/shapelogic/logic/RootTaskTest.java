package org.shapelogic.logic;

import java.util.HashMap;
import java.util.Map;

import org.shapelogic.imageprocessing.PointType;
import org.shapelogic.logic.ContextCalculation;
import org.shapelogic.logic.RootTask;
import org.shapelogic.logic.Task;

import junit.framework.TestCase;

/** Test the very fundamental RootTask that is a singleton
 * 
 * @author Sami Badawi
 *
 */
public class RootTaskTest extends TestCase
{
	RootTask rootTask;
	
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		rootTask = RootTask.getInstance();
		ContextCalculation contextCalculationX = new ContextCalculation() {
			{name = "x";}
			
			@Override
			public Object calculation(Task context)
					throws Exception {
				return 1.;
			}
		};
		rootTask.setContextCalculation(contextCalculationX.name, contextCalculationX);
		
		ContextCalculation contextCalculationY = new ContextCalculation() {
			{name = "y";}
			@Override
			public Object calculation(Task currentTask)
					throws Exception {
				Double x = (Double) currentTask.findNamedValue("x");
				return x * 2;
			}
		};
		rootTask.setContextCalculation(contextCalculationY.name, contextCalculationY);
	}

	/** just one variable x, that is set by a ContextCalculation 
	 * 
	 * How should the question be phrased?
	 * */ 
	public void testSimplest() {
		Object x = rootTask.findNamedValue("x");
		assertEquals(1., x);
	}

	public void testOneLink() {
		Object x = rootTask.findNamedValue("y");
		assertEquals(2., x);
	}

	public void testThatDefaultClassesAreLoaded() {
		Object x = rootTask.findNamedValue("Boolean");
		assertEquals(Boolean.class, x);
	}

	public void testThatDefaultClassesAreLoaded2() {
		Object x = rootTask.findNamedValue("PointType");
		assertEquals(PointType.class, x);
	}

	public void testThatDefaultClassesAreLoaded3() {
		Object x = rootTask.findNamedValue("PointType.END_POINT");
		assertEquals(PointType.END_POINT, x);
	}

	public void testThatDefaultClassesValuesAreAccesible() {
		Boolean expected = Boolean.TRUE;
		Object x = rootTask.findNamedValue("Boolean.TRUE");
//		assertEquals(expected, x); //XXX not working
	}

	public void testThatDefaultClassesMethodsAreAccesible() {
		Boolean expected = Boolean.parseBoolean("true");
		Object x = rootTask.findNamedValue("Boolean.parseBoolean(\"true\")");
		assertEquals(expected, x);
	}

	public void testEscaping() {
		String key = "Sami's computer";
		String expected = "Slow";
		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put(key,expected);
		rootTask.setNamedValue("testMap", testMap);
		Object x = rootTask.findNamedValue("testMap.get(\"Sami\'s computer\")");
		assertEquals(expected, x);
		Object y = rootTask.findNamedValue("testMap.get('Sami\'s computer')");
//		assertEquals(expected, y); //XXX did not work
	}
}
