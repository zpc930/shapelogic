package org.shapelogic.loader;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.shapelogic.loader.CSVParser;
import org.shapelogic.logic.DummyWithStringAndInteger;


import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class CVSParserTest extends TestCase {
	public final static String CVS_FILE_1 = "name,value\n" +
			"s,i\n" +
			"Sami,1\n";
	
	private StringReader stringToReader(String input) {
		return new StringReader(input);
	}
	
	public void testSimpleWithInputClassCVS() throws Exception {
		Reader r = stringToReader(CVS_FILE_1);
		CSVParser csvParser = new CSVParser(r, DummyWithStringAndInteger.class);
		List result = csvParser.parseAll();
		assertEquals("Bad size", 1, result.size());
		Object firstElement = result.get(0);
		assertNotNull("Not null", firstElement);
		assertTrue("Bad type", firstElement instanceof DummyWithStringAndInteger);
		DummyWithStringAndInteger firstDummyWithStringAndInteger = 
			(DummyWithStringAndInteger) firstElement;
		assertEquals("Bad name", "Sami",firstDummyWithStringAndInteger.getName());
		assertEquals("Bad value", new Integer(1), firstDummyWithStringAndInteger.getValue());
	}

	public void testSimpleWithoutInputClassCVS() throws Exception {
		Reader r = stringToReader(CVS_FILE_1);
		CSVParser csvParser = new CSVParser(r, null);
		List result = csvParser.parseAll();
		assertEquals("Bad size", 1, result.size());
		Object firstElement = result.get(0);
		assertNotNull("Not null", firstElement);
		assertFalse("Bad type", firstElement instanceof DummyWithStringAndInteger);
		assertTrue("Bad type", firstElement instanceof DynaBean);
		DynaBean firstDynaBean = 
			(DynaBean) firstElement;
		assertEquals("Bad name", "Sami",firstDynaBean.get("name"));
		assertEquals("Bad value", new Integer(1), firstDynaBean.get("value"));
	}
}
