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

    private IPoint2D north = new CPointDouble(110.,100.);
    private IPoint2D south = new CPointDouble(110.,120.);
    private IPoint2D east = new CPointDouble(120.,110.);
    private IPoint2D west = new CPointDouble(100.,110.);
    private IPoint2D center = new CPointDouble(110.,110.);
    private IPoint2D eMin = new CPointDouble(100.,100.);
    private IPoint2D eMax = new CPointDouble(120.,120.);
    
    public void testPolygon() {
    	Polygon poly1 = new Polygon();
        assertEquals("point size should be 0", 0, poly1.getPoints().size());
        poly1.addLine(north,south);
        assertEquals("point size should be 2", 2, poly1.getPoints().size());
        poly1.addLine(east,west);
        assertEquals("Fist point should be west ", poly1.getPoints().iterator().next(), west);
        poly1.calc();
        BBox bbox = poly1.getBBox();
        assertEquals("minVal wrong", bbox.minVal, eMin);
        assertEquals("maxVal wrong", bbox.maxVal, eMax);
    }
    
    public void testDiagonal() {
    	Polygon poly1 = new Polygon();
        poly1.addLine(north,south);
        poly1.addLine(east,west);
        poly1.calc();
        BBox bbox = poly1.getBBox();
        assertEquals("center wrong", eMin, bbox.getDiagonalVector(0.0));
        assertEquals("center wrong", center, bbox.getDiagonalVector(0.5));
        assertEquals("center wrong", eMax, bbox.getDiagonalVector(1.));
    }

}
