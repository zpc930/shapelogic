package org.shapelogic.logic;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.WrapDynaBean;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class BeanUtilsTest extends TestCase {
	
	public void testSimpleJavaBean() throws Exception {
		DummyWithStringAndInteger dummyWithStringAndInteger = new DummyWithStringAndInteger();
		String name = "Dummy";
		PropertyUtils.setSimpleProperty(dummyWithStringAndInteger, "name", name);
		assertEquals("Name not matching", name, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "name"));
		
		PropertyUtils.setSimpleProperty(dummyWithStringAndInteger, "value", 1);
		assertEquals("Value not matching", 1, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "value"));

		BeanUtils.setProperty(dummyWithStringAndInteger, "value", "2");
		assertEquals("Value not matching", 2, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "value"));
	}
	
	public void testSimpleDynaBean() throws Exception {
		DummyWithStringAndInteger dummyWithStringAndInteger = new DummyWithStringAndInteger();
		DynaBean dynaBean = new WrapDynaBean(dummyWithStringAndInteger);
		String name = "Dummy";
		PropertyUtils.setSimpleProperty(dynaBean, "name", name);
		assertEquals("Name not matching", name, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "name"));
		
		PropertyUtils.setSimpleProperty(dynaBean, "value", 1);
		assertEquals("Value not matching", 1, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "value"));
	}

	public void testBeanUtils() throws Exception {
		DummyWithStringAndInteger dummyWithStringAndInteger = new DummyWithStringAndInteger();
		DynaBean dynaBean = new WrapDynaBean(dummyWithStringAndInteger);
		String name = "Dummy";
		BeanUtils.setProperty(dynaBean, "name", name);
		assertEquals("Name not matching", name, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "name"));
		
		BeanUtils.setProperty(dynaBean, "value", "1");
		assertEquals("Value not matching", 1, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "value"));
	}

	/** This is a little surprising that the default value is 0 */
	public void testBeanUtilsWithNull() throws Exception {
		DummyWithStringAndInteger dummyWithStringAndInteger = new DummyWithStringAndInteger();
		DynaBean dynaBean = new WrapDynaBean(dummyWithStringAndInteger);
		String name = null;
		BeanUtils.setProperty(dynaBean, "name", name);
		assertEquals("Name not matching", name, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "name"));
		
		BeanUtils.setProperty(dynaBean, "value", "");
		assertEquals("Value not matching", 0, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "value"));

		BeanUtils.setProperty(dynaBean, "value", null);
		assertEquals("Value not matching", 0, PropertyUtils.getSimpleProperty(dummyWithStringAndInteger, "value"));
	}
}
