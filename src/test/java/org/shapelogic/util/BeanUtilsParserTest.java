package org.shapelogic.util;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.apache.commons.beanutils.BeanUtils;

/** Test BeanUtilsParser.<br />
 * 
 * The current solution is not elegant. I would have thougth that there should
 * be a simpler way to parse a property file style string and set it in a 
 * JavaBean.<br />
 * 
 * Current implementation assumes this format of arg: key1=value1 key2=value2<br />
 * 
 * @author Sami Badawi
 *
 */
public class BeanUtilsParserTest  extends TestCase {
	String seperators = " ;='";

    /** This does not work right for what I need.  */
	public void testSplitExtraSpace() {
        String toBeSplit = "ShapeLogic  Java";
        String[] split = toBeSplit.split("[ ]");
    	assertEquals( 3, split.length);
    	assertEquals( "ShapeLogic", split[0]);
    }
    
    public void testStringTokenizer1() {
        String toBeSplit = "ShapeLogic  Java";
        StringTokenizer stringTokenizer = new StringTokenizer(toBeSplit);
    	assertEquals( "ShapeLogic", stringTokenizer.nextToken());
    	assertEquals( "Java", stringTokenizer.nextToken());
    }

    public void testStringTokenizer2() {
        String toBeSplit = "name=ShapeLogic";
        StringTokenizer stringTokenizer = new StringTokenizer(toBeSplit,seperators);
    	assertEquals( "name", stringTokenizer.nextToken());
    	assertEquals( "ShapeLogic", stringTokenizer.nextToken());
    }

    public void testStringTokenizer3() {
        String toBeSplit = "name= ShapeLogic";
        StringTokenizer stringTokenizer = new StringTokenizer(toBeSplit,seperators);
    	assertEquals( "name", stringTokenizer.nextToken());
    	assertEquals( "ShapeLogic", stringTokenizer.nextToken());
    }

    public void testStringTokenizer4() {
        String toBeSplit = "name=ShapeLogic Java";
        StringTokenizer stringTokenizer = new StringTokenizer(toBeSplit,seperators);
    	assertEquals( "name", stringTokenizer.nextToken());
    	assertEquals( "ShapeLogic", stringTokenizer.nextToken());
    }

    public void testStringTokenizer5() {
        String toBeSplit = "name=ShapeLogic";
        BeanUtilsParser parser = new BeanUtilsParser();
        Map<String, String> map = parser.split(toBeSplit);
        assertEquals(1, map.size());
    }
    
    public void testBeanUtilsParserName() {
        String toBeSplit = "name=ShapeLogic";
        BeanUtilsParser parser = new BeanUtilsParser();
        DummyJavaBean bean = new DummyJavaBean();
        parser.parse(bean,toBeSplit);
        assertEquals("ShapeLogic", bean.getName());
    }

    public void testBeanUtilsParserNumber() {
        String toBeSplit = "number=1";
        BeanUtilsParser parser = new BeanUtilsParser();
        DummyJavaBean bean = new DummyJavaBean();
        parser.parse(bean,toBeSplit);
        assertEquals(1, bean.getNumber());
    }

    public void testBeanUtilsParserNameNumber() {
        String toBeSplit = "name=ShapeLogic number=1";
        BeanUtilsParser parser = new BeanUtilsParser();
        DummyJavaBean bean = new DummyJavaBean();
        parser.parse(bean,toBeSplit);
        assertEquals(1, bean.getNumber());
        assertEquals("ShapeLogic", bean.getName());
    }

    public void te_stBeanUtilsParser3() {
        String toBeSplit = "version=1.5";
        BeanUtilsParser parser = new BeanUtilsParser();
        DummyJavaBean bean = new DummyJavaBean();
        parser.parse(bean,toBeSplit);
        assertEquals(1.5, bean.version);
    }

    public void testBeanUtilsNumber() {
        try {
            DummyJavaBean bean = new DummyJavaBean();
            BeanUtils.setProperty(bean, "number", "1");
            assertEquals(1, bean.getNumber());
        } catch (Exception ex) {
            Logger.getLogger(BeanUtilsParserTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    /** Only a property defined by a getter and a setter works. */
    public void testBeanUtilsPublicField() {
        try {
            DummyJavaBean bean = new DummyJavaBean();
            BeanUtils.setProperty(bean, "version", "1");
            assertEquals(0., bean.version); //XXX it does not work
        } catch (Exception ex) {
            Logger.getLogger(BeanUtilsParserTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

} 