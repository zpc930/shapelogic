package org.shapelogic.polygon;

import org.shapelogic.polygon.CircleInterval;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class CircleIntervalTest extends TestCase {

	public void testCreate() {
		CircleInterval ci = new CircleInterval();
		assertTrue(ci.empty);
	}

	public void testAdd1Points() {
		CircleInterval ciFalling = new CircleInterval();
		double angle = 1.;
		ciFalling.addFallingAngle(angle);
		assertFalse(ciFalling.empty);
		assertEquals(angle,ciFalling.start);
		assertEquals(angle,ciFalling.end);

		CircleInterval ciGrowing = new CircleInterval();
		ciGrowing.addFallingAngle(angle);
		assertFalse(ciGrowing.empty);
		assertEquals(angle,ciGrowing.start);
		assertEquals(angle,ciGrowing.end);

		CircleInterval ciClosest = new CircleInterval();
		ciClosest.addFallingAngle(angle);
		assertFalse(ciClosest.empty);
		assertEquals(angle,ciClosest.start);
		assertEquals(angle,ciClosest.end);
	}

	public void testAdd2Points() {
		CircleInterval ciFalling = new CircleInterval();
		double angle1 = 1.;
		double angle2 = 2.;
		ciFalling.addFallingAngle(angle1);
		ciFalling.addFallingAngle(angle2);
		assertFalse(ciFalling.empty);
//		assertEquals(angle2,ciFalling.start);
//		assertEquals(angle1,ciFalling.end);

		CircleInterval ciGrowing = new CircleInterval();
		ciGrowing.addGrowingAngle(angle1);
		ciGrowing.addGrowingAngle(angle2);
		assertFalse(ciGrowing.empty);
		assertEquals(angle1,ciGrowing.end);
		assertEquals(angle2,ciGrowing.start);

		CircleInterval ciClosest = new CircleInterval();
		ciClosest.addClosestAngle(angle1);
		ciClosest.addClosestAngle(angle2);
		assertFalse(ciClosest.empty);
		assertEquals(angle1,ciClosest.start);
		assertEquals(angle2,ciClosest.end);
		assertEquals(1.,ciClosest.intervalLength());

		CircleInterval ciClosest2 = new CircleInterval();
		ciClosest2.addClosestAngle(angle2);
		ciClosest2.addClosestAngle(angle1);
		assertFalse(ciClosest2.empty);
		assertEquals(angle1,ciClosest2.start);
		assertEquals(angle2,ciClosest2.end);
		assertEquals(1.,ciClosest2.intervalLength());
}
}
