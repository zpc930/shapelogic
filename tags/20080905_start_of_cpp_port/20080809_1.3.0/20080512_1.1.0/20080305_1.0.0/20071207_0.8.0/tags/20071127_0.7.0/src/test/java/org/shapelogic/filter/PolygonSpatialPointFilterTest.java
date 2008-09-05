package org.shapelogic.filter;

import java.util.Collection;

public class PolygonSpatialPointFilterTest extends AbstractFilterTests {
    
	public void setUp() {
		super.setUp();
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
