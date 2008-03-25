package org.shapelogic.imageprocessing;

import junit.framework.TestCase;

/** Test ColorArea.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorAreaTest extends TestCase {
	
	public void test1Points() {
		ColorArea grayArea = new ColorArea(10,20,100);
		assertEquals(1, grayArea.getArea());
		assertEquals(10., grayArea.getCenterPoint().getX());
		assertEquals(20., grayArea.getCenterPoint().getY());
		assertEquals(10., grayArea.getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getBoundingBox().minVal.getY());
		assertEquals(10., grayArea.getBoundingBox().maxVal.getX());
		assertEquals(20., grayArea.getBoundingBox().maxVal.getY());
		assertEquals(100, grayArea.getMeanBlue());
	}

	public void test2Points() {
		ColorArea grayArea = new ColorArea(10,20,100);
		grayArea.addPoint(40, 30, 0);
		assertEquals(2, grayArea.getArea());
		assertEquals(25., grayArea.getCenterPoint().getX());
		assertEquals(25., grayArea.getCenterPoint().getY());
		assertEquals(10., grayArea.getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getBoundingBox().minVal.getY());
		assertEquals(40., grayArea.getBoundingBox().maxVal.getX());
		assertEquals(30., grayArea.getBoundingBox().maxVal.getY());
		assertEquals(50, grayArea.getMeanBlue());
	}
	
	public void testFactory() {
		ColorAreaFactory colorAreaFactory = new ColorAreaFactory();
		ColorArea grayArea = (ColorArea) colorAreaFactory.makePixelArea(10,20,100);
		assertEquals(1, grayArea.getArea());
		assertEquals(10., grayArea.getCenterPoint().getX());
		assertEquals(20., grayArea.getCenterPoint().getY());
		assertEquals(10., grayArea.getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getBoundingBox().minVal.getY());
		assertEquals(10., grayArea.getBoundingBox().maxVal.getX());
		assertEquals(20., grayArea.getBoundingBox().maxVal.getY());
		assertEquals(100, grayArea.getMeanBlue());
	}

}
