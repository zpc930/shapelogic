package org.shapelogic.logic;

import java.util.HashMap;
import java.util.Map;

import org.shapelogic.logic.BaseTask;
import org.shapelogic.logic.LogicState;
import org.shapelogic.logic.SimpleNumericTask;
import org.shapelogic.logic.SimpleTask;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class SimpleTaskTest extends TestCase {
	
	public void testCalcForOneChild() throws Exception {
		final String KEY = "number";
		final Integer VALUE = 1;
		SimpleTask st = new SimpleTask(null, true, KEY, VALUE);
		Map map = new HashMap();
		map.put(KEY, VALUE);
		st.setVars(map);
		st.calc();
		assertEquals(LogicState.SucceededDone, st.getState());
		assertEquals(VALUE, st.getVars().get(KEY));
		
		//Test of inheritance of context
		BaseTask dummyTask = new BaseTask(st);
		assertEquals(VALUE, dummyTask.getVars().get(KEY));
	}
	
	public void testCalcForTwoChildren() throws Exception {
		final String KEY = "number";
		final Integer VALUE = 1;
		final String KEY2 = "programmer";
		final String VALUE2 = "Sami";
		SimpleNumericTask st = new SimpleNumericTask(null, true, KEY, VALUE);
		Map map = new HashMap();
		map.put(KEY, VALUE);
		st.setVars(map);
		st.calc();
		SimpleTask st2 = new SimpleTask(null, true, KEY2, VALUE2);
		
		assertEquals(LogicState.SucceededDone, st.getState());
		assertEquals(VALUE, st.getVars().get(KEY));
		
		//Test of inheritance of context
		BaseTask dummyTask = new BaseTask(st);
		assertEquals(VALUE, dummyTask.getVars().get(KEY));
	}
	

}
