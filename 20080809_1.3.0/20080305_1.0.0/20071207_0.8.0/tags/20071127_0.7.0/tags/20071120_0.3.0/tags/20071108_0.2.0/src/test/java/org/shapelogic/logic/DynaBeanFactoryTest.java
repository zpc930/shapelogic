package org.shapelogic.logic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.PropertyUtils;
import org.shapelogic.logic.DynaBeanFactory;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class DynaBeanFactoryTest extends TestCase {
	private DynaBean dummyWithStringAndInteger; 
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		Map<String, String> classMap = new HashMap<String,String>();
		classMap.put("name", "string");
		classMap.put("value", "integer");
		DynaClass dynaClass = DynaBeanFactory.getDynaClassInstance(classMap, "DummyName"); 
		dummyWithStringAndInteger = dynaClass.newInstance(); 
	}
	
	/** So this DynaBeans have typed fields */
	public void testSimpleJavaBean() throws Exception {
		String name = "Dummy";
		PropertyUtils.setSimpleProperty(dummyWithStringAndInteger, "name", name);
		assertEquals("Name not matching", name, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "name"));
		
		PropertyUtils.setSimpleProperty(dummyWithStringAndInteger, "value", 1);
		assertEquals("Value not matching", 1, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "value"));
	}
	
	public void testSimpleDynaBean() throws Exception {
		DynaBean dynaBean = dummyWithStringAndInteger.getDynaClass().newInstance();
		String name = "Dummy";
		PropertyUtils.setSimpleProperty(dynaBean, "name", name);
		assertEquals("Name not matching", name, PropertyUtils.getSimpleProperty(dynaBean, "name"));
		
		PropertyUtils.setSimpleProperty(dynaBean, "value", 1);
		assertEquals("Value not matching", 1, PropertyUtils.getSimpleProperty(dynaBean, "value"));
	}

	public void testBeanUtils() throws Exception {
		DynaBean dynaBean = dummyWithStringAndInteger.getDynaClass().newInstance();
		String name = "Dummy";
		BeanUtils.setProperty(dynaBean, "name", name);
		assertEquals("Name not matching", name, PropertyUtils.getSimpleProperty(dynaBean, "name"));
		
		BeanUtils.setProperty(dynaBean, "value", "1");
		assertEquals("Value not matching", 1, PropertyUtils.getSimpleProperty(dynaBean, "value"));
	}

	/** This is a little surprising that the default value is 0 */
	public void testBeanUtilsWithNull() throws Exception {
		DynaBean dynaBean = dummyWithStringAndInteger.getDynaClass().newInstance();
		String name = null;
		BeanUtils.setProperty(dynaBean, "name", name);
		assertEquals("Name not matching", name, PropertyUtils.getSimpleProperty(dynaBean, "name"));
		
		BeanUtils.setProperty(dynaBean, "value", "");
		assertEquals("Value not matching", 0, PropertyUtils.getSimpleProperty(dynaBean, "value"));

		BeanUtils.setProperty(dynaBean, "value", null);
		assertEquals("Value not matching", 0, PropertyUtils.getSimpleProperty(dynaBean, "value"));
	}
}
