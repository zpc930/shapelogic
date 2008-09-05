package org.shapelogic.filter;

import java.util.Collection;

import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.Polygon;

import junit.framework.TestCase;

public class PolygonSpatialPointFilterTest extends TestCase {
    private IPoint2D north = new CPointDouble(110.,100.);
    private IPoint2D south = new CPointDouble(110.,120.);
    private IPoint2D east = new CPointDouble(120.,110.);
    private IPoint2D west = new CPointDouble(100.,110.);
    private IPoint2D center = new CPointDouble(110.,110.);
    private Polygon polygon;
    
    @Override
    public void setUp() {
    	polygon = new Polygon();
        assertEquals("point size should be 0", 0, polygon.getPoints().size());
        polygon.addLine(north,south);
        polygon.addLine(east,west);
    	polygon.addLine(north, center);
        polygon.calc();
    }
    
    public void testAboveFilter() {
		Collection<Object> col = polygon.filter("PointAboveFilter(0.4)");
		assertEquals(1,col.size());
    	assertEquals( col.iterator().next(), north);
		Collection<Object> withCenter = polygon.filter("PointAboveFilter(0.6)");
		assertEquals(4,withCenter.size());
    }

    public void testBelowFilter() {
		Collection<Object> col = polygon.filter("PointBelowFilter(0.6)");
		assertEquals(1,col.size());
    	assertEquals( col.iterator().next(), south);
		Collection<Object> withCenter = polygon.filter("PointBelowFilter(0.4)");
		assertEquals(4,withCenter.size());
    }

    public void testRightOfFilter() {
		Collection<Object> col = polygon.filter("PointRightOfFilter(0.6)");
		assertEquals(1,col.size());
    	assertEquals( col.iterator().next(), east);
		Collection<Object> withCenter = polygon.filter("PointRightOfFilter(0.4)");
		assertEquals(4,withCenter.size());
    }

    public void testLeftOfFilter() {
		Collection<Object> col = polygon.filter("PointLeftOfFilter(0.4)");
		assertEquals(1,col.size());
    	assertEquals( col.iterator().next(), west);
		Collection<Object> withCenter = polygon.filter("PointLeftOfFilter(0.6)");
		assertEquals(4,withCenter.size());
    }

}
