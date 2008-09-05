package org.shapelogic.polygon;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Arrays;

import org.shapelogic.polygon.CPointInt;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class VectorTest extends TestCase {

	public void testEquals() throws Exception {
		double[] da1 = {1.,2.};
		double[] da1b = {1.,2.};
		assertTrue(Arrays.equals(da1, da1b));
	}
	
	public void testSort() throws Exception {
		double[] expected = {2.,3.};
		double[] unsorted = {3.,2.};
		Arrays.sort(unsorted);
		assertTrue(Arrays.equals(expected, unsorted));
	}

	public void testHashcode() throws Exception {
		double[] da1 = {1.,2.};
		double[] da1b = {1.,2.};
		assertNotSame(da1, da1b);
		assertTrue(da1.hashCode() != da1b.hashCode());
	}
	
	public void testCompare() throws Exception {
		Point p1 = new Point(2,3);
		Point p2 = new Point(2,3);
		assertEquals(p1,p2);
		assertEquals(p1.hashCode(), p2.hashCode());
		Point2D pD1 = new Point2D.Double(2.,3.);
		assertEquals(p1,pD1);
		assertEquals(pD1,p1);
	}

	
	public void testComparable() throws Exception {
		CPointInt p1 = new CPointInt(2,3);
		CPointInt p2 = new CPointInt(2,3);
		assertEquals(p1,p2);
		assertEquals(p1.hashCode(), p2.hashCode());
		assertEquals(0,p1.compareTo(p2));
		assertTrue(p1.minus(p2).isNull());
		assertTrue(p2.multiply(0).isNull());
	}

}
