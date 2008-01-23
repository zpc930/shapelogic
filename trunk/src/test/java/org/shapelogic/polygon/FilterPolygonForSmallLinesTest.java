package org.shapelogic.polygon;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class FilterPolygonForSmallLinesTest extends TestCase {
	
    IPoint2D p1 = new CPointInt(2,1);
    IPoint2D p2 = new CPointInt(1,2);
    IPoint2D p3 = new CPointInt(30,1);
  
	public void testFilterPolygon() {
        Polygon polygon = new Polygon();
        polygon.startMultiLine();
        polygon.addAfterEnd(p1);
        polygon.addAfterEnd(p2);
        polygon.addAfterEnd(p3);
        polygon.endMultiLine();
	    assertEquals(3,polygon.getPoints().size());
	    FilterPolygonForSmallLines filterPolygonForSmallLines = new FilterPolygonForSmallLines();
	    filterPolygonForSmallLines.setInput(polygon);
	    Polygon polygonOut = filterPolygonForSmallLines.getValue();
	    assertEquals(2,polygonOut.getPoints().size());
	    assertNotSame(polygon, polygonOut);
	    Polygon polygonImproved = polygon.improve();
	    assertEquals(2,polygonImproved.getPoints().size());
    }

    public void testFilterMultiLinePolygon() {
        MultiLinePolygon polygon = new MultiLinePolygon();
        polygon.startMultiLine();
        polygon.addAfterEnd(p1);
        polygon.addAfterEnd(p2);
        polygon.addAfterEnd(p3);
        polygon.endMultiLine();
	    assertEquals(3,polygon.getPoints().size());
	    FilterPolygonForSmallLines filterPolygonForSmallLines = new FilterPolygonForSmallLines();
	    filterPolygonForSmallLines.setInput(polygon);
	    Polygon polygonOut = filterPolygonForSmallLines.getValue();
	    assertEquals(2,polygonOut.getPoints().size());
	    assertNotSame(polygon, polygonOut);
	    Polygon polygonImproved = polygon.improve();
	    assertEquals(2,polygonImproved.getPoints().size());
    }
}
