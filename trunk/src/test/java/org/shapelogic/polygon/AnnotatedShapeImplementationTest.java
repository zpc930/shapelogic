package org.shapelogic.polygon;

import org.shapelogic.imageprocessing.PointType;
import org.shapelogic.logic.RootTask;
import org.shapelogic.util.Constants;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class AnnotatedShapeImplementationTest extends TestCase {
	private static final String ANNOTATED_SHAPE = "annotatedShape";
	AnnotatedShape annotatedShape = new AnnotatedShapeImplementation();
	RootTask rootTask;
	
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		annotatedShape.setup();
		rootTask = RootTask.getInstance();
	}
	
	public void testBasicSetAndGet() {
		CPointInt p1 = new CPointInt(1,2); 
		CPointInt p2 = new CPointInt(2,2);
		Integer ONE = new Integer(1);
		annotatedShape.putAnnotation(p1, ONE);
		assertNull(annotatedShape.getShapesForAnnotation(Constants.ZERO));
		assertEquals(1,annotatedShape.getShapesForAnnotation(ONE).size());
		annotatedShape.putAnnotation(p2, 1);
		assertEquals(2,annotatedShape.getShapesForAnnotation(1).size());
	}
	
	public void testAnnotations() {
		CPointInt p1 = new CPointInt(1,2); 
		annotatedShape.putAnnotation(p1, PointType.END_POINT);
		assertEquals(1,annotatedShape.getShapesForAnnotation(PointType.END_POINT).size());
		assertEquals(1,annotatedShape.getMap().size());
		rootTask.setNamedValue(ANNOTATED_SHAPE, annotatedShape);
		assertEquals(annotatedShape,rootTask.findNamedValue(ANNOTATED_SHAPE));
		String annoationCount = "annotatedShape.getMap().size()";
		assertEquals(1,rootTask.findNamedValue(annoationCount));
		assertEquals(PointType.END_POINT, Enum.valueOf(PointType.class, "END_POINT"));
		String pointCount = "annotatedShape.getShapesForAnnotation(\"PointType.END_POINT\").size()";
		assertEquals(1,rootTask.findNamedValue(pointCount));
		String pointCountQuotes = "annotatedShape.getShapesForAnnotation('PointType.END_POINT').size()";
		assertEquals(1,rootTask.findNamedValue(pointCountQuotes));
	}
}
