package org.shapelogic.logic;

import java.util.HashMap;
import java.util.Map;

import org.shapelogic.logic.AndTask;
import org.shapelogic.logic.LogicState;
import org.shapelogic.logic.SimpleTask;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class AndTaskTest extends TestCase {
	
	public void testCalcForOneChild() throws Exception {
		final String KEY = "number";
		final Integer VALUE = 1;
		AndTask andTask = new AndTask(null,true);
		SimpleTask st = new SimpleTask(andTask, false, KEY, VALUE);
		Map map = new HashMap();
		map.put(KEY, VALUE);
		st.setVars(map);
		andTask.invoke();
		assertEquals(LogicState.SucceededDone, andTask.getState());
		assertEquals(VALUE, andTask.getVars().get(KEY));
	}
	
	public void testCalcForTwoChildrenSuccess() throws Exception {
		final String KEY1 = "number";
		final Integer VALUE1 = 1;
		final String KEY2 = "programmer";
		final String VALUE2 = "Sami";

		AndTask andTask = new AndTask(null,true);

		SimpleTask st1 = new SimpleTask(andTask, false, KEY1, VALUE1);
		Map map1 = new HashMap();
		map1.put(KEY1, VALUE1);
		st1.setVars(map1);

		SimpleTask st2 = new SimpleTask(andTask, false, KEY2, VALUE2);
		Map map2 = new HashMap();
		map2.put(KEY2, VALUE2);
		st2.setVars(map2);

		andTask.invoke();
		assertEquals(LogicState.SucceededDone, andTask.getState());
		assertEquals(VALUE1, andTask.getVars().get(KEY1));
	}
	
	public void testCalcForTwoChildrenFail() throws Exception {
		final String KEY1 = "number";
		final Integer VALUE1 = 1;
		final String KEY2 = "programmer";
		final String VALUE2 = "Sami";
		final Integer BAD_VALUE2 = 2;

		AndTask andTask = new AndTask(null,true);

		SimpleTask st1 = new SimpleTask(andTask, false, KEY1, VALUE1);
		Map map1 = new HashMap();
		map1.put(KEY1, VALUE1);
		st1.setVars(map1);

		SimpleTask st2 = new SimpleTask(andTask, false, KEY2, VALUE2);
		Map map2 = new HashMap();
		map2.put(KEY2, BAD_VALUE2);
		st2.setVars(map2);

		andTask.invoke();
		assertEquals(LogicState.FailedDone, andTask.getState());
		assertEquals(BAD_VALUE2, andTask.getVars().get(KEY2));
	}

}
