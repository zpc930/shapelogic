package org.shapelogic.color;

import junit.framework.TestCase;

/** Test ColorEdgeArea.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorEdgeAreaTest extends TestCase {
	
	public void test1Points() {
		ColorEdgeArea grayArea = new ColorEdgeArea(10,20,100);
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
		ColorEdgeArea grayArea = new ColorEdgeArea(10,20,100);
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
		ColorAndVariance grayArea = (ColorAndVariance) colorAreaFactory.makePixelArea(10,20,100);
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
