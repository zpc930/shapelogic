package org.shapelogic.filter;

import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.Polygon;

import junit.framework.TestCase;

/** Does setup of polygon to test filter in other test classes
 * 
 * @author Sami Badawi
 *
 */
public abstract class AbstractFilterTests extends TestCase {

	protected IPoint2D north = new CPointDouble(110.,100.);
	protected IPoint2D south = new CPointDouble(110.,120.);
	protected IPoint2D east = new CPointDouble(120.,110.);
	protected IPoint2D west = new CPointDouble(100.,110.);
	
	protected IPoint2D center = new CPointDouble(110.,110.);
	protected Polygon polygon;
	public static final String POLYGON = "polygon";

	public AbstractFilterTests() {
		super();
	}

	public AbstractFilterTests(String name) {
		super(name);
	}

	@Override
	public void setUp() {
		polygon = new Polygon();
	    assertEquals("point size should be 0", 0, polygon.getPoints().size());
	    polygon.addLine(north,south);
	    polygon.addLine(east,west);
		polygon.addLine(north, center);
	    polygon.calc();
	}
}