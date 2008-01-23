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
public class PolygonTest extends TestCase {

    IPoint2D p1 = new CPointDouble(3.f,4.f);
    IPoint2D p2 = new CPointDouble(1.f,2.f);
    IPoint2D eMin = new CPointDouble(1.f,2.f);
    IPoint2D eMax = new CPointDouble(3.f,4.f);
  
    public void testPolygon() {
        Polygon polygon = new Polygon();
        polygon.addLine(p1,p2);
	    BBox b1 = polygon.getBBox();
	    assertEquals(b1.minVal, eMin);
	    assertEquals(b1.maxVal, eMax);
	    assertEquals(2,polygon.getPoints().size());
    }

    public void testPolygonClone() {
        Polygon polygon = new Polygon();
        Polygon polygonClone = (Polygon) polygon.clone();
        assertNotSame(polygon, polygonClone);
        assertEquals(polygon.getClass(), polygonClone.getClass());
    }
}
