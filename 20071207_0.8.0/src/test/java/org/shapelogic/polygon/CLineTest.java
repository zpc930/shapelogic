package org.shapelogic.polygon;

import org.shapelogic.polygon.CLine;
import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.IPoint2D;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class CLineTest extends TestCase {
	CLine lineVertical; 
	CLine lineHorizontal; 

	IPoint2D p1 = new CPointDouble(1.,1.); 
	IPoint2D p2 = new CPointDouble(2.,1.); 
	IPoint2D p3 = new CPointDouble(1.,2.); 
	
	IPoint2D rV = new CPointDouble(0.,1.); 

	@Override
	public void setUp() {
		lineVertical = new CLine(p1,p3); 
		lineHorizontal = new CLine(p1,p2); 
	}

	public void testRelativePoint() {
		assertEquals(rV,lineVertical.relativePoint());
		assertTrue(lineVertical.isVertical());
		assertTrue(lineHorizontal.isHorizontal());
		assertEquals(0.,lineHorizontal.angle());
		assertEquals(Math.PI/2,lineVertical.angle());
	}
}
