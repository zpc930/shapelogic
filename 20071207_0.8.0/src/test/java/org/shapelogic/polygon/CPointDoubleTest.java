package org.shapelogic.polygon;

import static java.lang.Math.PI;

import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.IPoint2D;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class CPointDoubleTest extends TestCase {
      
    public void testCreationVector() {
        IPoint2D v0 = new CPointDouble();
        assertEquals(v0.getX(), 0.);
        IPoint2D v1 = new CPointDouble(1.f,2.f);
        assertTrue(v1.getY() == 2);
    }

  
    public void testAddVector() {
    	IPoint2D e1 = new CPointDouble(3.f,4.f);
    	IPoint2D v1 = new CPointDouble(1.f,2.f);
    	IPoint2D v2 = new CPointDouble(2.f,2.f);
    	IPoint2D sumV = v1.copy().add(v2);
        System.out.println("Sum: " + sumV);
        assertEquals(e1, sumV);
    }

    public void testCompareVector() {
    	IPoint2D v1 = new CPointDouble(1.f,2.f);
    	IPoint2D v1b = new CPointDouble(1.f,2.f);
    	IPoint2D v3 = new CPointDouble(2.f,1.f);
        assertEquals(v1b, v1);
        assertTrue(v1b.compareTo(v1)>=0);
        assertTrue(v1b.compareTo(v1)<=0);
        assertTrue(v3.compareTo(v1) >=0);
        assertTrue(v3.compareTo(v1) > 0);
        assertTrue(v1.compareTo(v3) <0 );
        assertTrue(v1.compareTo(v3) <= 0);
    }

    public void testRoundVector() {
    	IPoint2D v1 = new CPointDouble(1.1f,2.5f);
    	IPoint2D v1b = new CPointDouble(1.f,3.f);
        assertEquals(v1b, v1.round());
    }

	public void testAngle() throws Exception {
		CPointInt pA0 = new CPointInt(1,0);
		CPointInt pA45 = new CPointInt(1,1);
		CPointInt pA90 = new CPointInt(0,1);
		CPointInt pA135 = new CPointInt(-1,1);
		CPointInt pA180 = new CPointInt(-1,0);
		CPointInt pA225 = new CPointInt(-1,-1);
		CPointInt pA270 = new CPointInt(0,-1);
		CPointInt pA315 = new CPointInt(1,-1);
		assertEquals(0., pA0.angle());
		assertEquals(PI*0.25, pA45.angle());
		assertEquals(PI*0.5, pA90.angle());
		assertEquals(PI*0.75, pA135.angle());
		assertEquals(PI, pA180.angle());
		assertEquals(PI*-0.75, pA225.angle());
		assertEquals(PI*-0.5, pA270.angle());
		assertEquals(PI*-0.25, pA315.angle());
	}
}
