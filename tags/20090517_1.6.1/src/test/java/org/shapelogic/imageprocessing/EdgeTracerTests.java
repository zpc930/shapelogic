package org.shapelogic.imageprocessing;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.shapelogic.imageutil.IJImage;
import org.shapelogic.imageutil.SLImage;
import org.shapelogic.polygon.AnnotatedShape;
import org.shapelogic.polygon.GeometricShape2D;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.util.LineType;
import org.shapelogic.util.PointType;

/** Test of EdgeTracer.<br />
 * 
 * @author Sami Badawi
 *
 */
abstract public class EdgeTracerTests extends TestCase {
	final static String _dirURL = "./src/test/resources/images/particles";
	final static String _fileFormat = ".gif";
	protected Double boxPerimeter = 17.656854249492383; //
	protected Double iPerimeter = 54.;
	
	String filePath(String fileName) {
		return _dirURL + "/" + fileName + _fileFormat;
	}
	
	String filePath(String fileName, String fileFormat) {
		return _dirURL + "/" + fileName + fileFormat;
	}
	
	String filePath(String dir, String fileName, String fileFormat) {
		return dir + "/" + fileName + fileFormat;
	}
	
	abstract IEdgeTracer getInstance(SLImage image, int referenceColor, double maxDistance, boolean traceCloseToColor);
	
	public void testRedbox() {
		String filename = "redbox";
		SLImage image = new IJImage(filePath(filename,".png"));
		int foregroundColor = 0xff0000; 
		int foregroundColorClose = 0xfe0000; 
		assertNotNull(image);
		assertFalse(image.isEmpty());
		assertEquals(10,image.getWidth());
		assertEquals(10,image.getHeight());
		assertTrue(image.isRgb());
		assertEquals(-1, image.get(0, 0)); //Background unmasked
		assertEquals(0xffffff, image.get(0, 0) & 0xffffff); //Background
		assertEquals(foregroundColor, image.get(2, 2) & 0xffffff); //Foreground
		IEdgeTracer edgeTracer = getInstance(image,foregroundColorClose,10,true);
		Polygon cch = edgeTracer.autoOutline(5,2);
		//if 5,5 was used as start point a soft point would have been found
		assertEquals(boxPerimeter,cch.getPerimeter());
		assertEquals(4, cch.getAnnotatedShape().getShapesForAnnotation(PointType.HARD_CORNER).size());
		assertEquals(4, cch.getAnnotatedShape().getShapesForAnnotation(LineType.STRAIGHT).size());
		printAnnotaions(cch.getAnnotatedShape());
	}

	public void testRedboxInverse() {
		String filename = "redbox";
		SLImage image = new IJImage(filePath(filename,".png"));
		int foregroundColor = 0xff0000; 
		int backgroundColorClose = 0xfffeff; 
		assertNotNull(image);
		assertFalse(image.isEmpty());
		assertEquals(10,image.getWidth());
		assertEquals(10,image.getHeight());
		assertTrue(image.isRgb());
		assertEquals(-1, image.get(0, 0)); //Background unmasked
		assertEquals(0xffffff, image.get(0, 0) & 0xffffff); //Background
		assertEquals(foregroundColor, image.get(2, 2) & 0xffffff); //Foreground
		IEdgeTracer edgeTracer = getInstance(image,backgroundColorClose,10,false);
		Polygon cch = edgeTracer.autoOutline(5,2);
		//if 5,5 was used as start point a soft point would have been found
		assertEquals(boxPerimeter,cch.getPerimeter());
		assertEquals(4, cch.getAnnotatedShape().getShapesForAnnotation(PointType.HARD_CORNER).size());
		assertEquals(4, cch.getAnnotatedShape().getShapesForAnnotation(LineType.STRAIGHT).size());
		printAnnotaions(cch.getAnnotatedShape());
	}

	public void testBlackbox() {
		String filename = "blackbox";
		SLImage image = new IJImage(filePath(filename));
		int foregroundColor = 255; 
		assertNotNull(image);
		assertFalse(image.isEmpty());
		assertEquals(10,image.getWidth());
		assertEquals(10,image.getHeight());
		assertTrue(image.isGray());
		assertEquals(0, image.get(0, 0)); //Background
		assertEquals(foregroundColor, image.get(2, 2)); //Foreground
		IEdgeTracer edgeTracer = getInstance(image,foregroundColor,10,true);
		Polygon cch = edgeTracer.autoOutline(5,5);
		assertClose(boxPerimeter, cch.getPerimeter());
	}

	public void testI() {
		String filename = "I";
		SLImage image = new IJImage(filePath("./src/test/resources/images/smallThinLetters", filename, ".gif"));
		int foregroundColor = 255; 
		assertNotNull(image);
		assertFalse(image.isEmpty());
		assertEquals(30,image.getWidth());
		assertEquals(30,image.getHeight());
		assertTrue(image.isGray());
		assertEquals(0, image.get(0, 0)); //Background
		assertEquals(foregroundColor, image.get(14, 2)); //Foreground
		IEdgeTracer edgeTracer = getInstance(image,foregroundColor,10,true);
		Polygon cch = edgeTracer.autoOutline(14,2);
		assertEquals(iPerimeter,cch.getPerimeter());
	}

	public void printAnnotaions(AnnotatedShape annotatedShape) {
		System.out.println("Print annotations:");
		Map<Object, Set<GeometricShape2D>> map = annotatedShape.getMap();
		for (Entry<Object, Set<GeometricShape2D>> entry: map.entrySet())
			System.out.println(entry.getKey() +":\n" + entry.getValue());
	}
	
	public void testNegativeModulus() {
		assertEquals(-4, -4 % 8);
	}

    public void assertClose(Double expected, Double actual) {
        double absolute = 1;
        double difference = Math.abs(expected - actual);
        boolean far = absolute < difference;
        if (far)
            assertFalse("Expected: " + expected + " not close to actual: " + actual, true);
    }
}
