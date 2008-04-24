package org.shapelogic.color;

import org.shapelogic.color.ColorArea;
import org.shapelogic.color.ColorAreaFactory;

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
		assertEquals(10., grayArea.getPixelArea().getCenterPoint().getX());
		assertEquals(20., grayArea.getPixelArea().getCenterPoint().getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().minVal.getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().maxVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().maxVal.getY());
		assertEquals(100, grayArea.getMeanBlue());
	}

	public void test2Points() {
		ColorArea grayArea = new ColorArea(10,20,100);
		grayArea.putPixel(40, 30, 0);
		assertEquals(2, grayArea.getArea());
		assertEquals(25., grayArea.getPixelArea().getCenterPoint().getX());
		assertEquals(25., grayArea.getPixelArea().getCenterPoint().getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().minVal.getY());
		assertEquals(40., grayArea.getPixelArea().getBoundingBox().maxVal.getX());
		assertEquals(30., grayArea.getPixelArea().getBoundingBox().maxVal.getY());
		assertEquals(50, grayArea.getMeanBlue());
	}
	
	public void testFactory() {
		ColorAreaFactory colorAreaFactory = new ColorAreaFactory();
		ColorArea grayArea = (ColorArea) colorAreaFactory.makePixelArea(10,20,100);
		assertEquals(1, grayArea.getArea());
		assertEquals(10., grayArea.getPixelArea().getCenterPoint().getX());
		assertEquals(20., grayArea.getPixelArea().getCenterPoint().getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().minVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().minVal.getY());
		assertEquals(10., grayArea.getPixelArea().getBoundingBox().maxVal.getX());
		assertEquals(20., grayArea.getPixelArea().getBoundingBox().maxVal.getY());
		assertEquals(100, grayArea.getMeanBlue());
	}

}
