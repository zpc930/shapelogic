package org.shapelogic.color;

import org.shapelogic.color.GrayArea;
import org.shapelogic.color.GrayAreaFactory;

import junit.framework.TestCase;

/** Test GrayArea.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class GrayAreaTest extends TestCase {
	
	public void test1Points() {
		GrayArea grayArea = new GrayArea(10,20,100);
		assertEquals(1, grayArea.getArea());
		assertEquals(10., grayArea.getPixelArea().getCenterPoint().getX());
		assertEquals(20., grayArea.getPixelArea().getCenterPoint().getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().minVal.getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().maxVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().maxVal.getY());
		assertEquals(100, grayArea.getMeanGray());
	}

	public void test2Points() {
		GrayArea grayArea = new GrayArea(10,20,100);
		grayArea.putPixel(40, 30, 0);
		assertEquals(2, grayArea.getArea());
		assertEquals(25., grayArea.getPixelArea().getCenterPoint().getX());
		assertEquals(25., grayArea.getPixelArea().getCenterPoint().getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().minVal.getY());
		assertEquals(40., grayArea.getPixelArea().getBoundingBox().maxVal.getX());
		assertEquals(30., grayArea.getPixelArea().getBoundingBox().maxVal.getY());
		assertEquals(50, grayArea.getMeanGray());
	}

	public void testFactory() {
		GrayAreaFactory grayAreaFactory = new GrayAreaFactory();
		GrayArea grayArea = (GrayArea) grayAreaFactory.makePixelArea(10,20,100);
		assertEquals(1, grayArea.getArea());
		assertEquals(10., grayArea.getPixelArea().getCenterPoint().getX());
		assertEquals(20., grayArea.getPixelArea().getCenterPoint().getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().minVal.getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().maxVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().maxVal.getY());
		assertEquals(100, grayArea.getMeanGray());
	}

}
