package org.shapelogic.polygon;

import org.shapelogic.polygon.BBox;
import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.Polygon;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class BBoxTest extends TestCase {

    IPoint2D p1 = new CPointDouble(3.,4.);
    IPoint2D p2 = new CPointDouble(1.,2.);
    IPoint2D eMin = new CPointDouble(1.,2.);
    IPoint2D eMax = new CPointDouble(3.,4.);
  
    public void testPolygon() {
    	Polygon poly1 = new Polygon();
        assertEquals("point size should be 0", 0, poly1.getPoints().size());
        poly1.addLine(p1,p2);
        assertEquals("point size should be 2", 2, poly1.getPoints().size());
        assertTrue("Fist point should be p2 ", poly1.getPoints().iterator().next() == p2);
        poly1.calc();
        BBox bbox = poly1.getBBox();
        assertEquals("minVal wrong", bbox.minVal, eMin);
        assertEquals("maxVal wrong", bbox.maxVal, eMax);
    }

}
