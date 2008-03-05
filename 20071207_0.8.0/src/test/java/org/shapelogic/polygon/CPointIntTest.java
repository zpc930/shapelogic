package org.shapelogic.polygon;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.IPoint2D;

import static java.lang.Math.*;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class CPointIntTest extends TestCase {

	public void testConstructorAndArithmetic() throws Exception {
		IPoint2D p1 = new CPointInt(1,2);
		Point p2b = new Point(1,2);
		IPoint2D p2 = new CPointInt(p2b);
		assertEquals(p1,p2);
		assertEquals(p1.hashCode(),p2.hashCode());
		assertTrue(p1.minus(p2).isNull());
		assertTrue(p2.multiply(0).isNull());
	}

	public void testInteractionWithOtherClasses() throws Exception {
		IPoint2D p1 = new CPointInt(1,2);
		Point p2b = new Point(1,2);
		IPoint2D p2 = new CPointDouble(p2b);
		assertEquals(p1,p2);
		assertEquals(p1.hashCode(),p2.hashCode());
		assertTrue(p1.minus(p2).isNull());
		assertTrue(p2.multiply(0).isNull());
	}

	public void testSort() throws Exception {
		CPointInt p1 = new CPointInt(3,2);
		Point p2b = new Point(1,2);
		CPointInt p2 = new CPointInt(p2b);
		List<CPointInt> list = new ArrayList<CPointInt>();
		list.add(p1);
		list.add(p2);
		assertEquals(p1, list.get(0));
		assertEquals(p2, list.get(1));
		Collections.sort(list);
		assertEquals(p1, list.get(1));
		assertEquals(p2, list.get(0));
	}

	public void testTreeSet() throws Exception {
		TreeSet<CPointInt> set = new TreeSet<CPointInt>();
		CPointInt p0 = new CPointInt(1,3);
		CPointInt p1 = new CPointInt(1,26);
		CPointInt p2 = new CPointInt(2,2);
		CPointInt pBad2 = new CPointInt(2,2);
		CPointInt p3 = new CPointInt(1,27);
		CPointInt p4 = new CPointInt(2,28);
		CPointInt p5 = new CPointInt(26,28);
		CPointInt p6 = new CPointInt(27,27);
		set.add(p4);
		set.add(p0);
		assertEquals(2, set.size());
		set.add(p2);
		set.add(p3);
		assertEquals(4, set.size());
		set.add(pBad2);
		set.add(p6);
		set.add(p5);
		assertEquals(6, set.size());
		set.add(p1);
		assertEquals(7, set.size());
		set.add(p1);
		set.add(pBad2);
		set.add(p3);
		assertEquals(7, set.size());
		//Check that the compareTo is asymmetrical
		assertEquals(1,p5.compareTo(p2));
		assertEquals(-1,p2.compareTo(p5));
		for (IPoint2D point: set) {
			for (IPoint2D point2: set) {
				assertEquals(0,point.compareTo(point2) + point2.compareTo(point));
			}
		}
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
